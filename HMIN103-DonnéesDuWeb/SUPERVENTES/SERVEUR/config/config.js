//verif environement 
var env = process.env.NODE_ENV || 'development';
//mettre les env. dans config
var config = require('./config.json');
var envConfig = config[env];
//ajouter les valeurs de envConfig à process.env
Object.keys(envConfig).forEach(key => process.env[key] = envConfig[key]);

