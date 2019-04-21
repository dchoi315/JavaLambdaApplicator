package JavaLamdaApplicator;

import java.util.HashSet;

public class Function implements Expression {

    private Variable name;
    private Expression expression;

    public Function(Variable name, Expression expression){
        this.name = name;
        this.expression = expression;
    }
    @Override
    public Expression copy() {
        return new Function(this.name, this.expression);
    }

    @Override
    public boolean equals(Expression other) {
        if(!(other instanceof  Function)) return false;

        String commonName = nextVar(name.toString());

        Function e1 = (Function)this.alphaConvert(name, new Variable(commonName));
        Function e2 = (Function) other.alphaConvert(((Function)other).getName(), new Variable(commonName));

        boolean nameTrue = e1.getName().equals(e2.getName());
        boolean expressionTrue = e1.getExpression().equals(e2.getExpression());
        return nameTrue && expressionTrue;
    }

    @Override
    public HashSet<String> boundVariables() {
        HashSet<String> vars = name.boundVariables();
        vars.add(name.toString());
        vars.addAll(expression.boundVariables());
        return vars;
    }

    @Override
    public HashSet<String> allVariables() {
        HashSet<String> var = name.allVariables();
        var.addAll(expression.allVariables());
        return var;
    }
    @Override
    public Expression eval() {
        return new Function(name, expression.eval());
    }
    @Override

    public Expression replace(Expression from, Expression to) {
        if (name.equals(from))
            return copy();
        else
            return new Function(name, expression.replace(from ,to));
    }


    @Override
    public String toString(){
        return "(" + "λ" + name + "." + expression + ")";
    }
    public Variable getName(){
        return name;
    }
    public Expression getExpression(){
        return expression;
    }

    @Override
    public Expression alphaConvert(Expression from, Expression to) {
        return new Function((Variable) name.alphaConvert(from, to), expression.alphaConvert(from, to));
    }


    private String nextVar(String string){

        int splitPos = string.length();
        for (int i = 0; i < string.length(); i++){
            if (Character.isDigit(string.charAt(i))){
                splitPos = i;
                break;
            }
        }

        if (splitPos == string.length()){
            return string + "1";
        }
        int number = Integer.parseInt(string.substring(splitPos)) + 1;
        return string.substring(0, splitPos) + number;
    }
}
