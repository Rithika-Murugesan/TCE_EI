package com.rover.command;

import com.rover.Rover;

public interface Command {
    void execute(Rover rover) throws Exception;
}
