package com.equiperocket.compiler.v2.validation;

public class ExprValidator {

    public static boolean isBoolExprValid(String expression) {
        return !expression.isEmpty() &&
                !expression.contains("\"") &&
                containsRelOp(expression);
    }

    public static boolean isRelExprValid(String expression) {
        return !expression.isEmpty();
    }

    public static boolean isExprValid(String expression) {
        return !expression.isEmpty() &&
                !expression.contains("\"") &&
                !containsRelOp(expression);
//                !containsBoolOp(expression) &&
//                !containsBoolExpr(expression);
    }

    private static boolean containsRelOp(String expression) {
        return expression.contains("<") ||
                expression.contains(">") ||
                expression.contains("<=") ||
                expression.contains(">=") ||
                expression.contains("==") ||
                expression.contains("!=");
    }

//    private static boolean containsBoolOp(String expression) {
//        return expression.contains("OU") || expression.contains("E");
//    }
//
//    private static boolean containsBoolExpr(String expression) {
//        return expression.contains("NAO") ||
//                expression.contains("VERDADEIRO") ||
//                expression.contains("FALSO");
//    }
}
