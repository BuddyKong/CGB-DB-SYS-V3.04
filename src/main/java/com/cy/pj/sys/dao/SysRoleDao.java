package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.entity.SysRoleMenu;

@Mapper
public interface SysRoleDao {
	int getRowCount(@Param("name") String name);
	 List<SysRole> findPageObjects(String name);
	int doFindObjectById(Integer id);
	int doDeleteObject(Integer id);
	int insertObject(SysRole entity);
	int updateObject(SysRole entity);
	SysRoleMenu findObjectById(Integer id);
	@Select("select id,name from sys_roles")
	List<CheckBox> findObjects();
}
