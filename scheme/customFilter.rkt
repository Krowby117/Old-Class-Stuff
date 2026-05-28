#lang racket

(define (myFilter func lst)
  (if (empty? lst)
      '()
      (if (func (car lst))
          (cons (car lst) (myFilter func (cdr lst)))
          (myFilter func (cdr lst)))))

(myFilter (lambda (x) (= 0 (modulo x 2))) '(1 2 3 4 5 6 7 8))
(filter (lambda (x) (= 0 (modulo x 2))) '(1 2 3 4 5 6 7 8))