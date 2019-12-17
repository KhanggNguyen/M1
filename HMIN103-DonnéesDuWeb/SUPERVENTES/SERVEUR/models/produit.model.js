const mongoose = require('mongoose');

var produitSchema = new mongoose.Schema({
    _id: {
        type: mongoose.Schema.Types.ObjectId
    },
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
        type: Number 
    },
    quantite: {
        type: Number
    }
});

mongoose.model('Produit', produitSchema, 'produits');