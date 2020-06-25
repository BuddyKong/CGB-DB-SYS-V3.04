package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SysRoleMenuDao {
	int deleteObjectsByMenuId(Integer menuId);
	int getChildCount(Integer id);
	int deleteObject(Integer id);
	List<Integer> findMenuIdsByRoleId(Integer roleId);
	List<Integer> findMenuIdsByRoleIds(
            @Param("roleIds")Integer[] roleIds);
	int deleteObjectsByRoleId(Integer id);
	int insertObjects(
			@Param("roleId")Integer roleId,
			@Param("menuIds")Integer[] menuIds);
}
