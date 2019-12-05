const mongoose = require('mongoose');

const User = mongoose.model('User');//récuperer la schéma utilisateur 

module.exports.register = (req, res, next) => {
    console.log("/inscription");
    try{
        //verif si email est utilisé
        db.collection("membres").find({email : req.body.email}).toArray((err, documents) => {
            if (documents.length != 0){
                console.log("utilisateur existe");
                //res.end(JSON.stringify({"resultat" : 0 , "message" : "L'email existe dans la db "}));
            }else{
                var user = new User();
                user.nom = req.body.nom;
                user.prenom =req.body.prenom;
                user.email = req.body.email;
                user.password = req.body.mdp;
                user.save((err, doc) => {
                    if(!err){
                        res.send(doc);
                    }
                });
                /*
                db.collection("membres").insertOne(user, function(err, res) {
                    console.log("Utilisateur ajouté dans la db!");
                    //res.end(JSON.stringify({"resultat" : 1 , "message" : "Inscription réussi!"}));
                });*/
            }
        });
    }catch (err){
        console.log("Erreur sur /inscription : " + err);
        res.end(JSON.stringify({"resultat" : 0, "message" : err}));
    }
}