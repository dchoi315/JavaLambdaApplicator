# JavaLambda

## Installation

Clone the repository and run the following command in thedirectory with the lambda.jar file

```
java -jar lambda.jar
```

A new terminal will open up where we can begin running commands

## Usage

#### Exiting the program
Simply type 'exit' in the new termianl to exit the program
```
> exit
```

#### Creating a Variable
A variable is any set of characters
```
> a
a
> b
b 
> T
T
> Github
GitHub
```

#### Creating an Application
An application is essentially composed of a left an *Expression* and a right *Expression*
An expression is an interface used to describe either a *Variable*, *Application*, or a *Function*, which we will explain later.
```
> a b
(a b)
> a b c d
(((a b) c) d)
```
The default order of lambda expressions can be overwritten with parenthesis
```
> a (b c)
(a (b c))
```

### Creating a Function
The syntax of a function is very strict.
A function, like an application contains two components. A *variable* on the left and an *expression* on the the right
Here are some of the simplest functions. Use the '\\' character to mark the beginning of a function. After the backslash, the *Variable* and the *Expression* are separated by a period. 
```
>\a.a
λa.a
> \a.b
λa.b
```
**Note: The console/terminal has to support UTF-8 Character encoding.If not, the output lambda will become a '?'**
Just like Applications we can override the default order of lambda expressions using parenthesis
```
> \f.f x
(λf.(f x))
> (\f.f) x
((\f.f) x)
```


#### Storing Values
We can easily store values into definitions using the '=' operator
```
> 0 = \f.\x.x
Added (λf.(λx.x)) as 0
> 0
(λf.(λx.x))
> 0 = \a.b
0 is already defined
> 1 = λf.λx.f x
Added (λf.(λx.(f x))) as 1
> succ = \n.\f.\x.f (n f x)
Added (λn.(λf.(λx.(f ((n f) x))))) as succ
> 2 = succ 1
Added ((λn.(λf.(λx.(f ((n f) x))))) (λf.(λx.(f x)))) as 2
```
#### Running Applications
We can run an application with the "run" command.
```
> run a 
a
> run (x y)
(x y)
> run \x.x
(λx.x)
> run \x.x y
(λx.(x y))
```

Running an appication only becomes significant if the application has a function on the left. For example:
```
> run (\x.x) y
y
```
#### Some Functions and Test cases to Experiment with



```
x
> 0 = \f.\x.x
Added (λf.(λx.x)) as 0
> succ = \n.\f.\x.f (n f x)
Added (λn.(λf.(λx.(f ((n f) x))))) as succ
> 1 = run succ 0
Added (λf1.(λx1.(f1 x1))) as 1
> + = λm.λn.λf.λx.(m f) ((n f) x)
Added (λm.(λn.(λf.(λx.((m f) ((n f) x)))))) as +
> * = λn.λm.λf.λx.n (m f) x
Added (λn.(λm.(λf.(λx.((n (m f)) x))))) as *
> 2 = run succ 1
Added (λf.(λx.(f (f x)))) as 2
> 3 = run + 2 1
Added (λf2.(λx2.(f2 (f2 (f2 x2))))) as 3
> 4 = run * 2 2
Added (λf1.(λx1.(f1 (f1 (f1 (f1 x1)))))) as 4
> 5 = (λf.(λx.(f (f (f (f (f x)))))))
Added (λf.(λx.(f (f (f (f (f x))))))) as 5
> 7 = run succ (succ 5)
Added (λf.(λx.(f (f (f (f (f (f (f x))))))))) as 7
> pred = λn.λf.λx.n (λg.λh.h (g f)) (λu.x) (λu.u)
Added (λn.(λf.(λx.(((n (λg.(λh.(h (g f))))) (λu.x)) (λu.u))))) as pred
> 6 = run pred 7
Added (λf1.(λx1.(f1 (f1 (f1 (f1 (f1 (f1 x1)))))))) as 6
> - = λm.λn.(n pred) m
Added (λm.(λn.((n (λn.(λf.(λx.(((n (λg.(λh.(h (g f))))) (λu.x)) (λu.u)))))) m))) as -
> 10 = run succ (+ 3 6)
Added (λf1.(λx1.(f1 (f1 (f1 (f1 (f1 (f1 (f1 (f1 (f1 (f1 x1)))))))))))) as 10
> 9 = run pred 10
Added (λf.(λx.(f (f (f (f (f (f (f (f (f x))))))))))) as 9
> 8 = run - 10 2
Added (λf3.(λx3.(f3 (f3 (f3 (f3 (f3 (f3 (f3 (f3 x3)))))))))) as 8
> true = λx.λy.x
Added (λx.(λy.x)) as true
> false = 0
Added (λf.(λx.x)) as false
> not = λp.p false true
Added (λp.((p (λf.(λx.x))) (λx.(λy.x)))) as not
> even? = λn.n not true
Added (λn.((n (λp.((p (λf.(λx.x))) (λx.(λy.x))))) (λx.(λy.x)))) as even?
> odd? = \x.not (even? x)
Added (λx.((λp.((p (λf.(λx.x))) (λx.(λy.x)))) ((λn.((n (λp.((p (λf.(λx.x)))
(λx.(λy.x))))) (λx.(λy.x)))) x))) as odd?
> run even? 0
true
> run even? 5
false
> run (λy.λx.(x y)) (x x)
(λx1.(x1 (x x)))
> run (\x. \x . x) y z
z
```











