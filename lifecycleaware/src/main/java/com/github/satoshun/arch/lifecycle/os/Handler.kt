package com.github.satoshun.arch.lifecycle.os

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.os.Handler
import android.support.annotation.MainThread
import com.github.satoshun.arch.lifecycle.LifecycleAwareObserver
import com.github.satoshun.arch.lifecycle.correspondingEvent

/**
 * Version of [Handler.postDelayed]
 *
 * @param lifecycleEvent conjunction with specified owner
 * @return the result of [Handler.postDelayed]
 */
@MainThread
fun Handler.postDelayed(
    owner: LifecycleOwner,
    delayInMillis: Long,
    lifecycleEvent: Lifecycle.Event = owner.correspondingEvent(),
    action: () -> Unit
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
fun Handler.postDelayed(
    lifecycle: Lifecycle,
    delayInMillis: Long,
    lifecycleEvent: Lifecycle.Event = lifecycle.correspondingEvent(),
    action: () -> Unit
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
fun Handler.postAtTime(
    owner: LifecycleOwner,
    uptimeMillis: Long,
    token: Any? = null,
    lifecycleEvent: Lifecycle.Event = owner.correspondingEvent(),
    action: () -> Unit
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
fun Handler.postAtTime(
    lifecycle: Lifecycle,
    uptimeMillis: Long,
    token: Any? = null,
    lifecycleEvent: Lifecycle.Event = lifecycle.correspondingEvent(),
    action: () -> Unit
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
