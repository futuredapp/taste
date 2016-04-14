# Taste #

Taste is helper library for Android developers at The Funtasty.


## Instalation

Check the newest version on [https://jitpack.io/#org.bitbucket.thefuntasty/taste](https://jitpack.io/#org.bitbucket.thefuntasty/taste)

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Add the dependency:

```groovy
dependencies {
    compile 'org.bitbucket.thefuntasty:taste:1.0.8'
}
```

## Usage

```java
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Taste.init(this);
    }
}
```

***

## Gradle tasks

1. Open Android Studio and go to: File/Settings/Build/Compiler and add these command-line options: -PminSdk=21
2. In your `build.gradle` add row: **apply from: 'https://bitbucket.org/thefuntasty/treasure-android/raw/master/gradle/common.gradle'**
3. Locate row `minSdkVersion 16` and replace it with: `minSdkVersion minSdk(16)`

> Technical staff: release build is recognized by keystore