package JavaLamdaApplicator;

import java.util.HashSet;

public class Variable implements Expression {

    private String name;



    public Variable(String input){
        this.name = input;
    }

    @Override
    public Expression copy() {
        return new Variable(name);
    }

    @Override
    public boolean equals(Expression other) {
        return name.equals(other.toString());
    }

    @Override
    public HashSet<String> boundVariables() {
        return new HashSet<>();
    }
    @Override
    public HashSet<String> allVariables() {
        HashSet<String> var = new HashSet<>();
        var.add(name);
        return var;
    }

    @Override
    public Expression eval() {
        return copy();
    }

    @Override
    public Expression replace(Expression from, Expression to) {
        if (from.equals(this))
            return to;
        else return copy();
    }

    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public Expression alphaConvert(Expression from, Expression to) {
        if (name.equals(from.toString()))
            return to.copy();
        else
            return copy();
    }
}
