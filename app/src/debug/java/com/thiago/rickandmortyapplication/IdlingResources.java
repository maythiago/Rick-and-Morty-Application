package com.thiago.rickandmortyapplication;


import okhttp3.OkHttpClient;

public class IdlingResources {
    public static void registerOkHttp(OkHttpClient client) {
        //TODO with migration to androidx, this lib isnt compile
//        IdlingRegistry.getInstance().register(OkHttp3IdlingResource.create(
//                "okhttp", client));

    }
}

