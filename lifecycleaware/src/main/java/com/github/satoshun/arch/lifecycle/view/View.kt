package com.github.satoshun.arch.lifecycle.view

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.view.View
import com.github.satoshun.arch.lifecycle.LifecycleAwareObserver
import com.github.satoshun.arch.lifecycle.correspondingEvent

/**
 * Version of [View.postDelayed]
 *
 * todo
 */
fun View.postDelayed(
    owner: LifecycleOwner,
    delayInMillis: Long,
    lifecycleEvent: Lifecycle.Event = owner.correspondingEvent(),
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
