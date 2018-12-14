package com.company;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // write your code here
        double result = 0;
        String strExpr = "(-55.4+8)*12-120/4+29*2.5";

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

        ArrayList<TokenExpression> list = new ArrayList<TokenExpression>();
        ArrayList<TokenExpression> listPoland = new ArrayList<TokenExpression>();

        //ParsingExpression parseExpr new ParsingExpression(str);
        list = ParsingExpression.parseStrExpr(str);
        listPoland = ReversePolish.getPolandListElements(list);

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getValue() + "   " + list.get(i).getToken());
        }

        System.out.println("==========================================");
        for (int i = 0; i < listPoland.size(); i++) {
            System.out.println(listPoland.get(i).getValue() + "   " + listPoland.get(i).getToken());
        }

        result = EvalExpression.evalExpression(listPoland);

        return result;
    }
}