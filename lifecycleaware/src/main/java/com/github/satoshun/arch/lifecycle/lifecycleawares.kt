@file:Suppress("NOTHING_TO_INLINE")

package com.github.satoshun.arch.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

inline fun LifecycleOwner.correspondingEvent(): Lifecycle.Event {
  return lifecycle.correspondingEvent()
}

inline fun Lifecycle.correspondingEvent(): Lifecycle.Event {
  return when (currentState) {
    Lifecycle.State.CREATED -> Lifecycle.Event.ON_DESTROY
    Lifecycle.State.STARTED -> Lifecycle.Event.ON_STOP
    Lifecycle.State.RESUMED -> Lifecycle.Event.ON_PAUSE
    else -> Lifecycle.Event.ON_DESTROY
  }
}
