package com.github.satoshun.arch.lifecycle.os

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.os.Handler
import android.support.annotation.MainThread
import com.github.satoshun.arch.lifecycle.LifecycleAwareObserver

/**
 * todo
 */
@MainThread
fun Handler.postDelayed(
    owner: LifecycleOwner,
    delayInMillis: Long,
    lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    action: () -> Unit
): Boolean {
  val runnable = Runnable { action() }
  val result = postDelayed(runnable, delayInMillis)
  owner.lifecycle.addObserver(LifecycleAwareObserver(
      lifecycleEvent,
      action = {
        removeCallbacks(runnable)
      }
  ))
  return result
}

/**
 * todo
 */
@MainThread
fun Handler.postAtTime(
    owner: LifecycleOwner,
    uptimeMillis: Long,
    token: Any? = null,
    lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    action: () -> Unit
): Boolean {
  val runnable = Runnable { action() }
  val result = postAtTime(runnable, token, uptimeMillis)
  owner.lifecycle.addObserver(LifecycleAwareObserver(
      lifecycleEvent,
      action = {
        removeCallbacks(runnable)
      }
  ))
  return result
}
