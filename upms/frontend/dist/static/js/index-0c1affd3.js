import{a as axios,m as message,C as Card,S as SearchOutlined,P as PlusOutlined,I as Input,b as __unplugin_components_1,c as __unplugin_components_10,d as __unplugin_components_11,B as Button,_ as __unplugin_components_9,F as Form}from"./axios-85071db1.js";import{q as qs}from"./index-5f427048.js";import{M as Modal,E as EditOutlined,_ as __unplugin_components_9$1}from"./EditOutlined-007ce32e.js";import{i as defineComponent,j as ref,k as onActivated,l as createElementBlock,h as createVNode,w as withCtx,u as unref,m as isRef,o as openBlock,F as Fragment,z as renderList,p as createTextVNode,c as createBlock,b as createCommentVNode,A as toDisplayString}from"./vue-router-ef9fc363.js";import{D as DeleteOutlined,_ as __unplugin_components_5,S as SelectOption}from"./DeleteOutlined-ba572a4a.js";import{_ as __unplugin_components_8}from"./index-bed09c85.js";const _sfc_main=defineComponent({__name:"index",setup(__props){const fromAddOrUpdate=ref();let loading=ref(!1),searchModel=ref({pageSize:10,pageNumber:1}),visible=ref(!1),addOrUpdateModel=ref({}),selectedRowKeys=[],dataList=ref([]),stateList=ref([]);const rules={name:[{required:!0,message:"请输入租户名称",trigger:"blur"}],abbreviation:[{required:!0,message:"请输入租户简称",trigger:"blur"}],manager:[{required:!0,message:"请输入名称",trigger:"blur"}],phoneNumber:[{required:!0,message:"请输入联系电话",trigger:"blur"}],requestDomain:[{required:!0,message:"请输入访问域",trigger:"blur"}],bigScreenName:[{required:!0,message:"请输入数字大屏名称",trigger:"blur"}]},columns=[{title:"租户编码",dataIndex:"id",fixed:"left",key:"id"},{title:"租户名称",dataIndex:"name",key:"name"},{title:"状态",key:"state"},{title:"负责人",dataIndex:"manager",key:"manager"},{title:"联系电话",dataIndex:"phoneNumber",key:"phoneNumber"},{title:"访问域",dataIndex:"requestDomain",key:"requestDomain"},{title:"创建时间",dataIndex:"createDateTime",key:"createDateTime"},{title:"操作",key:"operation",fixed:"right",width:"160px"}],pagination=ref({current:searchModel.value.pageNumber?searchModel.value.pageNumber:1,pageSize:searchModel.value.pageSize?searchModel.value.pageSize:30,onChange:(a,e)=>{searchModel.value.pageSize=e,searchModel.value.pageNumber=a,search()}}),rowSelection={onChange:(a,e)=>{selectedRowKeys=a}};onActivated(()=>{search(),axios.axiosGet("/common/enum",{params:{code:"state",version:"1"}},data=>{stateList.value=eval("("+data.data.value+")")},null,null)});function search(){loading.value=!0,axios.axiosGet("/upms/console/tenant/list",{params:searchModel.value},a=>{dataList.value=a.data,pagination.value.total=a.totalElements,pagination.value.current=a.pageNumber,pagination.value.pageSize=a.pageSize,loading.value=!1},null,null)}function add(){visible.value=!0}function cancel(){visible.value=!1,addOrUpdateModel.value={}}function details(a){axios.axiosGet("/upms/console/tenant/details",{params:{id:a}},e=>{e.status===200&&(addOrUpdateModel.value=e.data,visible.value=!0)},null,null)}function save(){fromAddOrUpdate.value.validateFields().then(()=>{addOrUpdateModel.value.id?axios.axiosPut("/upms/console/tenant/update",addOrUpdateModel.value,a=>{a.status===200&&(visible.value=!1,addOrUpdateModel.value={},message.info(a.message),search())},null,null):axios.axiosPost("/upms/console/tenant/add",addOrUpdateModel.value,a=>{a.status===200&&(visible.value=!1,addOrUpdateModel.value={},message.info(a.message),search())},null,null)}).catch(a=>{})}function del(a){if(!a||a.length===0){message.error("请选择要删除的数据！");return}Modal.confirm({title:"是否要删除该数据?(错误的操作会带来灾难性的后果)",okText:"Yes",okType:"danger",cancelText:"No",onOk(){const e={params:{ids:a},paramsSerializer:n=>qs.stringify(n,{indices:!1})};axios.axiosDelete("/upms/console/tenant/delete",e,n=>{n.status===200&&(message.info(n.message),search())},null,null)},onCancel(){}})}function stateChange(a,e){let n=e?1:0;axios.service.put("/upms/console/tenant/update/state",{id:a,state:n},{headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}}).then(l=>{l.status===200&&message.info(l.message),search()}).catch(()=>{})}return(a,e)=>{const n=Input,l=__unplugin_components_1,o=__unplugin_components_10,m=SelectOption,f=__unplugin_components_5,u=__unplugin_components_11,d=Button,i=__unplugin_components_9,c=Form,p=Card,_=__unplugin_components_8,g=__unplugin_components_9$1,h=Modal;return openBlock(),createElementBlock("div",null,[createVNode(p,null,{default:withCtx(()=>[createVNode(c,{ref:"from",model:unref(searchModel),labelCol:{span:4,offset:0}},{default:withCtx(()=>[createVNode(u,{gutter:24},{default:withCtx(()=>[createVNode(o,{span:6},{default:withCtx(()=>[createVNode(l,{label:"名称",name:"name",ref:"name"},{default:withCtx(()=>[createVNode(n,{placeholder:"请输入名称",value:unref(searchModel).name,"onUpdate:value":e[0]||(e[0]=t=>unref(searchModel).name=t)},null,8,["value"])]),_:1},512)]),_:1}),createVNode(o,{span:6},{default:withCtx(()=>[createVNode(l,{label:"负责人",name:"manager",ref:"manager"},{default:withCtx(()=>[createVNode(n,{placeholder:"请输入负责人",value:unref(searchModel).manager,"onUpdate:value":e[1]||(e[1]=t=>unref(searchModel).manager=t)},null,8,["value"])]),_:1},512)]),_:1}),createVNode(o,{span:6},{default:withCtx(()=>[createVNode(l,{label:"状态",name:"state",ref:"state"},{default:withCtx(()=>[createVNode(f,{value:unref(searchModel).state,"onUpdate:value":e[2]||(e[2]=t=>unref(searchModel).state=t),allowClear:""},{default:withCtx(()=>[(openBlock(!0),createElementBlock(Fragment,null,renderList(unref(stateList),(t,v)=>(openBlock(),createBlock(m,{value:t.key},{default:withCtx(()=>[createTextVNode(toDisplayString(t.desc),1)]),_:2},1032,["value"]))),256))]),_:1},8,["value"])]),_:1},512)]),_:1}),createVNode(o,{span:6},{default:withCtx(()=>[createVNode(l,{label:"联系电话",name:"phoneNumber",ref:"phoneNumber"},{default:withCtx(()=>[createVNode(n,{placeholder:"请输入联系电话",value:unref(searchModel).phoneNumber,"onUpdate:value":e[3]||(e[3]=t=>unref(searchModel).phoneNumber=t)},null,8,["value"])]),_:1},512)]),_:1})]),_:1}),createVNode(u,{gutter:24,style:{height:"20px"}},{default:withCtx(()=>[createVNode(o,{span:24,style:{"text-align":"right"}},{default:withCtx(()=>[createVNode(l,null,{default:withCtx(()=>[createVNode(i,{size:5},{default:withCtx(()=>[createVNode(d,{type:"primary",onClick:e[4]||(e[4]=t=>{unref(searchModel).pageNumber=1,search()})},{icon:withCtx(()=>[createVNode(unref(SearchOutlined))]),default:withCtx(()=>[createTextVNode(" 查询 ")]),_:1}),createVNode(d,{type:"primary",onClick:e[5]||(e[5]=t=>add())},{icon:withCtx(()=>[createVNode(unref(PlusOutlined))]),default:withCtx(()=>[createTextVNode(" 添加 ")]),_:1}),createVNode(d,{type:"primary",danger:"",onClick:e[6]||(e[6]=t=>del(unref(selectedRowKeys)))},{icon:withCtx(()=>[createVNode(unref(DeleteOutlined))]),default:withCtx(()=>[createTextVNode(" 删除 ")]),_:1})]),_:1})]),_:1})]),_:1})]),_:1})]),_:1},8,["model"])]),_:1}),createVNode(p,null,{default:withCtx(()=>[createVNode(g,{rowKey:"id","row-selection":rowSelection,columns,"data-source":unref(dataList),pagination:pagination.value,loading:unref(loading),scroll:{y:440}},{bodyCell:withCtx(({column:t,text:v,record:r})=>[t.key==="state"?(openBlock(),createBlock(_,{key:0,onChange:s=>stateChange(r.id,r.stateTmp),checked:r.stateTmp,"onUpdate:checked":s=>r.stateTmp=s,"checked-children":"正常","un-checked-children":"禁用"},null,8,["onChange","checked","onUpdate:checked"])):createCommentVNode("",!0),t.key==="operation"?(openBlock(),createBlock(i,{key:1,size:5},{default:withCtx(()=>[createVNode(d,{size:"small",onClick:s=>details(r.id)},{icon:withCtx(()=>[createVNode(unref(EditOutlined))]),default:withCtx(()=>[createTextVNode(" 修改 ")]),_:2},1032,["onClick"]),createVNode(d,{size:"small",type:"primary",danger:"",onClick:s=>del(r.id)},{icon:withCtx(()=>[createVNode(unref(DeleteOutlined))]),default:withCtx(()=>[createTextVNode(" 删除 ")]),_:2},1032,["onClick"])]),_:2},1024)):createCommentVNode("",!0)]),_:1},8,["data-source","pagination","loading"])]),_:1}),createVNode(h,{width:"800px",visible:unref(visible),"onUpdate:visible":e[13]||(e[13]=t=>isRef(visible)?visible.value=t:visible=t),title:"添加/修改租户",cancelText:"取消",okText:"保存",destroyOnClose:!0,mask:!0,maskClosable:!1,onCancel:cancel,onOk:save},{default:withCtx(()=>[createVNode(c,{ref_key:"fromAddOrUpdate",ref:fromAddOrUpdate,rules,model:unref(addOrUpdateModel),labelCol:{span:4,offset:0}},{default:withCtx(()=>[createVNode(u,null,{default:withCtx(()=>[createVNode(o,{span:24},{default:withCtx(()=>[createVNode(l,{label:"名称",name:"name",ref:"name"},{default:withCtx(()=>[createVNode(n,{placeholder:"请输入租户名称",value:unref(addOrUpdateModel).name,"onUpdate:value":e[7]||(e[7]=t=>unref(addOrUpdateModel).name=t)},null,8,["value"])]),_:1},512)]),_:1}),createVNode(o,{span:24},{default:withCtx(()=>[createVNode(l,{label:"简称",name:"abbreviation",ref:"abbreviation"},{default:withCtx(()=>[createVNode(n,{placeholder:"请输入租户简称",value:unref(addOrUpdateModel).abbreviation,"onUpdate:value":e[8]||(e[8]=t=>unref(addOrUpdateModel).abbreviation=t)},null,8,["value"])]),_:1},512)]),_:1}),createVNode(o,{span:24},{default:withCtx(()=>[createVNode(l,{label:"数字大屏名称",name:"bigScreenName",ref:"bigScreenName"},{default:withCtx(()=>[createVNode(n,{placeholder:"请输入数字大屏名称",value:unref(addOrUpdateModel).bigScreenName,"onUpdate:value":e[9]||(e[9]=t=>unref(addOrUpdateModel).bigScreenName=t)},null,8,["value"])]),_:1},512)]),_:1}),createVNode(o,{span:24},{default:withCtx(()=>[createVNode(l,{label:"负责人",name:"manager",ref:"manager"},{default:withCtx(()=>[createVNode(n,{placeholder:"请输入负责人",value:unref(addOrUpdateModel).manager,"onUpdate:value":e[10]||(e[10]=t=>unref(addOrUpdateModel).manager=t)},null,8,["value"])]),_:1},512)]),_:1}),createVNode(o,{span:24},{default:withCtx(()=>[createVNode(l,{label:"联系电话",name:"phoneNumber",ref:"phoneNumber"},{default:withCtx(()=>[createVNode(n,{placeholder:"请输入负责人",value:unref(addOrUpdateModel).phoneNumber,"onUpdate:value":e[11]||(e[11]=t=>unref(addOrUpdateModel).phoneNumber=t)},null,8,["value"])]),_:1},512)]),_:1}),createVNode(o,{span:24},{default:withCtx(()=>[createVNode(l,{label:"访问域名",name:"requestDomain",ref:"requestDomain"},{default:withCtx(()=>[createVNode(n,{placeholder:"请输入访问域名(www.baidu.com)多个域逗号隔开",value:unref(addOrUpdateModel).requestDomain,"onUpdate:value":e[12]||(e[12]=t=>unref(addOrUpdateModel).requestDomain=t)},null,8,["value"])]),_:1},512)]),_:1})]),_:1})]),_:1},8,["model"])]),_:1},8,["visible"])])}}});export{_sfc_main as default};
