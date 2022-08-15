package com.app.clockmanager.contracts

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContract

class DrawOverlayContract(private val _context: Context) : ActivityResultContract<Unit?, Boolean>() {

    override fun createIntent(context: Context, input: Unit?) = Intent(
        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
        Uri.parse("package:${context.packageName}")
    )

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return Settings.canDrawOverlays(_context)
    }

}