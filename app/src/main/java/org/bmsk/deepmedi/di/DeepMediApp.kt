package org.bmsk.deepmedi.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DeepMediApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}