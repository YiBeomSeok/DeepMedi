package org.bmsk.camera.recognition

import android.graphics.Bitmap

interface FaceAnalyzerListener {
    fun detect()
    fun notDetect()
    fun detectProgress(message: String)
    fun onFaceDetected(bitmap: Bitmap)
}