package com.example.foregroundservices

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.foregroundservices.ui.theme.ForegroundServicesTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            requestNotificationPermission()
        }
        setContent {
            ForegroundServicesTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            Intent(this@MainActivity, CounterService::class.java).also {
                                it.action = CounterService.CounterAction.START.name
                                startService(it)
                            }
                        }
                    ) {
                        Text(text = "Start Counter")
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            Intent(this@MainActivity, CounterService::class.java).also {
                                it.action = CounterService.CounterAction.STOP.name
                                startService(it)
                            }
                        }
                    ) {
                        Text(text = "Stop Counter")
                    }
                }
            }
        }
    }

    private fun requestNotificationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            100
            )
    }

}