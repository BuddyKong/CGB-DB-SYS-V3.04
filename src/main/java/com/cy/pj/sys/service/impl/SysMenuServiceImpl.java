package com.cy.pj.sys.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;
@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	@Cacheable(value="menuCache")
	@Override
	public List<SysMenu> findObjects() {
		System.out.println("SysMenuServiceImplCache.findobject");
            List<SysMenu> list=
            		sysMenuDao.findObjects();
            if(list==null||list.size()==0)
            throw new ServiceException("没有对应的菜单信息");
            return list;
}
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@CacheEvict(value="menuCache")
	@Override
	public int doDeleteObject(Integer id) {
		if(id==null||id<=0)
			throw new IllegalArgumentException("请先选择");
		  //2.基于id进行子元素查询
        int count=sysRoleMenuDao.getChildCount(id);
        if(count>0)
        throw new ServiceException("请先删除子菜单");
        //3.删除角色,菜单关系数据
        sysRoleMenuDao.deleteObjectsByMenuId(id);
        //4.删除菜单元素
        int rows=sysRoleMenuDao.deleteObject(id);
        if(rows==0)
        throw new ServiceException("此菜单可能已经不存在");
        //5.返回结果
        return rows;
}
	@Override
	public int saveObject(SysMenu entity) {
		 //1.合法验证
        if(entity==null)
        throw new ServiceException("保存对象不能为空");
        if(StringUtils.isEmpty(entity.getName()))
        throw new ServiceException("菜单名不能为空");
        int rows;
        //2.保存数据
        try{
        rows=sysMenuDao.insertObject(entity);
        }catch(Exception e){
        e.printStackTrace();
        throw new ServiceException("保存失败");
        }
        //3.返回数据
        return rows;
	}
	 @Override
     public List<Node> findZtreeMenuNodes() {
             return sysMenuDao.findZtreeMenuNodes();
     }
	@Override
	public int doUpdateObject(SysMenu entity) {
		if(entity==null)
	        throw new ServiceException("保存对象不能为空");
		 if(StringUtils.isEmpty(entity.getName()))
		        throw new ServiceException("菜单名不能为空");
		        int rows;
		        //2.保存数据
		        try{
		        rows=sysMenuDao.doUpdateObject(entity);
		        }catch(Exception e){
		        e.printStackTrace();
		        throw new ServiceException("保存失败");
		        }
		        //3.返回数据
		        return rows;
	}
}
