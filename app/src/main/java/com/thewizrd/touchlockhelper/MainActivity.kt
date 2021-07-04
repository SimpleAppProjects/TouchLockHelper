package com.thewizrd.touchlockhelper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.thewizrd.touchlockhelper.databinding.ActivityMainBinding

class MainActivity : Activity() {
    companion object {
        private const val ACTION_ENABLE_WET_MODE =
            "com.google.android.wearable.action.ENABLE_WET_MODE"
    }

    private lateinit var binding: ActivityMainBinding
    private var mAutoLaunched = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.launchTouchlockPref.setOnClickListener {
            startWetMode()
        }

        binding.touchlockPrefSwitch.isChecked = Settings.isAutoLaunchTouchLockEnabled(this)
        binding.touchlockPrefSwitch.setOnClickListener {
            val state = !Settings.isAutoLaunchTouchLockEnabled(this)
            Settings.setAutoLaunchTouchLockEnabled(this, state)
            binding.touchlockPrefSwitch.isChecked = state
        }
    }

    override fun onResume() {
        super.onResume()
        if (Settings.isAutoLaunchTouchLockEnabled(this) && !mAutoLaunched) {
            startWetMode()
            mAutoLaunched = true
        }
    }

    private fun startWetMode() {
        sendBroadcast(Intent(ACTION_ENABLE_WET_MODE))
    }
}