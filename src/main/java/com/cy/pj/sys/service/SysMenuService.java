package com.cy.pj.sys.service;

import java.util.List;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;

public interface SysMenuService {
	 List<SysMenu> findObjects();
	 int doDeleteObject(Integer id);
	 int saveObject(SysMenu entity);
	 List<Node> findZtreeMenuNodes();
	 int doUpdateObject(SysMenu entity);
}

