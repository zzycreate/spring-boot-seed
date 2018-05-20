package com.dazzlzy.springbootseed;

import com.dazzlzy.common.support.TableModel;
import com.dazzlzy.common.utils.DruidPasswordUtil;
import com.dazzlzy.common.utils.PropertiesFileUtil;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.nutz.lang.Lang;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成mybatis代码
 *
 * @author dazzlzy
 * @date 2018/4/26
 */
@Slf4j
public class MybatisGenerator {

    /**
     * 数据库设置
     */
    private static final String JDBC_DIVER_CLASS_NAME;
    private static final String JDBC_URL;
    private static final String JDBC_USERNAME;
    private static final String JDBC_PASSWORD;

    /**
     * 项目设置
     */
    private static final String PROJECT_NAME = "spring-boot-seed";
    private static final String PATH_PROJECT = System.getProperty("user.dir");
    private static final String PATH_MAVEN_JAVA = "/src/main/java";
    private static final String PATH_MAVEN_RESOURCE = "/src/main/resources";
    private static final String PATH_TEMPLATE = PATH_PROJECT + PATH_MAVEN_RESOURCE + "/template";

    private static final String PACKAGE_BASE = "com.dazzlzy.springbootseed";
    private static final String PACKAGE_MODEL = PACKAGE_BASE + ".model";
    private static final String PACKAGE_DAO = PACKAGE_BASE + ".dao";
    private static final String PACKAGE_MAPPER = "/mapper";
    private static final String PACKAGE_SERVICE = PACKAGE_BASE + ".service";
    private static final String PACKAGE_CONTROLLER = PACKAGE_BASE + ".controller";

    private static final String MAPPER_INTERFACE_REFERENCE = "com.dazzlzy.common.base.BaseMapper";


    static {
        PropertiesFileUtil propertiesFileUtil = PropertiesFileUtil.getInstance("application-dev");
        JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
        JDBC_URL = propertiesFileUtil.get("spring.datasource.url");
        JDBC_USERNAME = propertiesFileUtil.get("spring.datasource.username");
        JDBC_PASSWORD = DruidPasswordUtil.decrypt(propertiesFileUtil.get("spring.datasource.publicKey"),
                propertiesFileUtil.get("spring.datasource.password"));
    }

    public static void main(String[] args) {
        MybatisGenerator.generator(Lang.list(
                TableModel.builder().tableName("sys_user").modelName("User").packageName("test").build()
//                TableModel.builder().tableName("sys_role").modelName("Role").packageName("user").build(),
//                TableModel.builder().tableName("sys_permission").modelName("Permission").packageName("user").build(),
//                TableModel.builder().tableName("sys_user_role").modelName("UserRole").packageName("user").build(),
//                TableModel.builder().tableName("sys_role_permission").modelName("RolePermission").packageName("user").build()
        ));
    }

    /**
     * 生成代码
     *
     * @param list 数据模型配置
     */
    public static void generator(List<TableModel> list) {
        for (TableModel tableModel : list) {
            generator(tableModel.getTableName(), tableModel.getModelName(), tableModel.getPackageName());
        }
    }

    /**
     * 生成代码
     *
     * @param tableName   表名
     * @param modelName   对象名
     * @param packageName 包名
     */
    public static void generator(String tableName, String modelName, String packageName) {
        genModelAndMapper(tableName, modelName, packageName);
    }

    /**
     * 生成数据库对象、mapper接口、mapper的xml
     *
     * @param tableName   表名
     * @param modelName   对象名
     * @param packageName 包名
     */
    private static void genModelAndMapper(String tableName, String modelName, String packageName) {
        final String modelPackage = StringUtils.isBlank(packageName) ? "" : "." + packageName;
        final String modelPath = StringUtils.isBlank(packageName) ? "" : "/" + packageName;
        Context context = new Context(ModelType.FLAT);
        context.setId("MysqlContext");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_AUTO_DELIMIT_KEYWORDS, "false");
        context.addProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING, "UTF-8");
        context.addProperty(PropertyRegistry.CONTEXT_JAVA_FORMATTER, "org.mybatis.generator.api.dom.DefaultJavaFormatter");
        context.addProperty(PropertyRegistry.CONTEXT_XML_FORMATTER, "org.mybatis.generator.api.dom.DefaultXmlFormatter");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        //Mapper接口集成BaseMapper
        PluginConfiguration mapperPluginConfiguration = new PluginConfiguration();
        mapperPluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        mapperPluginConfiguration.addProperty("mappers", MAPPER_INTERFACE_REFERENCE);
        context.addPluginConfiguration(mapperPluginConfiguration);

        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(PATH_PROJECT + PATH_MAVEN_JAVA);
        javaModelGeneratorConfiguration.setTargetPackage(PACKAGE_MODEL + modelPackage);
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(PATH_PROJECT + PATH_MAVEN_RESOURCE);
        sqlMapGeneratorConfiguration.setTargetPackage(PACKAGE_MAPPER + modelPath);
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(PATH_PROJECT + PATH_MAVEN_JAVA);
        javaClientGeneratorConfiguration.setTargetPackage(PACKAGE_DAO + modelPackage);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        if (StringUtils.isNotEmpty(modelName)) {
            tableConfiguration.setDomainObjectName(modelName);
        }
        tableConfiguration.setSelectByExampleStatementEnabled(false);
        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
        context.addTableConfiguration(tableConfiguration);

        MyBatisGenerator generator;
        List<String> warnings;
        try {
            warnings = new ArrayList<>();
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();

            DefaultShellCallback callback = new DefaultShellCallback(true);
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }
        if (StringUtils.isEmpty(modelName)) {
            modelName = tableNameConvertUpperCamel(tableName);
        }
        log.info("== JavaAndMapper【{}.java】+【{}Mapper.java】+【{}Mapper.xml】 生成成功", modelName, modelName, modelName);
    }

    /**
     * 表名转小写驼峰（小写下划线转小写驼峰）
     *
     * @param tableName 表名（小写下划线）
     * @return 小写驼峰
     */
    private static String tableNameConvertLowerCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    }

    /**
     * 表名转大写驼峰（小写下划线转大写驼峰）
     *
     * @param tableName 表名（小写下划线）
     * @return 大写驼峰
     */
    private static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());

    }

    /**
     * 表名转路径（小写下划线转/分割路径名）
     *
     * @param tableName 表名（小写下划线）
     * @return 路径名
     */
    private static String tableNameConvertMappingPath(String tableName) {
        //兼容使用大写的表名
        tableName = tableName.toLowerCase();
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }

    /**
     * 对象转路径（大写驼峰转路径格式）
     *
     * @param modelName 表名（大写驼峰）
     * @return 路径名
     */
    private static String modelNameConvertMappingPath(String modelName) {
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
        return tableNameConvertMappingPath(tableName);
    }

    /**
     * 包名转路径（.分割包名转/分割路径名）
     *
     * @param packageName 包名（.分割）
     * @return 路径（/分割）
     */
    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

}
