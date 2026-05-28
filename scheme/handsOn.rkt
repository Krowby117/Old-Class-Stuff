#lang racket

; Problem 1 Fahrenheit to Celsius
(define (f_to_c) 
    (display "Enter a temperature in Fahrenheit: ")
    (define fTemp (read))
    (define cTemp (* (/ 5.0 9) (- fTemp 32)))
    (display (number->string cTemp))
)

(f_to_c)

; Problem 2 Using 'cond'
(define (minWork lst curMin)
    (cond   ((empty? lst) curMin)
            ((< (car lst) curMin) (minWork (cdr lst) (car lst)))
            (else (minWork (cdr lst) curMin))
    )
)

(define (minInList lst) 
  (minWork (cdr lst) (car lst))) 
 
(display (string-append "The minimum is " 
         (number->string (minInList '(5 8 2 1))) "\n")) 
 
(display (string-append "The minimum is " 
         (number->string (minInList '(1 8 2 5))) "\n")) 
 
(display (string-append "The minimum is " 
         (number->string (minInList '(5 8 1 2))) "\n"))

; Problem 3 Range functuon
(define (lstr x y)
    (if (< x y)
        (cons x (lstr (+ x 1) y))
        (cons y '())
    )
)

(define rlst (lstr 1 10))
(display rlst)

; Problem 4 Fibonacci
(define (fib n)
    (if (< n 2)
        n
        (+ (fib (- n 1)) (fib (- n 2)))
    )
)

(display (fib 3))
(display (fib 11))

; Problem 5 Factorial list
(define (fact x) 
    (if (< x 1) 
        1 
        (* x (fact (- x 1))) 
    ) 
)

(define (fact_lst x y)
    (define lst (lstr x y))
    (set! lst (map fact lst))
    lst
)

(display (fact_lst 1 5))