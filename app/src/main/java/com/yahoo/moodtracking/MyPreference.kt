package com.yahoo.moodtracking

import android.content.Context

class MyPreference (context: Context){

    val PREFERENCE_NAME = "rateValue"
       val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
}