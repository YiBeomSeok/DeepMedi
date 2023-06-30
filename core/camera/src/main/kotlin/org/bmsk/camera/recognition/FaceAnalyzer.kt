package org.bmsk.camera.recognition

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.lifecycle.Lifecycle
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

internal class FaceAnalyzer(
    lifecycle: Lifecycle,
    private val listener: FaceAnalyzerListener?
) : ImageAnalysis.Analyzer {

    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)  // 정확한 얼굴 인식
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL) // 얼굴의 모든 윤곽 탐지
        .setMinFaceSize(0.4F) // 카메라 뷰에서 탐지될 수 있는 얼굴의 최소 크기: 카메라 뷰의 너비나 높이의 40% 크기의 얼굴 탐지
        .build()

    private val detector = FaceDetection.getClient(options)
    private var detectStatus = FaceRecognitionStatus.UnDetect

    init {
        lifecycle.addObserver(detector)
    }

    override fun analyze(image: ImageProxy) {
        detectFaces(image)
    }

    private fun detectFaces(imageProxy: ImageProxy) {
        val image = InputImage.fromMediaImage(
            imageProxy.image as Image,
            imageProxy.imageInfo.rotationDegrees
        )

        val bitmap = imageToBitmap(imageProxy.image as Image)

        val successListener = OnSuccessListener<List<Face>> { faces ->
            val face = faces.firstOrNull()

            if (face != null) {
                if (detectStatus == FaceRecognitionStatus.UnDetect) {
                    detectStatus = FaceRecognitionStatus.Detect
                    listener?.detect()
                    listener?.detectProgress("얼굴이 인식되었습니다.")
                    if (bitmap != null) {
                        listener?.onFaceDetected(bitmap)
                    } // Pass Bitmap to listenr
                }
            } else if (detectStatus != FaceRecognitionStatus.Detect) {
                detectStatus = FaceRecognitionStatus.UnDetect
                listener?.notDetect()
                listener?.detectProgress("얼굴을 인식하지 못했습니다.")
            }
        }
        val failureListener = OnFailureListener {
            Log.e("FaceAnalyzer", "failure")
            detectStatus = FaceRecognitionStatus.UnDetect
        }

        detector.process(image)
            .addOnSuccessListener(successListener)
            .addOnFailureListener(failureListener)
            .addOnCompleteListener {
                imageProxy.close()
            }
    }

    private fun imageToBitmap(image: Image): Bitmap? {
        val nv21 = YuvImage(
            image.planes[0].buffer.toByteArray(),
            ImageFormat.NV21,
            image.width,
            image.height,
            null
        )
        val out = ByteArrayOutputStream()
        nv21.compressToJpeg(Rect(0, 0, nv21.width, nv21.height), 100, out)
        val imageBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    // ByteBuffer에서 ByteArray로 변환
    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer. Buffer flip might not be a good idea here, because it affects the buffer original state
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer content to the byte array
        return data // Return the byte array
    }
}