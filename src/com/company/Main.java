package com.company;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        double result = 0;
        String strExpr = "(-55.4+8*(12-7))*12-120*(-2)/4+29*2.5";  //(-55.4+8)*12-120*(-2)/4+29*2.5


        // check brackets
        int res = checkBrackets(strExpr);
        if (res == 0) {
            try {
                result = evaluteExpression(strExpr);
            } catch (Exception ex) { System.out.println("Error: " + ex.getMessage()); }

            System.out.println("Result: " + strExpr + " = " + result);

            try { System.out.println("JS: " + engine.eval(strExpr)); } catch (Exception ex) { }
        } else if (res == 1){
            System.out.println("Quantity  '(' more than ')'");
        } else {
            System.out.println("Quantity  '(' less than ')'");
        }
    }


    /* =====================================================
    eval one expression
    ===================================================== */
    public static double evaluteExpression(String str) throws Exception {

        double result = 0;

        ArrayList<TokenExpression> list;    //  = new ArrayList<>()
        ArrayList<TokenExpression> listPoland;  //  = new ArrayList<>()

        list = ParsingExpression.parseStrExpr(str);
        listPoland = ReversePolish.getPolandListElements(list);

        for (TokenExpression elem:  list) {
            System.out.println(elem.getValue() + "   " + elem.getToken());
        }

        System.out.println("==========================================");

        for (TokenExpression elem:  listPoland) {
            System.out.println(elem.getValue() + "   " + elem.getToken());
        }

        result = EvalExpression.evalExpression(listPoland);

        return result;
    }

    /* =========================================================
    check quantity open brackets and close brackets
    return  = 1 - if "(" more than ")"
             = 0 - if "(" equal ")"
            = -1 - if "(" less than ")"
    ========================================================= */
    private static int checkBrackets(String str) {

        char ch;
        ArrayDeque<Character> stack = new ArrayDeque<Character>();
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            switch (ch) {
                case '(':
                    stack.addLast(ch);
                    break;
                case ')':
                    if (!stack.isEmpty()) {
                        stack.removeLast();
                    } else {
                        return -1;
                    }
            }
        }

        if (!stack.isEmpty()) {
            return 1;
        }
        else {
            return 0;
        }
    }
}

