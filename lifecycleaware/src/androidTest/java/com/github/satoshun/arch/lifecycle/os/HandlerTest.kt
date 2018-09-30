package com.github.satoshun.arch.lifecycle.os

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.test.rule.ActivityTestRule
import com.github.satoshun.arch.lifecycle.TestActivity
import com.google.common.truth.Truth
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class HandlerTest {

  @JvmField @Rule val rule = ActivityTestRule<TestActivity>(TestActivity::class.java)

  @Test
  fun postDelayed_call_to_success() {
    val handler = Handler(Looper.getMainLooper())
    val latch = CountDownLatch(1)
    handler.postDelayed(rule.activity, 100) {
      latch.countDown()
    }
    val result = latch.await(150, TimeUnit.MILLISECONDS)
    Truth.assertThat(result).isTrue()
  }

  @Test
  fun postDelayed_call_a_timeout() {
    val handler = Handler(Looper.getMainLooper())
    val latch = CountDownLatch(1)
    handler.postDelayed(rule.activity, 1000) {
      latch.countDown()
    }
    // task must be cancel So latch isn't advanced
    rule.finishActivity()
    val result = latch.await(1500, TimeUnit.MILLISECONDS)
    Truth.assertThat(result).isFalse()
  }

  @Test
  fun postAtTime_call_to_success() {
    val handler = Handler(Looper.getMainLooper())
    val latch = CountDownLatch(1)
    val token = Any()
    val base = SystemClock.uptimeMillis()
    handler.postAtTime(rule.activity, base + 100, token) {
      latch.countDown()
    }
    val result = latch.await(150, TimeUnit.MILLISECONDS)
    Truth.assertThat(result).isTrue()
  }

  @Test
  fun postAtTime_call_a_timeout() {
    val handler = Handler(Looper.getMainLooper())
    val latch = CountDownLatch(1)
    val token = Any()
    val base = SystemClock.uptimeMillis()
    handler.postAtTime(rule.activity, base + 1000, token) {
      latch.countDown()
    }
    // task must be cancel So latch isn't advanced
    rule.finishActivity()
    val result = latch.await(1500, TimeUnit.MILLISECONDS)
    Truth.assertThat(result).isFalse()
  }
}
