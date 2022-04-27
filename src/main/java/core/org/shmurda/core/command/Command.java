package core.org.shmurda.core.command;

import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;

@Getter
public abstract class Command implements ICommand {

    private final String[] names;
    private boolean adminOnly = false;
    private long cooldown = 0L;
    @Setter private long startTime;

    public Command(String[] names) {
        this.names = names;
    }

    public Command(String[] names, boolean adminOnly) {
        this.names = names;
        this.adminOnly = adminOnly;
    }

    public Command(String[] names, long cooldown) {
        this.names = names;
        this.cooldown = cooldown * 1000;
    }

    public boolean isOnCooldown() {
        return System.currentTimeMillis() <= startTime + cooldown;
    }

    public String getCooldown() {
        DecimalFormat format = new DecimalFormat("#.#");

        return format.format((startTime + cooldown - System.currentTimeMillis()) / 1000D) + "s";
    }

}
