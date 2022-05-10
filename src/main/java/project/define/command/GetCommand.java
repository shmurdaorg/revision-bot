package project.define.command;

import core.org.shmurda.core.Core;
import core.org.shmurda.core.command.Command;
import core.org.shmurda.core.command.event.CommandEvent;
import core.org.shmurda.core.embed.EmbedColour;
import net.dv8tion.jda.api.EmbedBuilder;
import project.Bot;

import java.time.Instant;

public class GetCommand extends Command {

    public GetCommand() {
        super(new String[]{"get"}, 1);
    }

    @Override
    public void handle(CommandEvent cmd) {
        if (cmd.getArgs().length != 2) {
            cmd.replyMessage("**Usage: " + Core.getInstance().getCommandPrefix() + "get <key>**");
            return;
        }

        String key = cmd.getArgs()[1].toLowerCase();
        String value = Bot.getBot().getDefinitionHandler().get(key);

        if (value == null) {
            cmd.replyEmbed(new EmbedBuilder()
                    .setTitle("Error: null")
                    .setColor(EmbedColour.ERROR.getColor())
                    .addField(key, "Has no assigned value, set one with the set command", false)
                    .setTimestamp(Instant.now()));
        } else {
            cmd.replyEmbed(new EmbedBuilder()
                    .setTitle(value)
                    .setColor(Core.getInstance().rgb())
                    .setFooter(key)
                    .setTimestamp(Instant.now()));
        }
    }
}
