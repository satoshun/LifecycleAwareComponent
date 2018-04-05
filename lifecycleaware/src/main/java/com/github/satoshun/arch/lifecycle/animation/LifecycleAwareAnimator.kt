package com.github.satoshun.arch.lifecycle.animation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.view.View
import android.view.ViewPropertyAnimator

fun ViewPropertyAnimator.start(target: View, owner: LifecycleOwner) {
  owner.lifecycle.addObserver(LifecycleAwareObserver(
      didDestroy = {
        setListener(null)
        target.clearAnimation()
        cancel()
      }
  ))
  start()
}

internal class LifecycleAwareObserver(
  private val didDestroy: () -> Unit
) : LifecycleObserver {
  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun onDestroy(owner: LifecycleOwner) {
    didDestroy()
    owner.lifecycle.removeObserver(this)
  }
}
