# OS Synchronization Assignment

**Operating Systems | Java | Multithreading | Semaphores | Monitors**

A Java-based implementation and visualization of two classic Operating System synchronization problems: **Readers-Writers** and **Dining Philosophers**. The project uses Java threads to demonstrate synchronization and generates HTML simulations to visualize the execution events.

## 📌 Table of Contents

* [Overview](#overview)
* [Objectives](#objectives)
* [Problems Implemented](#problems-implemented)
* [Technologies Used](#technologies-used)
* [Project Structure](#project-structure)
* [How to Run](#how-to-run)
* [HTML Simulations](#html-simulations)
* [Author](#author)

## 📖 Overview

Synchronization is an important concept in Operating Systems that coordinates concurrent threads while accessing shared resources.

This project demonstrates synchronization using **semaphores and monitors**. Each Java program records events during execution and generates an HTML simulation to help visualize thread activities and resource access.

## 🎯 Objectives

* Understand thread synchronization and concurrency.
* Implement semaphore-based synchronization.
* Implement monitor-based synchronization.
* Demonstrate safe access to shared resources.
* Visualize program execution using HTML, CSS, and JavaScript.

## 💻 Problems Implemented

### 1. Readers-Writers Problem

Demonstrates coordination between readers and writers accessing shared data.

* **Semaphore implementation:** Uses semaphores to coordinate access.
* **Monitor implementation:** Uses synchronized coordination to manage access.

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

## 📂 Project Structure

```text
OS-Synchronization-Assignment/
├── generated_html/
│   ├── dining_philosophers_monitor.html
│   ├── dining_philosophers_semaphore.html
│   ├── readers_writers_monitor.html
│   └── readers_writers_semaphore.html
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
2. Open Eclipse IDE.
3. Import the project into Eclipse.
4. Open the required Java class in the `ostask2` package.
5. Run the class containing the `main()` method as a Java Application.
6. Check the console for the generated HTML file location.
7. Open the corresponding HTML file from `generated_html/` in a web browser.

## 🌐 HTML Simulations

| Simulation                          | HTML file                            |
| ----------------------------------- | ------------------------------------ |
| Readers-Writers using Semaphore     | `readers_writers_semaphore.html`     |
| Readers-Writers using Monitor       | `readers_writers_monitor.html`       |
| Dining Philosophers using Semaphore | `dining_philosophers_semaphore.html` |
| Dining Philosophers using Monitor   | `dining_philosophers_monitor.html`   |

The simulations display recorded execution events. The Dining Philosophers visualizations represent four philosophers and four forks around a circular table.

## 👩‍💻 Author

* **Name:** Deepa
* **USN:** NNM25IS506
* **Course:** Operating Systems
