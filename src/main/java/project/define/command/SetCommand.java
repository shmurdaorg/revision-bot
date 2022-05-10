package project.define.command;

import core.org.shmurda.core.Core;
import core.org.shmurda.core.command.Command;
import core.org.shmurda.core.command.event.CommandEvent;
import core.org.shmurda.core.embed.EmbedColour;
import net.dv8tion.jda.api.EmbedBuilder;
import project.Bot;

import java.time.Instant;

public class SetCommand extends Command {

    public SetCommand() {
        super(new String[]{"set"}, 1);
    }

    @Override
    public void handle(CommandEvent cmd) {
        if (cmd.getArgs().length <= 2) {
            cmd.replyMessage("**Usage: " + Core.getInstance().getCommandPrefix() + "set <key> <value ...>**");
            return;
        }

        String key = cmd.getArgs()[1].toLowerCase();
        StringBuilder builder = new StringBuilder();

        for (int i = 2; i < cmd.getArgs().length; i++) {
            builder.append(cmd.getArgs()[i]).append(" ");
        }

        String value = builder.toString().trim();

        Bot.getBot().getDefinitionHandler().define(key, value);

        cmd.replyEmbed(new EmbedBuilder()
                .setTitle("New definition.")
                .setColor(EmbedColour.SUCCESS.getColor())
                .addField(key, "Has value: " + value, false)
                .setTimestamp(Instant.now()));
    }
}
