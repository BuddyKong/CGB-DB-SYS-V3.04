package com.cy.pj.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.utils.ShiroUtils;
import com.cy.pj.common.vo.PageObject;

import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.entity.SysUserDept;
import com.cy.pj.sys.service.SysUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
@Service
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	public SysUserDao sysUserDao;
	@RequiredLog(operation = "分页查询")
	//@Override
	public PageObject<SysUserDept> findPageObjects(String username, Integer pageCurrent) {
		//1.参数校验
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("页码值无效");
		//2.设置分页参数
		int pageSize=3;
		Page<SysUserDept> page=PageHelper.startPage(pageCurrent,pageSize);
		//3.查询当前页记录
		List<SysUserDept> records=sysUserDao.findPageObjects(username);
		return new PageObject<SysUserDept>(records, (int)page.getTotal(), (int)page.getPages(), pageSize, pageCurrent);
	}

	@Override
	public int validById(Integer id, Integer valid) {
		 if(id==null||id<=0)
             throw new ServiceException("参数不合法,id="+id);
             if(valid!=1&&valid!=0)
             throw new ServiceException("参数不合法,valie="+valid);
             //2.执行禁用或启用操作(admin为后续登陆用户）
             int rows=sysUserDao.validById(id, valid,"admin");
             //3.判定结果,并返回
             if(rows==0)
             throw new ServiceException("此记录可能已经不存在");
             return rows;
	}
	@Autowired
	 public SysUserRoleDao sysUserRoleDao;
	 @Override
		public int doSaveObject(SysUser entity, Integer[] roleIds) {
			//1.参数校验
			if(entity==null)
				throw new IllegalArgumentException("保存对象不能为空");
			if(StringUtils.isEmpty(entity.getUsername()))
				throw new IllegalArgumentException("用户名不能空");
			if(StringUtils.isEmpty(entity.getPassword()))
				throw new IllegalArgumentException("密码不能空");
			if(roleIds==null||roleIds.length==0)
				throw new IllegalArgumentException("必须为用户分配角色");
			//.....
			//2.对密码进行加密
			String salt=UUID.randomUUID().toString();
			SimpleHash sh=new SimpleHash(
					"MD5",//algorithmName算法名称
					entity.getPassword(), //source要加密的密码
					salt, //加密盐
					1);//加密次数
			String pwd=sh.toHex();
			entity.setSalt(salt);
			entity.setPassword(pwd);
			//3.将用户信息写入到数据库
			int rows=sysUserDao.insertObject(entity);
			//4.将用户对应的角色信息写入到数据库
			sysUserRoleDao.insertObjects(entity.getId(), roleIds);
			return rows;
		}
	 @Override
	 public Map<String, Object> findObjectById(Integer userId) {
	                 //1.合法性验证
	                 if(userId==null||userId<=0)
	                 throw new IllegalArgumentException(
	                 "参数数据不合法,userId="+userId);
	                 //2.业务查询
	                 SysUserDept user=
	                 sysUserDao.findObjectById(userId);
	                 if(user==null)
	                 throw new ServiceException("此用户已经不存在");
	                 List<Integer> roleIds=
	                 sysUserRoleDao.findRoleIdsByUserId(userId);
	                 //3.数据封装
	                 Map<String,Object> map=new HashMap<>();
	                 map.put("user", user);
	                 map.put("roleIds", roleIds);
	                 return map;
	         }
	 @Override

	 public int updateObject(SysUser entity,Integer[] roleIds) {
	                 //1.参数有效性验证
	                 if(entity==null)
	                         throw new IllegalArgumentException("保存对象不能为空");
	                 if(StringUtils.isEmpty(entity.getUsername()))
	                         throw new IllegalArgumentException("用户名不能为空");
	                 if(roleIds==null||roleIds.length==0)
	                         throw new IllegalArgumentException("必须为其指定角色");
	                 //其它验证自己实现，例如用户名已经存在，密码长度，...
	                 //2.更新用户自身信息
	                 int rows=sysUserDao.updateObject(entity);
	                 //3.保存用户与角色关系数据
	                 sysUserRoleDao.deleteObjectsByUserId(entity.getId());
	                 sysUserRoleDao.insertObjects(entity.getId(),
	                                 roleIds);
	                 //4.返回结果
	                 return rows;
	         }

	 @Override
		public int updatePassword(String sourcePassword, String newPassword, String cfgPassword) {
			//1.参数校验
			if(StringUtils.isEmpty(sourcePassword))
				throw new IllegalArgumentException("原密码不能为空");
			if(StringUtils.isEmpty(newPassword))
				throw new IllegalArgumentException("新密码不能为空");
			if(!newPassword.equals(cfgPassword))
				throw new IllegalArgumentException("两次新密码输入不一致");
			//校验输入的原密码是否正确
			SysUser user=ShiroUtils.getUser();
			String sourceHashedPassword=user.getPassword();
			SimpleHash sh=new SimpleHash("MD5", sourcePassword, user.getSalt(), 1);
			String hashedInputPassword=sh.toHex();
			if(!sourceHashedPassword.equals(hashedInputPassword))
				throw new IllegalArgumentException("原密码输入的不正确");
			//2.更新密码
			String newSalt=UUID.randomUUID().toString();
			sh=new SimpleHash("MD5", newPassword,newSalt, 1);
			String newHashedPassword=sh.toHex();
			int rows=sysUserDao.updatePassword(user.getUsername(), newHashedPassword, newSalt);
			if(rows==0)
				throw new ServiceException("用户可能已经不存在");
			return rows;
		}
}
