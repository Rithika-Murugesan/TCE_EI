package com.rover;

import com.rover.command.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Registry maps input characters to Command suppliers.
 * Avoids if/else dispatching.
 */
public final class CommandRegistry {
    private final Map<Character, Supplier<Command>> registry;

    public CommandRegistry() {
        Map<Character, Supplier<Command>> m = new HashMap<>();
        m.put('M', MoveCommand::new);
        m.put('L', LeftCommand::new);
        m.put('R', RightCommand::new);
        m.put('P', ReportCommand::new); // print report
        registry = Collections.unmodifiableMap(m);
    }

    public Supplier<Command> get(char c) {
        return registry.get(Character.toUpperCase(c));
    }
}
