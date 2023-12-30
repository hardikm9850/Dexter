# Dexter

[![](https://jitpack.io/v/hardikm9850/Dexter.svg)](https://jitpack.io/#hardikm9850/Dexter)
(https://img.shields.io/badge/API-19%2B-brightgreen?style=flat)](https://android-arsenal.com/api?level=19)
[![Twitter](https://img.shields.io/badge/twitter-ThatMRFBat-blue?style=flat)](https://twitter.com/thatmrfbat)

Dexter records global crashes and offers easy redirection to the exception tracing screen; moreover, it outlines footprints of Activity/Fragment along with the respective bundle data.

Use case :
The QA team may frequently raise a problem relating to an app crash that is either unique or difficult to duplicate. Without stacktrace information, it is difficult for us developers to determine the root problem.

![image description](media/dexter.gif)


Dexter to the rescue
With Dexter, the development team can receive above-mentioned details from QA team and would be able to perform the same steps using the same bundle data. This strategy might help detect serious issues much sooner.


## ✨Features

- Stacktrace of the uncaught exception
- User journey footprints along with the bundle data
- Option to export this detail via email

## ▶️ Demo


## ⚒ Download
[![](https://jitpack.io/v/hardikm9850/Dexter.svg)](https://jitpack.io/#hardikm9850/Dexter)
#### Gradle
Add the dependency below to your **module**'s `build.gradle` file:
```Gradle
implementation 'com.github.hardikm9850:Dexter:$latest_version'
```

### Usage
You can seamlessly install Dexter using the following example:
```kotlin
class MyApp : Application() {

  override fun onCreate() {
    super.onCreate()
    DexterInstaller.setup(application = this)
  }
}
```
It's recommended to install Dexter in your **Application** class or your initialization alternatives, for example [App Startup](https://developer.android.com/topic/libraries/app-startup).

### Tracing Global Exceptions

You can trace global exceptions via the lambda argument 'defaultExceptionHandler'. If you wish to collect and report exceptions to other platforms, such as [Firebase Crashlyrics](https://firebase.google.com/docs/crashlytics), this can be handy.

```kotlin
DexterInstaller.setup(
            application = this,
            defaultExceptionHandler = { thread, throwable ->
                Firebase.crashlytics.log(exception.stackTrace)
            },
)
```

## Find this repository useful? :heart:

Support it by joining __[stargazers](https://github.com/hardikm9850/dexter/stargazers)__ for this repository 🌟

# License

```xml
Designed and developed by 2023 Hardik Mehta

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
