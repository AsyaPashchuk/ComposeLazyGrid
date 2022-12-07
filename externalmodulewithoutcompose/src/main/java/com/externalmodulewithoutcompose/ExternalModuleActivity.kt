package com.externalmodulewithoutcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ExternalModuleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_external_module)
    }
}