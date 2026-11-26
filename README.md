# Secure Sync

Secure Sync is a native Android application prototype with local user registration and sign-in, a dashboard with navigation, a local user list, and a movie list loaded from a remote demo API.

> **Security Notice:** Despite its name, Secure Sync is an educational Android application prototype, not a production-ready secure authentication or synchronization system. User passwords are stored locally without hashing or encryption. Do not use real credentials or sensitive personal information with this application.

The Android project is in [`Secure Sync App`](Secure%20Sync%20App/). It is built with Java, Android views, and Gradle Kotlin DSL. The module configuration is in [`app/build.gradle.kts`](Secure%20Sync%20App/app/build.gradle.kts); the project uses [`settings.gradle.kts`](Secure%20Sync%20App/settings.gradle.kts) and the included [Gradle wrapper](Secure%20Sync%20App/gradle/wrapper/gradle-wrapper.properties).

## Features

- **Registration and sign-in:** Register a username, email address, 10-digit phone number, and password. Sign-in checks the entered username and password against the local Room database.
- **Remembered sign-in state:** A local `SharedPreferences` value controls whether the splash screen opens the dashboard or sign-in screen.
- **Dashboard navigation:** Navigate between the home screen, local user list, and movie list, or sign out.
- **Local user list:** The user screen queries the local database and its adapter maps names, phone numbers, and email addresses for display.
- **Movie list:** Request a title and movie/hero entries from a configured HTTP API and display them in a two-column grid.

## Technology and configuration

| Area | Configuration |
| --- | --- |
| Platform | Android |
| Language and UI | Java, Android XML layouts, View Binding |
| Build | Android Gradle Plugin 8.1.1; Gradle wrapper 8.0 |
| Android SDK | Compile SDK 33; target SDK 33; minimum SDK 27 |
| Java compatibility | Java 8 source and target compatibility |
| Local persistence | Room 2.5.2 over SQLite |
| Networking | Retrofit 2.9.0, Gson converter, RxJava 3, RxAndroid |
| App namespace and application ID | `com.example.securesync` |

The project also declares AndroidX AppCompat, Material Components, ConstraintLayout, CircleImageView, and OkHttp's logging interceptor. Test dependencies are JUnit 4, AndroidX Test, and Espresso. The logging-interceptor dependency is declared, but no interceptor configuration is present in the application source.

## Architecture

The single `:app` module contains the Android activities, Room persistence layer, API request code, and XML resources.

1. [`SplashScreen.java`](Secure%20Sync%20App/app/src/main/java/com/example/securesync/SplashScreen.java) reads the `isLoggedIn` preference and routes to the dashboard or sign-in screen.
2. [`SignUp.java`](Secure%20Sync%20App/app/src/main/java/com/example/securesync/SignUp.java) validates the entered fields and inserts a `UserModel` into the Room database.
3. [`MainActivity.java`](Secure%20Sync%20App/app/src/main/java/com/example/securesync/MainActivity.java) queries Room for a matching username and password. On a match, it saves the username and sign-in flag in `SharedPreferences`.
4. [`LoggedIn.java`](Secure%20Sync%20App/app/src/main/java/com/example/securesync/LoggedIn.java) provides dashboard and navigation-drawer actions.
5. [`Users.java`](Secure%20Sync%20App/app/src/main/java/com/example/securesync/Users.java) queries Room and binds user fields to a `RecyclerView` adapter. The current adapter is not notified when the asynchronous query populates its list, so loaded entries may not appear.
6. [`Movies.java`](Secure%20Sync%20App/app/src/main/java/com/example/securesync/Movies.java) calls the configured REST endpoint with Retrofit and RxJava, then renders the returned entries in a two-column `RecyclerView`.

The local persistence components are [`DatabaseClass.java`](Secure%20Sync%20App/app/src/main/java/com/example/securesync/DatabaseClass.java), [`DaoClass.java`](Secure%20Sync%20App/app/src/main/java/com/example/securesync/DaoClass.java), and [`UserModel.java`](Secure%20Sync%20App/app/src/main/java/com/example/securesync/UserModel.java). The API response shape is represented by [`Post.java`](Secure%20Sync%20App/app/src/main/java/com/example/securesync/Post.java) and [`MoviesData.java`](Secure%20Sync%20App/app/src/main/java/com/example/securesync/MoviesData.java).

The registration password rule accepts 8–24 characters from letters, digits, and `@`, `#`, `$`, or `_`. The mobile-number check requires 10 characters; the form uses a numeric input type.

## Remote movie data

The movie screen sends a `GET` request to `https://demo3540805.mockable.io/v1/getList`. The response model expects a JSON object with a Boolean `result` and a `data` object containing a `title` and a `movies` array. Each movie entry has `name` and `hero` string fields.

The endpoint is a hard-coded demo service, not an app-managed backend. The movie screen displays an error message if the request fails or the response indicates an unsuccessful result. Network availability and the continued availability or contents of the demo endpoint are not guaranteed by this repository.

## Prerequisites

- Android Studio or the Android SDK build tools.
- JDK 17 to run the Android Gradle Plugin 8.1.1.
- Android SDK Platform 33. A device or emulator running Android 8.1 (API 27) or newer is needed to run the app.

The Gradle wrapper is included, so a separate system Gradle installation is not required. Gradle may need to download the configured Gradle distribution and project dependencies on the first build.

## Build and run

Open the `Secure Sync App` directory in Android Studio and allow Gradle sync to complete. Alternatively, from the repository root, build a debug APK with the wrapper.

**Windows PowerShell**

```powershell
Set-Location "Secure Sync App"
.\gradlew.bat :app:assembleDebug
```

**macOS or Linux**

```sh
cd "Secure Sync App"
./gradlew :app:assembleDebug
```

The debug APK is written to `Secure Sync App/app/build/outputs/apk/debug/app-debug.apk`. From the repository root, install it on a connected device with Android Debug Bridge:

```sh
adb install "Secure Sync App/app/build/outputs/apk/debug/app-debug.apk"
```

Run the app on a device or emulator with API level 27 or later. Internet access is needed for the movie-list request; registration and sign-in use the on-device database.

## Tests

Run the local unit-test task from `Secure Sync App`:

**Windows PowerShell**

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

**macOS or Linux**

```sh
./gradlew :app:testDebugUnitTest
```

Run the instrumented test task with an emulator or connected Android device:

**Windows PowerShell**

```powershell
.\gradlew.bat :app:connectedDebugAndroidTest
```

**macOS or Linux**

```sh
./gradlew :app:connectedDebugAndroidTest
```

The current tests are starter examples: the unit test checks a basic arithmetic expression, and the instrumented test checks the application package name. They do not cover registration, sign-in, persistence, navigation, or the movie API.

## Repository structure

```text
.
├── README.md
├── .gitignore
└── Secure Sync App/
    ├── app/
    │   ├── build.gradle.kts
    │   ├── .gitignore
    │   ├── proguard-rules.pro
    │   └── src/
    │       ├── main/
    │       │   ├── AndroidManifest.xml
    │       │   ├── java/com/example/securesync/
    │       │   │   ├── MainActivity.java
    │       │   │   ├── SignUp.java
    │       │   │   ├── SplashScreen.java
    │       │   │   ├── LoggedIn.java
    │       │   │   ├── Users.java
    │       │   │   ├── Movies.java
    │       │   │   ├── DatabaseClass.java
    │       │   │   ├── DaoClass.java
    │       │   │   ├── UserModel.java
    │       │   │   ├── DataAdapter.java
    │       │   │   ├── MovieAdapter.java
    │       │   │   ├── ExampleItem.java
    │       │   │   ├── MoviesData.java
    │       │   │   ├── Post.java
    │       │   │   └── initialiseDatabase.java
    │       │   └── res/
    │       │       ├── drawable/       # Background, icons, and image assets
    │       │       ├── font/
    │       │       ├── layout/         # Activity and list-item layouts
    │       │       ├── mipmap-*/       # Launcher icons
    │       │       ├── values/         # Strings, colors, and themes
    │       │       ├── values-night/
    │       │       └── xml/            # Backup and data-extraction rules
    │       ├── test/                   # Local JVM tests
    │       └── androidTest/            # Instrumented Android tests
    ├── gradle/wrapper/                 # Gradle wrapper files
    ├── build.gradle.kts                # Android Gradle Plugin declaration
    ├── gradle.properties
    ├── gradlew
    ├── gradlew.bat
    ├── settings.gradle.kts
    └── .gitignore
```

`local.properties` is generated locally by Android Studio to specify the Android SDK location and is excluded from version control.

The drawable resources include `bkgnd.png`, `tata.png`, and `tcl.png`; the splash-screen layout references the latter two image assets. Generated Gradle output and IDE state are not shown in the tree.

## Limitations

- User records, including passwords, are stored in the local Room database. Passwords are compared directly and are not hashed or encrypted by the app. This implementation should not be used to protect real accounts or sensitive information.
- Account data and sign-in state are local to the device. There is no account service or user-data synchronization implementation.
- Registration starts the asynchronous database insert and immediately opens the sign-in screen; it does not wait for the insert to finish.
- The user-list adapter is attached before its asynchronous database query completes and is not notified after the list is populated, so the screen may not show the loaded records.
- The movie list depends on a hard-coded third-party demo endpoint.
- The movie loading dialog is dismissed on successful stream completion; the error callback does not dismiss it, so it may remain visible after a failed request.