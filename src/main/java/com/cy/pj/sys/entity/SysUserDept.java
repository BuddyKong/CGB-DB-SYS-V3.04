package com.cy.pj.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.cy.pj.sys.entity.SysDept;

import lombok.Data;

@Data
public class SysUserDept implements Serializable{

	private static final long serialVersionUID = -5736936320632317689L;

	private Integer id;

     private String username;

     private String password;//md5

     private String salt;

     private String email;

     private String mobile;

     private Integer valid=1;

     private SysDept sysDept; //private Integer deptId;

     private Date createdTime;

     private Date modifiedTime;

     private String createdUser;

     private String modifiedUser;

     
}
