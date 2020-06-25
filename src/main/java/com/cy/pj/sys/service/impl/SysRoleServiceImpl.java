package com.cy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysRoleDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.entity.SysRoleMenu;
import com.cy.pj.sys.service.SysRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysRoleDao sysRoleDao;
	@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		//1.参数校验
		if(pageCurrent==null||pageCurrent<1)
		  throw new IllegalArgumentException("页码值无效");
		//2.查询当前页角色记录
		//2.1设置查询参数
		int pageSize=2;
		Page<SysRole> page=PageHelper.startPage(pageCurrent, pageSize);
		//2.2启动查询操作
		List<SysRole> records=sysRoleDao.findPageObjects(name);
		//3.封装查询结果
		return new PageObject<>(records, (int)page.getTotal(),
				               (int)page.getPages(), pageSize, pageCurrent);
	}
	@Override
	public String doFindObjectById(Integer id) {
		sysRoleDao.doFindObjectById(id);
		return null;
	}
	@Override
	public int doDeleteObject(Integer id) {
		//1.验证数据的合法性
		if(id==null||id<=0)
			throw new IllegalArgumentException("请先选择");
		//3.基于id删除关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		sysUserRoleDao.deleteObjectsByRoleId(id);
		//4.删除角色自身
		int rows=sysRoleDao.doDeleteObject(id);
		if(rows==0)
			throw new ServiceException("此记录可能已经不存在");
		//5.返回结果
		return rows;

	}
	@Override
	public int saveObject(SysRole entity, Integer[] menuIds) {
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("角色名不允许为空");
		if(menuIds==null||menuIds.length==0)
			throw new ServiceException("必须为角色分配权限");
		//2.保存角色自身信息
		int rows=sysRoleDao.insertObject(entity);
		//3.保存角色菜单关系数据
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		//4.返回业务结果
		return rows;
	}
	@Override
	public SysRoleMenu findObjectById(Integer id) {
		if(id==null||id<=0)
			throw new IllegalArgumentException("id的值不合法");
		//2.执行查询
		SysRoleMenu result=sysRoleDao.findObjectById(id);
		List<Integer> menuId = sysRoleMenuDao.findMenuIdsByRoleId(id);
		result.setMenuIds(menuId);
		//3.验证结果并返回
		if(result==null)
			throw new ServiceException("此记录已经不存在");
		return result;
	}
	@Override
	public int updateObject(SysRole entity, Integer[] menuIds) {
		//1.合法性验证
		if(entity==null)
			throw new IllegalArgumentException("更新的对象不能为空");
		if(entity.getId()==null)
			throw new IllegalArgumentException("id的值不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("角色名不能为空");
		if(menuIds==null||menuIds.length==0)
			throw new IllegalArgumentException("必须为角色指定一个权限");
		//2.更新数据
		int rows=sysRoleDao.updateObject(entity);
		if(rows==0)
			throw new ServiceException("对象可能已经不存在");
		sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
		sysRoleMenuDao.insertObjects(entity.getId(),menuIds);
		//3.返回结果
		return rows;
	}
	@Override
	public List<CheckBox> findObjects() {
		  return sysRoleDao.findObjects();
	}
}
