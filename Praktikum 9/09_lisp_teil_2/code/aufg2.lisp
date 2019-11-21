;;
;; Testing the range function
;;


;; Create range of integers
(defun range (&rest args)
  (cond((= (length args) 1)(range 0 (car args)))
       ((= (length args) 2)(cond((< (car args) (cadr args)) (range (car args) (cadr args)  1))
                                ((> (car args) (cadr args)) (range (car args) (cadr args) -1))))
       ((= (length args) 3)(cond((< (car args) (cadr args)) (cond((< 0 (caddr args)) (cons (car args) (range (+ (caddr args) (car args)) (cadr args) (caddr args))))
                                                                 ((> 0 (caddr args)) nil)))
                                ((> (car args) (cadr args)) (cond((> 0 (caddr args)) (cons (car args) (range (+ (caddr args) (car args)) (cadr args) (caddr args))))
                                                                 ((< 0 (caddr args)) nil)))))))



;; Minimalist test tool
;; (flet, handler-case are not part of the PSPP topics)
;;
(defun run-tests (tests)
  (flet ((run-one (test)
           (handler-case (apply (first test) (second test))
             (error (c) (cadr (list c "throws error"))))))
    (cond ((null tests) "all ok")
          (t (let ((test (car tests)))
               (if (equal (run-one test) (third test))
                   (run-tests (cdr tests))
                   (format t "~S should be: ~S but is: ~S~%" 
                           (cons (first test) (second test))
                           (third test)
                           (run-one test))))))))


;; Some tests for range function
;;
(run-tests '(
  (range (0 5) 
      (0 1 2 3 4))
  (range (3 5) 
      (3 4))
  (range (0 0) 
      nil)
  (range (0 10 2) 
      (0 2 4 6 8))
  (range (10 30 5) 
      (10 15 20 25))
  (range (5)
      (0 1 2 3 4))
  (range (5 10 -1) 
      nil)
  (range (10 5 -1) 
      (10 9 8 7 6))
))
