# yue-chip-common-business-module
公共业务模块-ddd

# yue-chip-common-business-module
用gradle构建的DDD微服务架构

# yue-chip-common-business-module
DDD microservices architecture built with gradle

# yue-chip-platform
请拉取核心架构 https://github.com/yue-chip/yue-chip-platform  
前端代码 https://github.com/yue-chip/yue-chip-frontend  
前端代码（前端模块代码与为微服务接口一一对应拆分 具体实现逻辑在yue-chip-frontend仓库下的gateway项目以及本项目
upms工程->[frontend](upms%2Ffrontend)的 [registerGateway.js](upms%2Ffrontend%2FregisterGateway.js)）

# yue-chip-platform
[core code] -- git pull https://github.com/yue-chip/yue-chip-platform  
[frontend code]  --- git pull https://github.com/yue-chip/yue-chip-frontend 



# 修改 [gradle.properties](gradle.properties) 中的 maven 发布到私服的地址  不发布到私服不需要改
```java 
mavenRepository=http://127.0.0.1:8081/repository/maven-public/
mavenSnapshots=http://127.0.0.1:8081/repository/maven-snapshots/
mavenUsername=admin
mavenPassword=
```

# modify [gradle.properties](gradle.properties)  Maven is published to the address of the private server, and does not need to be changed if it is not published to the private server
```java 
mavenRepository=http://127.0.0.1:8081/repository/maven-public/
mavenSnapshots=http://127.0.0.1:8081/repository/maven-snapshots/
mavenUsername=admin
mavenPassword=
```