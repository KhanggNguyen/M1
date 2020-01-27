const mongoose = require('mongoose');
require('mongoose-double')(mongoose);

var SchemaTypes = mongoose.Schema.Types;
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
        type: SchemaTypes.Double
    }
});

mongoose.model('Produit', produitSchema, 'produits');