const request = require("request");
const registerGateway = function registerGateway() {
    request('http://192.168.11.172/register/gateway?port=5177&register_host=192.168.11.126', function (error, response, body) {
        if (error) {
            console.log(error); 
        }
    });
};
setInterval(registerGateway, 1000);
