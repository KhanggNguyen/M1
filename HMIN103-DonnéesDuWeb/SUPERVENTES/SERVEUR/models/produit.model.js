const mongoose = require('mongoose');

var produitSchema = new mongoose.Schema({
    nom: {
        type: String
    },
    marque: {
        type: String
    },
    type: {
        type: String
    },
    prix: {
        type: String 
    }
});

mongoose.model('Produit', produitSchema, 'produits');