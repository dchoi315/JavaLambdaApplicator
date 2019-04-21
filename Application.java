package JavaLamdaApplicator;

import java.util.HashSet;
import java.util.Iterator;

public class Application implements Expression {
    private Expression left;
    private Expression right;


    public Application (Expression left, Expression right){
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression copy() {
        return new Application(this.left, this.right);
    }

    @Override
    public boolean equals(Expression other) {
        if (!(other instanceof Application)) return false;
        return left.equals(((Application) other).getLeft())&&right.equals(((Application) other).getRight());
    }

    @Override
    public HashSet<String> boundVariables() {
        HashSet<String> bvar = new HashSet<>();
        bvar.addAll(right.boundVariables());
        bvar.addAll(left.boundVariables());
        return bvar;
    }
    @Override
    public HashSet<String> allVariables() {
        HashSet<String> variables = left.allVariables();
        variables.addAll(right.allVariables());
        return variables;
    }

    @Override
    public Expression eval() {
        Expression newLeft = stabilize(left.copy());
        Expression newRight = stabilize(right.copy());

        if (newLeft instanceof Variable|| newLeft instanceof Application ){
            return new Application(newLeft, newRight);
        } else{
            Application expression = removeConflicts(newLeft, newRight);
            Function leftEx = (Function)expression.getLeft();
            Expression leftFuncName = leftEx.getName();
            Expression leftFuncExpression = leftEx.getExpression();
            Expression evaluated = leftFuncExpression.copy().replace(leftFuncName, newRight);

            return evaluated.eval();
        }

    }

    @Override
    public Expression replace(Expression old, Expression current) {
        Expression newLeft = left.copy().replace(old,current);
        Expression newRight = right.copy().replace(old,current);
        return new Application(newLeft,newRight);
    }

    @Override
    public String toString(){
        return "(" + left + " " + right + ")";
    }



    public Application removeConflicts (Expression e1, Expression e2){
        HashSet<String> varsToChange = e1.boundVariables();
        varsToChange.retainAll(e2.allVariables());

        Iterator<String> it = varsToChange.iterator();

        while(it.hasNext()){

            String from = it.next();
            String to = nextVar(from);

            while (e1.allVariables().contains(to)){
                to = nextVar(to);
            }

            e1 = e1.alphaConvert(new Variable(from), new Variable(to));
        }
        return new Application(e1, e2);
    }


    public String nextVar(String string){
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



    @Override
    public Expression alphaConvert(Expression from, Expression to) {
        return new Application(left.copy().alphaConvert(from,to), right.copy().alphaConvert(from,to));
    }

    private Expression stabilize(Expression e){
        Expression previous = null;
        while(previous == null|| !(e.equals(previous))){
            previous = e;
            e = e.eval();
        }
        return e;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

}
