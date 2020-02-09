; Khang Nguyen, BenBrahim Sabri, TRAN Thi Tra My

(defun make-vm (name taille)
	(setMemoireSize name taille)
	
	(setf (get name 'symboleR) (make-hash-table))
	
	(setf (get name 'referenceNR) (make-hash-table)) 
	
	(setf (get name 'exitVM) 0)
	(setf (get name 'maxStack) 100000)
	(setf (get name 'BP) 10)
	(setf (get name 'SP) 10)
	(setf (get name 'FP) 10)
	(setf (get name 'PC) 100001)
	(setf (get name 'R0) 0)
	(setf (get name 'R1) 0)
	(setf (get name 'R2) 0)
	
	;flag
	(setf (get name 'DEQ) 0)
	(setf (get name 'DPP) 0)
	(setf (get name 'DPG) 0)

	"VM prêt"
)

(defun getMemoireSize (vm)
	(array-total-size (get vm 'memoire))
)

(defun setMemoireSize (vm taille)
	(setf (get vm 'memoire) (make-array taille))
	T
)

(defun getMemoireAt (vm adr)
	(if (>= adr (getMemoireSize vm))
		(error "Erreur Memoire" adr)
		(let ((value (aref (get vm 'memoire) adr)))
			(if (null value)
				0
				value
			)
		)
	)
)

(defun setMemoireAt (vm adr src)
	(if (>= adr (getMemoireSize vm))
		(error "Erreur Memoire" adr)
		(setf (aref (get vm 'memoire) adr) src)
	)
)



(defun getRegistre (vm reg)
	(let ((value (get vm reg)))
		(if (null value)
			(error "Erreur Registre" reg)
			value
		)
	)
)

(defun setRegistre (vm reg value)
	(if (null (get vm reg))
		(error "Erreur Registre" reg)
		(setf (get vm reg) value)
	)
)


(defun setSymbole (vm symb adr)
	(setf (gethash symb (get vm 'symboleR)) adr)
)

(defun getSymbole (vm symb)
	(gethash symb (get vm 'symboleR))
)

(defun isSymboleSet (vm symb)
	(if (getSymbole vm symb)
		t
		nil
	)
)


(defun setReferenceNR (vm ref adr)
	(if (isReferenceSet vm ref)
		(setf (gethash ref (get vm 'referenceNR)) (cons adr (gethash ref (get vm 'referenceNR))))
		(setf (gethash ref (get vm 'referenceNR)) (list adr))
	)
)

(defun getReferenceNR (vm ref)
	(gethash ref (get vm 'referenceNR))
)

(defun isReferenceSet (vm ref)
	(if (getReferenceNR vm ref)
		t
		nil
	)
)

(defun isLIT (arg)
	(and (consp arg) (eql (car arg) 'LIT))
)

(defun expr_move (vm arg1 arg2)
	(if (isLIT arg1)
		(setRegistre vm arg2 (cadr arg1))
		(setRegistre vm arg2 (getRegistre vm arg1))
	)
)

(defun expr_load (vm adr arg)
	(if (isLIT adr)
		(expr_move vm `(LIT ,(getMemoireAt vm adr)) arg)
		(expr_move vm `(LIT ,(getMemoireAt vm (getRegistre vm adr))) arg)
	)
	
)

(defun expr_store (vm arg adr)
	(if (isLIT adr)
		(setMemoireAt vm adr (getRegistre vm arg))
		(setMemoireAt vm (getRegistre vm adr) (getRegistre vm arg))
	)
)

(defun expr_incr (vm arg)

	(setRegistre vm arg (+ (getRegistre vm arg) 1))
)

(defun expr_decr (vm arg)
	(setRegistre vm arg (- (getRegistre vm arg) 1))
)

(defun expr_add (vm arg1 arg2)
	(if (isLIT arg1)
		(setRegistre vm arg2 (+ (getRegistre vm arg2) (cadr arg1)))
		(setRegistre vm arg2 (+ (getRegistre vm arg2) (getRegistre vm arg1)))
	)
)

(defun expr_mult (vm arg1 arg2)
	(if (isLIT arg1)
		(setRegistre vm arg2 (* (getRegistre vm arg2) (cadr arg1)))
		(setRegistre vm arg2 (* (getRegistre vm arg2) (getRegistre vm arg1)))
	)
)

(defun expr_sub (vm arg1 arg2)
	(if (isLIT arg1)
		(setRegistre vm arg2 (- (getRegistre vm arg2) (cadr arg1)))
		(setRegistre vm arg2 (- (getRegistre vm arg2) (getRegistre vm arg1)))
	)
)

(defun expr_div (vm arg1 arg2)
	(if (isLIT arg1)
		(setRegistre vm arg2 (/ (getRegistre vm arg2) (cadr arg1)))
		(if (= 0 (getRegistre arg1))
			(error "expr_div division par 0 impossible")
			(setRegistre vm arg2 (/ (getRegistre vm arg2) (getRegistre vm arg1)))
		)
	)
)

(defun expr_push (vm arg)
	;(print (getRegistre vm 'SP))
	(if (> (getRegistre vm 'SP) (getRegistre vm 'maxStack))
		(error "expr_push depassement de pile")
		(progn
			(if (isLIT arg)
				(setMemoireAt vm (getRegistre vm 'SP) (cadr arg))
				(setMemoireAt vm (getRegistre vm 'SP) (getRegistre vm arg)))
			(setRegistre vm 'SP (+ (getRegistre vm 'SP) 1))
		)
	)
)

(defun expr_pop (vm arg)
	;(print (getRegistre vm 'SP))
	(if (<= (getRegistre vm 'SP) (getRegistre vm 'BP))
		(error "expr_pop pile vide")
		(progn 
			(setRegistre vm 'SP (- (getRegistre vm 'SP) 1))
			(setRegistre vm arg (getMemoireAt vm (getRegistre vm 'SP)))
		)
	)
)


(defun expr_cmp (vm arg1 arg2)
	(if (isLIT arg1)
		(setq tmpArg1 (cadr arg1))
		(setq tmpArg1 (getRegistre vm arg1))
	)
	(setq tmpArg2 (getRegistre vm arg2))
	(if (equal tmpArg1 tmpArg2)
		( progn
			(setRegistre vm 'DEQ 1)
			(setRegistre vm 'DPG 0)
			(setRegistre vm 'DPP 0)
		)
		(
			if (< tmpArg1 tmpArg2)
			( progn
				(setRegistre vm 'DEQ 0)
				(setRegistre vm 'DPG 0)
				(setRegistre vm 'DPP 1)
			)
			( progn
				(setRegistre vm 'DEQ 0)
				(setRegistre vm 'DPG 1)
				(setRegistre vm 'DPP 0)
			)
		)
	)
)


(defun isLABEL (arg)
	(and (consp arg) (eql (car arg) 'LABEL))
)

(defun expr_jpg (vm etiq)
	(if (= (getRegistre vm 'DPG) 1)
		(expr_jmp vm etiq)
	)
)

(defun expr_jeq (vm etiq)
	(if (= (getRegistre vm 'DEQ) 1)
		(expr_jmp vm etiq)
	)
)
(defun expr_jpp (vm etiq)
	(if (= (getRegistre vm 'DPP) 1)
		(expr_jmp vm etiq)
	)
)

(defun expr_jge (vm etiq)
	(if (or (= (getRegistre vm 'DPG) 1) (= (getRegistre vm 'DEQ) 1))
		(expr_jmp vm etiq)
	)
)

(defun expr_jpe (vm etiq)
	(if (or (= (getRegistre vm 'DPP) 1) (= (getRegistre vm 'DEQ) 1))
		(expr_jmp vm etiq)
	)
)

(defun expr_jmp (vm etiq)
	(if (integerp etiq)
		(setRegistre vm 'PC etiq)
		(error "expr_jmp etiquette n'est pas une adresse : ~s" etiq)
	)
	
)

(defun expr_jsr (vm etiq)
	(setMemoireAt vm (getRegistre vm 'SP) (+ (getRegistre vm 'PC) 1))
	(setRegistre vm 'SP (+ (getRegistre vm 'SP) 1))
	(expr_jmp vm etiq)
)

(defun expr_rtn (vm)
	(setRegistre vm 'SP (- (getRegistre vm 'SP) 1))
	(expr_jmp vm (getMemoireAt vm (getRegistre vm 'SP)))
)

(defun expr_nop (vm))

(defun expr_halt (vm)
	(setRegistre vm 'exitVM 1)
)

(defun expr_cons (vm arg1 arg2)
	(setRegistre vm arg2 (cons (getRegistre vm arg1) (getRegistre vm arg2)))
)

(defun expr_car (vm arg)
	(setRegistre vm arg (car (getRegistre vm arg)))
)

(defun expr_cdr (vm arg)
	(setRegistre vm arg (cdr (getRegistre vm arg)))
)

(defun execInst (vm value)
	(case (car value)
		('MOVE	 (expr_MOVE vm (cadr value) (caddr value)))
		('LOAD	 (expr_LOAD vm (cadr value) (caddr value)))
		('STORE	 (expr_STORE vm (cadr value) (caddr value)))
		('INCR	 (expr_INCR vm (cadr value)))
		('DECR	 (expr_DECR vm (cadr value)))
		('ADD	 (expr_ADD vm (cadr value) (caddr value)))
		('MULT	 (expr_MULT vm (cadr value) (caddr value)))
		('SUB	 (expr_SUB vm (cadr value) (caddr value)))
		('DIV	 (expr_DIV vm (cadr value) (caddr value)))
		('PUSH	 (expr_PUSH vm (cadr value)))
		('POP	 (expr_POP vm (cadr value)))
		('CMP	 (expr_CMP vm (cadr value) (caddr value)))
		('JPG	 (expr_JPG vm (cadr value)))
		('JEQ	 (expr_JEQ vm (cadr value)))
		('JPP	 (expr_JPP vm (cadr value)))
		('JGE	 (expr_JGE vm (cadr value)))
		('JPE	 (expr_JPE vm (cadr value)))
		('JMP	 (expr_JMP vm (cadr value)))
		('JSR	 (expr_JSR vm (cadr value)))
		('RTN	 (expr_RTN vm))
		('NOP	 (expr_NOP vm))
		('HALT	 (expr_HALT vm))
		('CONS	 (expr_CONS vm (cadr value) (caddr value)))
		('CAR	 (expr_CAR vm (cadr value) ))
		('CDR	 (expr_CDR vm (cadr value) ))
		(t (error "execInst instruction inconnue ~s " (car value)))
	)
)

(defun expr_exec (vm)
	(loop while (= (get vm 'exitVM) 0) do
		(let* ((pc (getRegistre vm 'PC)) (instr (getMemoireAt vm pc)))
			(progn
				;(print instr)

				(execInst vm instr)
				(if (= (getRegistre vm 'PC) pc)
					(setRegistre vm 'PC (+ pc 1))
					nil
				)

				;(print (getRegistre vm 'R0))
				;(print (getRegistre vm 'R1))
				;(print (getRegistre vm 'R2))
				;(print (getRegistre vm 'SP))
				;(print (getRegistre vm 'FP))
			)
		)	
	)
	(print "Resultat : ")
	(getRegistre vm 'R0)
)

(defun lecture_code (vm nomfichier &optional (co 100001))
	(let ((fichier (open nomfichier)))
		(if fichier
			(prog1 
				(expr_chargeur vm (read fichier nil) co)
				(close fichier)
			)
		)
	)
	"Chargement du code termine !"
)

(defun expr_chargeur (vm fichier &optional (co 100001))
	;(print fichier)
	(loop while (not (null fichier)) do
		(let ((instr (car fichier)))
			;(print instr)

			(if (null instr)
				nil
				(if (eql 'LABEL (car instr))
					(expr_charger_symb vm (cadr instr) co)
					(progn
						(setMemoireAt vm co (expr_resoudre_symb vm instr co))
						(setf co (+ co 1))
					)
				)
			)
		)
		(setf fichier (cdr fichier))
	)
)


(defun expr_resoudre_symb (vm instr co)
	(if 
		(or 
			(eql 'JMP (car instr))
			(eql 'JSR (car instr))
			(eql 'JPG (car instr))
			(eql 'JEQ (car instr))
			(eql 'JPP (car instr))
			(eql 'JGE (car instr))
			(eql 'JPE (car instr))
		)

		(if (isLABEL (cadr instr))
			(if (isSymboleSet vm (cadadr instr))
				(cons (car instr) (list (getSymbole vm (cadadr instr)))) ; met l'adresse comme integer directement ex : JSR 100152
				(progn
					(setReferenceNR vm (cadadr instr) co)
					instr
				)
			)
			instr
		)
		instr
	)
)

(defun expr_charger_symb (vm symb co)
	(if (isSymboleSet vm symb)
		(error "expr_chargeur_symb symbole existe deja")
		(progn
			(setSymbole vm symb co)
			(expr_resoudre_refNR vm symb)
		)
	)
)


(defun expr_resoudre_refNR (vm symb)
	(if (isReferenceSet vm symb)
		(map
			'list
			(lambda 
				(co)
				(setMemoireAt vm co `(,(car (getMemoireAt vm co)) ,(getSymbole vm symb)))
			)
			(getReferenceNR vm symb)
		)
	)
)
