(defun member? (item ll &key (test 'eql))
    (if (atom ll)
        nil
        (if (apply test item (car ll) '())
            ll (member? item (cdr ll) :test test )
        )
    )
)

(member? 4 '(1 2 3 4))


(defun append_bis (l1 l2 &key (test 'cons))
    (if (null l2)
        l1
        (if  (null l1)
            l2
            (apply test (car l1) (append_bis (cdr l1) l2 :test test))
        )
    )
)