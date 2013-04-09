package com.ncl.assisted_search.io;

public class CruisePackage {
	public int _cruisePackageID;
	public String _SailID;//3-11-2013
	public String _PackageID;//3-11-2013
	public String _DateID;//3-11-2013
	public int _Date;	
	public String _shipID;
	public String _itineraryID;//3-11-2013
	public String _destination;//3-11-2013
	public String _roomType; // Cruise Package details
	public String _Meta;//3-11-2013
	public double _accomodationPreference;
	public double _interestPreference;
	public double _roomTypeViolation;
	public double _thingsToDoViolation;
	public double _priceViolation;
	public double _dateViolation;
	public double _durationViolation;
	public double _destinationViolation;
	public double _departingPortViolation;
	public double _drupalWeight;
	public String _discountType;
	public double _pricePerGuest;
	
	public CruisePackage(int cruisePackageID, 
			String SailID, String PackageID, String DateID, int Date, String shipID, String itineraryID, String destination, String roomType, String Meta, double accomodationPreference, 
			double interestPreference, double roomTypeViolation, double thingsToDoViolation, double priceViolation, double dateViolation,
			double durationViolation, double destinationViolation, double departingPortViolation, 
			String discountType, double pricePerGuest, double drupalWeight)
	{
		_cruisePackageID = cruisePackageID;
		_SailID = SailID;//3-11-2013
		_PackageID = PackageID;//3-11-2013
		_DateID = DateID;//3-11-2013
		_Date = Date;
		_shipID = shipID;
		_itineraryID = itineraryID;
		_destination = destination;//3-11-2013
		_roomType = roomType; 
		_Meta = Meta;//3-11-2013
		_accomodationPreference= accomodationPreference;
		_interestPreference = interestPreference;
		_roomTypeViolation = roomTypeViolation;
		_thingsToDoViolation = thingsToDoViolation;
		_priceViolation = priceViolation;
		_dateViolation = dateViolation;
		_durationViolation = durationViolation;
		_destinationViolation = destinationViolation;
		_departingPortViolation = departingPortViolation;
		_drupalWeight = drupalWeight;	
		_discountType = discountType;//3-11-2013
		_pricePerGuest = pricePerGuest;//3-11-2013
	}
	
	/* 3-11-2013
	public int return_optimRequestID()
	{
		return _optimRequestID;
	}*/
	
	public String return_SailID()
	{
		return _SailID;
	}
	public String return_PackageID()
	{
		return _PackageID;
	}
	
	public String return_itineraryID()
	{
		return _itineraryID;
	}
	
	public String return_destination()
	{
		return _destination;
	}
	
	public String return_roomType()
	{
		return _roomType;
	}
	
	public String return_DateID()
	{
		return _DateID;
	}
	
	public int return_Date()
	{
		return _Date;
	}
	
	public String return_meta()
	{
		return _Meta;
	}
	
	public double return_accomodationPreference()
	{
		return _accomodationPreference;
	}
	public double return_interestPreference()
	{
		return _interestPreference;
	}
	
	public double return_roomTypeViolation()
	{
		return _roomTypeViolation;
	}
	public double return_thingsToDoViolation()
	{
		return _thingsToDoViolation;
	}
	public double return_priceViolation()
	{
		return _priceViolation;
	}
	public double return_dateViolation()
	{
		return _dateViolation;
	}
	public double return_durationViolation()
	{
		return _durationViolation;
	}
	public double return_destinationViolation()
	{
		return _destinationViolation;
	}
	public double return_departingPortViolation()
	{
		return _departingPortViolation;
	}
	public String return_discountType()
	{
		return _discountType;
	}
	public double return_pricePerGuest()
	{
		return _pricePerGuest;
	}	
	public double return_drupalWeight()
	{
		return _drupalWeight;
	}
}
