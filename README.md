# **Android Kotlin starter project - 2022 edition**

[![Android Weekly #532 badge](https://androidweekly.net/issues/issue-532/badge)](https://androidweekly.net/issues/issue-532)

[![Jetc.dev #147 badge](https://img.shields.io/badge/Featured%20in%20jetc.dev-Issue%20%23147-blue)](https://jetc.dev/issues/147.html)

Android starter project, described precisely in [this](https://proandroiddev.com/clean-android-multi-module-offline-first-scalable-app-in-2022-including-jetpack-compose-mvi-987ebecbecae) and [this article](https://medium.com/codequest/clean-android-multi-module-offline-first-scalable-app-in-2022-part-2-including-compose-ui-e1fd0a0f410e).

The codebase is still up-to-date in 2023 and 2024 with periodically updates.

### **Purpose**
To show good practices using Kotlin features and latest Android libraries from Jetpack in 2022.

For comparison, 2019 edition code available [here](https://github.com/krzdabrowski/android-starter-2019).

### **Description**
Application connects to SpaceX API to download its rocket fleet.

Data always comes from the local persistence (offline-first approach) and updates when necessary.

Clicking on each item navigates user to a browser to read more information on the Web.

Use swipe-down gesture to refresh downloaded data.

Supports light/dark mode theming automatically.


### **Libraries/concepts used**

* Gradle modularised project by features
* The Clean Architecture with MVI pattern in presentation layer
* Jetpack Compose with Material3 design - for UI layer
* Kotlin Coroutines & Kotlin Flow - for concurrency & reactive approach
* Kotlin Serialization converter - for JSON parsing
* Retrofit - for networking
* Hilt - for Dependency Injection pattern implementation
* Room - for local database
* Coil - for image loading
* Version Catalog - for dependency management
* Baseline and Startup Profiles - for performance improvements during app launch
* Timber - for logging
* JUnit5, Turbine and MockK - for unit tests
* Jetpack Compose test dependencies, Maestro and Hilt - for UI tests
* GitHub Actions - for CI/CD
* Renovate - to update dependencies automatically
* KtLint and Detekt - for code linting
