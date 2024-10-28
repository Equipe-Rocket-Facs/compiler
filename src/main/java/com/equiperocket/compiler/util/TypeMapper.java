package com.equiperocket.compiler.util;


import com.equiperocket.compiler.constants.JavaConstants;

public final class TypeMapper {
    private TypeMapper() {}

    public static String toJavaType(String sourceType) {
        return switch (sourceType.toLowerCase()) {
            case "numero" -> JavaConstants.TYPE_INT;
            case "decimal" -> JavaConstants.TYPE_DOUBLE;
            case "bool" -> JavaConstants.TYPE_BOOLEAN;
            case "texto" -> JavaConstants.TYPE_STRING;
            default -> throw new IllegalArgumentException("Tipo não suportado: " + sourceType);
        };
    }

    public static String getDefaultValue(String type) {
        return switch (type) {
            case JavaConstants.TYPE_INT -> "0";
            case JavaConstants.TYPE_DOUBLE -> "0.0";
            case JavaConstants.TYPE_BOOLEAN -> "false";
            case JavaConstants.TYPE_STRING -> "\"\"";
            default -> throw new IllegalArgumentException("Tipo não suportado: " + type);
        };
    }

    public static String getScannerMethod(String type) {
        return switch (type) {
            case JavaConstants.TYPE_INT -> "Integer.parseInt(sc.nextLine())";
            case JavaConstants.TYPE_DOUBLE -> "Double.parseDouble(sc.nextLine())";
            case JavaConstants.TYPE_BOOLEAN -> "Boolean.parseBoolean(sc.nextLine())";
            case JavaConstants.TYPE_STRING -> "sc.nextLine()";
            default -> throw new IllegalArgumentException("Tipo não suportado para leitura: " + type);
        };
    }
}