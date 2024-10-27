package com.equiperocket.compiler.util;

public interface JavaConverter {

    default String toJavaType(String type) {
        return switch (type) {
            case "numero" -> "int";
            case "decimal" -> "double";
            case "bool" -> "boolean";
            case "texto" -> "String";
            default -> "";
        };
    }

    default String toScannerOfType(String varType) {
        return switch (varType) {
            case "int" -> "Integer.parseInt(sc.nextLine());\n";
            case "double" -> "Double.parseDouble(sc.nextLine());\n";
            case "boolean" -> "Boolean.parseBoolean(sc.nextLine());\n";
            case "String" -> "sc.nextLine();\n";
            default -> "";
        };
    }
}
