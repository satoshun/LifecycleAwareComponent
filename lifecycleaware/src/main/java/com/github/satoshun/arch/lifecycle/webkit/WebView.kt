package com.github.satoshun.arch.lifecycle.webkit

import android.arch.lifecycle.LifecycleOwner
import android.webkit.WebView
import com.github.satoshun.arch.lifecycle.GenericLifecycleAwareObserver

/**
 * todo
 */
fun WebView.bindLifecycle(
    owner: LifecycleOwner,
    onDestroy: () -> Unit = {
      removeAllViews()
      destroy()
    }
) {
  owner.lifecycle.addObserver(object : GenericLifecycleAwareObserver {
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
