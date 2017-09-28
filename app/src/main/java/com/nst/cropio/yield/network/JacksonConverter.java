package com.nst.cropio.yield.network;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst.cropio.yield.util.CalendarHandler;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by yuriy on 9/28/17.
 */

public class JacksonConverter implements Converter {

    private static final String MIME_TYPE = "application/json; charset=UTF-8";
    private final ObjectMapper objectMapper;

    public JacksonConverter() {
        this(new ObjectMapper());
    }

    private JacksonConverter(ObjectMapper objectMapper) {
        objectMapper.setDateFormat(CalendarHandler.ISO_8601_DATE_FORMAT);
        this.objectMapper = objectMapper;
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructType(type);
            return objectMapper.readValue(body.in(), javaType);
        } catch (IOException e) {
            throw new ConversionException(e);
        }
    }

    @Override
    public TypedOutput toBody(Object object) {
        try {
            String json = objectMapper.writeValueAsString(object);
            return new TypedByteArray(MIME_TYPE, json.getBytes("UTF-8"));
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

}
