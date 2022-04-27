package core.org.shmurda.core.command;

import core.org.shmurda.core.command.event.CommandEvent;

public interface ICommand {

    void handle(CommandEvent event);

}
