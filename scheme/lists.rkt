#lang racket

(define (total-value lst)
  (if (empty? lst)
      1
      (* (car lst) (total-value (cdr lst)))
  ))
(display (total-value '(1 2 3 4 5 6)))