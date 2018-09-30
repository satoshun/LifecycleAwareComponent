@file:Suppress("NOTHING_TO_INLINE")

package com.github.satoshun.arch.lifecycle.os

import android.os.Handler
import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.github.satoshun.arch.lifecycle.LifecycleAwareObserver
import com.github.satoshun.arch.lifecycle.correspondingEvent

/**
 * Version of [Handler.postDelayed]
 *
 * @param lifecycleEvent conjunction with specified owner
 * @return the result of [Handler.postDelayed]
 */
@MainThread
inline fun Handler.postDelayed(
  owner: LifecycleOwner,
  delayInMillis: Long,
  lifecycleEvent: Lifecycle.Event = owner.correspondingEvent(),
  noinline action: () -> Unit
): Boolean {
  return postDelayed(owner.lifecycle, delayInMillis, lifecycleEvent, action)
}

/**
 * Version of [Handler.postDelayed]
 *
 * @param lifecycleEvent conjunction with specified owner
 * @return the result of [Handler.postDelayed]
 */
@MainThread
inline fun Handler.postDelayed(
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

/**
 * Version of [Handler.postAtTime]
 *
 * @param lifecycleEvent conjunction with specified owner
 * @return the result of [Handler.postAtTime]
 */
@MainThread
inline fun Handler.postAtTime(
  owner: LifecycleOwner,
  uptimeMillis: Long,
  token: Any? = null,
  lifecycleEvent: Lifecycle.Event = owner.correspondingEvent(),
  noinline action: () -> Unit
): Boolean {
  return postAtTime(owner.lifecycle, uptimeMillis, token, lifecycleEvent, action)
}

/**
 * Version of [Handler.postAtTime]
 *
 * @param lifecycleEvent conjunction with specified owner
 * @return the result of [Handler.postAtTime]
 */
@MainThread
inline fun Handler.postAtTime(
  lifecycle: Lifecycle,
  uptimeMillis: Long,
  token: Any? = null,
  lifecycleEvent: Lifecycle.Event = lifecycle.correspondingEvent(),
  crossinline action: () -> Unit
): Boolean {
  val runnable = Runnable { action() }
  val result = postAtTime(runnable, token, uptimeMillis)
  lifecycle.addObserver(LifecycleAwareObserver(
      lifecycleEvent,
      action = {
        removeCallbacks(runnable)
      }
  ))
  return result
}
