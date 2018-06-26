# spring-boot-seed

#### 项目介绍
SpringBoot的种子框架项目，个人学习使用，集成一些常用的框架功能，方便快速开发。

#### 软件架构
```
spring-boot-seed  
├── src/main/java/com.dazzlzy  
|   ├── common -- 通用代码包  
|   |    ├── base -- 项目基础包  
|   |    ├── configuration -- springboot的配置注入  
|   |    ├── enums -- 枚举  
|   |    ├── exception -- 异常  
|   |    ├── filter -- 过滤器  
|   |    ├── redis -- redis支持  
|   |    ├── shiro -- shiro支持  
|   |    ├── support -- 项目支撑对象  
|   |    └── utils -- 工具包  
|   ├── springbootseed -- 项目业务  
|   |    ├── controller -- controller层  
|   |    ├── dao -- DAO层，主要为使用Mapper的接口  
|   |    ├── model -- 数据库模型对象  
|   |    └── service -- service的接口定义  
|   |         └── impl -- service的实现类  
|   └── SpringBootSeedApplication.java -- 项目启动类  
└── src/main/resources  
    ├── mapper -- Mybatis的Mapper.xml目录  
    ├── sql -- sql脚本目录  
    ├── application.yml -- 项目默认配置文件  
    ├── application-*.yml -- 不同环境下的配置文件  
    └── log4j2-*.xml -- log4j2在不同环境下的配置  
```

#### 技术使用

[x] | 技术名 | 版本  
 :---: | :---: | :---:  
[x] | SpringBoot | 2.0.2.RELEASE  
[x] | SpringMVC(spring-boot-starter-web) | 2.0.2.RELEASE  
[x] | JDBC(spring-boot-starter-jdbc) | 2.0.2.RELEASE  
[x] | Mybatis(mybatis-spring-boot-starter) | 1.3.2  
[x] | Druid(druid-spring-boot-starter) | 1.1.9  
[x] | Mapper(mapper-spring-boot-starter) | 2.0.2  
[x] | PageHelper(pagehelper-spring-boot-starter) |1.2.4  
[x] | Log4j2/Slf4j(spring-boot-starter-log4j2) | 2.0.2.RELEASE     
[x] | Redis(spring-boot-starter-data-redis) | 2.0.2.RELEASE  
[x] | jedis | 2.9.0   
[x] | mybatis generator | 1.3.6   
[x] | lombok | 1.16.20    
[x] | Swagger2(springfox.swagger2) | 2.8.0   
[x] | shiro | 1.3.2  
[x] | orika | 1.4.2  
[] | EhCache | -  
[] | HttpClient | -  

#### 使用说明

1. 项目使用了lombok，简化代码生成，因此开发使用的IDE（Eclipse/IDEA）需要安装lombok的插件，详细的技术请参考[lombok官网](https://www.projectlombok.org/ "lombok")
2. 使用gitflow工作流，master生产主线与develop开发主线

#### 开发环境

1. JDK8
2. Mysql5.7
3. SpringBoot2
4. Redis
