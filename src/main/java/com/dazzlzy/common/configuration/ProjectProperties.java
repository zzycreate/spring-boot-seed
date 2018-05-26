package com.dazzlzy.common.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

/**
 * 项目配置
 *
 * @author dazzlzy
 * @date 2018/5/26
 */
@Data
@Repository
@ConfigurationProperties("project")
public class ProjectProperties {

    private String name;

    private String version;

    private String description;

    private ProjectAuthorProperties author;

}
