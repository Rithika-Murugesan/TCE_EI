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

How to build
------------
Java 11+ recommended.

Compile:
  javac -d out src/com/rover/*.java

Run:
  java -cp out com.rover.App

Alternatively, import the src folder into your IDE (IntelliJ/Eclipse) as a Java project.
