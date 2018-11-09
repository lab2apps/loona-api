package com.loona.hachathon.util;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;

public class CsvAttributeConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return String.join(",", attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return Arrays.asList(dbData.split(","));
    }
}
