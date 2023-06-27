package org.bmsk.camera

import android.Manifest
import android.content.Context
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.Executors

class Camera(
    private val fragment: Fragment
) {
    private val context = fragment.requireContext()

    private val preview by lazy {
        Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
    }

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var previewView: PreviewView

    private var cameraExecutor = Executors.newSingleThreadExecutor()

    private var imageCapture: ImageCapture? = null

    fun initCamera(layout: ViewGroup) {
        previewView = PreviewView(context)
        layout.addView(previewView)
        permissionCheck(context)
    }

    fun shutdown() {
        cameraExecutor.shutdown()
    }

    private fun permissionCheck(context: Context) {
        val permissionList = listOf(Manifest.permission.CAMERA)

        if (!PermissionUtil.checkPermission(context, permissionList)) {
            PermissionUtil.requestPermission(fragment, permissionList)
        } else {
            openPreview()
        }
    }

    private fun openPreview() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            .also { providerFuture ->
                providerFuture.addListener({
                    startPreview()
                }, ContextCompat.getMainExecutor(context))
            }
    }

    private fun startPreview() {
        val cameraProvider = cameraProviderFuture.get()
        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                fragment as LifecycleOwner,
                cameraSelector,
                preview
            )
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}