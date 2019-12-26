const mongoose = require('mongoose');

const Produit = mongoose.model('Produit');//récuperer la schéma produit

module.exports.getProduits = (req, res, next) => {
    Produit.find({}, function(err, produits){
        res.send(produits);
    });
}

module.exports.getProduitsByCategorie = (req, res, next) => {
    let categorie = req.params.categorie;
    try{
        Produit.find({ type: categorie }, function(err, produits){
            res.send(produits);
        });
    }catch(e){
        console.log("Erreur sur /produits/" + categorie + " : " + e);
        res.end(JSON.stringify([]));
    }
}

module.exports.getCategories = (req, res, next) => {
    categories = [];
    try{
        Produit.find({}, function(err, produits){
            for (let p of produits){
                if (!categories.includes(p.type)) categories.push(p.type);
            }
            res.send(categories);
        });
    }catch(e){
        console.log("Erreur sur /categories : " + e);
        res.end(JSON.stringify([]));
    }
}