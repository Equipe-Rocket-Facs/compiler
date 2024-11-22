package com.equiperocket.compiler.v2.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static boolean isNumeric(String expression) {
        String regex = "\\b[-+]?[0-9]+\\.[0-9]+\\b|\\b[-+]?[0-9]+\\b";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expression);

        return matcher.find() || containsMathOp(expression);
    }

    private static boolean containsRelOp(String expression) {
        return expression.contains("<") ||
                expression.contains(">") ||
                expression.contains("<=") ||
                expression.contains(">=") ||
                expression.contains("==") ||
                expression.contains("!=");
    }

    private static boolean containsMathOp(String expression) {
        return expression.contains("+") ||
                expression.contains("-") ||
                expression.contains("*") ||
                expression.contains("/");
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
