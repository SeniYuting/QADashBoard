package com.ebay.cs.qadb.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;


@ParentPackage("publicPackage")
@Action("home")
@Results({

	@Result(name = "json", type="json",params = { "root", "dataMap" }),
	
	@Result(name ="ProductionBugTrends" ,location = "ProductQuality/ProductionBugTrends.jsp"),
	@Result(name ="Production_bugs_OOSLA_view",location = "ProductQuality/ProductionBugsOOSLAView.jsp"),
	@Result(name ="OpeningProductionBugs",location="ProductQuality/OpeningProductionBugs.jsp"),
	@Result(name ="WhenFoundBugs",location = "ProductQuality/WhenFoundBugs.jsp"),
	@Result(name ="HowFoundBugs",location = "ProductQuality/HowFoundBugs.jsp"),
	@Result(name ="ProductionAnalysis",location = "ProductQuality/ProductionAnalysis.jsp"),
	
	@Result(name ="QA_bugs_OOSLA_view",location="QAQuality/QABugsOOSLAView.jsp"),
	@Result(name ="opening_QA_bugs",location="QAQuality/OpeningQABugs.jsp"),
	@Result(name ="QABugsTrends",location="QAQuality/QABugsTrends.jsp"),
	@Result(name ="HowFound_QA_bugs",location="QAQuality/HowFoundQABugs.jsp"),
	@Result(name ="QAAnalysis",location="QAQuality/QAAnalysis.jsp"),
	
	@Result(name ="BugsEffectivity",location="Effectivity/BugsEffectivity.jsp"),
	@Result(name ="Reopen",location="Effectivity/Reopen.jsp"),

})
@Controller
public class HomePage extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7483087295235083502L;
	/**Product Quality
	 */
	public String ToProductionBugTrends(){
		return "ProductionBugTrends";
	}
	
	public String ToProductionBugsOOSLAView(){
		return "Production_bugs_OOSLA_view";
	}
	
	public String ToOpeningProductionBugs(){
		return "OpeningProductionBugs";
	}
	
	public String ToWhenFoundBugs(){
		return "WhenFoundBugs";
	}
	
	public String ToHowFoundBugs(){
		return "HowFoundBugs";
	}
	
	public String ToProductionAnalysis(){
		return "ProductionAnalysis";
	}
	
	/**QA Quality
	 */
	public String ToQABugsOOSLAView(){
		return "QA_bugs_OOSLA_view";
	}
	
	public String ToOpeningQABugs(){
		return "opening_QA_bugs";
	}
	
	public String ToQABugsTrends(){
		return "QABugsTrends";
	}
	
	public String ToHowFoundQABugs(){
		return "HowFound_QA_bugs";
	}
	
	public String ToQAAnalysis(){
		return "QAAnalysis";
	}
	
	/**Effectivity
	 */
	public String ToBugEffectivity(){
		return "BugsEffectivity";
	}
	
	public String ToReopen(){
		return "Reopen";
	}

}
