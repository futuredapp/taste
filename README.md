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

Add the dependencies you need:

```groovy
compile 'org.bitbucket.thefuntasty.taste:core:1.2.1'
compile 'org.bitbucket.thefuntasty.taste:parcel:1.2.1'
compile 'org.bitbucket.thefuntasty.taste:bus:1.2.1'
```

When using parcel module, do not forget to include [Android APT](https://bitbucket.org/hvisser/android-apt) plugin and add following annotation processors as apt dependency and Clojars repo as repository
```groovy
apt 'org.parceler:parceler:1.1.1'
apt 'frankiesardo:icepick-processor:3.2.0'
```
```groovy
maven { url "https://clojars.org/repo/" }
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
2. In your `build.gradle` add row: **apply from: 'https://bitbucket.org/thefuntasty/taste/raw/master/common.gradle'**
3. Locate row `minSdkVersion 16` and replace it with: `minSdkVersion minSdk(16)`

> Technical staff: release build is recognized by keystore