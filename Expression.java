package JavaLamdaApplicator;

import java.util.HashSet;

public interface Expression {

    public Expression copy();


    public boolean equals(Expression other);


    public HashSet<String> boundVariables();


    public HashSet<String> allVariables();
    public Expression eval();


    public Expression replace(Expression from, Expression to);

    public Expression alphaConvert(Expression from, Expression to);

}
