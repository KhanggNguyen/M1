% données 
homme(albert).
homme(jean).
homme(paul).
homme(bertrand).
homme(louis).
homme(benoit).
femme(germaine).
femme(christiane).
femme(simone).
femme(marie).
femme(sophie).
parent(albert,jean).
parent(jean,paul).
parent(paul,bertrand).
parent(paul,sophie).
parent(jean,simone).
parent(louis,benoit).
parent(germaine,jean).
parent(christiane,simone).
parent(christiane,paul).
parent(simone,benoit).
parent(marie,bertrand).
parent(marie,sophie).

% a.i
homme(paul).
femme(benoit).
femme(X).
homme(Y).
parent(marie,sophie),femme(marie).
parent(X,jean),femme(X).
parent(paul,X).
parent(X,_),homme(X).

% a.ii
% QQS X QQS Y [ (homme(X) & parent(X,Y)) => père(X,Y) ]
pere(X,Y):-homme(X),parent(X,Y).
mere(X,Y):-femme(X),parent(X,Y).
fils(X,Y):-homme(Y),parent(X,Y).
fille(X,Y):-femme(Y),parent(X,Y).
grand_pere(X,Y):-homme(X),parent(X,I),parent(I,Y).
grand_mere(X,Y):-femme(X),parent(X,I),parent(I,Y).
% QQS X QQS Y QQS I [ (homme(X) & parent(X,I) & parent(I,Y) ) => grandpere(X,Y) ]
% QQS X QQS Y       [ (EXISTS I (homme(X) & parent(X,I) & parent(I,Y) )) => grandpere(X,Y) ]
grandparent(X,Y):-parent(X,I),parent(I,Y).
demifrere(X,Y):- parent(I,Y),parent(I,X),homme(X),parent(U,X),parent(V,Y).
frere(X,Y):-parent(I,X),parent(I,Y),parent(U,X),parent(U,Y),homme(X).
soeur(X,Y):-parent(I,X),parent(I,Y),parent(U,X),parent(U,Y),femme(X).

% a.iii

ancetre(X,Y) :- parent(X,Y).
ancetre(X,Y) :- parent(X,Z),ancetre(Z,Y).

descendant(X,Y) :- fils(X,Y);fille(X,Y).
descendant(X,Y) :- fils(Y,Z),descendant(Z,X).

memefamille(X,Y) :- ancetre(I,X),ancetre(I,Y).

% b.

q(X,Z) :- p(X,Y),p(Y,Z).
p([1|Z],Z).

% pour definir une liste
liste1([a,2,b,3,4])

% c.i
appartient(X,[X|_]).
appartient(X,[_|L]) :- appartient(X,L).

% c.ii
non_appartient(_,[]) :- !.
non_appartient(X, [H|T]) :- X \= H, non_appartient(X,T).

% c.iii
sans_repetition([H|T]) :- non_appartient(H,T).

% c.iv
ajout_tete(X,L1,L2) :- L1 = [H1|T1], L2 = [H2|L1], X = H2, appartient(X,L2).

% c.v
ajout_queue(X,[],[X]). 
ajout_queue(X,[Y|M],[Y|N]) :- ajout_queue(X,M,N). 

% c.vi
supprimer(X,[],[]).
supprimer(X,[X|L1],L1).
supprimer(X,[Y|L1],L2):- X\==Y , ajout_tete(Y,L3,L2), supprimer(X,L1,L3).  

% c.vii
supprimer_fin([],[]).
supprimer_fin([Y|L],L1):- supprimer_fin(L,L2),ajout_tete(Y,L2,L1).


% c.viii
fusion(L1,[],L1).
fusion([],L2,L2).
fusion([X|L1],[Y|L2],L3):-ajout_tete(X,L4,L3),ajout_tete(Y,L5,L4), fusion(L1,L2,L5). 

% c.ix 
concatener(L1,[],L1).
concatener([],L2,L2).
concatener([X|L1],L2,L3):-ajout_tete(X,L4,L3),concatener(L1,L2,L4). 

% c.x
inverser([],[]). 
inverser([X|L1],L2):-inverser(L1,L3),ajout_fin(X,L3,L2). 

% c.xi
commun([],L2,[]).
commun([X|L1],L2,L3):- appartient(X,L2),commun(L1,L2,L4),ajout_tete(X,L4,L3).
commun([X|L1],L2,L3):- non_appartient(X,L2),commun(L1,L2,L3).

% c.xii
ens([],[]). 
ens([X|L1],L2):-appartient(X,L1),ens(L1,L2). 
ens([X|L1],L2):-non_appartient(X,L1),ens(L1,L3),ajout_tete(X,L3,L2).  

% c.xiii
reunion([],L2,L2). 
reunion([X|L1],L2,L4):-appartient(X,L2),reunion(L1,L2,L4). 
reunion([X|L1],L2,L3):-non_appartient(X,L2),  ajout_tete(X,L4,L3),reunion(L1,L2,L4).  

% c.xiv
reunionbis(L1,L2,L3):-ens(L1,L1ens), ens(L2,L2ens), reunion(L1ens,L2ens,L3).
