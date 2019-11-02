/* 1.1 */
/*VERTICAL-EDGE */
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

/* Monet DB */
CREATE TABLE presse (node VARCHAR(255) primary key not null, txtval VARCHAR(255), numval INT)

CREATE TABLE presse_journalistes (node VARCHAR(255) primary key not null, txtval VARCHAR(255), numval INT)
CREATE TABLE pressejournalistes_journaliste (node VARCHAR(255) primary key not null, txtval VARCHAR(255), numval INT)
CREATE TABLE pressejournalistes_journaliste_id (node VARCHAR(255) primary key not null, txtval VARCHAR(255), numval INT)
CREATE TABLE pressejournalistes_journaliste_anonymisation (node VARCHAR(255) primary key not null, txtval VARCHAR(255), numval INT)
CREATE TABLE pressejournalistes_journaliste_nom (node VARCHAR(255) primary key not null, txtval VARCHAR(255), numval INT)
CREATE TABLE pressejournalistes_journaliste_prenom (node VARCHAR(255) primary key not null, txtval VARCHAR(255), numval INT)

CREATE TABLE presse_journal (node VARCHAR(255) primary key not null, txtval VARCHAR(255), numval INT)
CREATE TABLE presse_journal_nom (node VARCHAR(255) primary key not null, txtval VARCHAR(255), numval INT)
CREATE TABLE presse_journal_directeur (node VARCHAR(255) primary key not null, txtval VARCHAR(255), numval INT)
CREATE TABLE presse_journal_directeur_nom (node VARCHAR(255) primary key not null, txtval VARCHAR(255), numval INT)
CREATE TABLE presse_journal_directeur_prenom (node VARCHAR(255) primary key not null, txtval VARCHAR(255), numval INT)
CREATE TABLE presse_journal_article (node VARCHAR(255) primary key not null, txtval VARCHAR(255), numval INT)
CREATE TABLE presse_journal_article_titre (node VARCHAR(255) primary key not null, txtval VARCHAR(255), numval INT)
CREATE TABLE presse_journal_article_auteur (node VARCHAR(255) primary key not null, txtval VARCHAR(255), numval INT)
CREATE TABLE presse_journal_article_corps (node VARCHAR(255) primary key not null, txtval VARCHAR(255), numval INT)

/* 1.2 */
/* peupler */
/* Vertical-Edge */
INSERT INTO presse VALUES (null, 'n1', 1, null, null);

INSERT INTO journal VALUES ('n1', 'n3', 1, null, null);
INSERT INTO journal_nom VALUES ('n3', 'n4', 1, 'MIDI LIBRE', null);

INSERT INTO journal_directeur VALUES ('n3', 'n5', 2, null, null);
INSERT INTO journal_directeur_nom VALUES ('n5', 'n7', 1, 'Nguyen', null);
INSERT INTO journal_directeur_prenom VALUES ('n5', 'n8', 2, 'Huu Khang', null);

INSERT INTO journalistes VALUES ('n1', 'n2', 2, null, null);

INSERT INTO journaliste VALUES ('n2', 'n12', 1, null, null);
INSERT INTO journaliste_id VALUES ('n12', 'n13', 1, null, 1);
INSERT INTO journaliste_anonymisation VALUES ('n12', 'n14', 2, 'oui', null);
INSERT INTO journaliste_nom VALUES ('n12', 'n15', 3, 'Tran', null);
INSERT INTO journaliste_prenom VALUES ('n12', 'n16', 4, 'My', null);

INSERT INTO journal_article VALUES ('n3', 'n6', 3, null, null);
INSERT INTO journal_article_titre VALUES ('n6', 'n9', 1, 'Voici le titre1', null);
INSERT INTO journal_article_auteur VALUES ('n6', 'n10', 2, null, null);
INSERT INTO journal_article_corps VALUES ('n6', 'n11', 3, 'Ceci est le corps', null);

INSERT INTO journal_article VALUES ('journal_id1', 'journal_article_id2', 4, null, null);
INSERT INTO journal_article_titre VALUES ('journal_article_id2', 'journal_article_titre_id2', 1, 'Voici le titre2', null);
INSERT INTO journal_article_auteur VALUES ('journal_article_id2', 'journaliste_id2', 2, null, null);
INSERT INTO journal_article_corps VALUES ('journal_article_id2', 'journal_article_corps_id2', 3, 'Ceci est le corps', null);

/* Monet DB */
INSERT INTO presse VALUES ('n1', null, null);
INSERT INTO presse_journalistes VALUES ('n2', null, null);
INSERT INTO presse_journalistes_journaliste VALUES('n3', null, null);
INSERT INTO presse_journalistes_journaliste_id VALUES('n4', null, 1);
INSERT INTO presse_journalistes_journaliste_anonymisation VALUES('n6', 'oui', null);
INSERT INTO presse_journalistes_journaliste_nom VALUES('n8', 'TRAN', null);
INSERT INTO presse_journalistes_journaliste_prenom VALUES('n10', 'MY', null);
INSERT INTO presse_journal VALUES('n12', null, null);
INSERT INTO presse_journal_nom VALUES('13', 'MIDI LIBRE', null);
INSERT INTO presse_journal_directeur VALUES('n15', null, null);
INSERT INTO presse_journal_directeur_nom VALUES('n16', 'NGUYEN', null);
INSERT INTO presse_journal_directeur_prenom VALUES('n18', 'KHANG', null);
INSERT INTO presse_journal_article_titre VALUES('n20', 'VOici le titre', null);
INSERT INTO presse_journal_article_auteur VALUES('n22', null, 1);
INSERT INTO presse_journal_article_corps VALUES('n24', 'Voici le corps', null);

