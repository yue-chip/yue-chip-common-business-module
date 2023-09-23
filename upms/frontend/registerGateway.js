const request = require("request");
const registerGateway = function registerGateway() {
    request('http://192.168.0.145/register/gateway?port=5174&register_host=192.168.0.132', function (error, response, body) {
        if (error) {
            console.log(error);
        }
    });
};
setInterval(registerGateway, 1000);
