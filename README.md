# 🚦 Smart Traffic Management & Signal Optimization

<div align="center">

![Platform](https://img.shields.io/badge/Platform-Android-brightgreen?style=for-the-badge&logo=android)
![Status](https://img.shields.io/badge/Status-Active-success?style=for-the-badge)
![Location](https://img.shields.io/badge/Deployed-Bhopal%2C%20MP-blue?style=for-the-badge&logo=googlemaps)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**An intelligent, real-time mobile application that dynamically monitors and optimizes traffic signal timing across multiple lanes — reducing urban congestion through data-driven automation.**

[Features](#-features) • [Screenshots](#-app-screenshots) • [Architecture](#-system-architecture) • [Getting Started](#-getting-started) • [How It Works](#-how-it-works) • [Analytics](#-analytics--insights) • [Contributing](#-contributing)

</div>

---

## 📌 Table of Contents

1. [Project Overview](#-project-overview)
2. [Features](#-features)
3. [App Screenshots](#-app-screenshots)
4. [System Architecture](#-system-architecture)
5. [Tech Stack](#-tech-stack)
6. [Getting Started](#-getting-started)
7. [How It Works](#-how-it-works)
8. [Dashboard & Signal Control](#-dashboard--signal-control)
9. [Map View](#-map-view)
10. [Analytics & Insights](#-analytics--insights)
11. [Project Structure](#-project-structure)
12. [Contributing](#-contributing)
13. [License](#-license)

---

## 🧭 Project Overview

Urban traffic congestion is a growing challenge in cities like Bhopal, Madhya Pradesh, where intersections frequently experience irregular signal cycles that do not respond to actual vehicle density. This project proposes a mobile-first solution that brings intelligent, adaptive signal management directly to traffic operators' hands.

**Smart Traffic Management & Signal Optimization** is an Android application that simulates and controls multi-lane traffic intersections in real time. Operators can monitor per-lane vehicle counts, signal phases, and congestion levels — and intervene manually when needed. The built-in analytics engine visualizes traffic trends across time, enabling informed infrastructure decisions.

> 🗺️ **Pilot Deployment Location:** Bhopal, Madhya Pradesh, India

---

## ✨ Features

### 🔴🟡🟢 Signal Control
- Per-lane signal phase display — **GO**, **STP (Stop)**, and **SLW (Slow)**
- Live countdown timers for each lane's active phase
- One-tap **Force Green** override per lane
- **Emergency mode** trigger per lane for priority vehicles

### 📊 Real-Time Dashboard
- Instant summary — total active lanes, total vehicle count, and congestion level
- Per-lane breakdown: vehicle count, phase status, and congestion tag (Low / Medium / High)
- Color-coded progress bars for quick visual scan
- **Add Lane** and **Stop Simulation** controls

### 🗺️ Interactive Map View
- Live map centered on Bhopal, MP
- Pin-marked intersection locations across the city
- Helps operators identify congested zones geographically

### 📈 Analytics
- **Bar chart** — vehicle count per lane (live and demo data)
- **Line chart** — traffic trend over simulation time steps
- Demo data preloaded; live data populates upon running a simulation
- Identifies peak traffic periods and low-traffic windows

### ⚙️ Simulation Engine
- Configurable simulation with adjustable parameters via Settings
- Supports adding multiple lanes dynamically
- Designed for both demo walkthroughs and real deployment scenarios

---

## 📱 App Screenshots

| Dashboard | Map View | Analytics |
|:---------:|:--------:|:---------:|
| ![Dashboard](screenshots/dashboard.jpg) | ![Map](screenshots/map.jpg) | ![Analytics](screenshots/analytics.jpg) |
| Live signal status for 4 lanes | Bhopal city intersection map | Vehicle count & traffic trend charts |

> **Note:** Place your screenshots inside a `/screenshots` folder in the repository root.

---

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────┐
│                 Mobile Application               │
│                  (Android App)                   │
│                                                  │
│  ┌──────────┐  ┌──────────┐  ┌───────────────┐  │
│  │Dashboard │  │  Map     │  │  Analytics    │  │
│  │          │  │  View    │  │  Engine       │  │
│  │- Lanes   │  │          │  │               │  │
│  │- Signals │  │- Pins    │  │- Bar Chart    │  │
│  │- Timers  │  │- Zones   │  │- Line Chart   │  │
│  └────┬─────┘  └────┬─────┘  └──────┬────────┘  │
│       │              │               │           │
│  ┌────▼──────────────▼───────────────▼────────┐  │
│  │         Core Simulation Engine             │  │
│  │  - Lane Manager   - Phase Controller       │  │
│  │  - Vehicle Counter - Congestion Evaluator  │  │
│  └────────────────────┬───────────────────────┘  │
│                       │                          │
│  ┌────────────────────▼───────────────────────┐  │
│  │           Data & State Layer               │  │
│  │   (Real-time state / Demo seed data)       │  │
│  └────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────┘
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Mobile Framework | Android (Java / Kotlin) |
| UI Components | Material Design, Custom Views |
| Map Integration | Google Maps SDK for Android |
| Charts & Graphs | MPAndroidChart / Custom Canvas |
| State Management | ViewModel + LiveData |
| Simulation Logic | Custom Lane & Phase Engine |
| Build Tool | Gradle |

---

## 🚀 Getting Started

### Prerequisites

- Android Studio **Hedgehog (2023.1.1)** or later
- Android SDK **API Level 24+** (Android 7.0 minimum)
- Google Maps API Key
- JDK 11 or higher

### Installation

**1. Clone the repository**
```bash
git clone https://github.com/rimzim9934/smart_traffic_managment.git
cd smart_traffic_managment
```

**2. Open in Android Studio**
```
File → Open → Select the cloned folder
```

**3. Configure your Google Maps API Key**

Inside `local.properties`, add:
```properties
MAPS_API_KEY=your_google_maps_api_key_here
```

Or directly in `AndroidManifest.xml`:
```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_API_KEY"/>
```

**4. Sync Gradle & Build**
```bash
./gradlew build
```

**5. Run on device or emulator**
```
Click ▶ Run in Android Studio, or:
./gradlew installDebug
```

---

## ⚙️ How It Works

### Signal Phase Logic

Each lane is independently managed through three possible states:

| State | Label | Color | Meaning |
|-------|-------|-------|---------|
| Green Phase | **GO** | 🟢 Green | Lane is actively flowing |
| Clearing Phase | **SLW** | 🟡 Amber | Lane is in transition/clearing |
| Waiting | **STP** | 🔴 Red | Lane is stopped and waiting |

The simulation engine assigns and rotates phases based on vehicle count and a configurable timer cycle. Lanes with higher vehicle density are prioritized for longer green durations.

### Congestion Evaluation

At any point, total vehicles across all lanes are summed and tagged:

| Vehicle Count Range | Congestion Level |
|--------------------|-----------------|
| < 15 vehicles | LOW |
| 15 – 35 vehicles | MED (Medium) |
| > 35 vehicles | HIGH |

The header dashboard tile reflects this in real time (e.g., **MED** for 26 vehicles across 4 lanes).

### Manual Override Controls

Each lane card exposes two manual controls:

- **Force Green** — Immediately switches that lane to green phase, overriding the automatic cycle. Useful when an operator notices unusual buildup.
- **Emergency** — Triggered for emergency vehicle passage. Turns the selected lane green and holds all other lanes on stop until cleared.

---

## 📋 Dashboard & Signal Control

The **Dashboard** is the primary screen and the operational heart of the app.

```
┌─────────────────────────────────────┐
│   4 Lanes  |  26 Vehicles  |  MED   │  ← Summary Bar
├─────────────────────────────────────┤
│ SIGNAL STATUS                       │
│                                     │
│ [GO]  Lane 1 [Low]          20s     │  ← Green Phase
│       10 vehicles · Green phase     │
│ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━  │  ← Green progress bar
│  Force Green          Emergency     │
│                                     │
│ [STP] Lane 2 [Low]          15s     │  ← Stop
│       5 vehicles · Waiting          │
│ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━  │  ← Red progress bar
│  Force Green          Emergency     │
│                                     │
│ [SLW] Lane 3 [Low]          10s     │  ← Slow/Clearing
│       8 vehicles · Clearing phase   │
│ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━  │  ← Amber progress bar
│  Force Green          Emergency     │
│                                     │
│ [STP] Lane 4 [Low]          25s     │  ← Stop
│       3 vehicles · Waiting          │
│ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━  │
│  Force Green          Emergency     │
├─────────────────────────────────────┤
│ [Stop Sim]  [+ Add Lane]  [Settings]│
└─────────────────────────────────────┘
```

---

## 🗺️ Map View

The **Map** tab renders an interactive Google Map centered on **Bhopal, Madhya Pradesh**. Red pin markers indicate monitored intersections within the city. This geographic view allows operators to:

- Identify which physical intersection corresponds to which lane group
- Spot geographically clustered congestion zones
- Navigate to a specific area for on-ground inspection

The map integrates with the simulation engine — markers can reflect live congestion levels through color changes in extended configurations.

---

## 📈 Analytics & Insights

The **Analytics** tab presents two charts that update based on simulation data:

### Bar Chart — Vehicle Count Per Lane
Displays the vehicle count for each lane as a colored bar. Demo data shows values of **22, 41, 45, 37, and 30** across lanes, giving a quick density comparison. Labels appear above each bar for precise reading.

### Line Chart — Traffic Trend Over Time
Tracks cumulative or rolling traffic volume across simulation time steps. The demo trend curve shows a pattern peaking at **35 vehicles** around step 8, then tapering — illustrating a realistic congestion buildup and dissipation cycle.

> 💡 **Live data replaces demo data** the moment a simulation session is started from the Dashboard.

---

## 📁 Project Structure

```
smart_traffic_managment/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/smarttraffic/
│   │   │   │   ├── ui/
│   │   │   │   │   ├── dashboard/       # Dashboard fragment & ViewModel
│   │   │   │   │   ├── map/             # Map fragment & marker logic
│   │   │   │   │   └── analytics/       # Charts & data visualization
│   │   │   │   ├── simulation/
│   │   │   │   │   ├── LaneManager.kt   # Lane state machine
│   │   │   │   │   ├── PhaseController.kt
│   │   │   │   │   └── CongestionEvaluator.kt
│   │   │   │   ├── model/
│   │   │   │   │   ├── Lane.kt
│   │   │   │   │   └── SignalPhase.kt
│   │   │   │   └── MainActivity.kt
│   │   │   ├── res/
│   │   │   │   ├── layout/              # XML layouts
│   │   │   │   ├── drawable/            # Icons & shapes
│   │   │   │   └── values/              # Colors, strings, themes
│   │   │   └── AndroidManifest.xml
│   └── build.gradle
│
├── screenshots/
│   ├── dashboard.jpg
│   ├── map.jpg
│   └── analytics.jpg
│
├── .gitignore
├── build.gradle
├── gradle.properties
└── README.md
```

---

## 🤝 Contributing

Contributions are welcome and appreciated. Here's how to get involved:

**1. Fork the repository**
```bash
# Click "Fork" on the GitHub page
```

**2. Create a feature branch**
```bash
git checkout -b feature/your-feature-name
```

**3. Commit your changes**
```bash
git commit -m "feat: describe what you added or fixed"
```

**4. Push to your fork**
```bash
git push origin feature/your-feature-name
```

**5. Open a Pull Request** against the `main` branch with a clear description of your changes.

### Contribution Guidelines
- Follow existing code style and naming conventions
- Test on at least one physical Android device before submitting
- Keep pull requests focused — one feature or fix per PR
- Update this README if you add new features or change setup steps

---

## 🐛 Reporting Issues

Found a bug or have a feature request? Open an issue at:

👉 [https://github.com/rimzim9934/smart_traffic_managment/issues](https://github.com/rimzim9934/smart_traffic_managment/issues)

Please include: steps to reproduce, expected vs actual behavior, and your Android version.

---

## 📄 License

This project is released under the **MIT License**.

```
MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

---

## 🌟 Acknowledgements

- Pilot intersection data sourced from **Bhopal, Madhya Pradesh** urban traffic corridors
- Map rendering powered by **Google Maps Platform**
- Chart components inspired by open-source Android charting libraries
- Built as part of an initiative to bring smarter infrastructure tooling to Tier-2 Indian cities

---

<div align="center">

**⭐ Star this repository if you find it useful!**

[Back to Top ↑](#-smart-traffic-management--signal-optimization)

</div>
