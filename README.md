# spring-boot-seed

#### 项目介绍
SpringBoot的种子框架项目，个人学习使用，集成一些常用的框架功能，方便快速开发。

#### 软件架构
spring-boot-seed
├── base -- 项目基础包  
├── bean -- 非数据库对模型对象  
├── configuration -- springboot的配置注入  
├── dao -- DAO层，主要为使用Mapper的接口  
├── enums -- 枚举  
├── exception -- 异常  
├── model -- 数据库模型对象  
├── service -- service的接口定义  
|   └── impl -- service的实现类  
├── support -- 一些支撑的类或接口  
├── utils -- 工具包  
├── web -- controller层  
└── SpringBootSeedApplication.java -- spring-boot-seed的启动类  

#### 安装教程

1. 项目使用了lombok，简化代码生成，因此开发使用的IDE（Eclipse/IDEA）需要安装lombok的插件，详细的技术请参考[lombok官网](https://www.projectlombok.org/ "lombok")

#### 技术使用

1. SpringBoot
2. SpringMVC
3. spring-jdbc
4. Mybatis
5. Mysql
6. lombok

#### 开发环境

1. JDK8
2. Mysql5.7
3. SpringBoot2
