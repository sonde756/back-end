package com.br.techroom.utils;

import com.br.techroom.enums.RoleName;
import com.br.techroom.service.exception.InvalidEnumValueException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ConvertingType {

    public static String listToString(List<? extends Enum<?>> enums) {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, enums.size())
                .forEach(i -> {
                    sb.append(enums.get(i));
                    if (i < enums.size() - 1) sb.append(", ");
                });
        return sb.toString();
    }

    public static <T extends Enum<T>> T convertStringToEnum(Class<T> enumClass, String enumOnString) {
        if (enumClass.equals(RoleName.class) & enumOnString == null) {
            enumOnString = "role_user";
        }
        try {
            return Enum.valueOf(enumClass, enumOnString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumValueException(enumOnString, enumClass.toString(),
                    listToString(Arrays.asList(RoleName.values())));
        }
    }
}