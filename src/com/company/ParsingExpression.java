package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class ParsingExpression {

    //private String str;
    private static HashMap<String, OperationExpression> hmOperation;

    /* ============================================================
                    add token of expression in the array of tokens
            ============================================================ */
    private static void AddList(ArrayList<TokenExpression> list, TypeToken en, String str) {
        int priority = 0;
        int arity = 0;
        String key;
        TokenExpression token = new TokenExpression("empty", TypeToken.NOTHING);

        if (en == TypeToken.OPERAND) {
            token = new OperandExpression(str, en);
        } else if (en == TypeToken.OPEN_BRACKET || en == TypeToken.CLOSE_BRACKET) {
            token = new Bracket(str, en);
        } else if (en == TypeToken.OPERATION || en == TypeToken.UNARY_OPERATION) {
            key = en.getPrefix() + str;
            if (hmOperation.containsKey(key)) {
                priority = hmOperation.get(key).getPriority();
                arity = hmOperation.get(key).getArity();
            }
            token = new OperationExpression(str, en, priority,arity);
        }
        list.add(token);
    }

    /* ============================================================
    parsing string expression
    ============================================================ */
    public static ArrayList<TokenExpression> parseStrExpr(String str) throws Exception {
        ArrayList<TokenExpression> list = new  ArrayList<>();
        TypeToken typeBuffer;
        hmOperation = LoadOperation();

        double tempVar;

        char ch = str.charAt(0);
        String readBuffer = String.valueOf(ch);

        // take first symbol for figure out first typeBuffer
        if (Character.isDigit(ch) || ch == '.') {
            typeBuffer = TypeToken.OPERAND;
        } else if (ch == '-' || ch == '+') {
            typeBuffer = TypeToken.UNARY_OPERATION;
        } else if (ch == '(') {
            typeBuffer = TypeToken.OPEN_BRACKET;
        } else if (Character.isLetter(ch)) {
            typeBuffer = TypeToken.FUNCTION;
        } else {
            throw new Exception("First symbol is incorrect!");
        }

        for (int i = 1; i < str.length(); i++) {
            ch = str.charAt(i);

            if (Character.isDigit(ch) || ch == '.') {
                if (typeBuffer == TypeToken.OPERAND) {
                    readBuffer += ch;
                } else {
                    if (typeBuffer == TypeToken.OPERATION || typeBuffer == TypeToken.OPEN_BRACKET || typeBuffer == TypeToken.UNARY_OPERATION) {
                        AddList(list, typeBuffer, readBuffer);    // move operation to out stream
                        readBuffer = String.valueOf(ch);    // empty readBuffer and init new symbol
                    } else {
                        throw new Exception("Incorrect digit is position: " + (i + 1));
                    }
                    typeBuffer = TypeToken.OPERAND;
                }
            } else if (ch == '-' || ch == '+' || ch == '*' || ch == '/') {
                if (typeBuffer == TypeToken.UNARY_OPERATION || typeBuffer == TypeToken.OPERATION) {
                    readBuffer += ch;
                } else {
                    AddList(list, typeBuffer, readBuffer);    // move ( to out stream
                    readBuffer = String.valueOf(ch);    // empty readBuffer and init new symbol

                    if (typeBuffer == TypeToken.OPEN_BRACKET) {
                        // unary operation
                        typeBuffer = TypeToken.UNARY_OPERATION;
                    } else if (typeBuffer == TypeToken.OPERAND || typeBuffer == TypeToken.CLOSE_BRACKET) {
                        // binary operation
                        typeBuffer = TypeToken.OPERATION;
                    } else if (typeBuffer == TypeToken.FUNCTION) {
                        throw new Exception("Incorrect digit is position: " + (i + 1));
                    }
                }
            } else if (ch == '(') {
                if (typeBuffer == TypeToken.OPEN_BRACKET) {
                    AddList(list, typeBuffer, readBuffer);    // move ( to out stream
                } else if (typeBuffer == TypeToken.UNARY_OPERATION || typeBuffer == TypeToken.OPERATION || typeBuffer == TypeToken.FUNCTION) {
                    AddList(list, typeBuffer, readBuffer);
                    readBuffer = String.valueOf(ch);
                } else {
                    // typeBuffer == TypeToken.OPERAND) or typeBuffer == TypeToken.CLOSE_BRACKET)
                    throw new Exception("Incorrect symbol " + ch + " in position: " + (i + 1));
                }
                typeBuffer = TypeToken.OPEN_BRACKET;
            } else if (ch == ')') {
                if (typeBuffer == TypeToken.OPERAND) {
                    AddList(list,typeBuffer, readBuffer);
                    readBuffer = String.valueOf(ch);
                    typeBuffer = TypeToken.CLOSE_BRACKET;
                } else  if (typeBuffer == TypeToken.CLOSE_BRACKET) {
                    AddList(list,typeBuffer, readBuffer);
                }
                else {
                    throw new Exception("Incorrect symbol(UNARY_OPER) " + ch + " in position: " + (i + 1));
                }
            }
        }
        AddList(list, typeBuffer, readBuffer);

        return list;
    }

    /* ============================================================
    load permitted operations
    ============================================================ */
    //
    private static HashMap<String, OperationExpression> LoadOperation() {
        HashMap<String, OperationExpression> hmOperation = new HashMap<>();

        hmOperation.put("+", new OperationExpression("+", TypeToken.OPERATION,2,2)); // binary add
        hmOperation.put("-", new OperationExpression("-", TypeToken.OPERATION,2,2));// binary minus
        hmOperation.put("*", new OperationExpression("*", TypeToken.OPERATION,3,2)); // binary add
        hmOperation.put("/", new OperationExpression("/", TypeToken.OPERATION,3,2));// binary minus
        hmOperation.put("U+", new OperationExpression("+", TypeToken.UNARY_OPERATION,4, 1)); // unary add
        hmOperation.put("U-", new OperationExpression("-", TypeToken.UNARY_OPERATION,4,1)); // unary minus

        return hmOperation;
    }
}
