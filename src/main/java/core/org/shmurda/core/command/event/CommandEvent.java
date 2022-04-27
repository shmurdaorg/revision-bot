package core.org.shmurda.core.command.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@AllArgsConstructor
@Getter
public class CommandEvent {

    private final MessageReceivedEvent event;
    private final User sender;
    private final String[] args;

    public void replyMessage(String string) {
        event.getChannel().sendMessage(string).queue();
    }

    public void replyEmbed(EmbedBuilder embed) {
        event.getChannel().sendMessageEmbeds(embed.build()).queue();
    }

}
