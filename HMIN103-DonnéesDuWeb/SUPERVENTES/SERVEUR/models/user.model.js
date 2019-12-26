const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

var userSchema = new mongoose.Schema({
    _id: {
        type: mongoose.Schema.Types.ObjectId
    },
    nom: {
        type: String,
        required: "Le prénom ne peut pas être vide."
    },
    prenom:{
        type: String,
        required: "Le nom ne peut pas être vide."
    },
    email: {
        type: String,
        required: "L'email ne peut pas être vide.",
        unique: true
    },
    mdp: {
        type: String,
        required: "Le mot de passe ne peut pas être vide.",
        minlength : [4,'Password must be atleast 4 character long']
    },
    salt: String
});

//event
userSchema.pre('save', function(next){
    bcrypt.genSalt(10, (err, salt) => {
        bcrypt.hash(this.mdp, salt, (err, hash) => {
            this.mdp = hash;
            this.salt = salt;
            next();
        });
    });
});

userSchema.methods.verifyPassword = function(password){
    return bcrypt.compareSync(password, this.mdp );
};

userSchema.methods.generateJwt = function (){
    return jwt.sign({ _id: this._id, _nom: this.nom, _prenom: this.prenom  },
            process.env.JWT_SECRET,
            {
                expiresIn: process.env.JWT_EXP
            }
        );
}

mongoose.model('User', userSchema, 'membres');