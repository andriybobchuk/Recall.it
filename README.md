# 📦 Intro for Viktor

- We are using **Compose Multiplatform (KMP + Jetpack Compose)**.
- Our shared module is named `composeApp`, which is perfectly fine (just a naming preference).
- We don't have a dedicated `androidApp` module — Android code is built and run directly from `composeApp/androidMain`.
- We do have a native **SwiftUI shell (`iosApp`)** for integrating with the shared `composeApp` code on iOS.

---

## 🗂 High-level Package Structure

<pre>
Recallit/
├── composeApp/              ← Shared Kotlin Multiplatform module (UI + logic)
│   ├── androidMain/         ← Android-specific code
│   ├── commonMain/          ← Shared Compose UI and business logic
│   └── iosMain/             ← iOS-specific code
│
├── iosApp/                  ← iOS entrypoint (SwiftUI shell)
│   ├── iosApp.xcodeproj     ← Xcode project
│   └── iosApp/              ← SwiftUI content + asset management
│
├── gradle/                  ← Gradle wrapper + version catalog
│
├── build.gradle.kts         ← Root build file
├── settings.gradle.kts      ← Includes `composeApp` and `iosApp`
├── gradle.properties        ← Global project config
├── README.md
└── gradlew / gradlew.bat    ← Wrapper scripts
</pre>

---

# 🧱 Architecture Guide

We follow **canonical Android Clean Architecture**.  
We keep the same layered separation but implement it inside `commonMain` (the shared module).

### 📐 Traditional Clean Architecture (Android)

<pre>
com.example.myapp
├── data        // Repository implementations, remote/local data sources
├── domain      // Use cases, interfaces (e.g., Repository)
├── presentation
│   ├── screens // ViewModels + UI logic
│   └── ui      // Compose UI
</pre>


### 🌐 Compose Multiplatform Equivalent (`commonMain`)

<pre>
composeApp/
└── src/
    └── commonMain/
        └── kotlin/
            └── com/recallit/
                ├── data/             // shared implementations
                ├── domain/           // use cases & interfaces
                ├── presentation/
                │   ├── screens/      // screen-level logic
                │   └── ui/           // composables
                ├── di/               // dependency injection
                ├── navigation/       // screen components / Decompose
                └── utils/
</pre>

And full version of `kotlin/com/recallit`:

<pre>
com/
└── example/
└── recallit/
├── data/                       ← Data layer (Repositories, Data Sources, Mappers)
│   ├── local/                  ← Local data sources (e.g., databases, SharedPreferences)
│   ├── remote/                 ← Remote data sources (e.g., API calls, Network)
│   └── models/                 ← Shared data models (e.g., DTOs)
├── domain/                     ← Domain layer (Business logic, Use cases, Interfaces)
│   ├── models/                 ← Domain models (e.g., User, Item, etc.)
│   ├── repositories/           ← Repository interfaces
│   └── usecases/               ← Use case implementations (e.g., AddItem, FetchUser)
├── presentation/                ← Presentation layer (ViewModels, UI logic)
│   ├── screens/                ← Composables, Screen-specific ViewModels
│   ├── ui/                     ← Common UI components (e.g., Buttons, Text fields)
│   └── navigation/             ← Navigation components (e.g., Decompose for multiplatform)
├── di/                          ← Dependency Injection (e.g., Koin modules)
│   ├── AppModule.kt            ← Global Koin module
│   ├── DataModule.kt           ← Data layer Koin bindings
│   └── ViewModelModule.kt      ← ViewModel Koin bindings
├── utils/                       ← Utility classes (e.g., extensions, helpers)
├── app/                         ← Application-level configuration (e.g., App initialization)
│   ├── App.kt                  ← App startup logic (commonMain or androidMain)
│   └── KoinInitializer.kt      ← Koin initialization (commonMain)
├── navigation/                  ← Navigation-related logic (e.g., Decompose)
├── resources/                   ← Shared resources (commonMain or platform-specific)
└── platform/                    ← Platform-specific code (androidMain, iosMain, etc.)
├── android/                ← Android-specific code
└── ios/                    ← iOS-specific code
</pre>
