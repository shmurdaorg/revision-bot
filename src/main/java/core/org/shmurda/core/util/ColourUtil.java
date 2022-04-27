package core.org.shmurda.core.util;

import lombok.experimental.UtilityClass;

import java.awt.*;

@UtilityClass
public class ColourUtil {

    private Float lastHue = 0F;

    public Color nextColour() {
        lastHue = lastHue + 0.05F;
        return Color.getHSBColor(lastHue, 1F, 1F);
    }

}
