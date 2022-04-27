package core.me.tomraylord.log5j;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum LogLevel {

    INFO("[INFO %s] ", false),
    WARN("[WARN %s] ", false),
    ERROR("[ERROR %s] ", false),
    SEVERE("[SEVERE %s] ", true);

    private final String prefix;
    private final boolean stop;

}
