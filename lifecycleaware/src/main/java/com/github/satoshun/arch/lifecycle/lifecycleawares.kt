package com.github.satoshun.arch.lifecycle

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner

fun LifecycleOwner.correspondingEvent(): Lifecycle.Event {
  return when (lifecycle.currentState) {
    Lifecycle.State.CREATED -> Lifecycle.Event.ON_DESTROY
    Lifecycle.State.STARTED -> Lifecycle.Event.ON_STOP
    Lifecycle.State.RESUMED -> Lifecycle.Event.ON_PAUSE
    else -> Lifecycle.Event.ON_DESTROY
  }
}
