package com.github.satoshun.arch.lifecycle

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import java.util.concurrent.CountDownLatch

class TestActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    setContentView(R.layout.test_activity)
  }
}

class TestService : Service() {
  // Binder given to clients
  private val binder = LocalBinder()

  var latch: CountDownLatch? = null
  var isBound = false

  inner class LocalBinder : Binder() {
    val service: TestService get() = this@TestService
  }

  override fun onBind(intent: Intent): IBinder {
    isBound = true
    return binder
  }

  override fun onUnbind(intent: Intent?): Boolean {
    isBound = false
    latch?.countDown()
    return super.onUnbind(intent)
  }
}

class TestBroadcastReceiver : BroadcastReceiver() {
  companion object {
    const val ACTION = "MY_NOTIFICATION"
  }

  var lastCountDown: Boolean = false
  var latch: CountDownLatch? = null

  override fun onReceive(context: Context, intent: Intent) {
    if (latch!!.count == 0L) {
      lastCountDown = false
      return
    }
    lastCountDown = true
    latch!!.countDown()
  }
}
