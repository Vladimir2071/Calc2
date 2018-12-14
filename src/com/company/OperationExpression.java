package com.company;

public class OperationExpression extends TokenExpression {

    private int priority;
    private int arity;      // arity operation, f.e. unary, binary etc

    public OperationExpression(String value, TypeToken token, int priority, int arity) {
        super(value, token);
        this.priority = priority;
        this.arity = arity;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getArity() {
        return arity;
    }

    public void setArity(int arity) {
        this.arity = arity;
    }

}
