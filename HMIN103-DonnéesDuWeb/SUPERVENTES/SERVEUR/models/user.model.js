const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');

var userSchema = new mongoose.Schema({
    FirstName: {
        type: String
    },
    LastName:{
        type: String
    },
    email: {
        type: String
    },
    password: {
        type: String
    },
    salt: String
});

//event 
userSchema.pre('save', function(next){
    bcrypt.genSalt(10, (err, salt) => {
        bcrypt.hash(this.password, salt, (err, hash) => {
            this.password = hash;
            this.salt = salt;
            next();
        });
    });
});

mongoose.model('User', userSchema);