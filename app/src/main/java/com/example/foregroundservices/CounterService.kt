package com.example.foregroundservices

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//Use hilt annotation in order to Inject  @AndroidComponent
class CounterService : Service() {

    private val counter = Counter()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        when (intent?.action) {
            CounterAction.START.name -> start()
            CounterAction.STOP.name -> stop()

        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        CoroutineScope(Dispatchers.Default).launch {
            counter.start().collect { counterValue ->
                Log.d("Counter", counterValue.toString())
                notification(counterValue)
            }
        }
    }


    private fun notification(counterValue : Int){
        val counterNotification = NotificationCompat
            .Builder(this,"counter_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Counter")
            .setContentText(counterValue.toString())
            .setStyle(NotificationCompat.BigTextStyle())
            .build()
        startForeground(1,counterNotification)
    }

    private fun stop() {
        counter.stop()
        stopSelf()  // A function from service class it is used to stop the service
    }


    enum class CounterAction {
        START, RESUME, RESTART, PAUSE , STOP
    }


}