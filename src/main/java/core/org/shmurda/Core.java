package core.org.shmurda;

import core.me.tomraylord.log5j.LogLevel;
import core.me.tomraylord.log5j.Logger;
import core.org.shmurda.util.TokenUtil;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Core {

    private static Optional<Core> instance; // We have this for ease of access, not just for us, but for
                                            // other developers as well. see Core#getInstance()
    @Getter
    private final Logger logger = new Logger();
    @Getter @Setter
    protected String botToken;
    @Getter
    private DefaultShardManagerBuilder builder;
    @Getter @Setter
    private String commandPrefix = "."; // Set this to . by default

    public Core(String token) {
        new Core(token, new ArrayList<>(Arrays.asList(
                /* Default values if you don't want to pass some intents in start. */
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_EMOJIS
        )));
    }

    public Core(String token, List<GatewayIntent> intents) {
        instance = Optional.of(this);

        if (token == null) {
            try {
                TokenUtil.loadTokenFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            botToken = token;
        }

        builder = DefaultShardManagerBuilder.create(botToken, intents);
    }

    public void out(LogLevel level, String message) {
        logger.log(level, message);
    }

    public static Core getInstance() {
        return instance.orElseThrow(
                () -> new IllegalStateException("Core instance is null.")
        );
    }

}
