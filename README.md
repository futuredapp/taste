# Taste #

Taste is helper library for Android developers at The Funtasty.


## Instalation

Check the newest version on [https://jitpack.io/#thefuntasty/taste](https://jitpack.io/#thefuntasty/taste)

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Add the dependencies you need:

```groovy
compile 'com.github.thefuntasty:taste:core:2.0.0'
compile 'com.github.thefuntasty:taste:mvp:2.0.0'
compile 'com.github.thefuntasty:taste:debugdrawer:2.0.0'
compile 'com.github.thefuntasty:taste:testing:2.0.0'
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

1. Open Android Studio and go to: File / Settings / Build / Compiler and add these command-line options: **-PminSdk=21**
2. In your `build.gradle` add row: **apply from: 'https://github.com/thefuntasty/taste/raw/master/common.gradle'**
3. Locate row `minSdkVersion 16` and replace it with: `minSdkVersion minSdk(16)`

> Technical staff: release build is recognized by keystore
