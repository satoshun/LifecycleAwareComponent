@file:Suppress("NOTHING_TO_INLINE")

package com.github.satoshun.arch.lifecycle.view

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.github.satoshun.arch.lifecycle.LifecycleAwareObserver
import com.github.satoshun.arch.lifecycle.correspondingEvent

/**
 * Version of [View.postDelayed]
 */
inline fun View.postDelayed(
  owner: LifecycleOwner,
  delayInMillis: Long,
  lifecycleEvent: Lifecycle.Event = owner.correspondingEvent(),
  noinline action: () -> Unit
): Boolean {
  return postDelayed(owner.lifecycle, delayInMillis, lifecycleEvent, action)
}

/**
 * Version of [View.postDelayed]
 */
inline fun View.postDelayed(
  lifecycle: Lifecycle,
  delayInMillis: Long,
  lifecycleEvent: Lifecycle.Event = lifecycle.correspondingEvent(),
  crossinline action: () -> Unit
): Boolean {
  val runnable = Runnable { action() }
  val result = postDelayed(runnable, delayInMillis)
  lifecycle.addObserver(LifecycleAwareObserver(
      lifecycleEvent,
      action = {
        removeCallbacks(runnable)
      }
  ))
  return result
}
