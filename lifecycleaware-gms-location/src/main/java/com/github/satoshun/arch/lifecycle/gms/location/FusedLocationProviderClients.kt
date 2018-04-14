package com.github.satoshun.arch.lifecycle.gms.location

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.os.Looper
import android.support.annotation.RequiresPermission
import com.github.satoshun.arch.lifecycle.LifecycleAwareObserver
import com.github.satoshun.arch.lifecycle.correspondingEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.tasks.Task

@SuppressLint("MissingPermission")
@RequiresPermission(anyOf = [
  Manifest.permission.ACCESS_COARSE_LOCATION,
  Manifest.permission.ACCESS_FINE_LOCATION
])
fun FusedLocationProviderClient.requestLocationUpdates(
    owner: LifecycleOwner,
    request: LocationRequest,
    locationCallback: LocationCallback,
    logger: Looper? = null,
    lifecycleEvent: Lifecycle.Event = owner.correspondingEvent()
): Task<Void> {
  val result = requestLocationUpdates(request, locationCallback, logger)
  owner.lifecycle.addObserver(LifecycleAwareObserver(
      lifecycleEvent = lifecycleEvent,
      action = {
        removeLocationUpdates(locationCallback)
      }
  ))
  return result
}
