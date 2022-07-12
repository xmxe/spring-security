package com.xmxe.entity;

import lombok.Data;

import java.util.List;

/**
 * 菜单
 */
@Data
public class Menu {
	private Integer id;
	private String pattern;
	// 菜单类中包含一个 roles 属性，表示访问该项资源所需要的角色。
	private List<Role> roles;
}
