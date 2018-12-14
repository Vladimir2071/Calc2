package com.company;

public enum TypeToken {
    OPERAND(""),
    UNARY_OPERATION("U"),
    OPERATION(""),
    FUNCTION(""),
    OPEN_BRACKET(""),
    CLOSE_BRACKET(""),
    NOTHING("");

    private String prefix;
    TypeToken(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix(){ return prefix;}
}
