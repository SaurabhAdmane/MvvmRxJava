package com.saurabh.demo.networking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


/**
 * Created by SaurabhA on 03,October,2020
 *
 */
public class SynerzipConverterFactory extends Converter.Factory {
    private final ObjectMapper mapper;

    private String TAG = SynerzipConverterFactory.class.getSimpleName();

    private SynerzipConverterFactory(ObjectMapper mapper) {
        if (mapper == null) throw new NullPointerException("mapper == null");
        this.mapper = mapper;
    }

    public static SynerzipConverterFactory create() {
        return create(
                new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE));
    }

    /**
     * Create an instance using {@code mapper} for conversion.
     */
    public static SynerzipConverterFactory create(ObjectMapper mapper) {
        return new SynerzipConverterFactory(mapper);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new SynerzipResponseBodyConverter(mapper, type);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new SynerzipRequestBodyConverter(mapper);
    }

}
