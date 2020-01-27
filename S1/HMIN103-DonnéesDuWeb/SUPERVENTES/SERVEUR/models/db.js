const mongoose = require('mongoose');

mongoose.connect(process.env.MONGODB_URI, (err) => {
    if(!err) { console.log('Connecté sur la MongoDB.'); }
    else { console.log('Erreur de connexion sur la MongoBD : ' + JSON.stringify(err, undefined, 2)); }
});

require('./user.model');
require('./produit.model');
require('./panier.model');