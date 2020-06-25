package com.cy.pj.sys.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class SysLog implements Serializable{
private static final long serialVersionUID = 1L;
private	int id;
private	String username;
private	String operation;
private	String method;
private	String params;

private	Long time;
private	String ip;
@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
private	Date createdTime;
}
