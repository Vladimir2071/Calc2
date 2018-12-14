package com.company;

import java.util.HashMap;

public class ListOperations {

    public static HashMap<String, OperationExpression> LoadOperation() {
        HashMap<String, OperationExpression> hmOperation = new HashMap<String, OperationExpression>();

        hmOperation.put("+", new OperationExpression("+", TypeToken.OPERATION,2,2)); // binary add
        hmOperation.put("-", new OperationExpression("-", TypeToken.OPERATION,2,2));// binary minus
        hmOperation.put("*", new OperationExpression("*", TypeToken.OPERATION,3,2)); // binary add
        hmOperation.put("/", new OperationExpression("/", TypeToken.OPERATION,3,2));// binary minus
        hmOperation.put("U+", new OperationExpression("+", TypeToken.UNARY_OPERATION,4, 1)); // unary add
        hmOperation.put("U-", new OperationExpression("-", TypeToken.UNARY_OPERATION,4,1)); // unary minus

        return hmOperation;
    }
}
