package com.loona.hachathon.util;

import javax.persistence.AttributeConverter;
import java.util.*;

public class CsvAttributeConverter implements AttributeConverter<Set<String>, String> {
    @Override
    public String convertToDatabaseColumn(Set<String> attribute) {
        if (attribute == null) {
            return null;
        } else return String.join(",", attribute);
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return new HashSet<>();
        } else return new HashSet<>(Arrays.asList(dbData.split(",")));
    }
}
