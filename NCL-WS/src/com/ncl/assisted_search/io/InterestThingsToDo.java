package com.ncl.assisted_search.io;

public class InterestThingsToDo {
	
	//3-11-2013 public int _optimRequestID;
	public String _interestID;
	public String _thingsToDoID;
	public int _thingsToDoPrefMatch;
	//3-11-2013 public int _thingsToDoSelected;
	public double _thingsToDoViolation;
	public double _drupalWeight;
	
	public InterestThingsToDo(//int optimRequestID, 
			String interestID, String thingsToDoID,
			int thingsToDoPrefMatch,
			//3-11-2013 int thingsToDoSelected, 
			double thingsToDoViolation, double drupalWeight)
	{
		//3-11-2013 _optimRequestID = optimRequestID;
		_interestID = interestID;
		_thingsToDoID = thingsToDoID;
		_thingsToDoPrefMatch = thingsToDoPrefMatch;
		//3-11-2013 _thingsToDoSelected = thingsToDoSelected;
		_thingsToDoViolation = thingsToDoViolation;
		_drupalWeight = drupalWeight;
	}
	
	/* 3-11-2013
	public int return_optimRequestID()
	{
		return _optimRequestID;
	}*/
	
	public String return_interestID()
	{
		return _interestID;
	}
	public String return_thingsToDoID()
	{
		return _thingsToDoID;
	}
	public int return_thingsToDoPrefMatch()
	{
		return _thingsToDoPrefMatch;
	}
	/*
	public int return_thingsToDoSelected()
	{
		return _thingsToDoSelected;
	}*/
	
	public double return_thingsToDoViolation()
	{
		return _thingsToDoViolation;
	}
	public double return_drupalWeight()
	{
		return _drupalWeight;
	}
}