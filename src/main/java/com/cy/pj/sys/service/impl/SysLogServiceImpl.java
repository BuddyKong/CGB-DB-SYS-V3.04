package com.cy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
@Service
public class SysLogServiceImpl implements SysLogService {
	 @Autowired
     private SysLogDao sysLogDao;
	@Override
	public PageObject<SysLog> findPageObjects(String name, Integer pageCurrent) {
		 if(pageCurrent==null||pageCurrent<1)
             throw new IllegalArgumentException("当前页码不正确");
		 int rowCount=sysLogDao.getRowCount(name);
		 if(rowCount==0)
	          throw new ServiceException("系统没有查到对应记录");
		 //查询当前页记录
		 //每页最多要显示的记录数
		 int pageSize=5;
		 //计算当前页查询的起始位置
		 int startIndex=(pageCurrent-1)*pageSize;
		//对业务层查询结果进行处理和封装
		 //注意pageObject构造方法传参的顺序由构造方法定义时的顺序决定
		 List<SysLog> records=
                 sysLogDao.findPageObjects(name, startIndex, pageSize);
		 PageObject<SysLog> pageObject=new PageObject<>();
		 pageObject.setPageCurrent(pageCurrent);
         pageObject.setPageSize(pageSize);
         pageObject.setRowCount(rowCount);
         pageObject.setRecords(records);
         pageObject.setPageCount((rowCount-1)/pageSize+1);
         //5.返回封装结果。
         return pageObject;
	}
	@Override
	public int deleteObjects(Integer... ids) {
		if(ids==null||ids.length==0)
			throw new IllegalArgumentException("请选择一个");
		int rows;
		
		try{
            rows=sysLogDao.deleteObjects(ids);
            }catch(Throwable e){
            e.printStackTrace();
            //发出报警信息(例如给运维人员发短信)
            throw new ServiceException("系统故障，正在恢复中...");
            }
            //4.对结果进行验证
            if(rows==0)
            throw new ServiceException("记录可能已经不存在");
            //5.返回结果
            return rows;
	}
	@Override
    public void saveObject(SysLog entity) {
      sysLogDao.insertObject(entity);
}
}
