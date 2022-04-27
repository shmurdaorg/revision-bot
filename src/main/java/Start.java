import core.org.shmurda.core.Core;
import project.Bot;

public class Start {

    public static void main(String[] args) {
        Core core;

        if (args.length == 0) {
            core = new Core(null, Bot.intents);
        } else {
            core = new Core(args[0], Bot.intents);
        }

        Bot bot = new Bot(core);
    }

}
