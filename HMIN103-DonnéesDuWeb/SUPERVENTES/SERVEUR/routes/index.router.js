const express = require('express');
const router = express.Router();

const ctrlUser = require('../controllers/user.controller');

router.post('/inscription', ctrlUser.register);

module.exports = router;