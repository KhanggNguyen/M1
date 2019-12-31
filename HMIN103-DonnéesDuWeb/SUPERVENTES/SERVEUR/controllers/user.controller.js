const mongoose = require('mongoose');
const passport = require('passport');
const _ = require('lodash');

const User = mongoose.model('User');//récuperer la schéma utilisateur 

module.exports.register = (req, res, next) => {
    //verif si email est utilisé
    var user = new User();
    user._id = new mongoose.Types.ObjectId(),
    user.nom = req.body._nom;
    user.prenom =req.body._prenom;
    user.email = req.body._email;
    user.mdp = req.body._password;
    user.save((err, doc) => {
        if(!err){
            res.send(doc);
        }else{
            if(err.code == 11000){
                res.status(422).send(["L'email est déjà utilisé par un autre utilisateur!"]);
            }else{
                return next(err);
           }
        }
    });
}

module.exports.authentification = (req, res, next) => {
    passport.authenticate('local', (err, user, info) => {
        //erreur de passport middleware
        if(err) return res.status(400).json(err);
        //utilisateur trouvé
        else if(user) return res.status(200).json({ "token" : user.generateJwt() });
        //mauvais mdp ou l'email non existant
        else return res.status(404).json(info);
    })(req, res);
}

module.exports.profile = (req, res, next) => {
    User.findOne({ _id: req._id }, 
        (err, user) => {
            if(!user)
                return res.status(404).json({ status: false, message: 'Utilisateur non trouvé'});
            else
                return res.status(200).json({status: true, user: _.pick(user,['nom', 'prenom', 'email']) });
    });
}