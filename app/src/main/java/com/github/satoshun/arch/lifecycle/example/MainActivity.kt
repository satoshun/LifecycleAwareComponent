package com.github.satoshun.arch.lifecycle.example

import android.animation.Animator
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.satoshun.arch.lifecycle.animation.start
import com.github.satoshun.arch.lifecycle.content.bindService
import com.github.satoshun.arch.lifecycle.os.postDelayed
import java.util.Random

private const val FINISH_MILLS = 5000L
private const val BASE_MILLS = 10000L

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_act)

    val target = findViewById<View>(R.id.text)
    target.animate()
        .setDuration(BASE_MILLS)
        .alpha(0f)
        .setListener(object : Animator.AnimatorListener {
          private var isCancel = false

          override fun onAnimationRepeat(animation: Animator?) {
          }

          override fun onAnimationEnd(animation: Animator) {
            if (!isCancel) {
              TODO("never call")
            }
          }

          override fun onAnimationCancel(animation: Animator?) {
            isCancel = true
          }

          override fun onAnimationStart(animation: Animator?) {
          }
        })
        .start(this, target)

    Handler().postDelayed(this, BASE_MILLS) {
      TODO("never call")
    }

    val intent = Intent(this, LocalService::class.java)
    bindService(
        this,
        intent,
        object : ServiceConnection {
          override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
          }

          override fun onServiceDisconnected(name: ComponentName?) {
          }
        },
        Context.BIND_AUTO_CREATE
    )

    Handler().postDelayed({
      finish()
    }, FINISH_MILLS)
  }
}

/**
 * https://developer.android.com/guide/components/bound-services.html
 */
class LocalService : Service() {
  // Binder given to clients
  private val binder = LocalBinder()
  // Random number generator
  private val generator = Random()

  inner class LocalBinder : Binder() {
    val service: LocalService get() = this@LocalService
  }

  override fun onBind(intent: Intent): IBinder {
    return binder
  }
}
