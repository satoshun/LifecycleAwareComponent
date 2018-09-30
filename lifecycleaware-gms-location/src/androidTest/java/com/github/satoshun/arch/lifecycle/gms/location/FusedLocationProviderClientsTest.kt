package com.github.satoshun.arch.lifecycle.gms.location

import androidx.appcompat.app.AppCompatActivity
import androidx.test.annotation.UiThreadTest
import androidx.test.rule.ActivityTestRule
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import org.junit.Rule
import org.junit.Test

class FusedLocationProviderClientsTest {
  @JvmField @Rule val rule = ActivityTestRule<TestActivity>(TestActivity::class.java)

  @Test
  @UiThreadTest
  fun requestLocationUpdates() {
    val client = LocationServices.getFusedLocationProviderClient(rule.activity)
    val request = LocationRequest()
    val callback = object : LocationCallback() {
    }
    client.requestLocationUpdates(
        rule.activity,
        request,
        callback
    )

    // todo: add test
  }
}

class TestActivity : AppCompatActivity()
