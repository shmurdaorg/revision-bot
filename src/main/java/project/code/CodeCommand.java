package project.code;

import core.org.shmurda.core.Core;
import core.org.shmurda.core.command.Command;
import core.org.shmurda.core.command.event.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.time.Instant;

public class CodeCommand extends Command {

    public CodeCommand() {
        super(new String[]{"code"}, 5);
    }

    @Override
    public void handle(CommandEvent cmd) {
        cmd.replyEmbed(new EmbedBuilder()
                .setTitle("Bot code in src/main/java/")
                .setColor(Core.getInstance().rgb())
                .addField("GitHub Repository", "https://github.com/shmurdaorg/revision-bot", false)
                .setTimestamp(Instant.now()));
    }
}
