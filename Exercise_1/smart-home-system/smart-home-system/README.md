# Smart Home System - Java (Design Patterns Demonstration)

## Overview
This project is a **Smart Home System** simulation implemented in **Java**. It demonstrates **six design patterns** across multiple use-cases:

## Design Patterns Demonstrated

| Pattern Type   | Pattern Name     | Where/How Used                                               |
|----------------|------------------|--------------------------------------------------------------|
| Behavioral     | Observer         | Devices observe hub events and update state accordingly      |
| Behavioral     | Command          | Device actions encapsulated as command objects               |
| Creational     | Factory Method   | `DeviceFactory` creates devices by type                      |
| Creational     | Singleton        | `SmartHomeHub` is a singleton central controller             |
| Structural     | Proxy            | `DeviceProxy` controls access to real devices                |
| Structural     | Composite        | `DeviceGroup` allows group operations on devices             |

---

## Architecture

- **app/**: Entry point (`App.java`) and CLI logic.
- **command/**: Command pattern for device actions.
- **composite/**: Composite pattern for device groups.
- **exceptions/**: Custom exceptions for robust error handling.
- **factory/**: Factory for device creation.
- **hub/**: Central hub, automation triggers, and scheduling.
- **model/**: Device abstractions and implementations.
- **observer/**: Observer pattern interfaces and events.
- **proxy/**: Proxy for device access control.
- **util/**: Logging and validation utilities.

See `System-diagram.png` for a visual overview.

---

## Quick Run (Java 11+)

1. Create the output folder:
```bash
mkdir ../../../../out
```
2. Compile:
```bash
Get-ChildItem -Recurse -Filter *.java | ForEach-Object {
    Write-Host "Compiling $($_.FullName)"
    javac -d ../../../../out $_.FullName
}
```
3. Run:
```bash
java -cp ../../../../out com.smarthome.app.SmartHomeApp
```
3. Interact via console. Type `help` to see commands; type `exit` to stop the program.

---

## Design Decisions & Walkthrough (how to present in an interview)
1. **Packages & structure**: clear package separation:
   - `model` – device abstractions and concrete devices
   - `factory` – `DeviceFactory`
   - `hub` – `SmartHomeHub` (Singleton), scheduler & trigger management
   - `command` – Command pattern implementations and invoker
   - `observer` – `Subject` & `Observer` interfaces and `Event`
   - `proxy` – `DeviceProxy` for transient-errors + access control + retry/backoff
   - `composite` – `DeviceGroup` allowing group operations (Composite pattern)
   - `util` – logging, validation utilities
   - `exceptions` – custom typed exceptions
2. **Resiliency**: `DeviceProxy` simulates transient errors and retries (exponential backoff) to demonstrate transient error handling logic.
3. **Concurrency**: Uses `ScheduledExecutorService` for scheduled tasks and retries; uses concurrent-safe maps for device registry.
4. **Logging & errors**: `LoggerUtil` wraps `java.util.logging` with consistent format; exceptions are caught and logged with friendly messages; input commands are validated and errors returned to the user.
5. **Extensibility**: Adding new device types only requires a new `Device` subclass and a `DeviceFactory` entry — minimal impact to the rest of the system.

---

## Files of interest to highlight during walkthrough
- `com.smarthome.app.App` — program entry, sample data, input loop
- `com.smarthome.hub.SmartHomeHub` — central manager (Singleton), Subject implementation, manages schedules & triggers
- `com.smarthome.factory.DeviceFactory` — Factory Method implementation
- `com.smarthome.proxy.DeviceProxy` — Proxy with retry/backoff
- `com.smarthome.command.*` — Commands and `CommandInvoker`
- `com.smarthome.composite.DeviceGroup` — Compound device example
- `com.smarthome.observer.*` — Observer interfaces and `Event`

---

## Example commands (type in console)
- `status` — prints status of all devices
- `turnOn(1)` / `turnOff(1)` — control a device by id
- `setTemp(2, 75)` — set thermostat temperature
- `setSchedule(1, 23:30, turnOff(1))` — schedule a command at HH:mm (today or tomorrow)
- `addTrigger temperature > 75 turnOff(1)` — add an automation trigger
- `addDevice light` — dynamically add a light (factory used)
- `removeDevice 4` — remove device by id
- `help` — show help
- `exit` — graceful shutdown

---



