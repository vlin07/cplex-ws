package com.ncl.assisted_search.io;

import java.util.ArrayList;

public class OptimizationResponseData 

{
	 private static ArrayList<CruisePackageRecommendation> _cruisePackageData = new ArrayList();
	    private static ArrayList<ConstraintViolation> _constraintViolationData = new ArrayList();
	    private static ArrayList<ThingsToDoRecommendation> _ttdrData = new ArrayList();	
	    
	    public OptimizationResponseData()
	    {
	    	//Default Constructor.	    	
	    }
	
	    public ArrayList<CruisePackageRecommendation> return_cruisePackageData()
		{
			return _cruisePackageData;
		}
	    
	    public ArrayList<ConstraintViolation> return_constraintViolationData()
		{
			return _constraintViolationData;
		}
	    
	    public ArrayList<ThingsToDoRecommendation> return_ttdrData()
		{
			return _ttdrData;
		}
	    
	    public void set_cruisePackageData(ArrayList<CruisePackageRecommendation> cruisePackageData)
		{
		_cruisePackageData = cruisePackageData;
		}
	    
	    public void set_constraintViolationData(ArrayList<ConstraintViolation> constraintViolationData)
		{
		_constraintViolationData = constraintViolationData;
		}
	    
	    public void set_ttdrData(ArrayList<ThingsToDoRecommendation> ttdrData)
		{
		_ttdrData = ttdrData;
		}

}
