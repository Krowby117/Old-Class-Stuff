#lang racket
(require file/sha1)

(define alphabet '("a" "b" "c" "d" "e" "f" "g" "h" "i" "j" "k" "l" "m"
                   "n" "o" "p" "q" "r" "s" "t" "u" "v" "w" "x" "y" "z"))

(define hashes '("fd1286353570c5703799ba76999323b7c7447b06"
                 "66b27417d37e024c46526c2f6d358a754fc552f3"
                 "39ccb32d95edfdbcd882f2b01809724ec640ea16"
                 "8abf15bef376e0e21f1f9e9c3d74483d5018f3d5"
                 "163e65be076bbea20ab8275969700373a6179a3c"))

(define (hashit str)
  (sha1 (open-input-string str)))

(define (brutal hash max-length)
  (define (attempt current length)
    (cond
      [(= length 0) #f]
      [(string=? (hashit current) hash) current]
      [else
       (let loop ((chars alphabet))
         (if (null? chars)
             #f
             (or (attempt (string-append current (car chars)) (- length 1))
                 (loop (cdr chars)))))]))

  (let loop ((length 1))
    (let ((result (attempt "" length)))
      (if result
          result
          (if (<= length max-length)
              (loop (+ length 1))
              #f)))))

(define (crack-all hashes)
  (for-each (lambda (hash)
              (let ((result (time (brutal hash 6))))
                (printf "~a is \"~a\"\n" hash (or result "not found"))))
            hashes))


(crack-all hashes)