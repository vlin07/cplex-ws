package com.ncl.assisted_search.io;

public class ConstraintViolation {
	
	//3-11-2013 public int _optimRequestID;
	public double _packageLimitViolation;
	public double _thingsToDoLimitViolation;
	public double _destinationLimitViolation;
	public double _roomTypeLimitViolation;
	public double _itineraryUniquenessViolation;
	
	public ConstraintViolation(//3-11-2013 int optimRequestID, 
			double packageLimitViolation, double thingsToDoLimitViolation,
			double destinationLimitViolation, double roomTypeLimitViolation, double itineraryUniquenessViolation)
	{
		//3-11-2013 _optimRequestID = optimRequestID;
		_packageLimitViolation = packageLimitViolation;
		_thingsToDoLimitViolation = thingsToDoLimitViolation;
		_destinationLimitViolation = destinationLimitViolation;
		_roomTypeLimitViolation = roomTypeLimitViolation;
		_itineraryUniquenessViolation = itineraryUniquenessViolation;
	}
	
	/* 3-11-2013 
	public int return_optimRequestID()
	{
		return _optimRequestID;
	}*/
	
	public double return_packageLimitViolation()
	{
		return _packageLimitViolation;
	}
	public double return_thingsToDoLimitViolation()
	{
		return _thingsToDoLimitViolation;
	}
	public double return_destinationLimitViolation()
	{
		return _destinationLimitViolation;
	}
	public double return_roomTypeLimitViolation()
	{
		return _roomTypeLimitViolation;
	}
	public double return_itineraryUniquenessViolation()
	{
		return _itineraryUniquenessViolation;
	}
}
