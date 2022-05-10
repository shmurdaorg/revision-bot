package project.help;

import core.org.shmurda.core.Core;
import core.org.shmurda.core.command.Command;
import core.org.shmurda.core.command.event.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.time.Instant;

public class HelpCommand extends Command {

    public HelpCommand() {
        super( new String[]{"help", "?"} , 5);
    }

    @Override
    public void handle(CommandEvent event) {
        if (event.getEvent().getAuthor().isBot()) {
            return;
        }

        event.replyEmbed(new EmbedBuilder()
                .setTitle("Page #1")
                .setColor(Core.getInstance().rgb())
                .addField(Core.getInstance().getCommandPrefix(), "is the current command prefix", false)
                .addField("help", "display help menu (cooldown: 5s)", false)
                .setTimestamp(Instant.now()));

        event.replyEmbed(new EmbedBuilder()
                .setTitle("Page #2")
                .setColor(Core.getInstance().rgb())
                .addField("set", "set the value of a key (cooldown: 1s)", false)
                .addField("get", "get the value of a key (cooldown: 1s)", false)
                .setTimestamp(Instant.now()));

        event.replyEmbed(new EmbedBuilder()
                .setTitle("Page #3")
                .setColor(Core.getInstance().rgb())
                .addField("code", "github repository for the code (cooldown: 5s)", false)
                .setTimestamp(Instant.now()));
    }
}
