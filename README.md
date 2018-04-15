# LifecycleAwareComponent

Handling Lifecycles with Lifecycle-Aware Components. This library respects with [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html).

```kotlin
// This postDelayed obeys Lifecycle of myActivity.  
handler.postDelayed(
  owner = myActivity,
  delayInMillis = 100L
) {
  // your action
}
```

## Getting started

The first step is to add into your build.gradle. 

```groovy
// todo: not released yet
```

## Supported classes

- [Handler](https://developer.android.com/reference/android/os/Handler.html)
- [WebView](https://developer.android.com/reference/android/webkit/WebView.html)
- [ContextWrapper](https://developer.android.com/reference/android/content/ContextWrapper.html)
  - [BroadcastReceiver](https://developer.android.com/reference/android/content/BroadcastReceiver.html)
  - [Service](https://developer.android.com/reference/android/app/Service.html)
- Animation
  - [ViewPropertyAnimator](https://developer.android.com/reference/android/view/ViewPropertyAnimator.html)
