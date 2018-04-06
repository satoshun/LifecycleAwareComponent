package com.github.satoshun.arch.lifecycle.os

import android.arch.lifecycle.LifecycleOwner
import android.os.Handler
import android.support.annotation.MainThread
import com.github.satoshun.arch.lifecycle.LifecycleAwareObserver

@MainThread
fun Handler.postDelayed(owner: LifecycleOwner, delayMillis: Long, r: () -> Unit) {
  val action = Runnable(r::invoke)
  postDelayed(action, delayMillis)
  owner.lifecycle.addObserver(LifecycleAwareObserver(
      didDestroy = {
        removeCallbacks(action)
      }
  ))
}
