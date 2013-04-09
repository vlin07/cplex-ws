package com.ncl.assisted_search.io;

public class ThingsToDoRecommendation {
	
	//3-11-2013 public int _optimRequestID;
	public String _thingsToDoID;
	//3-11-2013 public String  _itineraryID;
	public String _interestID;
	public double _interestPrefContribution;
	public double _drupalWeightContribution;
	public int _isRecommended;
	
	public ThingsToDoRecommendation(//3-11-2013 int optimRequestID, int thingsToDoID, int itineraryID, int interestID,
			String thingsToDoID, String interestID,
			double interestPrefContribution, double drupalWeightContribution,int isRecommended)
	{
		//3-11-2013 _optimRequestID = optimRequestID;
		_thingsToDoID = thingsToDoID;
		//_itineraryID = itineraryID;
		_interestID = interestID;
		_interestPrefContribution = interestPrefContribution;
		_drupalWeightContribution = drupalWeightContribution;
		_isRecommended = isRecommended;
		
	}
	
	/*
	public int return_optimRequestID()
	{
		return _optimRequestID;
	}*/
	
	public String return_thingsToDoID()
	{
		return _thingsToDoID;
	}
	/*
	public String return_itineraryID()
	{
		return _itineraryID;
	}*/
	
	public String return_interestID()
	{
		return _interestID;
	}
	
	public double return_interestPrefContribution()
	{
		return _interestPrefContribution;
	}
	
	public double return_drupalWeightContribution()
	{
		return _drupalWeightContribution;
	}
	public int return_isRecommended()
	{
		return _isRecommended;
	}
}
