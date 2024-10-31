package com.equiperocket.compiler.util;

import static com.equiperocket.compiler.constants.JavaConstants.TYPE_BOOLEAN;
import static com.equiperocket.compiler.constants.JavaConstants.TYPE_DOUBLE;
import static com.equiperocket.compiler.constants.JavaConstants.TYPE_INT;
import static com.equiperocket.compiler.constants.JavaConstants.TYPE_STRING;

public class TypeMapper {

    public static String toJavaType(String sourceType) {
        return switch (sourceType.toLowerCase()) {
            case "inteiro" -> TYPE_INT;
            case "decimal" -> TYPE_DOUBLE;
            case "bool" -> TYPE_BOOLEAN;
            case "texto" -> TYPE_STRING;
            default -> "";
        };
    }

    public static String getScannerMethod(String type) {
        return switch (type) {
            case TYPE_INT -> "Integer.parseInt(sc.nextLine())";
            case TYPE_DOUBLE -> "Double.parseDouble(sc.nextLine())";
            case TYPE_BOOLEAN -> "Boolean.parseBoolean(sc.nextLine())";
            case TYPE_STRING -> "sc.nextLine()";
            default -> "";
        };
    }
}
