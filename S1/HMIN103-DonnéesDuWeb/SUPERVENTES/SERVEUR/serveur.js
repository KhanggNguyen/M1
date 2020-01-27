require('./config/config'); // les configs du serveur et bdd
require('./models/db'); // connecter à la bdd
require('./config/passportConfig'); //gestion d'authentification

const express = require('express');
const app = express();
const bodyParser = require('body-parser');//permet d'extraire les données du requete plus simple
const cors = require('cors');//proteger les clients contre les requêtes des differents domain
const passport = require('passport');

const rtsIndex = require('./routes/index.router');

app.use(cors());
app.use(passport.initialize());
app.use(bodyParser.json());
//app.use(express.json());//pour les requests POST et PUT 
//app.use(express.urlencoded({extended : true}));
app.use(function (req, res, next){
    res.setHeader('Access-Control-Allow-origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET,POST,PUT,DELETE');
    res.setHeader('Access-Control-Allow-Headers', '*');
    next();
});
app.use('/', rtsIndex);

//gestion d'erreur
app.use((err, req, res, next) => {
    if(err.name === 'ValidationError'){
        var valError = [];
        Object.keys(err.errors).forEach(key => valError.push(err.errors[key].message));
        res.status(422).send(valError);
    }
});

//demarrage du serveur
app.listen(process.env.PORT, () => console.log(`Serveur demarrage sur la port : ${process.env.PORT}`));