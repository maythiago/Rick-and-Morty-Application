package com.thiago.rickandmortyapplication

import android.content.res.Resources
import java.io.IOException
import java.io.InputStream

internal object Util {
    fun loadStringFromRawResource(resources: Resources, resId: Int): String {
        val rawResource = resources.openRawResource(resId)
        val content = streamToString(rawResource)
        try {
            rawResource.close()
        } catch (e: IOException) {
        }

        return content
    }

    private fun streamToString(`in`: InputStream): String {
        val inputAsString = `in`.bufferedReader().use { it.readText() }  // defaults to UTF-8
        return inputAsString
    }

}