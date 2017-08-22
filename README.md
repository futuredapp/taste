# Taste #

Taste is helper library for Android developers at FUNTASTY


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

## License

The MIT License (MIT)
Copyright © 2017 FUNTASTY Digital s.r.o.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
