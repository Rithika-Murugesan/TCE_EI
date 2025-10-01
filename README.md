# TCE_EI – Java Design Patterns Exercises

This repository contains two Java projects demonstrating object-oriented programming and classic design patterns:

- **Exercise 1:** Smart Home System (multiple patterns, real-world simulation)
- **Exercise 2:** Mars Rover Console Application (command and composite patterns, OOP/SOLID)

---

## Exercise 1: Smart Home System

A simulation of a smart home controller, showcasing **six design patterns**:

- **Behavioral:** Observer, Command
- **Creational:** Factory Method, Singleton
- **Structural:** Proxy, Composite

**Features:**
- Device management (lights, thermostats, doors)
- Command-line interface for device control and automation
- Scheduling and automation triggers
- Robust error handling and logging

**Key Classes:**
- [`com.smarthome.app.App`](Exercise_1/smart-home-system/smart-home-system/src/main/java/com/smarthome/app/App.java)
- [`com.smarthome.hub.SmartHomeHub`](Exercise_1/smart-home-system/smart-home-system/src/main/java/com/smarthome/hub/SmartHomeHub.java)
- [`com.smarthome.factory.DeviceFactory`](Exercise_1/smart-home-system/smart-home-system/src/main/java/com/smarthome/factory/DeviceFactory.java)
- [`com.smarthome.proxy.DeviceProxy`](Exercise_1/smart-home-system/smart-home-system/src/main/java/com/smarthome/proxy/DeviceProxy.java)
- [`com.smarthome.composite.DeviceGroup`](Exercise_1/smart-home-system/smart-home-system/src/main/java/com/smarthome/composite/DeviceGroup.java)

**How to Run:**
1. Compile all Java files in `Exercise_1/smart-home-system/smart-home-system/src/main/java/`.
2. Run `com.smarthome.app.App`.
3. Use the console to interact (type `help` for commands).

See [Exercise_1/smart-home-system/smart-home-system/README.md](Exercise_1/smart-home-system/smart-home-system/README.md) for details.

---

## Exercise 2: Mars Rover Console Application

A console-based simulation of a Mars Rover, demonstrating:

- **Command Pattern:** Encapsulates rover actions as command objects.
- **Composite Pattern:** Grid and obstacles structure.
- **OOP/SOLID Principles:** Encapsulation, abstraction, inheritance, polymorphism, and all SOLID principles.

**Features:**
- Configurable grid and obstacles
- Rover movement with boundary and obstacle checks
- Command registry for input dispatch (no if/else chains)
- Logging and exception handling

**Key Classes:**
- [`com.rover.App`](Exercise_2/mars-rover-java/src/com/rover/App.java)
- [`com.rover.Rover`](Exercise_2/mars-rover-java/src/com/rover/Rover.java)
- [`com.rover.grid.Grid`](Exercise_2/mars-rover-java/src/com/rover/grid/Grid.java)
- [`com.rover.command.Command`](Exercise_2/mars-rover-java/src/com/rover/command/Command.java)
- [`com.rover.direction.Direction`](Exercise_2/mars-rover-java/src/com/rover/direction/Direction.java)

**How to Run:**
1. Compile all Java files in `Exercise_2/mars-rover-java/src/`.
2. Run `com.rover.App`.
3. Follow the console prompts to control the rover.

See [Exercise_2/mars-rover-java/README.md](Exercise_2/mars-rover-java/README.md) for details.

---

