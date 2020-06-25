package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.entity.SysUserDept;

@Mapper
public interface SysUserDao {
	 int getRowCount( String username);
	 List<SysUserDept> findPageObjects(String username);
	 int validById(
             @Param("id")Integer id,
             @Param("valid")Integer valid,
             @Param("modifiedUser")String modifiedUser);
	 int insertObject(SysUser entity);
	 SysUserDept findObjectById(Integer id);
	 int updateObject(SysUser entity);
	 SysUser findUserByUserName(String username);
	 @Update("update sys_users set password=#{newHashedPassword},salt=#{newSalt},modifiedTime=now() where username=#{username}")
	 int updatePassword(String username,String newHashedPassword,String newSalt);
}
