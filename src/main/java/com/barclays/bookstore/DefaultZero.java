package com.barclays.bookstore;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DefaultZero extends JsonDeserializer<Double> {

    @Override
    public Double deserialize(JsonParser jsonParser,
        DeserializationContext deserializationContext) {
        try {
            return jsonParser.getDoubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    public Double getNullValue(DeserializationContext ctxt) {
        return 0.0;
    }
}
