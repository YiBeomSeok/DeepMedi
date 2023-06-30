package org.bmsk.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import org.bmsk.presentation.R
import org.bmsk.presentation.databinding.ActivityMainBinding
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var backPressedTime = 0L
    private val exitAppWhenBackButtonPressedTwiceCallback =
        createExitAppWhenBackButtonPressedTwiceCallback()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }
        onBackPressedDispatcher.addCallback(exitAppWhenBackButtonPressedTwiceCallback)
    }

    private fun createExitAppWhenBackButtonPressedTwiceCallback() =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backButtonPressedTwiceWithinFinishInterval()) {
                    exitApp()
                } else {
                    backPressedTime = System.currentTimeMillis()
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.guide_double_tab_exit),
                        Toast.LENGTH_SHORT
                    ).show();
                }
            }

            private fun backButtonPressedTwiceWithinFinishInterval(): Boolean {
                val currentTime = System.currentTimeMillis()
                val timeSinceLastBackButtonPress = currentTime - backPressedTime
                return timeSinceLastBackButtonPress in 0..FINISH_INTERVAL_TIME
            }

            private fun exitApp() {
                ActivityCompat.finishAffinity(this@MainActivity)
                exitProcess(0)
            }
        }

    companion object {
        private const val FINISH_INTERVAL_TIME = 1000L
    }
}