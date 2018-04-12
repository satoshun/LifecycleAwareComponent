package com.github.satoshun.arch.lifecycle.content

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.content.BroadcastReceiver
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Build
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
fun ContextWrapper.bindService(
    owner: LifecycleOwner,
    service: Intent,
    conn: ServiceConnection,
    flag: Int,
    lifecycleEvent: Lifecycle.Event = owner.correspondingEvent()
): Boolean {
  val result = bindService(service, conn, flag)
  owner.lifecycle.addObserver(LifecycleAwareObserver(
      lifecycleEvent,
      action = {
        unbindService(conn)
      }
  ))
  return result
}

/**
 * Version of [ContextWrapper.registerReceiver]
 *
 * todo
 */
@MainThread
fun ContextWrapper.registerReceiver(
    owner: LifecycleOwner,
    receiver: BroadcastReceiver,
    filter: IntentFilter,
    lifecycleEvent: Lifecycle.Event = owner.correspondingEvent()
) {
  registerReceiver(receiver, filter)
  owner.lifecycle.addObserver(LifecycleAwareObserver(
      lifecycleEvent,
      action = {
        unregisterReceiver(receiver)
      }
  ))
}

/**
 * Version of [ContextWrapper.registerReceiver]
 *
 * todo
 */
@RequiresApi(Build.VERSION_CODES.O)
@MainThread
fun ContextWrapper.registerReceiver(
    owner: LifecycleOwner,
    receiver: BroadcastReceiver,
    flags: Int,
    filter: IntentFilter,
    lifecycleEvent: Lifecycle.Event = owner.correspondingEvent()
) {
  registerReceiver(receiver, filter, flags)
  owner.lifecycle.addObserver(LifecycleAwareObserver(
      lifecycleEvent,
      action = {
        unregisterReceiver(receiver)
      }
  ))
}
