package core.org.shmurda.core;

import core.me.tomraylord.log5j.LogLevel;
import core.me.tomraylord.log5j.Logger;
import core.org.shmurda.core.command.Command;
import core.org.shmurda.core.command.CommandManager;
import core.org.shmurda.core.util.ColourUtil;
import core.org.shmurda.core.util.TokenUtil;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Core {

    private static Optional<Core> instance; // We have this for ease of access, not just for us, but for
                                            // other developers as well. see Core#getInstance()
    @Getter
    private final Logger logger = new Logger();
    @Getter @Setter
    private String botToken = null;
    @Getter
    private DefaultShardManagerBuilder builder;
    @Getter
    private ShardManager shardManager;
    @Getter @Setter
    private String commandPrefix = "."; // Set this to . by default
    @Getter
    private CommandManager commandManager;
    @Getter
    private long start;

    public Core() {
        new Core(null);
    }
    
    public Core(String token) {
        new Core(token, new ArrayList<>(Arrays.asList(
                /* Default values if you don't want to pass some intents in start. */
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_EMOJIS
        )));
    }

    public Core(String token, List<GatewayIntent> intents) {
        instance = Optional.of(this);
        start = System.currentTimeMillis();

        if (token == null) {
            try {
                TokenUtil.loadTokenFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            botToken = token;
        }

        logger.log(LogLevel.INFO, "Token " + (getBotToken() == null ? "is null." : "isn't null."));
        logger.log(LogLevel.INFO, "----------==========----------");

        builder = DefaultShardManagerBuilder.createDefault(getBotToken(), intents);
        commandManager = new CommandManager(this);
    }

    public void finished() {
        logger.log(LogLevel.INFO, "----------==========----------");

        try {
            shardManager = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        Thread console = new Thread(() -> {
            Scanner s = new Scanner(System.in);

            while (s.hasNextLine()) {
                process(s.nextLine());
            }
        });

        console.setDaemon(true);
        console.setName("Console");
        console.start();

        logger.log(LogLevel.INFO, "Finished, took " + (System.currentTimeMillis() - start) / 1000D + "s.");
        logger.log(LogLevel.INFO, "Console commands: help, stop");
    }

    public void registerCommands(Command... commands) {
        commandManager.addCommands(commands);
    }

    public void registerCommand(Command command) {
        commandManager.addCommand(command);
    }

    public Color rgb() {
        return ColourUtil.nextColour();
    }

    public void process(String s) {
        logger.log(LogLevel.INFO, "Console ran \"" + s + "\".");

        switch (s) {
            case "stop":
                logger.log(LogLevel.INFO, "Stopping...");
                shardManager.shutdown();
                logger.log(LogLevel.INFO, "Stopped, goodbye.");
                System.exit(0);
                break;
            case "help":
                logger.log(LogLevel.INFO, "Commands:\nhelp - lists all commands.\nstop - stops the server.");
                break;
            default:
                logger.log(LogLevel.INFO, "Unknown command, use \"help\" for a list of commands.");
        }
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
