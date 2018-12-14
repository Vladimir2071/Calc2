package com.company;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // write your code here
        double result = 0;
        String strExpr = "(-55.4+8)*12-120*(-2)/4+29*2.5";  //(-55.4+8)*12-120*(-2)/4+29*2.5

        try {
            result = evaluteExprassion(strExpr);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        System.out.println("Result: " + strExpr + " = " + result);

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        try { System.out.println("JS: " + engine.eval(strExpr)); } catch (Exception ex) { }
    }

    /* =====================================================
    eval one expression
    ===================================================== */
    public static double evaluteExprassion(String str) throws Exception {

        double result = 0;

        ArrayList<TokenExpression> list;    //  = new ArrayList<>()
        ArrayList<TokenExpression> listPoland;  //  = new ArrayList<>()

        //ParsingExpression parseExpr new ParsingExpression(str);
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
}