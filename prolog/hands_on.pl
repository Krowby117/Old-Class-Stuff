%% 1. Fahrenheit to Celsius
f_c(F, C) :-    number(F), C is (F - 32) * (5/9);
                number(C), F is (C * (9/5)) + 32.

%% Range
range(Head, Tail, []) :- Head > Tail.
range(Head, Tail, [Head | Rest]) :- Head =< Tail,
                                    Next is Head + 1,
                                    range(Next, Tail, Rest).
%   range(Head, Tail, List) :-  Head =< Tail,
%                               Next is Head + 1; 
%                               range(Next, Tail, NewList),
%                               append([Head], NewList, List).

%% Factorial
fact(N, _) :- N < 0, !, fail.
fact(0, 1).
fact(Cur, R) :- Num > 0,
                Next is Cur - 1,
                fact(Next, Rest),
                R is Cur * Rest.

%% Fibonacci
fib(N, _) :- N =< 0, !, fail.
fib(0, 0).
fib(1, 1).
fib(Num, R) :-  Num > 1,
                H1 is Num - 1, fib(H1, F1),
                H2 is Num - 2, fib(H2, F2),
                R is F1 + F2.

%% Fibonacci List
fib_list(N, _) :- N < 0, !, fail.
fib_list(0, [0]).
fib_list(1, [1]).
fib_list(Num, L) :- 