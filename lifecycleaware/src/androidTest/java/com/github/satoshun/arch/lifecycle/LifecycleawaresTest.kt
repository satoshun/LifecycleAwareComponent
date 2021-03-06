package com.github.satoshun.arch.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.common.truth.Truth
import org.junit.Test

class LifecycleawaresTest {
  @Test
  fun correct_correspondingEvent() {
    var owner = LifecycleOwner {
      TestLifecycle(Lifecycle.State.CREATED)
    }
    Truth.assertThat(owner.correspondingEvent()).isEqualTo(Lifecycle.Event.ON_DESTROY)

    owner = LifecycleOwner {
      TestLifecycle(Lifecycle.State.STARTED)
    }
    Truth.assertThat(owner.correspondingEvent()).isEqualTo(Lifecycle.Event.ON_STOP)

    owner = LifecycleOwner {
      TestLifecycle(Lifecycle.State.RESUMED)
    }
    Truth.assertThat(owner.correspondingEvent()).isEqualTo(Lifecycle.Event.ON_PAUSE)
  }
}

internal class TestLifecycle(
  private val currentState: Lifecycle.State
) : Lifecycle() {
  override fun addObserver(observer: LifecycleObserver) {
    TODO("not implemented")
  }

  override fun removeObserver(observer: LifecycleObserver) {
    TODO("not implemented")
  }

  override fun getCurrentState(): State {
    return currentState
  }
}
