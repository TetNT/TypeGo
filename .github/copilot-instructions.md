# App overview
TypeGo is an Android app aimed at improving typing speed by completing time limited games. 
The app functionality includes game history, achievements, and statistics.

There are two game modes available:
- Own text: the user can choose any text to play with.
- Random text: the app generates random set of words for the user to play with.

## Architecture and Stack
- Single-module app
- Gradle
- Kotlin for coding
- Android SDK
- Android XML Views
- Hilt
- MVVM architecture
- Coroutines
- Flow

## Project general coding standards
- Business logic and scripts have to be wrapped into an interface for testing purposes.
- All the code should be covered with unit tests.
- All the code should be covered with UI tests.