package core.org.shmurda.core.embed;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@AllArgsConstructor
@Getter
public enum EmbedColour {

    SUCCESS(Color.GREEN),
    ERROR(Color.RED),
    NONE(Color.DARK_GRAY);

    private final Color color;

}
