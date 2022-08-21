# **Android Kotlin starter project - 2022 edition**

![Android Weekly #532 badge](https://androidweekly.net/issues/issue-532/badge)

Android starter project, described precisely [in this article](https://proandroiddev.com/clean-android-multi-module-offline-first-scalable-app-in-2022-including-jetpack-compose-mvi-987ebecbecae).

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
* Jetpack Compose - for UI layer
* Kotlin Coroutines & Kotlin Flow - for concurrency & reactive approach
* Kotlin Serialization converter - for JSON parsing
* Retrofit - for networking
* Hilt - for Dependency Injection pattern implementation
* Room - for local database
* Coil - for image loading
* Version Catalog - for dependency management
* Timber - for logging
