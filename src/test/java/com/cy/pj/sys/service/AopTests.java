package com.cy.pj.sys.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysUserDept;

@SpringBootTest
public class AopTests {
	@Autowired

    private SysUserService userService;

    @Test

    public void testSysUserService() {

            PageObject<SysUserDept> po=

            userService.findPageObjects("admin",1);

            System.out.println("rowCount:"+po.getRowCount());

    }
}
