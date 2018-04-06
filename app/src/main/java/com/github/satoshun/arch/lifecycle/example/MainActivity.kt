package com.github.satoshun.arch.lifecycle.example

import android.animation.Animator
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.satoshun.arch.lifecycle.animation.start
import com.github.satoshun.arch.lifecycle.os.postDelayed

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
          override fun onAnimationRepeat(animation: Animator?) {
          }

          override fun onAnimationEnd(animation: Animator?) {
            TODO("never call")
          }

          override fun onAnimationCancel(animation: Animator?) {
          }

          override fun onAnimationStart(animation: Animator?) {
          }
        })
        .start(target, this)

    Handler().postDelayed(this, BASE_MILLS) {
      TODO("never call")
    }

    Handler().postDelayed({
      finish()
    }, 5000)
  }
}
