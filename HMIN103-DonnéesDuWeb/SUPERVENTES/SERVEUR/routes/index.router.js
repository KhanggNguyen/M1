const express = require('express');
const router = express.Router();

const jwtHelper = require('../config/jwtHelper');//pour user profile

const ctrlUser = require('../controllers/user.controller');
const ctrlProduit = require('../controllers/produit.controller');

//users
router.post('/inscription', ctrlUser.register);
router.post('/authentification', ctrlUser.authentification);
router.get('/profile', jwtHelper.verifyJwtToken, ctrlUser.profile); 

//produits
router.get("/produits", ctrlProduit.getProduits);
router.get("/produits/:categorie", ctrlProduit.getProduitsByCategorie);
router.get("/categories", ctrlProduit.getCategories);

module.exports = router;