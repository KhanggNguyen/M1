(defun member? (item ll &key (test 'eql))
    (if (atom ll)
        nil
        (if (apply test item (car ll) '())
            ll (member? item (cdr ll) :test test )
        )
    )
)

(member? 4 '(1 2 3 4))
