require('./config/config'); // les configs du serveur et bdd
require('./models/db'); // connecter à la bdd

const express = require('express'); //traiter les requêtes avec la BDD et renvoyer les reponses
const app = express();
const bodyParser = require('body-parser');//permet d'extraire les données du requete plus simple
const cors = require('cors');//proteger les clients contre les requêtes des differents domain

const rtsIndex = require('./routes/index.router');

app.use(bodyParser.json());
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({extended : true}));
app.use(function (req, res, next){
    res.setHeader('Access-Control-Allow-origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET,POST,PUT,DELETE');
    res.setHeader('Access-Control-Allow-Headers', '*');
    next();
});
app.use('/', rtsIndex);

//var MongoDB
const MongoClient = require('mongodb').MongoClient;
const ObjectID = require('mongodb').ObjectId;

const url ="mongodb://localhost:27017";

//etablie une connexion sur la mongodb
MongoClient.connect(url, {useNewUrlParser: true}, (err, client) => {
    let db = client.db("SUPERVENTES");

    /* liste les produits */
    app.get("/produits", (req,res) => {
        console.log("/produits");
        try{
            db.collection("produits").find().toArray((err, documents) => {
                res.end(JSON.stringify(documents));
            });
        }catch(e){
            console.log("Erreur sur /produits : " + e);
            res.end(JSON.stringify([]));
        }
    });

    app.get("/produits/:categorie", (req,res) => {
        let categorie = req.params.categorie;
        console.log("/produits/" + categorie);
        try{
            db.collection("produits").find({ type: categorie}).toArray((err, documents) => {
                res.end(JSON.stringify(documents));
            })
        }catch{
            console.log("Erreur sur /produits/" + categorie + " : " + e);
            res.end(JSON.stringify([]));
        }
    });

    /*  liste des categories */
    app.get("/categories", (req, res) => {
        console.log("/categories");
        categories = [];
        try{
            db.collection("produits").find().toArray((err, documents) => {
                for (let doc of documents){
                    if (!categories.includes(doc.type)) categories.push(doc.type);
                }
                console.log("Renvoi de" + JSON.stringify(categories));
                res.end(JSON.stringify(categories));
            });
        }catch(e){
            console.log("Erreur sur /categories : " + e);
            res.end(JSON.stringify([]));
        }
    });

    /* connexion */
    app.post("/membre/connexion", (req, res) => {
        console.log("/utilisateurs/connexion avec " + JSON.stringify(req.body));
        try{
            db.collection("membres").find(req.body).toArray((err, documents) => {
                if (documents.length == 1){
                    res.end(JSON.stringify({"resultat" : 1 , "message" : "Authentification réussie"}));
                }else{
                    res.end(JSON.stringify({ "resultat" : 0, "message" : "Email et/ou mdp incorrect" }));
                }
            });
        }catch(e){
            console.log("Erreur sur /membre/connexion : " + e);
            res.end(JSON.stringify({"resultat" : 0, "message" : e}));
        }
    });

    /* ajout dans panier */

    /* rechercher */

});

//demarrage du serveur
app.listen(process.env.PORT, () => console.log(`Serveur demarrage sur la port : ${process.env.PORT}`));