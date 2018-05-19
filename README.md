# spring-boot-seed

#### 项目介绍
SpringBoot的种子框架项目，个人学习使用，集成一些常用的框架功能，方便快速开发。

#### 软件架构
```
spring-boot-seed  
├── common -- 通用代码包  
|    ├── base -- 项目基础包  
|    ├── configuration -- springboot的配置注入  
|    ├── enums -- 枚举  
|    ├── exception -- 异常  
|    ├── support -- 一些支撑的类或接口  
|    └── utils -- 工具包  
├── springbootseed -- 项目业务  
|    ├── controller -- controller层  
|    ├── dao -- DAO层，主要为使用Mapper的接口  
|    ├── model -- 数据库模型对象  
|    └── service -- service的接口定义  
|         └── impl -- service的实现类  
└── SpringBootSeedApplication.java -- spring-boot-seed的启动类  
```

#### 安装教程

1. 项目使用了lombok，简化代码生成，因此开发使用的IDE（Eclipse/IDEA）需要安装lombok的插件，详细的技术请参考[lombok官网](https://www.projectlombok.org/ "lombok")

#### 技术使用

[x] SpringBoot  
[x] SpringMVC  
[x] spring-jdbc  
[x] Mybatis  
[x] Mysql  
[x] druid 
[x] Log4j2  
[x] lombok  
[x] Mapper  
[x] PageHelper  
[x] mybatis generator  
[] shiro  
[] EhCache  
[] HttpClient  
[] redis  

#### 开发环境

1. JDK8
2. Mysql5.7
3. SpringBoot2
