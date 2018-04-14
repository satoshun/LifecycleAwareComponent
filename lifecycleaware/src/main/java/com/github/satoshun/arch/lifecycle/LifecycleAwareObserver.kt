package com.github.satoshun.arch.lifecycle

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent

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
