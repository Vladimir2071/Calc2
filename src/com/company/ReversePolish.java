package com.company;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class ReversePolish {

    /* ==============================================================
    конвертация выражения в польскую обратную запись
    ================================================================ */
    public static ArrayList<TokenExpression> getPolandListElements(ArrayList<TokenExpression> list) {
        TokenExpression lastElementInStack;
        ArrayDeque<TokenExpression> stack = new ArrayDeque<>();
        ArrayList<TokenExpression> listPoland = new ArrayList<>();

        for (TokenExpression elem : list) {
            switch (elem.getToken()) {
                case OPERAND:
                    listPoland.add(elem);
                    break;
                case UNARY_OPERATION:
                    stack.addLast(elem);
                    break;
                case OPERATION:
                    //!stack.isEmpty() && ((OperationExpression) elem).getPriority() <= ((OperationExpression)stack.getLast()).getPriority() - DON'T WORK
                    while (!stack.isEmpty()) {
                        lastElementInStack = stack.getLast();
                        if(lastElementInStack.getToken() == TypeToken.OPERATION ||lastElementInStack.getToken()  == TypeToken.UNARY_OPERATION) {
                            if (((OperationExpression)elem).getPriority() <= ((OperationExpression)lastElementInStack).getPriority()) {
                                listPoland.add(stack.removeLast());
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    stack.addLast(elem);
                    break;
                case OPEN_BRACKET:
                    stack.addLast(elem);
                    break;
                case CLOSE_BRACKET:
                    while (!stack.isEmpty() && !(stack.getLast().getToken() == TypeToken.OPEN_BRACKET)) {
                        listPoland.add(stack.removeLast());
                    }
                    stack.removeLast(); // remove open bracket
                    // check, may be exists UNARY OPERATION
                    if (!stack.isEmpty()) {
                        lastElementInStack = stack.getLast();
                        if(lastElementInStack.getToken()  == TypeToken.UNARY_OPERATION) {
                            listPoland.add(stack.removeLast());
                        }
                        break;
                    }
                    break;
                default: {
                    System.out.println("Error " + elem.getToken());
                }
            }
        }
        // get rest operation from stack
        while (!stack.isEmpty()) {
            listPoland.add(stack.removeLast());
        }
        return  listPoland;
    }
}
