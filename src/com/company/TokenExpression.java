package com.company;

public class TokenExpression {
    private String value;   // signature of operation, f.e. "+' or '/' etc or value of operand f.e. 35
    private TypeToken token;

    public TokenExpression(String value, TypeToken token) {
        this.value = value;
        this.token = token;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TypeToken getToken() {
        return token;
    }

    public void setToken(TypeToken token) {
        this.token = token;
    }
}
