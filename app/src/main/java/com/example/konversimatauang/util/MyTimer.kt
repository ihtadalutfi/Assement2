package com.example.konversimatauang.util

import android.os.Looper
import android.util.Log
import java.util.logging.Handler

class MyTimer {

    private var secondsCount = 0
    private var handler = android.os.Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    fun startTimer() {
        runnable = Runnable {
            secondsCount++
            Log.i("Timer", "Detik ke: $secondsCount")
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }
    fun stopTimer() {
        handler.removeCallbacks(runnable)
    }

}