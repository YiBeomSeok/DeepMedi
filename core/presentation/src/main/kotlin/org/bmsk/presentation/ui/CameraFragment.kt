package org.bmsk.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.bmsk.camera.Camera
import org.bmsk.presentation.R
import org.bmsk.presentation.databinding.FragmentCameraBinding

@AndroidEntryPoint
class CameraFragment : Fragment() {

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

        camera.initCamera(binding.cameraLayout)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        camera.shutdown()
    }
}