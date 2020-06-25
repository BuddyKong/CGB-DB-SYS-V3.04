package com.cy.pj.sys.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;

@RequestMapping("/user/")
@RestController
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("doUpdatePassword")
    public JsonResult doUpdatePassword(String pwd,String newPwd,String cfgPwd) {
   	  sysUserService.updatePassword(pwd, newPwd, cfgPwd);
   	 return new JsonResult("update ok");
    }
	
	@RequestMapping("doFindPageObjects")
	public JsonResult doFindPageObjects(String username, Integer pageCurrent) {
		return new JsonResult(sysUserService.findPageObjects(username, pageCurrent));
	}

	@RequestMapping("doValidById")
	public JsonResult doValidById(Integer id, Integer valid) {
		sysUserService.validById(id, valid);
		return new JsonResult("update ok");
	}

	@RequestMapping("doSaveObject")
	public JsonResult doSaveObject(SysUser entity, Integer[] roleIds) {
		sysUserService.doSaveObject(entity, roleIds);
		return new JsonResult("save ok");
	}

	@RequestMapping("doFindObjectById")
	public JsonResult doFindObjectById(
			Integer id) {
		Map<String, Object> map =
				sysUserService.findObjectById(id);
		return new JsonResult(map);
	}
	 @RequestMapping("doUpdateObject")
     public JsonResult doUpdateObject(
         SysUser entity,Integer[] roleIds){
             sysUserService.updateObject(entity,roleIds);
             return new JsonResult("update ok");
     }
	 @RequestMapping("doLogin")
     public JsonResult doLogin(boolean isRememberMe,String username,String password){      
             //2.通过Subject提交用户信息,交给shiro框架进行认证操作
             //2.1对用户进行封装
             UsernamePasswordToken token=
             new UsernamePasswordToken(
            		 
                             username,//身份信息
                             password);//凭证信息
             //2.2对用户信息进行身份认证
             if(isRememberMe) {
                 token.setRememberMe(true);
          }
             Subject subject=SecurityUtils.getSubject();
             subject.login(token);
             //分析:
             //1)token会传给shiro的SecurityManager
             //2)SecurityManager将token传递给认证管理器
             //3)认证管理器会将token传递给realm
             return new JsonResult("login ok");
     }
}
