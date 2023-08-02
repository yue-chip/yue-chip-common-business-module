const request = require("request");
const registerGateway = function registerGateway() {
    request('http://192.168.177.129/register/gateway?port=5174&register_host=192.168.177.1', function (error, response, body) {
        if (error) {
            console.log(error);
        }
    });
};
setInterval(registerGateway, 1000);
