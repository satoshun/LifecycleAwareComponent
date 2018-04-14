package com.github.satoshun.arch.lifecycle.example

import android.Manifest
import android.animation.Animator
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Binder
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.github.satoshun.arch.lifecycle.animation.start
import com.github.satoshun.arch.lifecycle.content.bindService
import com.github.satoshun.arch.lifecycle.os.postDelayed
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

private const val FINISH_MILLS = 5000L
private const val BASE_MILLS = 10000L

class MainActivity : AppCompatActivity() {

  private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
  private lateinit var locationCallback: LocationCallback

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_act)

    fun testAnimate() {
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
    }

    fun testHandler() {
      Handler().postDelayed(this, BASE_MILLS) {
        TODO("never call")
      }
    }

    fun testBindService() {
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
    }

    testAnimate()
    testHandler()
    testBindService()
    testLocationService()

    Handler().postDelayed({
      finish()
    }, FINISH_MILLS)
  }

  private fun testLocationService() {
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    locationCallback = object : LocationCallback() {
      override fun onLocationResult(result: LocationResult?) {
        result ?: return
        for (location in result.locations) {
          Log.d("onLocationCallbackLocationResult", location.toString())
        }
      }

      override fun onLocationAvailability(availability: LocationAvailability?) {
        Log.d("onLocationAvailability", availability.toString())
      }
    }
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      fusedLocationProviderClient.requestLocationUpdates(
          LocationRequest(),
          locationCallback,
          null
      )
    } else {
      ActivityCompat.requestPermissions(
          this,
          arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
          1
      )
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    fusedLocationProviderClient.removeLocationUpdates(locationCallback)
  }

  override fun onRequestPermissionsResult(
      requestCode: Int,
      permissions: Array<out String>,
      grantResults: IntArray
  ) {
    if (requestCode == 1) {
      testLocationService()
    }
  }
}

/**
 * https://developer.android.com/guide/components/bound-services.html
 */
class LocalService : Service() {
  // Binder given to clients
  private val binder = LocalBinder()

  inner class LocalBinder : Binder() {
  }

  override fun onBind(intent: Intent): IBinder {
    return binder
  }
}
