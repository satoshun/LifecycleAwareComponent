package com.github.satoshun.arch.lifecycle

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent

internal class LifecycleAwareObserver(
  private val didDestroy: () -> Unit
) : LifecycleObserver {
  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun onDestroy(owner: LifecycleOwner) {
    didDestroy()
    owner.lifecycle.removeObserver(this)
  }
}
