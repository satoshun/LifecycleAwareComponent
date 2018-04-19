package com.github.satoshun.arch.lifecycle.view

import android.support.test.rule.ActivityTestRule
import com.github.satoshun.arch.lifecycle.TestActivity
import com.google.common.truth.Truth
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class ViewTest {
  @JvmField @Rule val rule = ActivityTestRule<TestActivity>(TestActivity::class.java)

  @Test
  fun postDelayed_call_to_success() {
    val latch = CountDownLatch(1)
    rule.activity.rootView.postDelayed(rule.activity, 100) {
      latch.countDown()
    }
    val result = latch.await(150, TimeUnit.MILLISECONDS)
    Truth.assertThat(result).isTrue()
  }

  @Test
  fun postDelayed_call_a_timeout() {
    val latch = CountDownLatch(1)
    rule.activity.rootView.postDelayed(rule.activity, 1000) {
      latch.countDown()
    }
    // task must be cancel So latch isn't advanced
    rule.finishActivity()
    val result = latch.await(1500, TimeUnit.MILLISECONDS)
    Truth.assertThat(result).isFalse()
  }
}
