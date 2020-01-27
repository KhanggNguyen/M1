const mongoose = require('mongoose');

const Produit = mongoose.model('Produit');//récuperer la schéma produit

//liste produits
module.exports.getProduits = (req, res, next) => {
    Produit.find(
        {}, 
        null,
        { sort: {nom: 1} }, 
        function(err, produits){
            res.send(produits);
        }
    );
}

//liste produits par catégories
module.exports.getProduitsByCategorie = (req, res, next) => {
    let categorie = req.params.categorie;
    try{
        Produit.find(
            { type: categorie }, 
            null,
            { sort: {nom: 1} }, 
            function(err, produits){
                res.send(produits);
            }
        );
    }catch(e){
        console.log("Erreur sur /produits/" + categorie + " : " + e);
        res.end(JSON.stringify([]));
    }
}

module.exports.getCategories = (req, res, next) => {
    categories = [];
    try{
        Produit.find(
            {}, 
            null,
            { sort: {nom: 1} }, 
            function(err, produits){
                for (let p of produits){
                    if (!categories.includes(p.type)) categories.push(p.type);
                }
                res.send(categories);
            }
        );
    }catch(e){
        console.log("Erreur sur /categories : " + e);
        res.end(JSON.stringify([]));
    }
}

module.exports.getProduitsByCriteres = (req, res, next) => {
    var nom = '';
    var marque = '';
    var categorie = '';
    var prix = 9999;
    if(req.body.nom !== ''){
        nom = req.body.nom;
    }
    
    if(req.body.marque !== ''){
        marque = req.body.marque;
    }

    if(req.body.categorie !== ''){
        categorie = req.body.categorie;
    } 

    if(typeof req.body.prix !== 'undefined' && req.body.prix){
        prix = parseInt(req.body.prix);
    }

    try{
        Produit.find(
            {
                nom : new RegExp(nom, 'i'), 
                marque: new RegExp(marque, 'i'), 
                type : new RegExp(categorie, 'i'), 
                prix: {$lt :  prix } 
            }, null,
            { sort: {nom: 1} },
            function(err, produits){
                res.send(produits);
            }, 
        );
    }catch(e){
        console.log("Erreur sur /recherche : " + e);
        res.end(JSON.stringify([]));
    }
}