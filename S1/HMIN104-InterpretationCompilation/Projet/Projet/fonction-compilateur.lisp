; Khang Nguyen, BenBrahim Sabri, TRAN Thi Tra My

(setf indexIf 0)
(setf indexComp 0)

(defun compiler (nom_fichier_source &optional nom_fichier_destination)
	(let ((fichier (open nom_fichier_source)) (code '()) (bytecode '()))
		(loop for expr = (read fichier nil) while expr do
			(setf code (append code (list expr)))
		)
		(close fichier)
		(setf bytecode (compiler-liste (append code '((HALT)))))
		(if (not (null nom_fichier_destination))
			(with-open-file (str (string-concat "./" nom_fichier_destination)
                     :direction :output
                     :if-exists :supersede
                     :if-does-not-exist :create)
  			(format str (write-to-string bytecode)))
		)
		"Compiler!"
	)
)

(defun compiler-liste (liste)
  (if (null liste)
    NIL
    (append
      (expression (car liste))
      (compiler-liste (cdr liste)))))

(defun expression (expr &optional env)
  (cond 
  	((consp expr)
  		(case (car expr)
  			('if (compiler-condition-if expr env))
  			('defun (compiler-fonction expr env))
			('halt `((HALT)))
			('nop `((NOP)))
  			(t (compiler-appel-fonction expr env))))
  	
  	((constantp expr) (compiler-constant expr))
  	
  	((symbolp expr) (compiler-var expr env))
  	
  	(t (error "Erreur d'Expression" expr))))


(defun compiler-constant (constant)
	`((MOVE (LIT ,constant) R0)))

(defun compiler-condition-if (code  &optional env)
	(setf indexIf (+ indexIf 1))
	(let ((sinon (intern (string-concat (string "SINON") (write-to-string indexIf))))
		 (fin (intern (string-concat (string "FIN") (write-to-string indexIf)))))
		(append 
			(expression (cadr code) env)
			`((CMP (LIT 0) R0))
			`((JEQ (LABEL ,sinon)))
			(expression (caddr code) env)
			`((JMP (LABEL ,fin)))
			`((LABEL ,sinon))
			(expression (cadddr code) env)
			`((LABEL ,fin))
		)
	)
)

(defun compiler-var (var  &optional env)
	(let ((lib (assoc var env)))
		(if lib
			(append
				`((MOVE FP R0))
				`((SUB (LIT ,(cdr lib)) R0))
				`((LOAD R0 R0))
			)
			`((MOVE (VAR ,var) RO)) 
		)
	)
)

(defun compiler-fonction (code &optional env) 
	(let ((positionPile 0))
		(progn
			(map
				'list
				(lambda (param)
					(progn 
						(setf positionPile (+ positionPile 1))
						(setf env (acons param positionPile env))
					)
				)
				(caddr code)
			)
			(append
				`((JMP (LABEL ,(intern (string-concat "END" (string (cadr code)))))))
				`((LABEL ,(cadr code)))

				(expression (cadddr code) env)

				`((MOVE FP R1))
				`((ADD (LIT 4) R1))
				'((MOVE R1 SP))
				`((RTN))


				`((LABEL ,(intern (string-concat "END" (string (cadr code))))))
			)
		)
	)
)

(defun compiler-appel-fonction (code  &optional env)
	(append 
		(apply 
			'append 
				(map 
					'list
					(lambda (param) 
						(append 
							(expression param env) 
							`((PUSH R0))
						)
					)
					(reverse (cdr code))
				)
		)
		`((MOVE FP R1))
		`((MOVE SP FP))
		`((PUSH (LIT ,(list-length (cdr code)))))
		`((MOVE SP R2))
		`((SUB (LIT ,(+ (list-length (cdr code)) 1)) R2))
		`((PUSH R2))
		`((PUSH R1))

		(atomique (car code))

		`((POP R1))
		`((POP R2))

		`((MOVE R1 FP))
		`((MOVE R2 SP))
	)
)

(defun atomique (nomFonction)
	(cond 
		((member nomFonction '(+ - * /))
			(append 
				'(
					(MOVE FP R0)
					(SUB (LIT 1) R0)
					(LOAD R0 R0)
					(MOVE FP R1)
					(SUB (LIT 2) R1)
					(LOAD R1 R1)
				)
				(case nomFonction 
					('+ '((ADD R1 R0)))
					('- '((SUB R1 R0)))
					('* '((MULT R1 R0)))
					('/ '((DIV R1 R0)))
				)
			)
		)
		((member nomFonction '(= <= < > >=))
			(setf indexComp (+ indexComp 1))
			(let ((finCond (intern (string-concat (string "FINCOMP") (write-to-string indexComp)))))
				(append 
					'(
						(MOVE FP R0)
						(SUB (LIT 1) R0)
						(LOAD R0 R0)
						(MOVE FP R1)
						(SUB (LIT 2) R1)
						(LOAD R1 R1)
						(CMP R0 R1) 
						(MOVE (LIT 1) R0)
					)

					(case nomFonction
						('= `((JEQ (LABEL ,finCond))))
						('<= `((JPE (LABEL ,finCond))))
						('< `((JPG (LABEL ,finCond))))
						('> `((JPP (LABEL ,finCond))))
						('>= `((JGE (LABEL ,finCond))))
					)
					'((MOVE (LIT 0) R0))
					`((LABEL ,finCond))
				)
			)
		)
		(t `((JSR (LABEL ,nomFonction))))	
	)
)
