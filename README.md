# Taste #

Taste is helper library for Android developers at The Funtasty.


## Instalation

Check the newest version on [https://jitpack.io/#org.bitbucket.thefuntasty/taste](https://jitpack.io/#org.bitbucket.thefuntasty/taste)

Add it in your root build.gradle at the end of repositories:

```

allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}

```

Add the dependency:

```
dependencies {
    compile 'org.bitbucket.thefuntasty:taste:1.0.1'
}
```

## Usage

```
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Taste.init(this);
    }
}
```