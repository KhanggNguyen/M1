CREATE TABLE NODE (begin int, end int, par int, tag VARCHAR(255), type VARCHAR(255), primary key (begin, end)); 

INSERT INTO NODE VALUES (1, 24, null, 'batiment', 'ELT');
INSERT INTO NODE VALUES (2, 23, 1, 'etage', 'ELT');
INSERT INTO NODE VALUES (3, 6, 2, 'description', 'ELT');
INSERT INTO NODE VALUES (4, 5, 3, 'RDC', 'TEXT');
INSERT INTO NODE VALUES (7, 16, 2, 'bureau', 'ELT');
INSERT INTO NODE VALUES (8, 11, 7, 'code', 'ELT');
INSERT INTO NODE VALUES (9, 10, 8, '123', 'TEXT');
INSERT INTO NODE VALUES (12, 15, 7, 'personne', 'ELT');
INSERT INTO NODE VALUES (13, 14, 12, 'TRAN M1', 'TEXT');
INSERT INTO NODE VALUES (17, 22, 2, 'salle', 'ELT');
INSERT INTO NODE VALUES (18, 21, 17, 'nombrePlaces', 'ELT');
INSERT INTO NODE VALUES (19, 20, 18, '20', 'TEXT');

1) SELECT b.begin FROM NODE a, NODE b WHERE a.tag = "nombrePlaces" AND Descendant(a.begin, b.begin) ;
