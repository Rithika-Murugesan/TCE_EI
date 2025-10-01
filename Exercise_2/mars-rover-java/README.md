Mars Rover - Java Console Application
=====================================

Description
-----------
A console-based Mars Rover simulation that demonstrates OOP principles,
Command Pattern (behavioral), Composite-like Grid/Obstacle structure, and
SOLID design principles. The program avoids using if-else for command dispatch
by using a Command registry (map).

Features
--------
- Initialize grid size
- Place obstacles
- Start Rover at position with direction (N, E, S, W)
- Commands: M (move), L (turn left), R (turn right), P (print/report), Q (quit)
- Obstacle detection and boundary checks
- Logging and exceptions
- Each class in its own file under package `com.rover`

OOP Concepts Applied
------------------------
--Encapsulation:
Position, Rover, and Grid keep their internal state private with getters/setters.

--Abstraction:
Direction is an abstract class; concrete classes (North, East, etc.) implement movement logic.

--Inheritance:
Directions inherit from Direction.

--Polymorphism:
Commands (MoveCommand, LeftCommand, RightCommand) implement a common interface Command and can be executed interchangeably.

SOLID Principles
-------------------
--Single Responsibility Principle (SRP)

Rover handles rover movement.
Grid manages boundaries/obstacles.
Command classes encapsulate individual actions.

--Open/Closed Principle (OCP)

Adding a new direction (e.g., NorthEast) does not modify existing code, only extend Direction.
Adding a new command (e.g., ReportCommand) requires just a new class.

--Liskov Substitution Principle (LSP)

North, South, East, West can be used wherever a Direction is expected.
Interface Segregation Principle (ISP)
Commands implement a minimal Command interface (execute() only).
Dependency Inversion Principle (DIP)
Rover depends on abstractions (Command, Direction), not concrete implementations.

Design Patterns
----------------
--Command Pattern
Each action (M, L, R) is encapsulated in a separate Command class.
CommandRegistry maps user input ('M', 'L', 'R') to the correct command object.

--Composite Pattern
Grid contains both empty cells and Obstacle objects.
The rover queries the grid instead of handling obstacle detection itself.

How to build
------------
Java 11+ recommended.
 
Compilation Process:
---------------------

1.Go back to the project root:
```bash
cd "C:\TCE_EI\Exercise_2\mars-rover-java (1)"
```

2.Run this compile command:
```bash
javac -d out src/com/rover/*.java src/com/rover/command/*.java src/com/rover/direction/*.java src/com/rover/grid/*.java
```
The above cmd will create an out folder with all .class files in the correct package structure.

3.Run the Program
```bash
java -cp out com.rover.App
```

