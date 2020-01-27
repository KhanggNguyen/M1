2)
(defun FACT (n) (if (= n 0) 1 (* n (FACT (- n 1)))))
(defun FIBO (n) (if (<= n 2) 1 ( + (FIBO (- n 1)) (FIBO (- n 2)))))

3) 
(cons 1 '(2 3 4))
(cons 1 (cons '(2 3) '(4))) 
(cons 1 (cons '(2) (cons '(3) '(4))))

4)
(defun MEMBER? (x ll) 
    (if (atom ll) 
        nil 
        (if (eql x (car ll)) 
            ll 
            (MEMBER? x (cdr ll)))))

(defun LENGTH? (ll)
    (if (atom ll)
        0
        (+ 1 (LENGTH? (cdr ll)))))

(defun LAST? (ll)
    (if (atom ll)
        nil
        (if (eql nil (car (cdr ll)))
            ll 
            (LAST? (cdr ll)))))
( 1 2 3 4 5)
//decroissant
(defun MAKELIST (n)
    (if (< n 1)
        nil
        (if (= n 1)
            '(1)
            (cons n (MAKELIST (- n 1)) ))))

//croissant
(defun MAKELIST (n)
    (if (< n 1)
        nil
        (if (= n 1)
            (list 1)
            (append (MAKELIST (- n 1)) (list n) ))))

(defun COPYLIST (ll)
    (if (atom ll)
        ll
        (if (eql nil (cdr ll)) 
            (list (car ll))
            (cons (car ll) (COPYLIST (cdr ll)) ))))

(defun REMOVE-X (x ll)
    (if (atom ll)
        ll
        (if (eql x (car ll))
            (REMOVE-X x (cdr ll))
            (let ((rest (REMOVE-X x (cdr ll))))
            (if (eq rest (cdr ll))
                ll
            (cons (car ll) rest ))))))

//remove 1st occ only
(defun REMOVE-X (x ll)
    (if (atom ll)
        ll
        (if (eql x (car ll))
            (cdr ll)
            (let ((rest (REMOVE-X x (cdr ll))))
            (if (eq rest (cdr ll))
                ll
            (cons (car ll) rest ))))))

(defun append-l (l1 l2)
    (if (null l2)
        l1
        (if (null l1) 
            l2
        (cons (car l1) (append-l (cdr l1) l2)))))

//??
(defun adjoin-l (x l)
    (if (null l)
        '(x)
        (if (eql x (car l))
            l
            (if (eql nil (cdr l))
                (cons x l)
                (adjoin-l x (cdr l))))))

(defun size (tree)
    (if (atom tree)
        0
        (+ 1 (size (car tree)) (size (cdr tree)) )))

(defun copytree (tree) 
    (if (atom tree) 
        nil 
        (cons (car tree) (cdr tree)))
)

 (defun subst (old new tree &rest x &key test test-not key)
   (cond ((satisfies-the-test old tree :test test
                              :test-not test-not :key key)
          new)
         ((atom tree) tree)
         (t (let ((a (apply #'subst old new (car tree) x))
                  (d (apply #'subst old new (cdr tree) x)))
              (if (and (eql a (car tree))
                       (eql d (cdr tree)))
                  tree
                  (cons a d))))))

(defun tree-leaves (tree) 
    (cond ((eql nil (cdr tree))
        (car tree))
        (t (list (tree-leaves (cdr tree))))))