package org.bmsk.deepmedi

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DeepMediApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}