package com.company;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class ReversePolish {

    /* ==============================================================
    конвертация выражения в польскую обратную запись
    ================================================================ */
    public static ArrayList<TokenExpression> getPolandListElements(ArrayList<TokenExpression> list) {
        TokenExpression elem;
        TokenExpression lastElementInStack;
        ArrayDeque<TokenExpression> stack = new ArrayDeque<TokenExpression>();
        ArrayList<TokenExpression> listPoland = new ArrayList<TokenExpression>();

        for (int i = 0; i < list.size(); i++) {
            elem = list.get(i);

            switch (elem.getToken()) {
                case OPERAND: {
                    //System.out.println("Operand: " + elem.getValue());
                    listPoland.add(elem);
                    break;
                }
                case UNARY_OPERATION: {
                    //System.out.println("Operand: " + elem.getValue());
                    stack.addLast(elem);
                    break;
                }
                case OPERATION: {
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
                        };
                    }
                    stack.addLast(elem);
                    break;
                }
                case OPEN_BRACKET:{
                    //System.out.println("Bracket: " + elem.getValue());
                    stack.addLast((TokenExpression) elem);
                    break;
                }
                case CLOSE_BRACKET:{
                    //&& !(stack.getLast().getToken() == TypeToken.OPEN_BRACKET)
                    while (!stack.isEmpty() && !(stack.getLast().getToken() == TypeToken.OPEN_BRACKET)) {
                        listPoland.add(stack.removeLast());
                    }
                    stack.removeLast(); // remove open bracket
                    // check, may be exists UNARY OPERATION
                    while (!stack.isEmpty()) {
                        lastElementInStack = stack.getLast();
                        if(lastElementInStack.getToken()  == TypeToken.UNARY_OPERATION) {
                            listPoland.add(stack.removeLast());
                        }
                    }
                    break;
                }
                default: {
                    System.out.println("Error");
                }
            }
        }

        while (!stack.isEmpty()) {
            listPoland.add(stack.removeLast());
        }

        return  listPoland;

    }
}
