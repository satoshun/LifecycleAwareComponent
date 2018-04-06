package com.github.satoshun.arch.lifecycle.animation

import android.arch.lifecycle.LifecycleOwner
import android.view.View
import android.view.ViewPropertyAnimator
import com.github.satoshun.arch.lifecycle.LifecycleAwareObserver

fun ViewPropertyAnimator.start(target: View, owner: LifecycleOwner) {
  owner.lifecycle.addObserver(LifecycleAwareObserver(
      didDestroy = {
        setListener(null)
        target.clearAnimation()
        cancel()
      }
  ))
  start()
}
