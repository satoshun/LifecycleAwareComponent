[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.satoshun.lifecycleaware/lifecycleaware/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.satoshun.lifecycleaware/lifecycleaware)

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
implementation 'com.github.satoshun.lifecycleaware:lifecycleaware:${latest-version}'

// for gms
implementation 'com.github.satoshun.lifecycleaware:lifecycleaware-gms-location:${latest-version}'
```

## Supported classes

- [Handler](https://developer.android.com/reference/android/os/Handler.html)
- [WebView](https://developer.android.com/reference/android/webkit/WebView.html)
- [ContextWrapper](https://developer.android.com/reference/android/content/ContextWrapper.html)
  - [BroadcastReceiver](https://developer.android.com/reference/android/content/BroadcastReceiver.html)
  - [Service](https://developer.android.com/reference/android/app/Service.html)
- Animation
  - [ViewPropertyAnimator](https://developer.android.com/reference/android/view/ViewPropertyAnimator.html)

## Notes

- Default Lifecycle.Event is corresponding event if not specified a Lifecycle.Event.
  - corresponding event is symmetric event.
    - if subscribing during onCreate, it will terminate on onDestroy.
    - if subscribing during onStart, it will terminate on onStop.
    - if subscribing during onResume, it will terminate on onPause.
    - if subscribing during onPause or onStop or onDestroy, it will terminate on onDestroy.
  - It inspired from [RxLifecycle](https://github.com/trello/RxLifecycle).
