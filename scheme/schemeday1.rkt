#lang racket

(define pi 3.14)                  ; real
(define myComplexNumber 2+5i)     ; complex
(define amIUsingScheme #t)        ; boolean
(define myName "Kevin")           ; string
(define letter #\a)               ; character

(let (
      (x 4)
      (y 8)
     )

     ; x and y can only be used here
     (display (+ x y))
)

; simple hello world
(display "\nhello world\n")

(define msg "hello there\n")
(display msg)


(let (
      (msg "hello world\n")
     )
  (display msg)
  )

(/ 6 5)

(+ 6/5 1/2)
(+ 1.2 0.5)

