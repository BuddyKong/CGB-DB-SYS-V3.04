package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;
@Mapper
public interface SysMenuDao {
	List<SysMenu> findObjects();
	int insertObject(SysMenu entity);
	List<Node> findZtreeMenuNodes();
	int doUpdateObject(SysMenu entity);
	List<String> findPermissions(
            @Param("menuIds")
            Integer[] menuIds);
}
