# OS Synchronization Assignment

**Operating Systems | Java | Multithreading | Semaphores | Monitors**

A Java-based implementation and visualization of two classic Operating System synchronization problems: **Readers-Writers** and **Dining Philosophers**. The project uses Java threads to demonstrate synchronization and generates HTML simulations to visualize execution events.

## 📌 Table of Contents

* [Overview](#overview)
* [Objectives](#objectives)
* [Problems Implemented](#problems-implemented)
* [Technologies Used](#technologies-used)
* [Project Structure](#project-structure)
* [How to Run](#how-to-run)
* [HTML Simulations](#html-simulations)
* [Screenshots](#screenshots)
* [Author](#author)

## 📖 Overview

Synchronization is an important concept in Operating Systems that coordinates concurrent threads while they access shared resources.

This project demonstrates synchronization using **semaphores and monitors**. The Java programs record execution events and generate HTML simulations to help visualize thread activities and resource access.

## 🎯 Objectives

* Understand thread synchronization and concurrency.
* Implement semaphore-based synchronization.
* Implement monitor-based synchronization.
* Demonstrate coordination when accessing shared resources.
* Visualize program execution using HTML, CSS, and JavaScript.

## 💻 Problems Implemented

### 1. Readers-Writers Problem

Demonstrates coordination between readers and writers accessing shared data.

* **Semaphore implementation:** Uses semaphores to coordinate access.
* **Monitor implementation:** Uses monitor-based coordination to manage access.

### 2. Dining Philosophers Problem

Demonstrates resource sharing among four philosophers competing for four forks.

* **Semaphore implementation:** Coordinates fork access using semaphores.
* **Monitor implementation:** Coordinates philosopher states and fork usage.

## 🛠️ Technologies Used

* Java
* Java Threads
* Semaphores and Monitors
* HTML
* CSS
* JavaScript
* Eclipse IDE
* Git and GitHub

## 📂 Project Structure

```text
OS-Synchronization-Assignment/
├── generated_html/
│   ├── dining_philosophers_monitor.html
│   ├── dining_philosophers_semaphore.html
│   ├── readers_writers_monitor.html
│   └── readers_writers_semaphore.html
├── screenshots/
├── references/
├── ai_prompts/
├── src/
│   ├── module-info.java
│   └── ostask2/
│       ├── DiningPhilosophersMonitor.java
│       ├── DiningPhilosophersSemaphore.java
│       ├── HtmlSimulation.java
│       ├── package-info.java
│       ├── ReadersWritersMonitor.java
│       ├── ReadersWritersSemaphore.java
│       ├── SimEvent.java
│       └── SimulationLog.java
└── README.md
```

## ▶️ How to Run

1. Clone or download this repository.
2. Install a compatible Java Development Kit (JDK).
3. Open Eclipse IDE and import the project into your workspace.
4. Open the `ostask2` package inside `src`.
5. Run the required Java class containing the `main()` method as a Java Application.
6. Check the Eclipse Console for execution messages and the generated HTML file location.
7. Open the corresponding HTML file in the `generated_html/` folder using a web browser.

**Note:** Run each Java program first if it generates or updates its HTML simulation.

## 🌐 HTML Simulations

Open the simulation files directly from the repository:

| Simulation                          | HTML file                                                            |
| ----------------------------------- | -------------------------------------------------------------------- |
| Readers-Writers using Semaphore     | [Open simulation](generated_html/readers_writers_semaphore.html)     |
| Readers-Writers using Monitor       | [Open simulation](generated_html/readers_writers_monitor.html)       |
| Dining Philosophers using Semaphore | [Open simulation](generated_html/dining_philosophers_semaphore.html) |
| Dining Philosophers using Monitor   | [Open simulation](generated_html/dining_philosophers_monitor.html)   |

The simulations display recorded execution events. The Dining Philosophers visualizations represent four philosophers and four forks around a circular table.

## 📸 Screenshots

Screenshots of the project and its simulations are available in the [`screenshots/`](screenshots/) folder.

## 📚 References and AI Assistance

* [`references/`](references/) — references and learning resources used for the assignment.
* [`ai_prompts/`](ai_prompts/) — AI prompt log documenting assistance used during development, where applicable.

## 👩‍💻 Author

* **Name:** Deepa
* **USN:** NNM25IS506
* **Department:** Information Science and Engineering
* **Institution:** NMAM Institute of Technology, Nitte
* **Course:** Operating Systems

## 🔗 Repository

[View OS Synchronization Assignment on GitHub](https://github.com/deepa435/OS-Synchronization-Assignment)
