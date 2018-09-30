object Vers {
  val compile_sdk = 28
  val min_sdk = 15
  val target_sdk = 28
  val agp = "3.2.0"

  val kotlin = "1.2.71"
  val ktlint = "0.24.0"
}

object Libs {
  val android_plugin = "com.android.tools.build:gradle:${Vers.agp}"
  val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Vers.kotlin}"
  val dokka_plugin = "org.jetbrains.dokka:dokka-android-gradle-plugin:0.9.16"

  val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Vers.kotlin}"

  val android_annotation = "androidx.annotation:annotation:1.0.0"

  val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.0.0"
  val livedata = "androidx.lifecycle:lifecycle-livedata:2.0.0"
  val gms = "com.google.android.gms:play-services-location:15.0.1"
  val task = "com.google.android.gms:play-services-tasks:15.0.1"

  val junit = "junit:junit:4.12"
  val test_runner = "androidx.test:runner:1.1.0-alpha4"
  val test_rule = "androidx.test:rules:1.1.0-alpha4"
  val espresso = "androidx.test.espresso:espresso-core:3.1.0-alpha4"

  val appcompat = "androidx.appcompat:appcompat:1.0.0"

  val truth = "com.google.truth:truth:0.39"
  val mockito_kotlin = "com.nhaarman:mockito-kotlin-kt1.1:1.5.0"
}
