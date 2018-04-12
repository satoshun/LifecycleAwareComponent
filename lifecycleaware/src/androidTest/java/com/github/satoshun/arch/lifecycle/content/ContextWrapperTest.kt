package com.github.satoshun.arch.lifecycle.content

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.IBinder
import android.support.test.rule.ActivityTestRule
import com.github.satoshun.arch.lifecycle.TestActivity
import com.github.satoshun.arch.lifecycle.TestBroadcastReceiver
import com.github.satoshun.arch.lifecycle.TestService
import com.google.common.truth.Truth
import org.junit.Assume
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch

class ContextWrapperTest {
  @JvmField @Rule val rule = ActivityTestRule<TestActivity>(TestActivity::class.java)

  @Test
  fun bindService__to_call_unbindService() {
    val onUnbindLatch = CountDownLatch(1)
    val onServiceConnectedLatch = CountDownLatch(1)
    var testService: TestService? = null
    rule.activity.bindService(
        rule.activity,
        Intent(rule.activity, TestService::class.java),
        object : ServiceConnection {
          override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            testService = (service as TestService.LocalBinder).service
            testService!!.latch = onUnbindLatch
            onServiceConnectedLatch.countDown()
          }

          override fun onServiceDisconnected(name: ComponentName?) {
          }
        },
        Context.BIND_AUTO_CREATE
    )

    onServiceConnectedLatch.await()
    Truth.assertThat(testService!!.isBound).isTrue()

    rule.finishActivity()

    onUnbindLatch.await()
    Truth.assertThat(testService!!.isBound).isFalse()
  }

  @Test
  fun bindRegister__to_call_unbindRegister() {
    val onUnbindLatch = CountDownLatch(1)
    val br = TestBroadcastReceiver().apply {
      latch = onUnbindLatch
    }
    val filter = IntentFilter(TestBroadcastReceiver.ACTION)
    rule.activity.registerReceiver(
        rule.activity,
        br,
        filter,
        Lifecycle.Event.ON_PAUSE
    )

    Truth.assertThat(onUnbindLatch.count).isEqualTo(1)

    rule.activity.sendBroadcast(Intent(TestBroadcastReceiver.ACTION))

    onUnbindLatch.await()
    Truth.assertThat(onUnbindLatch.count).isEqualTo(0)
    Truth.assertThat(br.lastCountDown).isTrue()

    // todo: Is it best to simulate a ON_PAUSE lifecycle Event?
    (rule.activity.lifecycle as LifecycleRegistry).handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)

    rule.activity.sendBroadcast(Intent(TestBroadcastReceiver.ACTION))

    // already released BroadcastReceiver
    Truth.assertThat(br.lastCountDown).isTrue()
  }

  @Test
  fun bindRegister__to_call_unbindRegister_with_flags() {
    Assume.assumeTrue(android.os.Build.VERSION.SDK_INT >= 26)
    val onUnbindLatch = CountDownLatch(1)
    val br = TestBroadcastReceiver().apply {
      latch = onUnbindLatch
    }
    val filter = IntentFilter(TestBroadcastReceiver.ACTION)
    val flags = 0
    rule.activity.registerReceiver(
        rule.activity,
        br,
        flags,
        filter,
        Lifecycle.Event.ON_PAUSE
    )

    Truth.assertThat(onUnbindLatch.count).isEqualTo(1)

    rule.activity.sendBroadcast(Intent(TestBroadcastReceiver.ACTION))

    onUnbindLatch.await()
    Truth.assertThat(onUnbindLatch.count).isEqualTo(0)
    Truth.assertThat(br.lastCountDown).isTrue()

    // todo: Is it best to simulate a ON_PAUSE lifecycle Event?
    (rule.activity.lifecycle as LifecycleRegistry).handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)

    rule.activity.sendBroadcast(Intent(TestBroadcastReceiver.ACTION))

    // already released BroadcastReceiver
    Truth.assertThat(br.lastCountDown).isTrue()
  }
}
