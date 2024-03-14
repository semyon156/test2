package com.example.figma

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.figma.domain.model.QueueModel
import com.example.figma.domain.queue.MyQueue
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import java.util.LinkedList

class MyApp(): Application() {
    companion object {
        lateinit var supabase: SupabaseClient
        lateinit var sharedPref: SharedPreferences
        lateinit var queue: MyQueue
    }

    override fun onCreate() {
        super.onCreate()

        supabase = createSupabaseClient(
            supabaseUrl = "https://xxfybzlohqrjvckttvis.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inh4ZnliemxvaHFyanZja3R0dmlzIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTcwNjUwNzkyMCwiZXhwIjoyMDIyMDgzOTIwfQ.4b0AHTLuTjsMZJ0sfuGBoYIcFj4Y_25q20sKggsZYno"
        ) {
            install(Auth) {
                autoLoadFromStorage = false
                alwaysAutoRefresh = false
            }
            install(Postgrest)
        }

        val context = applicationContext

        sharedPref = context.getSharedPreferences("skip_onboard", Context.MODE_PRIVATE)

        queue = MyQueue(LinkedList())
        queue.addItem(QueueModel(R.drawable.onboard_1, "Quick Delivery At Your Doorstep", "Enjoy quick pick-up and delivery to your destination"))
        queue.addItem(QueueModel(R.drawable.onboard_2, "Flexible Payment", "Different modes of payment either before and after delivery without stress"))
        queue.addItem(QueueModel(R.drawable.onboard_3, "Real-time Tracking", "Track your packages/items from the comfort of your home till final destination"))
    }
}