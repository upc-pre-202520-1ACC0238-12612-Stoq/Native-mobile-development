package com.stoq.StockWise.shared.data.local

import android.content.Context
import android.content.SharedPreferences

object ProfileStorage {
    private const val PREFS_NAME = "profile_prefs"
    private const val KEY_NAME = "profile_name"
    private const val KEY_JOB_TITLE = "profile_job_title"
    private const val KEY_IMAGE_URI = "profile_image_uri"

    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveProfile(name: String, jobTitle: String, imageUri: String?) {
        sharedPreferences.edit()
            .putString(KEY_NAME, name)
            .putString(KEY_JOB_TITLE, jobTitle)
            .putString(KEY_IMAGE_URI, imageUri)
            .apply()
    }

    fun getName(): String? = sharedPreferences.getString(KEY_NAME, null)
    fun getJobTitle(): String? = sharedPreferences.getString(KEY_JOB_TITLE, null)
    fun getImageUri(): String? = sharedPreferences.getString(KEY_IMAGE_URI, null)

    fun clearProfile() {
        sharedPreferences.edit()
            .remove(KEY_NAME)
            .remove(KEY_JOB_TITLE)
            .remove(KEY_IMAGE_URI)
            .apply()
    }
}
