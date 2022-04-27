package core.org.shmurda.core.command;

import core.me.tomraylord.log5j.LogLevel;
import core.org.shmurda.core.Core;
import core.org.shmurda.core.command.event.CommandEvent;
import core.org.shmurda.core.embed.EmbedColour;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager implements EventListener {

    private final Core core;
    private static List<Command> commands;

    public CommandManager(Core core) {
        this.core = core;

        commands = new ArrayList<>();
        core.getBuilder().addEventListeners(this);
        core.getLogger().log(LogLevel.INFO, "Starting Command Manager...");
    }

    public void addCommand(Command command) {
        if (getCommandByNames(command.getNames()) != null) {
            throw new RuntimeException("Tried registering a command that already has that name!");
        } else {
            commands.add(command);
            core.getLogger().log(LogLevel.INFO, "Registered command: "
                    + Arrays.toString(command.getNames()).replace('[', ' ').replace(']', ' ').trim());
        }
    }

    public void addCommands(Command... commands1) {
        for (Command cmd : commands1) {
            addCommand(cmd);
        }
    }

    public Command getCommandByName(String label) {
        for (Command command : commands) {
            if (Arrays.stream(command.getNames()).anyMatch(name -> name.equalsIgnoreCase(label))) {
                return command;
            }
        }

        return null;
    }

    public Command getCommandByNames(String[] labels) {
        for (String label : labels) {
            for (Command command : commands) {
                if (Arrays.stream(command.getNames()).anyMatch(name -> name.equalsIgnoreCase(label))) {
                    return command;
                }
            }
        }

        return null;
    }

    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {
        MessageReceivedEvent event;

        if (genericEvent instanceof MessageReceivedEvent) {
            event = (MessageReceivedEvent) genericEvent;
        } else {
            return;
        }

        String[] args = event.getMessage().getContentRaw().split(" ");

        if (!args[0].startsWith(core.getCommandPrefix())) {
            return;
        }

        String label = args[0].replace(core.getCommandPrefix(), "");

        Command cmd = getCommandByName(label);

        if (cmd == null) {
            return;
        }

        if (cmd.isOnCooldown()) {
            core.getLogger().log(LogLevel.INFO, event.getAuthor().getAsTag() + " tried " + cmd.getNames()[0] + " but it is on cooldown. (" + event.getMessage().getContentRaw() + ")");

            event.getChannel().sendMessageEmbeds(new EmbedBuilder()
                            .setDescription("Command is on cooldown for `" + cmd.getCooldown() + "`.")
                            .setColor(EmbedColour.ERROR.getColor())
                            .build()).queue();
            return;
        }

        CommandEvent cmdEvent = new CommandEvent(event, event.getAuthor(), args);
        cmd.handle(cmdEvent);
        cmd.setStartTime(System.currentTimeMillis());

        core.getLogger().log(LogLevel.INFO, event.getAuthor().getAsTag() + " has issued command " + cmd.getNames()[0] + ". (" + event.getMessage().getContentRaw() + ")");
    }
}
