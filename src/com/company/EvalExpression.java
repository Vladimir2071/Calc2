package com.company;

import java.util.ArrayList;
import java.util.List;

public class EvalExpression {

    private static double evalBinaryOperation(List<TokenExpression> list, TokenExpression elem) {

        double x1, x2, result = 0.0;

        x1 = Double.parseDouble(list.get(0).getValue());
        x2 = Double.parseDouble(list.get(1).getValue());

        switch (elem.getValue()) {
            case "+": {
                result = x1 + x2;
                break;
            }
            case "-": {
                result = x1 - x2;
                break;
            }
            case "*": {
                result = x1 * x2;
                break;
            }
            case "/": {
                result = x1 / x2;
                break;
            }
        }

        return result;
    }

    private static double evalUnaryOperation(List<TokenExpression> list, TokenExpression elem) {

        double x1, result = 0.0;

        x1 = Double.parseDouble(list.get(0).getValue());

        switch (elem.getValue()) {
            case "+": {
                result = x1;
                break;
            }
            case "-": {
                result = -x1;
                break;
            }
        }

        return result;
    }

    public static double evalExpression(ArrayList<TokenExpression> list) {

        List<TokenExpression> operands;

        double tempResult = 0;
        int index = 0;
        int arity = 0;

        while (list.size() > 1) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getToken() == TypeToken.OPERATION || list.get(i).getToken() == TypeToken.UNARY_OPERATION) {
                    arity = ((OperationExpression)list.get(i)).getArity();
                    index = i;
                    break;
                }
            }

            operands = list.subList(index - arity, index);
            switch (arity) {
                case 1:
                    tempResult =  evalUnaryOperation(operands, list.get(index));
                    break;
                case 2:
                    tempResult =  evalBinaryOperation(operands, list.get(index));
                    break;
            }

            list.get(index - arity).setValue(Double.toString(tempResult));

            for (int i = index;  i > index - arity; i--) {
                list.remove(i);
            }
        }

        return Double.parseDouble(list.get(0).getValue());
    }

}
