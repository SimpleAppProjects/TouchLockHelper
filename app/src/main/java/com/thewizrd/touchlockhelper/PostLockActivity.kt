package com.thewizrd.touchlockhelper

import android.app.Activity
import android.os.Bundle

class PostLockActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(MainActivity.buildNoAutoStartIntent(this))
        finishAffinity()
    }
}