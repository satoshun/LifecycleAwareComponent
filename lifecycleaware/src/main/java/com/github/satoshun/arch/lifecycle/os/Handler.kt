package com.github.satoshun.arch.lifecycle.os

import android.arch.lifecycle.LifecycleOwner
import android.os.Handler
import android.support.annotation.MainThread
import com.github.satoshun.arch.lifecycle.LifecycleAwareObserver

@MainThread
fun Handler.postDelayed(owner: LifecycleOwner, delayInMillis: Long, action: () -> Unit) {
  val runnable = Runnable(action::invoke)
  postDelayed(runnable, delayInMillis)
  owner.lifecycle.addObserver(LifecycleAwareObserver(
      didDestroy = {
        removeCallbacks(runnable)
      }
  ))
}
