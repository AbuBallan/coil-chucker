# Coil Chucker

Images are a critical part of many Android applications, and handling them can be a challenging task for developers. Working with large bitmaps, in particular, can quickly introduce performance issues that can negatively impact the user experience. However, a new library called Coil Chucker can help simplify image inspection and optimize app performance.

Coil Chucker is an open-source library designed to work as an interceptor for Coil, a popular image loading library for Android. The library is built to persist all image-related events within an app and provide a user-friendly interface for inspecting and sharing the content. When an app uses Coil Chucker, it displays a notification summarizing ongoing image activity, and tapping on the notification launches the full Coil Chucker UI.




## Getting Started 👣
Chucker is distributed through Jitpack.

```bash
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

```bash
dependencies {
        debugImplementation 'com.github.AbuBallan.coil-chucker:library:0.0.1'
        releaseImplementation 'com.github.AbuBallan:coil-chucker:library-no-op:0.0.1'
}
```

To start using Coil Chucker, just plug in a new CoilChuckerInterceptor
 to your ImageLoader:
```bash
class App : Application(), ImageLoaderFactory {
   override fun newImageLoader() = ImageLoader(this)
       .newBuilder()
       .components {
           add(
               CoilChuckerInterceptor(
                   context = this@App
               )
           )
       }
       .build()
}
```
