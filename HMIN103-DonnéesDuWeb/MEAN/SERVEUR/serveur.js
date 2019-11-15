const express = require('express');
const app = express();
app.use(express.json());
app.use(express.urlencoded({extended : true}));
app.use(function (req, res, next){
    res.setHeader('Access-Control-Allow-origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET,POST,PUT,DELETE');
    res.setHeader('Access-Control-Allow-Headers', '*');
    next();
});

const MongoClient = require('mongodb').MongoClient;
const ObjectID = require('mongodb').ObjectId;
const url ="mongodb://localhost:27017";

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

    /* inscription */
    app.post("/inscription", (req, res) => {
        console.log("/inscription");
        try{
            db.collection("membres").find({email : req.body.email}).toArray((err, documents) => {
                if (documents.length != 0){
                    console.log("utilisateur existe");
                    //res.end(JSON.stringify({"resultat" : 0 , "message" : "L'email existe dans la db "}));
                }else{
                    let nom = req.body.nom;
                    let prenom =req.body.prenom;
                    let email = req.body.email;
                    let mdp = req.body.mdp;
                    let myUser = {nom : nom, prenom : prenom, email : email, mdp : mdp};
                    db.collection("membres").insertOne(myUser, function(err, res) {
                        console.log("Utilisateur ajouté dans la db!");
                        //res.end(JSON.stringify({"resultat" : 1 , "message" : "Inscription réussi!"}));
                    });
                }
            });
        }catch (e){
            console.log("Erreur sur /inscription : " + e);
            res.end(JSON.stringify({"resultat" : 0, "message" : e}));
        }
    });

    /* panier */

    /* rechercher */

});

app.listen(8888);