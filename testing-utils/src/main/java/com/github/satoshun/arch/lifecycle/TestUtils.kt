package com.github.satoshun.arch.lifecycle

import com.google.common.truth.Truth
import kotlin.reflect.KClass

fun Any?.isNull() = Truth.assertThat(this).isNull()
fun Any?.isNotNull() = Truth.assertThat(this).isNotNull()
fun Any?.isEqualTo(other: Any?) = Truth.assertThat(this).isEqualTo(other)
fun Any?.isInstanceOf(other: KClass<*>) = Truth.assertThat(this).isInstanceOf(other.java)
fun Boolean?.isTrue() = Truth.assertThat(this).isTrue()
fun Boolean?.isFalse() = Truth.assertThat(this).isFalse()
