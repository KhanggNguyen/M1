const mongoose = require('mongoose');

var panierSchema = new mongoose.Schema({
    _id: {
        type: mongoose.Schema.Types.ObjectId
    },
    nom: {
        type: String
    },
    type: {
        type: String
    },
    prix: {
        type: String
    },
    quantite: {
        type: Number
    },
    user: {
        type: String
    }
});

mongoose.model('Panier', panierSchema);