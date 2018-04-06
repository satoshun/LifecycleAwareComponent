package com.github.satoshun.arch.lifecycle.animation

import android.arch.lifecycle.LifecycleOwner
import android.support.annotation.MainThread
import android.view.View
import android.view.ViewPropertyAnimator
import com.github.satoshun.arch.lifecycle.LifecycleAwareObserver

/**
 * todo
 */
@MainThread
fun ViewPropertyAnimator.start(
  owner: LifecycleOwner,
  target: View? = null,
  didDestroy: () -> Unit = {
    target?.clearAnimation()
    cancel()
  }
) {
  owner.lifecycle.addObserver(LifecycleAwareObserver(
      didDestroy = didDestroy
  ))
  start()
}
