;; This buffer is for notes you don't want to save, and for Lisp evaluation.
;; If you want to create a file, visit that file with C-x C-f,
;; then enter the text in that file's own buffer.

(defun marque-terminal-li (fun)
  (let ((expr (third (get-li-defun fun))))
    (cond
     ((eq :IF (car expr))
      (list :IF
	    (second expr)
	    (if (eq (car (third expr)) :CALL)
		(list :CALLT (cdr (third expr)))
	      (if (eq (car (third expr)) :MCALL)
		  (list :MCALLT
		  (marque-terminal-li (second (third expr))))
	      (list (third expr))))
	    (third expr)
	    (if (eq (car (fourth expr)) :CALL)
		(list :CALLT (cdr (fourth expr)))
	      (if (eq (car (fourth expr)) :MCALL)
		  (list :MCALLT
		  (marque-terminal-li (second (fourth expr))))
	      (list (fourth expr))))))
     ((eq :PROGN (car expr))
      (list :PROGN
	    (all-but-last (cdr expr))
	    (if (eq (car (last expr)) :CALL)
		(list :CALLT (cdr (last expr)))
	      (if (eq (car (last expr)) :MCALL)
		  (list :MCALLT
			(marque-terminal-li (second (last expr))))
		(last expr)))))
     ((eq :SET-VAR (car expr))
      (list :SET-VAR
	    (second expr)
	    (if (eq (car (third expr)) :CALL)
		(list :CALLT (cdr (third expr)))
	      (if (eq (car (third expr)) :MCALL)
		  (list :MCALLT
			(marque-terminal-li (second (third expr))))
		(third expr)))))
     ((eq :MCALL (car expr))
      (list :MCALLT
	    (marque-terminal-li (cdr expr))))
     ((eq :CALL (car expr))
      (list :CALLT (cdr expr)))
     (T
      expr))))

(defun all-but-last (expr)
  (if (not nil (cdr expr))
      (cons (car expr) (all-but-last (cdr expr)))))
	 
		    
