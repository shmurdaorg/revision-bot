package project;

import core.org.shmurda.Core;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bot {

    /*
    * This is an example class.
    */

    public static List<GatewayIntent> intents = new ArrayList<>(Arrays.asList(
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MESSAGE_REACTIONS
    ));

    public Bot(Core core) {
        core.getBuilder().setStatus(OnlineStatus.ONLINE);
        core.getBuilder().setActivity(Activity.playing("!help"));
        core.setCommandPrefix("!");
    }

}
