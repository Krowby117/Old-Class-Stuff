#lang racket

(struct table (numAnimals numLegs))

(define myTable (table 3 4))

(define (table-collapse t)
  (table (table-numAnimals t) 0))

(table? myTable)
(table-numAnimals myTable)
(table-numLegs myTable)

(table-numLegs (table-collapse myTable))