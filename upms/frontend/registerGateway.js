const request = require("request");
const registerGateway = function registerGateway() {
    request('http://192.168.11.218/register/gateway?port=5177&register_host=192.168.11.118', function (error, response, body) {
        if (error) {
            console.log(error); 
        }
    });
};
setInterval(registerGateway, 1000);
