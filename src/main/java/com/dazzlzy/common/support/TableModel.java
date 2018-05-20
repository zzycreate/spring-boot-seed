package com.dazzlzy.common.support;

import lombok.Builder;
import lombok.Data;

/**
 * mybatis generator util用的表模型
 *
 * @author dazzlzy
 * @date 2018/5/7
 */
@Data
@Builder
public class TableModel {

    /**
     * 表名（小写下划线）
     */
    private String tableName;

    /**
     * 数据对象名（大写驼峰）
     */
    private String modelName;

    /**
     * 包名
     */
    private String packageName;
}
