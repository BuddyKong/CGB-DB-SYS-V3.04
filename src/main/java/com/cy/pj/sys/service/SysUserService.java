package com.cy.pj.sys.service;

import java.util.Map;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.entity.SysUserDept;

public interface SysUserService {
	PageObject<SysUserDept> findPageObjects(
            String username,Integer pageCurrent);
	int validById(Integer id,Integer valid);
	Map<String,Object> findObjectById(Integer userId) ;
	int doSaveObject(SysUser entity,Integer[]roleIds);
	int updateObject(SysUser entity,Integer[] roleIds);
	int updatePassword(String sourcePassword,String newPassword,String name);
}

