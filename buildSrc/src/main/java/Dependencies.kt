object Vers {
  val compile_sdk = 27
  val min_sdk = 15
  val target_sdk = 27

  val kotlin = "1.2.31"
  val support_lib = "27.1.1"
  val ktlint = "0.21.0"
}

object Libs {
  val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jre7:${Vers.kotlin}"

  val viewmodel = "android.arch.lifecycle:viewmodel:1.1.1"
  val livedata = "android.arch.lifecycle:livedata:1.1.1"
  val gms = "com.google.android.gms:play-services-location:15.0.0"
}
