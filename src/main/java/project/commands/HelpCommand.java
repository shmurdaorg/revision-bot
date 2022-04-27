package project.commands;

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
                .setTitle("Help Menu")
                .setColor(Core.getInstance().rgb())
                .addField("help", "does some shit", true)
                .setTimestamp(Instant.now()));
    }
}
