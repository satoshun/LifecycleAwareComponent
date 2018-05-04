@file:Suppress("NOTHING_TO_INLINE")

package com.github.satoshun.arch.lifecycle.webkit

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.webkit.WebView
import com.github.satoshun.arch.lifecycle.GenericLifecycleAwareObserver

/**
 * Handle lifecycle between Lifecycle and WebView.
 */
fun WebView.bindLifecycle(
    owner: LifecycleOwner,
    onDestroy: () -> Unit = defaultAction()
) {
  bindLifecycle(owner.lifecycle, onDestroy)
}

/**
 * Handle lifecycle between Lifecycle and WebView.
 */
fun WebView.bindLifecycle(
    lifecycle: Lifecycle,
    onDestroy: () -> Unit = defaultAction()
) {
  lifecycle.addObserver(object : GenericLifecycleAwareObserver {
    override fun onResume(owner: LifecycleOwner) {
      this@bindLifecycle.onResume()
    }

    override fun onPause(owner: LifecycleOwner) {
      this@bindLifecycle.onPause()
    }

    override fun onDestroy(owner: LifecycleOwner) {
      onDestroy()
    }
  })
}

private inline fun WebView.defaultAction() = {
  removeAllViews()
  destroy()
}
