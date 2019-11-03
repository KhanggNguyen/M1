CREATE TABLE tweeter_table_clob (colonne_texte VARCHAR(20), colonne_xml XMLTYPE) XMLTYPE colonne_xml STORE AS CLOB;

INSERT INTO tweeter_table_clob VALUES ('test', sys.xmltype.createxml('') ); 
