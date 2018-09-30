package com.github.satoshun.arch.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class LifecycleAwareObserver(
  private val lifecycleEvent: Lifecycle.Event,
  private val action: () -> Unit
) : LifecycleObserver {
  @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
  fun onDestroy(owner: LifecycleOwner, event: Lifecycle.Event) {
    if (event == lifecycleEvent) {
      action()
      owner.lifecycle.removeObserver(this)
      return
    }
    if (event == Lifecycle.Event.ON_DESTROY) {
      owner.lifecycle.removeObserver(this)
    }
  }
}

interface GenericLifecycleAwareObserver : LifecycleObserver {
  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  fun onCreate(owner: LifecycleOwner) {
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  fun onStart(owner: LifecycleOwner) {
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  fun onResume(owner: LifecycleOwner) {
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  fun onPause(owner: LifecycleOwner) {
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  fun onStop(owner: LifecycleOwner) {
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun onDestroy(owner: LifecycleOwner) {
  }
}
