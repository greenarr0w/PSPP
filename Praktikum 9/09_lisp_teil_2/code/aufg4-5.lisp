;; file-to-string
;; Read text file into string
;;
(defun file-to-string (path)
  (with-open-file (stream path)
    (let ((data (make-string (file-length stream))))
      (read-sequence data stream)
      data)))


;; alternative implementation in case the Common Lisp used does not
;; support the read-sequence function
;;
(defun file-to-string (path)
  (with-open-file (stream path)
    (apply #'concatenate 
           (cons 'string 
                 (loop
                   for line = (read-line stream nil 'eof)
                   until (eq line 'eof)
                   collect (format nil "~A~%" line))))))


;; string-split
;; Split string at character
;;
(defun string-split (c strg &optional (start 0))
  (let ((end (position c strg :start start)))
    (cond (end (cons (subseq strg start end) 
                     (string-split c (subseq strg (+ start end 1)))))
          (t (list (subseq strg start))))))


;; simple-csv
(defun simple-csv (csvText)
  (mapcar (lambda (x) (string-split #\, x)) (string-split #\linefeed csvText)))

;; Beispiel aufruf: 
(simple-csv (file-to-string "/Users/Uensal/Desktop/Uensal/Schule/nichtabgeschlossen/PSPP/Woche_08_45/Unterlagen_Teil2/_praktikum/09_lisp_teil_2/code/products.csv"))


;; parse-float
;; Parse string to float if possible, else return NIL
;;
(defun parse-float (strg)
  (with-input-from-string (s strg)
    (let ((res (read s)))
      (if (eq (type-of res) 'single-float) res nil))))


;; zip-to-alist
;; Merge two lists to an alist
;;
(defun zip-to-alist (lst1 lst2)
  (cond ((or (null lst1) (null lst2)) nil)
        (t (cons (cons (car lst1) (car lst2)) 
                 (zip-to-alist (cdr lst1) (cdr lst2))))))

;; make-alist
(defun make-alist (csv_data)
  (mapcar (lambda (x) (zip-to-alist (car csv_data) x)) (cdr csv_data))) 

