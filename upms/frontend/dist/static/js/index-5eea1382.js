import{a as m,m as A,C as W,S as K,P as I,b as Y,c as J,d as Q,B as X,_ as Z,F as ee,I as le}from"./axios-85071db1.js";import{M as L,E as ae,_ as te}from"./EditOutlined-007ce32e.js";import{D as ne,S as se,_ as oe}from"./DeleteOutlined-ba572a4a.js";import{i as ue,j as v,k as ie,l as re,h as e,w as l,u as s,m as de,o as x,p as r,c as b,b as g}from"./vue-router-ef9fc363.js";import{_ as ce}from"./index-9035f93f.js";const Ce=ue({__name:"index",setup(pe){const T=v();let c=v(!1);v(!1);const f=v({scope:"CONSOLE"});let t=v({});const O=v(!1),M=[{title:"名称",dataIndex:"name",key:"name"},{title:"编码",dataIndex:"code",key:"code"},{title:"url",dataIndex:"url",key:"url",width:200},{title:"备注",dataIndex:"remark",key:"remark",width:300},{title:"作用域",dataIndex:["scope","desc"],key:["scope","name"]},{title:"类型",dataIndex:["type","desc"],key:["type","name"]},{title:"状态",dataIndex:["state","desc"],key:["state","name"]},{title:"操作",key:"operation",width:255}];let E=v([]);const F={code:[{required:!0,validator:S,trigger:"blur"}],name:[{required:!0,validator:$,trigger:"blur"}],url:[{validator:z,trigger:"blur"}]};async function S(u,a){let n=null;return!a||a.trim()===""?Promise.reject("请输入编码"):(a&&a.trim()!==""&&await m.service.get("/upms/console/resources/check/code/exist",{params:{code:t.value.code,id:t.value.id}}).then(i=>{i.status===200&&i.data?n=Promise.reject("该编码已存在"):n=Promise.resolve()}).catch(i=>{}).finally(()=>{}),n)}async function $(u,a){let n=null;return!a||a.trim()===""?Promise.reject("请输入名称"):(a&&a.trim()!==""&&await m.service.get("/upms/console/resources/check/name/exist",{params:{name:t.value.name,parentId:t.value.parentId,id:t.value.id}}).then(i=>{i.status===200&&i.data?n=Promise.reject("名称在同节点已存在"):n=Promise.resolve()}).catch(i=>{}).finally(()=>{}),n)}async function z(u,a){let n=null;return t.value.type==="MENU"&&(!a||a.trim()==="")?Promise.reject("请输入url"):(t.value.type==="MENU"&&a&&a.trim()!==""&&await m.service.get("/upms/console/resources/check/url/exist",{params:{url:t.value.url,id:t.value.id}}).then(i=>{i.status===200&&i.data?n=Promise.reject("该url已存在"):n=Promise.resolve()}).catch(i=>{}).finally(()=>{}),n)}ie(()=>{k()});function k(){O.value=!0,m.axiosGet("/upms/console/resources/tree/list",{params:f.value},u=>{E.value=u.data,O.value=!1},null,null)}function U(u,a,n,i){u&&(t.value.id=u),a&&(t.value.parentId=a),n&&(t.value.scope=n),i&&(t.value.type=i),c.value=!0}function G(){c.value=!1,t.value={}}function B(){T.value.validateFields().then(()=>{t.value.id?m.axiosPut("/upms/console/resources/update",t.value,u=>{u.status===200&&(c.value=!1,t.value={},A.info(u.message),k())},null,null):m.axiosPost("/upms/console/resources/add",t.value,u=>{u.status===200&&(c.value=!1,t.value={},A.info(u.message),k())},null,null)}).catch(u=>{})}function R(u){m.axiosGet("/upms/console/resources/details",{params:{id:u}},a=>{a.status===200&&(t.value=a.data,t.value.scope=a.data.scope.name,t.value.type=a.data.type.name,t.value.state=a.data.state.name,c.value=!0)},null,null)}function D(u){L.confirm({title:"是否要删除该数据?(错误的操作会带来灾难性的后果)",okText:"Yes",okType:"danger",cancelText:"No",onOk(){m.axiosDelete("/upms/console/resources/delete",{params:{id:u}},a=>{a.status===200&&(A.info(a.message),k())},null,null)},onCancel(){}})}return(u,a)=>{const n=se,i=oe,p=Y,d=J,C=Q,y=X,h=Z,w=ee,j=W,V=te,N=le,q=ce,H=L;return x(),re("div",null,[e(j,null,{default:l(()=>[e(w,{ref:"from",model:f.value,labelCol:{span:5,offset:0}},{default:l(()=>[e(C,null,{default:l(()=>[e(d,{span:6},{default:l(()=>[e(p,{label:"作用域",name:"scope",ref:"scope"},{default:l(()=>[e(i,{value:f.value.scope,"onUpdate:value":a[0]||(a[0]=o=>f.value.scope=o)},{default:l(()=>[e(n,{value:"APP"},{default:l(()=>[r("APP")]),_:1}),e(n,{value:"CONSOLE"},{default:l(()=>[r("后台")]),_:1}),e(n,{value:"FRONT"},{default:l(()=>[r("前端")]),_:1}),e(n,{value:"WE_CHAT"},{default:l(()=>[r("微信")]),_:1})]),_:1},8,["value"])]),_:1},512)]),_:1}),e(d,{span:6}),e(d,{span:6}),e(d,{span:6})]),_:1}),e(C,{style:{height:"20px"}},{default:l(()=>[e(d,{span:24,style:{"text-align":"right"}},{default:l(()=>[e(p,null,{default:l(()=>[e(h,{size:5},{default:l(()=>[e(y,{type:"primary",onClick:a[1]||(a[1]=o=>k())},{icon:l(()=>[e(s(K))]),default:l(()=>[r(" 查询 ")]),_:1}),e(y,{type:"primary",onClick:a[2]||(a[2]=o=>U(null,"0",f.value.scope,"CATALOG"))},{icon:l(()=>[e(s(I))]),default:l(()=>[r(" 添加 ")]),_:1})]),_:1})]),_:1})]),_:1})]),_:1})]),_:1},8,["model"])]),_:1}),e(j,null,{default:l(()=>[e(V,{rowKey:"id",columns:M,"data-source":s(E),loading:O.value,pagination:!1,scroll:{y:500}},{bodyCell:l(({column:o,text:me,record:_})=>[o.key==="operation"?(x(),b(h,{key:0,size:5},{default:l(()=>[e(y,{size:"small",onClick:P=>R(_.id)},{icon:l(()=>[e(s(ae))]),default:l(()=>[r(" 修改 ")]),_:2},1032,["onClick"]),_.type.name==="CATALOG"?(x(),b(y,{key:0,size:"small",onClick:P=>U(null,_.id,f.value.scope,"MENU")},{icon:l(()=>[e(s(I))]),default:l(()=>[r(" 添加菜单 ")]),_:2},1032,["onClick"])):g("",!0),_.type.name==="MENU"?(x(),b(y,{key:1,size:"small",onClick:P=>U(null,_.id,f.value.scope,"FUNCTION")},{icon:l(()=>[e(s(I))]),default:l(()=>[r(" 添加功能 ")]),_:2},1032,["onClick"])):g("",!0),_.isDefault===!1?(x(),b(y,{key:2,size:"small",type:"dashed",onClick:P=>D(_.id)},{icon:l(()=>[e(s(ne))]),default:l(()=>[r(" 删除 ")]),_:2},1032,["onClick"])):g("",!0)]),_:2},1024)):g("",!0)]),_:1},8,["data-source","loading"])]),_:1}),e(H,{width:"800px",visible:s(c),"onUpdate:visible":a[9]||(a[9]=o=>de(c)?c.value=o:c=o),title:"添加/修改资源",cancelText:"取消",okText:"保存",destroyOnClose:!0,mask:!0,maskClosable:!1,onCancel:G,onOk:B},{default:l(()=>[e(w,{ref_key:"fromAddOrUpdate",ref:T,rules:F,model:s(t),labelCol:{span:5,offset:0}},{default:l(()=>[e(C,null,{default:l(()=>[e(d,{span:12},{default:l(()=>[e(p,{label:"作用域",name:"scope",ref:"scope"},{default:l(()=>[e(i,{disabled:"",value:s(t).scope,"onUpdate:value":a[3]||(a[3]=o=>s(t).scope=o)},{default:l(()=>[e(n,{value:"APP"},{default:l(()=>[r("APP")]),_:1}),e(n,{value:"CONSOLE"},{default:l(()=>[r("后台")]),_:1}),e(n,{value:"FRONT"},{default:l(()=>[r("前端")]),_:1}),e(n,{value:"WE_CHAT"},{default:l(()=>[r("微信")]),_:1})]),_:1},8,["value"])]),_:1},512)]),_:1}),e(d,{span:12},{default:l(()=>[e(p,{label:"类型",name:"type",ref:"type"},{default:l(()=>[e(i,{value:s(t).type,"onUpdate:value":a[4]||(a[4]=o=>s(t).type=o)},{default:l(()=>[e(n,{value:"CATALOG"},{default:l(()=>[r("目录")]),_:1}),e(n,{value:"MENU"},{default:l(()=>[r("菜单")]),_:1}),e(n,{value:"FUNCTION"},{default:l(()=>[r("功能")]),_:1})]),_:1},8,["value"])]),_:1},512)]),_:1})]),_:1}),e(C,null,{default:l(()=>[e(d,{span:12},{default:l(()=>[e(p,{label:"名称",name:"name",ref:"name"},{default:l(()=>[e(N,{placeholder:"请输入名称",value:s(t).name,"onUpdate:value":a[5]||(a[5]=o=>s(t).name=o)},null,8,["value"])]),_:1},512)]),_:1}),e(d,{span:12},{default:l(()=>[e(p,{label:"编码",name:"code",ref:"code"},{default:l(()=>[e(N,{placeholder:"请输入编码",value:s(t).code,"onUpdate:value":a[6]||(a[6]=o=>s(t).code=o)},null,8,["value"])]),_:1},512)]),_:1})]),_:1}),e(C,null,{default:l(()=>[e(d,{span:12},{default:l(()=>[e(p,{label:"URL",name:"url",ref:"url"},{default:l(()=>[e(N,{disabled:s(t).type==="FUNCTION"||s(t).type==="CATALOG",placeholder:"请输入URL",value:s(t).url,"onUpdate:value":a[7]||(a[7]=o=>s(t).url=o)},null,8,["disabled","value"])]),_:1},512)]),_:1}),e(d,{span:12},{default:l(()=>[e(p,{label:"排序",name:"sort",ref:"sort"},{default:l(()=>[e(q,{value:s(t).sort,"onUpdate:value":a[8]||(a[8]=o=>s(t).sort=o)},null,8,["value"])]),_:1},512)]),_:1})]),_:1})]),_:1},8,["model"])]),_:1},8,["visible"])])}}});export{Ce as default};
