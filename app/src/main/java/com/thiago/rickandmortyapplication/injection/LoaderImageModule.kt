package com.thiago.rickandmortyapplication.injection

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream


@GlideModule
class LoaderImageModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val bitmapPoolSize: Long = 30 * 1024 * 1024
        val lruBitmapPool = LruBitmapPool(bitmapPoolSize)
        builder.setDiskCache(ExternalPreferredCacheDiskCacheFactory(context))
        builder.setBitmapPool(lruBitmapPool)
        builder.setLogLevel(Log.DEBUG)
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        var okhttp = OkHttpClient.Builder().build()
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okhttp))

    }
}