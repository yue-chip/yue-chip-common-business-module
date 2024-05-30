import axios from 'axios';
import { message } from 'ant-design-vue'
// import "ant-design-vue/es/message/style/index.css"
/* 防止重复提交，利用axios的cancelToken */
let pending: any[] = []; // 声明一个数组用于存储每个ajax请求的取消函数和ajax标识
const CancelToken: any = axios.CancelToken;
const removePending: any = (config: any, f: any) => {
    // // 获取请求的url
    // const flagUrl = config.url;
    // // 判断该请求是否在请求队列中
    // if (pending.indexOf(flagUrl) !== -1) {
    //     // 如果在请求中，并存在f,f即axios提供的取消函数
    //     if (f) {
    //         f('cancelDuplicateRequest'); // 执行取消操作
    //     } else {
    //         pending.splice(pending.indexOf(flagUrl), 1); // 把这条记录从数组中移除
    //     }
    // } else {
    //     // 如果不存在在请求队列中，加入队列
    //     if (f) {
    //         pending.push(flagUrl);
    //     }
    // }
};
/* 创建axios实例 */
const service = axios.create({
    // baseURL: import.meta.env.BASE_URL,
    timeout: 3600000 // 请求超时时间
});

/* request拦截器 */
service.interceptors.request.use((config: any) => {
    if (pending.length>=50){
        pending=[];
    }
    if (config.url.indexOf("http") > -1) {
        config.url = config.url + (config.url.indexOf("?") > -1 ? "&" : "?") + "_t=" + new Date().getTime();
    } else {
        // @ts-ignore
        config.url = import.meta.env.VITE_BASEURL + import.meta.env.VITE_BASEAPI + config.url + (config.url.indexOf("?") > -1 ? "&" : "?") + "_t=" + new Date().getTime();
    }
    // if (request_host) {
    //     config.url = config.url + "&request_host="+request_host
    // }
    // neverCancel 配置项，允许多个请求
    if (!config.neverCancel) {
        // 生成cancelToken
        config.cancelToken = new CancelToken((c: any) => {
            removePending(config, c);
        });
    }
    const token = sessionStorage.getItem("token");
    const access_token = sessionStorage.getItem("access_token");

    if (token && token !== ''){
        config.headers.token = `${token}`;
    }
    if (access_token && access_token !== ''){
        config.headers.Authorization = `Bearer ${access_token}`;
    }
    let request_host = sessionStorage.getItem("register_host");
    if (request_host && request_host != 'null'){
        config.headers.requestHost = request_host;
    }
    return config;
}, (error: any) => {
    // @ts-ignore
    Promise.reject(error);
});

/* respone拦截器 */
service.interceptors.response.use(
    (response: any) => {
        // 移除队列中的该请求，注意这时候没有传第二个参数f
        removePending(response.config);
        if (response.status != 200 ){
            message.error(response.status);
        }
        // 获取返回数据，并处理。
        if (response.data.status === 403){
            message.error('登陆异常，请重新登陆！');
            setTimeout(function () {
                if (self != top) {
                    window.top.location.href="/";
                }else {
                    window.location.href="/";
                }
            },5000);
        }else if (response.data.status !== 200) {
            if(response.data.message){
                message.error(response.data.message);
            }else if(response.data.exceptionMessage){
                message.error(response.data.exceptionMessage);
            }
        }
        return response.data;
    },
    (error: any) => {
        // 异常处理
        if (error & error.message) {
            message.error(error.message);
        }
        pending = [];
        if (error.message === 'cancelDuplicateRequest') {
            // @ts-ignore
            return Promise.reject(error);
        }
        // @ts-ignore
        return Promise.reject(error);
    },
);
function delay(ms: number) {
    // @ts-ignore
    return new Promise(resolve => setTimeout(resolve, ms));
}

function _fail(failCallBack,fail){
    if (failCallBack) {
        failCallBack(fail)
    }
    // else {
    //     if (fail && fail.message) {
    //         message.error(fail.message);
    //     }
    // }
}

const axiosGet = function (url:string,params:any,success:any,fail:any,finallyF:any){
    service.get(url,params)
        .then((data)=>{
            if (success) {
                success(data)
            }
        })
        .catch(obj => {
            _fail(fail,obj)
        })
        .finally(()=>{
            if (finallyF) {
                finallyF();
            }
        });
}

const axiosPut = function (url:string,params:any,success:any,fail:any,finallyF:any){
    service.put(url,params)
        .then((data)=>{
            if (success) {
                success(data)
            }
        })
        .catch(obj => {
            _fail(fail,obj)
        })
        .finally(()=>{
            if (finallyF) {
                finallyF();
            }
        });
}

const axiosPost = function (url:string,params:any,success:any,fail:any,finallyF:any){
    service.post(url,params)
        .then((data)=>{
            if (success) {
                success(data)
            }
        })
        .catch(obj => {
            _fail(fail,obj)
        })
        .finally(()=>{
            if (finallyF) {
                finallyF();
            }
        });
}

const axiosDelete = function (url:string,params:any,success:any,fail:any,finallyF:any){
    service.delete(url,params)
        .then((data)=>{
            if (success) {
                success(data);
            }
        })
        .catch(obj => {
            _fail(fail,obj);
        })
        .finally(()=>{
            if (finallyF) {
                finallyF();
            }
        });
}

export default {service,axiosGet,axiosPut,axiosPost,axiosDelete};