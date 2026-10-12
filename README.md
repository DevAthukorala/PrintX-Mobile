# PrintX Mobile

PrintX Mobile (branded **PrintXpress** in the app) is a native Android printing-service prototype. Users can browse print products, customize an order, attach artwork from their device, and manage orders stored locally.

## Features

- Browse a seeded catalog of business cards, posters, vinyl banners, mugs, stickers, and T-shirts.
- View product details and choose sizes, materials, and quantities.
- Calculate order totals in Sri Lankan rupees (LKR).
- Choose store pickup or enter a home delivery address.
- Select a local artwork file and add custom printing instructions.
- View order history, cancel processing orders, and reschedule eligible orders.
- Explore login and registration forms, a profile screen, sample notifications, and printing guidelines with FAQs.

## Technology stack

| Component | Implementation |
| --- | --- |
| Language | Kotlin 2.0.0 |
| UI | Jetpack Compose and Material 3 |
| Navigation | Navigation Compose |
| State management | Android ViewModel, coroutines, and StateFlow |
| Local storage | Room 2.6.1 with KSP |
| Android support | Android 7.0 (API 24) and above |
| Compile / target SDK | API 34 |
| Application ID | `com.example.dewminiproject` |

The app separates Compose screens, ViewModel state, repository operations, and Room entities/DAOs.

## Getting started

1. Clone the repository:

   ```bash
   git clone https://github.com/DevAthukorala/PrintX-Mobile.git
   cd PrintX-Mobile
   ```

2. Open the repository root in Android Studio.
3. Install Android SDK Platform 34 through SDK Manager and allow Gradle dependencies to download.
4. Configure a JDK 21 installation for Gradle; the checked-in daemon configuration requests Java 21.
5. Sync the project, select the `app` run configuration, and run it on an emulator or device with API 24 or later.

Android Studio normally creates `local.properties` with your SDK location. This machine-specific file is excluded from Git.

### Command-line build

From the repository root on Windows:

```powershell
.\gradlew.bat assembleDebug
```

On macOS or Linux:

```bash
bash ./gradlew assembleDebug
```

After a successful build, the debug APK is written to `app/build/outputs/apk/debug/app-debug.apk`.

The checked-in build uses Gradle 9.4.1 and Android Gradle Plugin 8.4.0. Build compatibility has not been verified; if sync fails, check the wrapper, Android plugin, Kotlin, and KSP versions together.

## Try the app

1. On the login screen, enter a valid email address and a password of at least six characters.
2. Browse the catalog and open a product.
3. Choose order specifications, enter a quantity, and optionally attach artwork and instructions.
4. Choose pickup or delivery and place the order.
5. Open order history to inspect and manage the saved order.

The catalog is populated automatically when the local product table is empty.

## Project structure

```text
app/src/main/
├── AndroidManifest.xml
├── java/com/example/dewminiproject/
│   ├── MainActivity.kt
│   ├── data/
│   │   ├── local/          # Room database, entities, and DAOs
│   │   └── repository/     # Data access operations
│   ├── navigation/        # Routes and navigation graph
│   ├── ui/
│   │   ├── screens/       # Compose screens
│   │   └── theme/         # Colors, typography, and theme
│   ├── util/              # Form validation
│   └── viewmodel/         # UI state and order operations
└── res/                   # Android resources
```

## Prototype limitations

- **Authentication is a demonstration flow.** Login looks up users by email and creates a guest record for an unknown email. Passwords are validated in the form but are not authenticated against stored credentials.
- Products, users, and orders are stored in a local Room database. There is no remote backend, cross-device synchronization, payment processing, or live fulfillment integration.
- Artwork selection stores a local URI with the order; it does not upload the file to a server.
- Notifications and offers are static examples. Saved addresses and gallery previews are placeholders, and saved designs are not implemented.
- The database uses destructive migration fallback, so a schema change without a migration can erase local data.

## Tests

The repository includes starter JUnit and Android instrumentation tests. Run them on Windows with:

```powershell
.\gradlew.bat testDebugUnitTest
.\gradlew.bat connectedDebugAndroidTest
```

Instrumentation tests require a running emulator or connected Android device. On macOS/Linux, replace `.\gradlew.bat` with `bash ./gradlew`. These starter tests do not cover the complete ordering workflow.
