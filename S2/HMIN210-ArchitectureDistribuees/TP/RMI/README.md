Pour compiler : 

javac ./Client/share/*.java
javac ./Client/client/*.java

javac./Server/share/*.java
javac ./Server/server/*.java

Pour exécuter, ouvrez le terminal sur le dossier respectivement Client et Server

java server.Server//Pour démarrer le serveur

java client.Client //Pour démarrer le client

//pour la partie alert, on veut avoir une mécanisme où le client sera alerté quand le véto atteint la seuil -> une liste des client(on sera conscience celui qui est encore connecté) -> cherche à enregistrer la liste des clients
