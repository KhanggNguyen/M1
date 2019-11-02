/*2.1*/
CREATE TABLE batiment (idbatiment int primary key not null);
CREATE TABLE etage (idetage int primary key not null, description varchar(255), idbatiment int, foreign key(idbatiment) references batiment(idbatiment));
CREATE TABLE bureau (idbureau int primary key not null, code varchar(255), idetage int, foreign key(idetage) references etage(idetage));
CREATE TABLE personne (idp int primary key not null, description varchar(255), idbureau int, foreign key(idbureau) references bureau(idbureau));
CREATE TABLE salle (idsalle int primary key not null, nombrePlaces int, idetage int, foreign key (idetage) references etage(idetage));

/*2.2*/
INSERT INTO batiment VALUES (1);
INSERT INTO batiment VALUES (2);
INSERT INTO batiment VALUES (3);

INSERT INTO etage VALUES (1, 'RDC', 1);
INSERT INTO bureau VALUES (1, '2520', 1);
INSERT INTO personne VALUES (1, 'personne 1', 1);
INSERT INTO personne VALUES (2, 'personne 2', 1);
INSERT INTO salle VALUES (1, 100, 1); 

INSERT INTO etage VALUES (2, 'ETG 1', 1);
INSERT INTO bureau VALUES (2, '2000', 2);
INSERT INTO personne VALUES (3, 'personne 3', 2);
INSERT INTO personne VALUES (4, 'personne 4', 2);
INSERT INTO salle VALUES (2, 150, 2);

INSERT INTO etage VALUES (3, 'RDC', 2);
INSERT INTO bureau VALUES (3, '1111', 3);
INSERT INTO salle VALUES (3, 111, 3);

INSERT INTO etage VALUES (4, 'RDC', 3);
INSERT INTO bureau VALUES (4, '1234', 4);
INSERT INTO salle VALUES (4, 123, 4);

/*2.3*/
SELECT * FROM batiment;
SELECT idetage FROM etage WHERE description = 'RDC';
SELECT * FROM personne;
SELECT idbatiment FROM etage WHERE description = 'ETG 1';
SELECT idbatiment FROM etage, bureau, personne WHERE etage.idetage = bureau.idetage AND bureau.idbureau = personne.idbureau HAVING COUNT(idp) > 1 GROUP BY idbatiment;
