package core.me.tomraylord.log5j;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum LogLevel {

    INFO("[INFO] ", false),
    WARN("[WARN] ", false),
    ERROR("[ERROR] ", false),
    SEVERE("[SEVERE] ", true);

    private final String prefix;
    private final boolean stop;

}
