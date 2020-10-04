package com.saurabh.synerzipdemo.networking;

import android.util.Log;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Scanner;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by SaurabhA on 03,October,2020
 *
 */
public class SynerzipResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final ObjectMapper adapter;
    private Type type;
    private String TAG = SynerzipResponseBodyConverter.class.getSimpleName();

    SynerzipResponseBodyConverter(ObjectMapper adapter, Type type) {
        this.adapter = adapter;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JavaType javaType = adapter.getTypeFactory().constructType(type);
        try {
            String data = convertStreamToString(value.byteStream());
            return adapter.readValue(data, javaType);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getLocalizedMessage());
            throw e;
        } finally {
            value.close();
        }
    }

    private String convertStreamToString(InputStream charStream) {
        Scanner scanner = new Scanner(charStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}