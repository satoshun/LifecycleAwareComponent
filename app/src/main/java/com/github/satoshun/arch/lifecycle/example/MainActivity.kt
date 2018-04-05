package com.github.satoshun.arch.lifecycle.example

import android.animation.Animator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.satoshun.arch.lifecycle.animation.start

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_act)

    val target = findViewById<View>(R.id.text)
    target.animate()
        .setDuration(5000)
        .alpha(0f)
        .setListener(object : Animator.AnimatorListener {
          override fun onAnimationRepeat(animation: Animator?) {
          }

          override fun onAnimationEnd(animation: Animator?) {
            TODO("not implemented")
          }

          override fun onAnimationCancel(animation: Animator?) {
          }

          override fun onAnimationStart(animation: Animator?) {
          }
        })
        .start(target, this)
  }
}
