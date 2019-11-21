;;
;; Factorial with range
;;

(defun factorial (n)
  (cond((> n 0) (apply #'* (range 1 (+ 1 n))))
       ((= n 0) 1)))