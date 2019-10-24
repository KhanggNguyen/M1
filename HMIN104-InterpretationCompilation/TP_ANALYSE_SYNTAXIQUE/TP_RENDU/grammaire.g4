//NGUYEN Huu Khang - TRAN Thi Tra My 

grammar grammaire;

demarre : (declaration | defFunction | instruction)*;

defFunction : 
	name LEFT (variable ':' type)+ RIGHT ':' type (declaration|instruction)* 'fin' ;

callFunction :
    name LEFT expr (',' expr)* RIGHT ';'
    | 'read();'
    | 'write' LEFT callFunction RIGHT ';'
    ;

condition : equation ((AND | OR) equation)*;

equation : NOT expr | BOOLEAN |expr relop expr;

expr : mulExpr ((ADD|SUB) mulExpr)*;

mulExpr	: atom ((MUL|DIV) atom)*;

affectation	: 
	variable ASSIGN expr ';'
	| variable ASSIGN condition ';'
	| arrayList
    | variable ASSIGN callFunction ';'
	;

declaration : type affectation;

entreesortie : READ LEFT expr* RIGHT | WRITE LEFT expr* RIGHT;

call : variable LEFT expr* RIGHT;

atom
	: ADD atom
	| SUB atom
	| NOT atom
	| call
	| variable
	| CONSTANTE
	| BOOLEAN
	| LEFT expr RIGHT
	| getArrayList
	;

instruction :
	affectation | entreesortie | call  
    | IF condition THEN instruction (ELSE instruction)
    | WHILE condition DO instruction
    | 'skip'
    | instruction ';' instruction;

initArrayList : expr (',' expr)* ;

arrayList :
    variable '[' CONSTANTE ']' (ASSIGN '{' initArrayList '}')? ';'
    ;

getArrayList : variable '[' CONSTANTE ']' ';';

relop : INF | INFE | EG	| NEG | SUP	| SUPE;

LEFT : '(';
RIGHT : ')';

ADD : '+';
SUB : '-';
MUL : '*';
DIV : '/';

SUP : '>'; 
SUPE: '>=';
INF : '<';
INFE: '<=';
EG : '==';
NEG: '!=';
NOT :'!';
ASSIGN : ':=';

IF : 'if';
THEN : 'then';
ELSE : 'else';
WHILE : 'while';
DO : 'do';

AND : '&&';
OR : '||';

READ : 'read';
WRITE : 'write';

CONSTANTE : NUMBER;
variable : STRING;
name : STRING;

NUMBER : ('0'..'9') + ('.' ('0'..'9') +)?;
STRING : ('a'..'z')+;
BOOLEAN : 'true' | 'false' ;

types: (NUMBER|BOOLEAN|array)+;
type : 'string' | 'integer' | 'void' | 'boolean' | 'array';
array: LEFT (type ',')+ types RIGHT;

WS : [ \t]+ -> skip;

Newline: ('\r' '\n'? | '\n') -> skip;