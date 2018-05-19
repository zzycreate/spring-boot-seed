package com.dazzlzy.springbootseed.model.user;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

/**
 * 角色权限关系
 *
 * @author dazzlzy
 * @date 2018/5/19
 */
@Data
@Table(name = "sys_role_permission")
public class RolePermission {
    /**
     * 角色权限关系ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 插入时间
     */
    @Column(name = "insert_time")
    private Date insertTime;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 权限ID
     */
    @Column(name = "permission_id")
    private Long permissionId;

}