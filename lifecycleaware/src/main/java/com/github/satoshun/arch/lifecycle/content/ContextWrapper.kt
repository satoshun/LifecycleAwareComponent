@file:Suppress("NOTHING_TO_INLINE")

package com.github.satoshun.arch.lifecycle.content

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.content.BroadcastReceiver
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Build
import android.os.Handler
import android.support.annotation.MainThread
import android.support.annotation.RequiresApi
import com.github.satoshun.arch.lifecycle.LifecycleAwareObserver
import com.github.satoshun.arch.lifecycle.correspondingEvent

/**
 * Version of [ContextWrapper.bindService]
 *
 * @param lifecycleEvent conjunction with specified owner
 * @return the result of [ContextWrapper.bindService]
 */
@MainThread
inline fun ContextWrapper.bindService(
    owner: LifecycleOwner,
    service: Intent,
    conn: ServiceConnection,
    flag: Int,
    lifecycleEvent: Lifecycle.Event = owner.correspondingEvent()
): Boolean {
  return bindService(owner.lifecycle, service, conn, flag, lifecycleEvent)
}

/**
 * Version of [ContextWrapper.bindService]
 *
 * @param lifecycleEvent conjunction with specified owner
 * @return the result of [ContextWrapper.bindService]
 */
@MainThread
inline fun ContextWrapper.bindService(
    lifecycle: Lifecycle,
    service: Intent,
    conn: ServiceConnection,
    flag: Int,
    lifecycleEvent: Lifecycle.Event = lifecycle.correspondingEvent()
): Boolean {
  val result = bindService(service, conn, flag)
  lifecycle.addObserver(LifecycleAwareObserver(
      lifecycleEvent,
      action = {
        unbindService(conn)
      }
  ))
  return result
}

/**
 * Version of [ContextWrapper.registerReceiver]
 */
@MainThread
inline fun ContextWrapper.registerReceiver(
    owner: LifecycleOwner,
    receiver: BroadcastReceiver,
    filter: IntentFilter,
    lifecycleEvent: Lifecycle.Event = owner.correspondingEvent()
): Intent? {
  return registerReceiver(owner.lifecycle, receiver, filter, lifecycleEvent)
}

/**
 * Version of [ContextWrapper.registerReceiver]
 */
@MainThread
inline fun ContextWrapper.registerReceiver(
    lifecycle: Lifecycle,
    receiver: BroadcastReceiver,
    filter: IntentFilter,
    lifecycleEvent: Lifecycle.Event = lifecycle.correspondingEvent()
): Intent? {
  val result = registerReceiver(receiver, filter)
  lifecycle.addObserver(LifecycleAwareObserver(
      lifecycleEvent,
      action = {
        unregisterReceiver(receiver)
      }
  ))
  return result
}

/**
 * Version of [ContextWrapper.registerReceiver]
 */
@RequiresApi(Build.VERSION_CODES.O)
@MainThread
inline fun ContextWrapper.registerReceiver(
    owner: LifecycleOwner,
    receiver: BroadcastReceiver,
    filter: IntentFilter,
    flags: Int,
    lifecycleEvent: Lifecycle.Event = owner.correspondingEvent()
): Intent {
  return registerReceiver(owner.lifecycle, receiver, filter, flags, lifecycleEvent)
}

/**
 * Version of [ContextWrapper.registerReceiver]
 */
@RequiresApi(Build.VERSION_CODES.O)
@MainThread
inline fun ContextWrapper.registerReceiver(
    lifecycle: Lifecycle,
    receiver: BroadcastReceiver,
    filter: IntentFilter,
    flags: Int,
    lifecycleEvent: Lifecycle.Event = lifecycle.correspondingEvent()
): Intent {
  val result = registerReceiver(receiver, filter, flags)
  lifecycle.addObserver(LifecycleAwareObserver(
      lifecycleEvent,
      action = {
        unregisterReceiver(receiver)
      }
  ))
  return result
}

/**
 * Version of [ContextWrapper.registerReceiver]
 */
@MainThread
inline fun ContextWrapper.registerReceiver(
    owner: LifecycleOwner,
    receiver: BroadcastReceiver,
    filter: IntentFilter,
    broadcastPermission: String,
    scheduler: Handler,
    lifecycleEvent: Lifecycle.Event = owner.correspondingEvent()
): Intent? {
  return registerReceiver(owner.lifecycle, receiver, filter, broadcastPermission, scheduler, lifecycleEvent)
}

/**
 * Version of [ContextWrapper.registerReceiver]
 */
@MainThread
inline fun ContextWrapper.registerReceiver(
    lifecycle: Lifecycle,
    receiver: BroadcastReceiver,
    filter: IntentFilter,
    broadcastPermission: String,
    scheduler: Handler,
    lifecycleEvent: Lifecycle.Event = lifecycle.correspondingEvent()
): Intent? {
  val result = registerReceiver(receiver, filter, broadcastPermission, scheduler)
  lifecycle.addObserver(LifecycleAwareObserver(
      lifecycleEvent,
      action = {
        unregisterReceiver(receiver)
      }
  ))
  return result
}

/**
 * Version of [ContextWrapper.registerReceiver]
 */
@RequiresApi(Build.VERSION_CODES.O)
@MainThread
inline fun ContextWrapper.registerReceiver(
    owner: LifecycleOwner,
    receiver: BroadcastReceiver,
    filter: IntentFilter,
    broadcastPermission: String,
    scheduler: Handler,
    flags: Int,
    lifecycleEvent: Lifecycle.Event = owner.correspondingEvent()
): Intent? {
  return registerReceiver(owner.lifecycle, receiver, filter, broadcastPermission, scheduler, flags, lifecycleEvent)
}

/**
 * Version of [ContextWrapper.registerReceiver]
 */
@RequiresApi(Build.VERSION_CODES.O)
@MainThread
inline fun ContextWrapper.registerReceiver(
    lifecycle: Lifecycle,
    receiver: BroadcastReceiver,
    filter: IntentFilter,
    broadcastPermission: String,
    scheduler: Handler,
    flags: Int,
    lifecycleEvent: Lifecycle.Event = lifecycle.correspondingEvent()
): Intent? {
  val result = registerReceiver(receiver, filter, broadcastPermission, scheduler, flags)
  lifecycle.addObserver(LifecycleAwareObserver(
      lifecycleEvent,
      action = {
        unregisterReceiver(receiver)
      }
  ))
  return result
}
