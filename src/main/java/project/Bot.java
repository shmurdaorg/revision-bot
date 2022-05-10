package project;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import core.org.shmurda.core.Core;
import lombok.Getter;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import project.code.CodeCommand;
import project.define.DefinitionHandler;
import project.define.command.GetCommand;
import project.define.command.SetCommand;
import project.help.HelpCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bot {

    public static List<GatewayIntent> intents = new ArrayList<>(Arrays.asList(
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MESSAGE_REACTIONS
    ));

    @Getter
    private static Bot bot;

    @Getter
    private final MongoDatabase mongoDatabase;
    @Getter
    private final DefinitionHandler definitionHandler;

    public Bot(Core core) {
        bot = this;

        mongoDatabase = new MongoClient("127.0.0.1", 27017).getDatabase("revision");

        core.getBuilder().setStatus(OnlineStatus.ONLINE);
        core.getBuilder().setActivity(Activity.playing("-help"));
        core.getBuilder().disableCache(
                CacheFlag.CLIENT_STATUS, CacheFlag.ACTIVITY, CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE, CacheFlag.EMOTE, CacheFlag.ONLINE_STATUS
        );
        core.setCommandPrefix("-");

        core.registerCommands(new HelpCommand(), new GetCommand(), new SetCommand(), new CodeCommand());

        definitionHandler = new DefinitionHandler();

        core.finished();
    }

}
