package org.bmsk.presentation.ui.camera

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.bmsk.camera.Camera
import org.bmsk.camera.recognition.FaceAnalyzerListener
import org.bmsk.presentation.R
import org.bmsk.presentation.databinding.FragmentCameraBinding

@AndroidEntryPoint
class CameraFragment : Fragment(), FaceAnalyzerListener {

    private val viewModel: CameraViewModel by viewModels()
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private val camera: Camera by lazy { Camera(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<FragmentCameraBinding>(
            inflater,
            R.layout.fragment_camera,
            container,
            false
        ).apply {
            lifecycleOwner = this@CameraFragment.viewLifecycleOwner
            viewModel = this@CameraFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        camera.initCamera(binding.cameraLayout, this)
        camera.startFaceDetect()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        camera.shutdown()
    }

    override fun detect() {
    }

    override fun notDetect() {
    }

    override fun detectProgress(message: String) {
    }

    override fun onFaceDetected(bitmap: Bitmap) {
        Log.e("CameraFragment", "onFaceDetected")
        camera.shutdown()
        viewModel.uploadImage(bitmap)
    }
}