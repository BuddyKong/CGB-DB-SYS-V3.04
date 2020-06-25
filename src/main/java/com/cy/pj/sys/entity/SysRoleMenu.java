package com.cy.pj.sys.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
@Data
public class SysRoleMenu implements Serializable {

	private static final long serialVersionUID = 4403526835805041865L;
	  private Integer id;

      private String name;

      private String note;

      private List<Integer> menuIds;
}
