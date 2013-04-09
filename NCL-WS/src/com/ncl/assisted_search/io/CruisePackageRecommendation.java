package com.ncl.assisted_search.io;

public class CruisePackageRecommendation {

	//3-11-2013 public int _optimRequestID;
	public String _SailID;//3-11-2013
	public String _PackageID;//3-11-2013
	public String  _DateID;//3-11-2013
	public String _itineraryID;//3-11-2013
	public String _Destination;//3-11-2013
	public String _roomType;
	public String _Meta;//3-11-2013
	public double _interestPrefContribution;
	public double _accomodationPrefContribution;
	public double _roomViolationValue;
	public double _thingsToDoViolationValue;
	public double _priceViolationValue;
	public double _dateViolationValue;
	public double _durationViolationValue;
	public double _destinationViolationValue;
	public double _portViolationValue;
	public double _drupalWeightContribution;
	public int _isRecommended;
	//public String _discountType;
	public double _pricePerGuest;

public CruisePackageRecommendation(String SailID, String PackageID,//3-11-2013  
		String DateID, String itineraryID, String Destination,
		String roomType,String Meta, double accomodationPrefContribution,
		double interestPrefContribution, double roomViolationValue, double thingsToDoViolationValue, double priceViolationValue,
		double dateViolationValue, double durationViolationValue, double destinationViolationValue, double portViolationValue,
		double drupalWeightContribution, //String discountType, 
		double pricePerGuest, int isRecommended)
{
	//_optimRequestID = optimRequestID;
	_SailID = SailID;//3-11-2013
	_PackageID = PackageID;//3-11-2013
	_itineraryID = itineraryID;
	_DateID = DateID;
	_roomType = roomType;
	_Meta = Meta;//3-11-2013
	_Destination = Destination;
	_accomodationPrefContribution = accomodationPrefContribution;
	_interestPrefContribution = interestPrefContribution;
	_roomViolationValue = roomViolationValue;
	_thingsToDoViolationValue = thingsToDoViolationValue;
	_priceViolationValue = priceViolationValue;
	_dateViolationValue = dateViolationValue;
	_durationViolationValue = durationViolationValue;
	_destinationViolationValue = destinationViolationValue;
	_portViolationValue = portViolationValue;
	_drupalWeightContribution = drupalWeightContribution;
	//_discountType = discountType;
	_pricePerGuest = pricePerGuest;
	_isRecommended = isRecommended;
}
/*
public int return_optimRequestID()
{
	return _optimRequestID;
}*/

public String return_itineraryID()
{
	return _itineraryID;
}
public String return_DateID()
{
	return _DateID;
}
public String return_roomType()
{
	return _roomType;
}

public String return_Destination()
{
	return _Destination;
}
public double return_accomodationPrefContribution()
{
	return _accomodationPrefContribution;
}
public double return_interestPrefConstribution()
{
	return _interestPrefContribution;
}
public double return_roomViolationValue()
{
	return _roomViolationValue;
}
public double return_thingsToDoViolationValue()
{
	return _thingsToDoViolationValue;
}
public double return_priceViolationValue()
{
	return _priceViolationValue;
}
public double return_dateViolationValue()
{
	return _dateViolationValue;
}
public double return_durationViolationValue()
{
	return _durationViolationValue;
}
public double return_destinationViolationValue()
{
	return _destinationViolationValue;
}
public double return_portViolationValue()
{
	return _portViolationValue;
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
