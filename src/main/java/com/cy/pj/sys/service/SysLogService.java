package com.cy.pj.sys.service;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
/**
 * 业务层业务
 *	1)查询日志业务(添加分页业务实现)
 *	2)删除日志业务(后续会进行权限控制)
 *	3)添加日志业务(学了AOP再实现)
 */
public interface SysLogService {
	/*
	 * 定义日志的分页查询业务
	 * username用户名(数据最终来源为client)
	 * pageCurrent页码值(数据最终来源为client)
	 * 封装当前记录和分页信息的对象(pageObject)
	 */
	PageObject<SysLog> findPageObjects(
            String username,
            Integer pageCurrent);

	int deleteObjects(Integer...ids);
	void saveObject(SysLog entity);
}

