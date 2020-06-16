> x
X
> x;withcommentnospaces
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
