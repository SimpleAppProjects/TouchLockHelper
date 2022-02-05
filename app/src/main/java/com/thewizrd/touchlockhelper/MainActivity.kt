package com.thewizrd.touchlockhelper

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.thewizrd.touchlockhelper.databinding.ActivityMainBinding

class MainActivity : Activity() {
    companion object {
        private const val TAG = "TouchLock"

        private const val ACTION_ENABLE_WET_MODE =
            "com.google.android.wearable.action.ENABLE_WET_MODE"
        private const val EXTRA_RELAUNCH_COMPONENT_NAME = "relaunch_component_name"

        private const val EXTRA_NO_AUTO_LAUNCH = "TouchLockHelper.extra.NO_AUTOLAUNCH"

        fun buildNoAutoStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_NO_AUTO_LAUNCH, true)
            }
        }
    }

    private lateinit var binding: ActivityMainBinding
    private var mAutoLaunched = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate")

        handleIntent(intent)

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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent == null) return

        if (intent.getBooleanExtra(EXTRA_NO_AUTO_LAUNCH, false)) {
            mAutoLaunched = true
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart")

        if (Settings.isAutoLaunchTouchLockEnabled(this) && !mAutoLaunched) {
            Log.d(TAG, "auto-launching wet mode...")
            startWetMode()
            mAutoLaunched = true
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    private fun startWetMode() {
        Log.d(TAG, "Starting wet mode...")
        sendBroadcast(Intent(ACTION_ENABLE_WET_MODE).apply {
            putExtra(
                EXTRA_RELAUNCH_COMPONENT_NAME,
                ComponentName(this@MainActivity, PostLockActivity::class.java).flattenToString()
            )
        })
        // Go back home (launcher) once wet mode has started
        moveTaskToBack(true)
    }
}