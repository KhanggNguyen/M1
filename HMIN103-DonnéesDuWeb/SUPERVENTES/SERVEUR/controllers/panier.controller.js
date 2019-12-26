const mongoose = require('mongoose');
const passport = require('passport');
const Panier = mongoose.model('Panier');
const User = mongoose.model('User');

module.exports.getProduitsPanier = (req, res, next) => {
    let user_id = req.params.id;
    Panier.find({user : user_id}, function(err, produits){
        res.send(produits);
    });
}

module.exports.ajoutProduitPanier = (req, res, next) => {
    User.findOne({_id: req.body.userId}, 
        (err, user) => { 
            if(!user) return res.status(404).json({ status: false, message: 'Utilisateur non trouvé'});
            else{
                let produitPanier = new Panier({
                    _id: new mongoose.Types.ObjectId(),
                    nom: req.body.nomProduit,
                    type: req.body.type,
                    prix: req.body.prix,
                    quantite: req.body.quantite,
                    user: req.body.userId
                });
                produitPanier.save((err, doc) => {
                    if(!err){
                        res.status(200).json(produitPanier);
                    }else{

                    }
                });
            }
        })
    }
