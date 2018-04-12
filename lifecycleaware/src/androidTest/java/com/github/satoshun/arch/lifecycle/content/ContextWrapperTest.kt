package com.github.satoshun.arch.lifecycle.content

import android.Manifest
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.test.rule.ActivityTestRule
import android.support.v4.app.FragmentActivity
import com.github.satoshun.arch.lifecycle.TestActivity
import com.github.satoshun.arch.lifecycle.TestBroadcastReceiver
import com.github.satoshun.arch.lifecycle.TestService
import com.google.common.truth.Truth
import org.junit.Assume
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

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

    rule.activity.simulateLifecycleEvent()

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
        filter,
        flags,
        Lifecycle.Event.ON_PAUSE
    )

    Truth.assertThat(onUnbindLatch.count).isEqualTo(1)

    rule.activity.sendBroadcast(Intent(TestBroadcastReceiver.ACTION))

    onUnbindLatch.await()
    Truth.assertThat(onUnbindLatch.count).isEqualTo(0)
    Truth.assertThat(br.lastCountDown).isTrue()

    rule.activity.simulateLifecycleEvent()

    rule.activity.sendBroadcast(Intent(TestBroadcastReceiver.ACTION))

    // already released BroadcastReceiver
    Truth.assertThat(br.lastCountDown).isTrue()
  }

  @Test
  fun bindRegister__to_call_unbindRegister_with_allow_permission() {
    val onUnbindLatch = CountDownLatch(1)
    val br = TestBroadcastReceiver().apply {
      latch = onUnbindLatch
    }
    val filter = IntentFilter(TestBroadcastReceiver.ACTION)
    val allowPermission = Manifest.permission.INTERNET
    val scheduler = Handler(Looper.getMainLooper())
    rule.activity.registerReceiver(
        rule.activity,
        br,
        filter,
        allowPermission,
        scheduler,
        Lifecycle.Event.ON_PAUSE
    )

    Truth.assertThat(onUnbindLatch.count).isEqualTo(1)

    rule.activity.sendBroadcast(Intent(TestBroadcastReceiver.ACTION))

    onUnbindLatch.await(2, TimeUnit.SECONDS)
    Truth.assertThat(onUnbindLatch.count).isEqualTo(0)
    Truth.assertThat(br.lastCountDown).isTrue()

    rule.activity.simulateLifecycleEvent()

    rule.activity.sendBroadcast(Intent(TestBroadcastReceiver.ACTION))

    // already released BroadcastReceiver
    Truth.assertThat(br.lastCountDown).isTrue()
  }

  @Test
  fun bindRegister__to_call_unbindRegister_with_deny_permission() {
    val onUnbindLatch = CountDownLatch(1)
    val br = TestBroadcastReceiver().apply {
      latch = onUnbindLatch
    }
    val filter = IntentFilter(TestBroadcastReceiver.ACTION)
    val denyPermission = Manifest.permission.CALL_PHONE
    val scheduler = Handler(Looper.getMainLooper())
    rule.activity.registerReceiver(
        rule.activity,
        br,
        filter,
        denyPermission,
        scheduler,
        Lifecycle.Event.ON_PAUSE
    )

    Truth.assertThat(onUnbindLatch.count).isEqualTo(1)

    rule.activity.sendBroadcast(Intent(TestBroadcastReceiver.ACTION))

    onUnbindLatch.await(2, TimeUnit.SECONDS)

    // no reach any events
    Truth.assertThat(onUnbindLatch.count).isEqualTo(1)
    Truth.assertThat(br.lastCountDown).isFalse()
  }

  @Test
  fun bindRegister__to_call_unbindRegister_with_allow_permission_and_flags() {
    Assume.assumeTrue(android.os.Build.VERSION.SDK_INT >= 26)
    val onUnbindLatch = CountDownLatch(1)
    val br = TestBroadcastReceiver().apply {
      latch = onUnbindLatch
    }
    val filter = IntentFilter(TestBroadcastReceiver.ACTION)
    val allowPermission = Manifest.permission.INTERNET
    val scheduler = Handler(Looper.getMainLooper())
    val flags = 0
    rule.activity.registerReceiver(
        rule.activity,
        br,
        filter,
        allowPermission,
        scheduler,
        flags,
        Lifecycle.Event.ON_PAUSE
    )

    Truth.assertThat(onUnbindLatch.count).isEqualTo(1)

    rule.activity.sendBroadcast(Intent(TestBroadcastReceiver.ACTION))

    onUnbindLatch.await(2, TimeUnit.SECONDS)
    Truth.assertThat(onUnbindLatch.count).isEqualTo(0)
    Truth.assertThat(br.lastCountDown).isTrue()

    rule.activity.simulateLifecycleEvent()

    rule.activity.sendBroadcast(Intent(TestBroadcastReceiver.ACTION))

    // already released BroadcastReceiver
    Truth.assertThat(br.lastCountDown).isTrue()
  }

  @Test
  fun bindRegister__to_call_unbindRegister_with_deny_permission_and_flags() {
    Assume.assumeTrue(android.os.Build.VERSION.SDK_INT >= 26)
    val onUnbindLatch = CountDownLatch(1)
    val br = TestBroadcastReceiver().apply {
      latch = onUnbindLatch
    }
    val filter = IntentFilter(TestBroadcastReceiver.ACTION)
    val denyPermission = Manifest.permission.CALL_PHONE
    val scheduler = Handler(Looper.getMainLooper())
    val flags = 0
    rule.activity.registerReceiver(
        rule.activity,
        br,
        filter,
        denyPermission,
        scheduler,
        flags,
        Lifecycle.Event.ON_PAUSE
    )

    Truth.assertThat(onUnbindLatch.count).isEqualTo(1)

    rule.activity.sendBroadcast(Intent(TestBroadcastReceiver.ACTION))

    onUnbindLatch.await(2, TimeUnit.SECONDS)

    // no reach any events
    Truth.assertThat(onUnbindLatch.count).isEqualTo(1)
    Truth.assertThat(br.lastCountDown).isFalse()
  }
}

private fun FragmentActivity.simulateLifecycleEvent(
    event: Lifecycle.Event = Lifecycle.Event.ON_PAUSE
) {
  // todo: Is it best to simulate a ON_PAUSE lifecycle Event?
  (lifecycle as LifecycleRegistry).handleLifecycleEvent(event)
}
