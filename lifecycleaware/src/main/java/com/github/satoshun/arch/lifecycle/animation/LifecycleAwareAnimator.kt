@file:Suppress("NOTHING_TO_INLINE")

package com.github.satoshun.arch.lifecycle.animation

import android.view.View
import android.view.ViewPropertyAnimator
import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.github.satoshun.arch.lifecycle.LifecycleAwareObserver
import com.github.satoshun.arch.lifecycle.correspondingEvent

/**
 * Version of [ViewPropertyAnimator.start]
 */
@MainThread
fun ViewPropertyAnimator.start(
  owner: LifecycleOwner,
  target: View? = null,
  lifecycleEvent: Lifecycle.Event = owner.correspondingEvent(),
  action: () -> Unit = defaultAction(target)
) {
  start(owner.lifecycle, target, lifecycleEvent, action)
}

/**
 * Version of [ViewPropertyAnimator.start]
 */
@MainThread
fun ViewPropertyAnimator.start(
  lifecycle: Lifecycle,
  target: View? = null,
  lifecycleEvent: Lifecycle.Event = lifecycle.correspondingEvent(),
  action: () -> Unit = defaultAction(target)
) {
  lifecycle.addObserver(
      LifecycleAwareObserver(
          lifecycleEvent = lifecycleEvent,
          action = action
      )
  )
  start()
}

private inline fun ViewPropertyAnimator.defaultAction(view: View?) = {
  view?.clearAnimation()
  cancel()
}
