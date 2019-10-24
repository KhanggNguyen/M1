/* 1.1 */
/*VERTICAL-EDGE */
CREATE TABLE people (source varchar(10), target varchar(10) primary key not null, ordinal int, txtval varchar(255), numval int);
CREATE TABLE person (source varchar(10), target varchar(10), ordinal int, txtval varchar(255), numval int, primary key (target), foreign key (source) references people(target));
CREATE TABLE name (source varchar(10), target varchar(10), ordinal int, txtval varchar(255), numval int, primary key (target), foreign key (source) references person(target));
CREATE TABLE age (source varchar(10), target varchar(10), ordinal int, txtval varchar(255), numval int, primary key (target), foreign key(source) references person(target));

INSERT INTO people (source, target, ordinal, txtval, numval) VALUES (null, 'n1', null, null, null);
INSERT INTO person VALUES ('n1', 'n2', 1, null, null);
INSERT INTO person VALUES ('n1', 'n5', 2, null, null);
INSERT INTO person VALUES ('n1', 'n7', 3, null, null);
INSERT INTO name VALUES ('n2', 'n3', 1, 'Serge', null);
INSERT INTO name VALUES ('n5', 'n6', 1, null, null);
INSERT INTO age VALUES ('n7', 'n8', 1, null, 42);

SELECT age.numval FROM people p1, person p2, age WHERE p1.target = p2.source AND p2.target = age.source;

/*Monet DB */
CREATE TABLE monet_people(node varchar(10), txtval varchar(255), numval int, primary key (node)); 
CREATE TABLE monet_people_person(node varchar(10), txtval varchar(255), numval int, primary key (node)); 
CREATE TABLE monet_people_person_name(node varchar(10), txtval varchar(255), numval int, primary key (node)); 
CREATE TABLE monet_people_person_age(node varchar(10), txtval varchar(255), numval int, primary key (node)); 

INSERT INTO monet_people VALUES ('n1', null, null);
INSERT INTO monet_people_person VALUES ('n2', null, null);
INSERT INTO monet_people_person VALUES('n5', null, null);
INSERT INTO monet_people_ person VALUES('n7', null, null);
INSERT INTO monet_people_person_name VALUES ('n3', 'Serge', null);
INSERT INTO monet_people_person_name VALUES ('n6', null, null);
INSERT INTO monet_people_person_age VALUES ('n8', null, 8);

SELECT txtval, numval
FROM monet_people_person_age;

/* 1.2 */
CREATE TABLE presse(source varchar(10), target varchar(10) primary key not null, ordinal int, txtval varchar(255), numval int);

CREATE TABLE journal(source varchar(10), target varchar(10) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references presse(target));
CREATE TABLE journal_nom(source varchar(10), target varchar(10) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journal(target));
CREATE TABLE journal_directeur(source varchar(10), target varchar(10) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journal(target));
CREATE TABLE journal_article(source varchar(10), target varchar(10) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journal(target));
CREATE TABLE journal_directeur_nom(source varchar(10), target varchar(10) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journal_directeur(target));
CREATE TABLE journal_directeur_prenom(source varchar(10), target varchar(10) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journal_directeur(target));
CREATE TABLE journal_article_titre(source varchar(10), target varchar(10) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journal_article(target));
CREATE TABLE journal_article_auteur(source varchar(10), target varchar(10) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journal_article(target));
CREATE TABLE journal_article_corps(source varchar(10), target varchar(10) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journal_article(target));

CREATE TABLE journalistes (source varchar(10), target varchar(10) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key(source) references presse(target));
CREATE TABLE journaliste (source varchar(10), target varchar(10) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journalistes(target));
CREATE TABLE journaliste_anonymisation (source varchar(10), target varchar(10) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journaliste(target));
CREATE TABLE journaliste_nom (source varchar(10), target varchar(10) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journaliste(target));
CREATE TABLE journaliste_prenom (source varchar(10), target varchar(10) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journaliste(target));

