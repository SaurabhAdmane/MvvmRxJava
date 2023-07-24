package com.saurabh.demo.networking;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by SaurabhA on 03,October,2020
 *
 */
public class SynerzipRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private ObjectMapper adapter;

    SynerzipRequestBodyConverter(ObjectMapper adapter) {
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        String enString = adapter.writeValueAsString(value);
        Log.e("Plain request", "enString: " + enString);
        byte[] bytes = adapter.writeValueAsBytes(enString);
        return RequestBody.create(MEDIA_TYPE, bytes);
    }
}
