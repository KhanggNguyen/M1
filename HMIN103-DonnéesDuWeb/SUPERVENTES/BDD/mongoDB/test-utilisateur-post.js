//set a reference to the request module
var request = require('request'),
	//stubs
	postData = {},
	postConfig = {},
	postSuccessHandler = null;

//make the POST request
request.post('http://localhost:8888/inscription',{
    json : {
            nom:'Nom Testeur',
            prenom:'Prenom Testeur',
            email: 'tester@yopmail.com',
            mdp : '123456'
    }
}, (error, res, body) => {
    if (error) {
      console.error(error)
      return
    }
    console.log(`statusCode: ${res.statusCode}`)
    console.log(body)
});
