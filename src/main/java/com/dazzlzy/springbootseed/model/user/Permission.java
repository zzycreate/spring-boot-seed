package com.dazzlzy.springbootseed.model.user;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

/**
 * 权限
 *
 * @author dazzlzy
 * @date 2018/5/19
 */
@Data
@Table(name = "sys_permission")
public class Permission {
    /**
     * 权限ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 启用状态
     */
    @Column(name = "state_code")
    private Integer stateCode;

    /**
     * 权限名
     */
    @Column(name = "permission_name")
    private String permissionName;

    /**
     * 权限编码
     */
    @Column(name = "permission_code")
    private String permissionCode;

    /**
     * 权限描述
     */
    private String description;

}