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
/* creation des tables */
CREATE TABLE presse(source varchar(255), target varchar(255) primary key not null, ordinal int, txtval varchar(255), numval int);

CREATE TABLE journalistes (source varchar(255), target varchar(255) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key(source) references presse(target));
CREATE TABLE journaliste (source varchar(255), target varchar(255) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journalistes(target));
CREATE TABLE journaliste_id (source varchar(255), target varchar(255) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journaliste(target));
CREATE TABLE journaliste_anonymisation (source varchar(255), target varchar(255) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journaliste(target));
CREATE TABLE journaliste_nom (source varchar(255), target varchar(255) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journaliste(target));
CREATE TABLE journaliste_prenom (source varchar(255), target varchar(255) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journaliste(target));

CREATE TABLE journal(source varchar(255), target varchar(255) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references presse(target));
CREATE TABLE journal_nom(source varchar(255), target varchar(255) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journal(target));
CREATE TABLE journal_directeur(source varchar(255), target varchar(255) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journal(target));
CREATE TABLE journal_article(source varchar(255), target varchar(255) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journal(target));
CREATE TABLE journal_directeur_nom(source varchar(255), target varchar(255) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journal_directeur(target));
CREATE TABLE journal_directeur_prenom(source varchar(255), target varchar(255) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journal_directeur(target));
CREATE TABLE journal_article_titre(source varchar(255), target varchar(255) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journal_article(target));
CREATE TABLE journal_article_auteur(source varchar(255), target varchar(255) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journal_article(target), foreign key (target) references journaliste_id(target));
CREATE TABLE journal_article_corps(source varchar(255), target varchar(255) primary key not null, ordinal int, txtval varchar(255), numval int, foreign key (source) references journal_article(target));



/* peupler */
INSERT INTO presse VALUES (null, 'press_id1', 1, null, null);

INSERT INTO journal VALUES ('press_id1', 'journal_id1', 1, null, null);
INSERT INTO journal_nom VALUES ('journal_id1', 'journal_nom_id1', 1, 'MIDI', null);

INSERT INTO journal_directeur VALUES ('journal_id1', 'journal_directeur_id1', 2, null, null);
INSERT INTO journal_directeur_nom VALUES ('journal_directeur_id1', 'journal_directeur_nom_id1', 1, 'Nguyen', null);
INSERT INTO journal_directeur_prenom VALUES ('journal_directeur_id1', 'journal_directeur_prenom_id1', 2, 'Huu Khang', null);

INSERT INTO journalistes VALUES ('press_id1', 'journalistes_id1', 2, null, null);

INSERT INTO journaliste VALUES ('journalistes_id1', 'journaliste_id1', 1, null, null);
INSERT INTO journaliste_id VALUES ('journaliste_id1', 'journaliste_id1', 1, null, 1);
INSERT INTO journaliste_anonymisation VALUES ('journaliste_id1', 'journaliste_anonymisation_id1', 2, 'oui', null);
INSERT INTO journaliste_nom VALUES ('journaliste_id1', 'journaliste_nom_id1', 3, 'Tran', null);
INSERT INTO journaliste_prenom VALUES ('journaliste_id1', 'journaliste_prenom_id1', 4, 'My', null);

INSERT INTO journaliste VALUES ('journalistes_id1', 'journaliste_id2', 2, null, null);
INSERT INTO journaliste_id VALUES ('journaliste_id2', 'journaliste_id2', 1, null, 2);
INSERT INTO journaliste_anonymisation VALUES ('journaliste_id2', 'journaliste_anonymisation_id2', 2, 'non', null);
INSERT INTO journaliste_nom VALUES ('journaliste_id2', 'journaliste_nom_id2', 3, 'Anas', null);
INSERT INTO journaliste_prenom VALUES ('journaliste_id2', 'journaliste_prenom_id2', 4, 'Strike', null);

INSERT INTO journaliste VALUES ('journalistes_id1', 'journaliste_id3', 3, null, null);
INSERT INTO journaliste_id VALUES ('journaliste_id3', 'journaliste_id3', 1, null, 3);
INSERT INTO journaliste_anonymisation VALUES ('journaliste_id3', 'journaliste_anonymisation_id3', 2, 'non', null);
INSERT INTO journaliste_nom VALUES ('journaliste_id3', 'journaliste_nom_id3', 3, 'Monkey D', null);
INSERT INTO journaliste_prenom VALUES ('journaliste_id3', 'journaliste_prenom_id3', 4, 'Luffy', null);

INSERT INTO journal_article VALUES ('journal_id1', 'journal_article_id1', 3, null, null);
INSERT INTO journal_article_titre VALUES ('journal_article_id1', 'journal_article_titre_id1', 1, 'Voici le titre', null);
INSERT INTO journal_article_auteur VALUES ('journal_article_id1', 'journaliste_id1', 2, null, null);
INSERT INTO journal_article_corps VALUES ('journal_article_id1', 'journal_article_corps_id1', 3, 'Ceci est le corps', null);

INSERT INTO journal_article VALUES ('journal_id1', 'journal_article_id2', 4, null, null);
INSERT INTO journal_article_titre VALUES ('journal_article_id2', 'journal_article_titre_id2', 1, 'Voici le titre', null);
INSERT INTO journal_article_auteur VALUES ('journal_article_id2', 'journaliste_id2', 2, null, null);
INSERT INTO journal_article_corps VALUES ('journal_article_id2', 'journal_article_corps_id2', 3, 'Ceci est le corps', null);



/* 1.3 */



