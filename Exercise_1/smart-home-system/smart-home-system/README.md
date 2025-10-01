# Smart Home System - Java (Design Patterns Demonstration)

## Overview
This project is a **Smart Home System** simulation implemented in **Java**. It demonstrates **six design patterns** across multiple use-cases:
- **Behavioral:** Observer (system → devices notifications), Command (user commands & scheduling)
- **Creational:** Factory Method (device creation), Singleton (central Hub)
- **Structural:** Proxy (controlled device access), Composite (groups of devices)

The project meets the exercise constraints: modular code (each class in its own file), strong OOP design, logging, defensive programming, transient error handling, scheduling, dynamic device management, and sample inputs for manual testing.

## What you get in this ZIP
- Complete Java source under `src/main/java/com/smarthome/...`
- `README.md` (this file)
- `system-diagram.png` — a simple visual diagram of the architecture
- `run-instructions.txt` — step-by-step commands to compile & run locally
- `git-upload-steps.txt` — exact `git` commands to create a GitHub repo and push this project
- `smart-home-system.zip` — this archive (already produced)

> **Important:** I cannot create or push a GitHub repository on your behalf. I prepared the project and the exact `git` commands (see `git-upload-steps.txt`) so you can push it to GitHub and share that link (as required).

---

## Quick Run (Java 11+)
1. Unzip `smart-home-system.zip` and navigate into the folder.
2. Compile:
```bash
javac -d out $(find src/main/java -name "*.java")
```
3. Run:
```bash
java -cp out com.smarthome.app.App
```
4. Interact via console. Type `help` to see commands; type `exit` to stop the program.

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
3. **No hard-coded `while(true)`**: console input handled by an `ExecutorService` reading lines from `Scanner` while relying on `Scanner.hasNextLine()` so the loop condition isn't a blind `true` constant; graceful shutdown uses a shutdown hook.
4. **Concurrency**: Uses `ScheduledExecutorService` for scheduled tasks and retries; uses concurrent-safe maps for device registry.
5. **Logging & errors**: `LoggerUtil` wraps `java.util.logging` with consistent format; exceptions are caught and logged with friendly messages; input commands are validated and errors returned to the user.
6. **Extensibility**: Adding new device types only requires a new `Device` subclass and a `DeviceFactory` entry — minimal impact to the rest of the system.

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

## Git upload steps
See `git-upload-steps.txt` included in the archive for exact steps to create a GitHub repository and push this project (you only need your GitHub account & `git` configured).

---

## Notes & Limitations
- This is a simulation — interactions with real hardware would require specific device drivers / network protocols. The design intentionally models extension points where real device drivers could be plugged in.
- The scheduling uses system clock (`LocalDateTime`) and schedules the nearest occurrence of the supplied `HH:mm` time.
- For interviews: prepare to explain tradeoffs, where you'd add persistence (e.g., save schedules/triggers to a DB), and how you'd secure device access (authentication, TLS) in a real deployment.

---
Good luck — unzip and start exploring! If you want, I can also:
- produce a ready-to-push GitHub Actions CI workflow (mvn/gradle) or
- convert this to a Maven project with a `pom.xml`.
