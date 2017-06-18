package com.ebay.cs.qadb.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@ParentPackage("publicPackage")
@Action("range")
@Results({
	
	@Result(name = "tolocal",location = "/"),
	@Result(name = "toglobal",location = "/")
})
@Controller
public class RangeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7483087295235083502L;

	public String ToLocal() {
		
		ActionContext.getContext().getSession().put("range", "local");
		System.out.println("in ToLocal");
		
		return "tolocal";
	}
	
	public String ToGlobal() {
		
		ActionContext.getContext().getSession().put("range", "global");
		System.out.println("in ToGlobal");
		
		return "toglobal";
	}
	
}
