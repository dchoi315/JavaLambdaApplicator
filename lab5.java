package JavaLamdaApplicator;

import java.util.*;

public class lab5 {

    private static HashMap<String, Expression> definitions;

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String input;
        definitions = new HashMap<>();

//        definitions.put("0", lambdify(runStringMaker(splitTopLevelArgs("\\f.\\x.x"))));
//        definitions.put("succ", lambdify(runStringMaker(splitTopLevelArgs("\\n.\\f.\\x.f (n f x)"))));
//        definitions.put("1", lambdify(runStringMaker(splitTopLevelArgs("run succ 0"))));
//        definitions.put("2", lambdify(runStringMaker(splitTopLevelArgs("run succ 1"))));
//        definitions.put("+", lambdify(runStringMaker(splitTopLevelArgs("\\m.\\n.\\f.\\x.(m f) ((n f) x)"))));
//        definitions.put("*", lambdify(runStringMaker(splitTopLevelArgs("\\n.\\m.\\f.\\x.n (m f) x"))));
//        definitions.put("3", lambdify(runStringMaker(splitTopLevelArgs("run + 2 1"))));
//        definitions.put("4", lambdify(runStringMaker(splitTopLevelArgs("run * 2 2"))));
//        definitions.put("5", lambdify(runStringMaker(splitTopLevelArgs("(\\f.(\\x.(f (f (f (f (f x)))))))"))));
//        definitions.put("7", lambdify(runStringMaker(splitTopLevelArgs("run succ (succ 5)"))));
//        definitions.put("pred", lambdify(runStringMaker(splitTopLevelArgs("\\n.\\f.\\x.n (\\g.\\h.h (g f)) (\\u.x) (\\u.u)"))));
//        definitions.put("6", lambdify(runStringMaker(splitTopLevelArgs("run pred 7"))));
//        definitions.put("-", lambdify(runStringMaker(splitTopLevelArgs("\\m.\\n. (n pred) m"))));
//        definitions.put("10", lambdify(runStringMaker(splitTopLevelArgs("run succ (+ 3 6)"))));
//        definitions.put("true", lambdify(runStringMaker(splitTopLevelArgs("\\x.\\y.x"))));
//        definitions.put("false", lambdify(runStringMaker(splitTopLevelArgs("0"))));
//        definitions.put("not",lambdify(runStringMaker(splitTopLevelArgs("\\p.p false true"))));
//        definitions.put("even?", lambdify(runStringMaker(splitTopLevelArgs("\\n.n not true"))));
//        definitions.put("odd?", lambdify(runStringMaker(splitTopLevelArgs("\\x.not (even? x)"))));
//        definitions.put("8", lambdify(runStringMaker(splitTopLevelArgs("run * 4 2"))));
//        definitions.put("9", lambdify(runStringMaker(splitTopLevelArgs("* 3 3"))));

        do{
            System.out.print("> ");
            input = scan.nextLine();

            if (!input.equalsIgnoreCase("exit"))   {

                // Run Statement
                if (input.length() > 3 && input.substring(0,3).equalsIgnoreCase("run")){
                    ArrayList<String> arguments = splitTopLevelArgs(input.substring(4));
                    String runString = runStringMaker(arguments);
                    Expression e = lambdify(runString);
                    e = e.eval();

                    boolean found = false;
                    for(String key : definitions.keySet()){
                        if (e.equals(definitions.get(key))) {
                            System.out.println(key);
                            System.out.println(e);
                            found = true;
                            break;
                        }
                    }

                    if (!found) System.out.println(e);
                }

                //Definition Statement
                else if(input.contains("=")) {

                    ArrayList<String> arguments = splitTopLevelArgs(input.substring(input.indexOf('=') + 1).trim());
                    String var = input.substring(0, input.indexOf('=')).trim();

                    if (definitions.get(var) == null){
                        String runString = runStringMaker(arguments);

                        Expression define = lambdify(runString).eval();

                        if (arguments.get(0).equals("run"))
                            define = define.eval();
                        definitions.put(var, define);
                        System.out.println("Added " + define + " as " + var);
                    }
                    else
                        System.out.printf("%s is already defined as %s%n", var, definitions.get(var));
                }

                //Find Definition
                else if (definitions.get(input) != null){
                    System.out.println(definitions.get(input));
                }

                //Create Expression
                else{
                    List<String> arguments= splitTopLevelArgs(input);
                    Expression e = lambdify(runStringMaker(arguments));
                    System.out.println(e);
                }
            }

            else System.out.println("Goodbye!");

        } while(!input.equalsIgnoreCase("exit"));
    }



    private static String runStringMaker(List<String> arguments){
        String runString = "";
        for (int i = 0; i < arguments.size(); i++){
            String token = arguments.get(i);

            if(token.equals("run"))
                return runStringMaker(arguments.subList(1, arguments.size()));


            if (definitions.containsKey(token))
                runString += definitions.get(token).toString().replace("λ", "\\") + " ";

            else if (token.charAt(0) == '(' && token.charAt(token.length()-1) == ')') {
                String temp = token;


                for(String key : definitions.keySet()){

                    if (token.contains(key)){
                        int start = token.indexOf(key);
                        int end = start + key.length()-1; // inclusive
                        boolean startCleared = token.charAt(start-1) == '(' || token.charAt(start-1) == ' ';
                        boolean endCleared = token.charAt(end + 1) == ')' || token.charAt(end+1) == ' ';
                        if (startCleared && endCleared)
                            temp = temp.replace(key, definitions.get(key).toString().replace("λ", "\\"));
                    }
                }
                runString += temp + " ";
            }
            else
                runString += token + " ";
        }
        return runString;
    }

    private static Expression lambdify(String e){
        return buildTree(e.trim());
    }

    private static Expression buildTree(String e){
        ArrayList<String> args = splitTopLevelArgs(e);

        if (args.size() == 1){
            String single = args.get(0);

            if (single.charAt(0) =='(' && single.charAt(e.length()-1) ==')')
                return lambdify(single.substring(1, single.length()-1));
            else
                return new Variable(single);
        }

        else {
            Expression currentExpression = null;

            for (int i = 0; i < args.size(); i++){
                String argument = args.get(i);

                if (argument.charAt(0) == '(' && argument.charAt(argument.length()-1) == ')' && splitTopLevelArgs(argument).size() == 1)
                    argument = argument.substring(1, argument.length()-1);

                boolean functionIsGropued = false;

                if (argument.charAt(0) == '\\'){
                    if (i == 0) {

                        Variable name;
                        Expression expression;

                        if (argument.contains(" ")){
                            functionIsGropued = true;

                            int space = argument.indexOf(' ');
                            name = new Variable(argument.substring(1, space));
                            expression = lambdify(argument.substring(space + 1));
                        } else {
                            name = new Variable(argument.substring(1));

                            String runString = String.join(" ", args.subList(i + 1, args.size()));
                            expression = lambdify(runString);
                        }
                        currentExpression = new Function(name, expression);
                    }

                    else {
                        Variable name;
                        Expression expression;

                        if (argument.contains(" ")){
                            functionIsGropued = true;
                            int space = argument.indexOf(' ');

                            name = new Variable(argument.substring(1, space));
                            expression = lambdify(argument.substring(space+1));
                        } else{
                            name = new Variable(argument.substring(1));
                            String runString = String.join(" ", args.subList(i + 1, args.size()));
                            expression = lambdify( runString);
                        }
                        currentExpression = new Application(currentExpression, new Function(name, expression));
                    }

                    if (!functionIsGropued) break;

                }

                else {
                    if (i == 0) currentExpression = lambdify(argument);
                    else currentExpression = new Application(currentExpression, lambdify(argument));
                }
            }
            return currentExpression;
        }
    }

    private static ArrayList<String> splitTopLevelArgs(String e){
        ArrayList<String> args = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(e, ". " );

        int counter = 0;
        String token = "";
        String previous = "";

        while (st.hasMoreElements()){
            token = st.nextToken();
            if (token.contains("(")){
                for (int i = 0; i < token.length(); i++)
                    if(token.charAt(i) == '(') counter ++;
            }
            if (token.contains(")")){
                for (int i = 0; i < token.length(); i++)
                    if(token.charAt(i) == ')') counter--;
            }
            if (counter == 0){
                String combined = (previous + " " + token).trim();
                args.add(combined);
                previous = "";
            }
            else{
                previous += " " + token;
            }
        }
        return args;
    }
}
