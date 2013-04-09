package com.ncl.assisted_search.io;

public class Parameters {
	public int _optimRequestID;
	public double _customerAccomodationPriority; // Customer Parameters
	public double _customerThingsToDoPriority;
	public double _customerPricePriority;
	public double _customerTimePriority;
	public double _customerDestinationPriority;
	public double _businessAccomodationPriority; // Business Parameters
	public double _businessThingsToDoPriority;
	public double _businessPricePriority;
	public double _businessTimePriority;
	public double _businessDestinationPriority;
	public double _businessDepartingPortPriority;
		
	public Parameters(int optimRequestID,double customerAccomodationPriority, double customerThingsToDoPriority,
			double customerPricePriority, double customerTimePriority, double customerDestinationPriority, double businessAccomodationPriority,
			double businessThingsToDoPriority,double businessPricePriority,double businessTimePriority, double businessDestinationPriority,
			double businessDepartingPortPriority)
	{
		_optimRequestID = optimRequestID;
		_customerAccomodationPriority = customerAccomodationPriority;
		_customerThingsToDoPriority = customerThingsToDoPriority;
		_customerPricePriority = customerPricePriority;
		_customerTimePriority = customerTimePriority;
		_customerDestinationPriority = customerDestinationPriority;
		_businessAccomodationPriority = businessAccomodationPriority;
		_businessThingsToDoPriority = businessThingsToDoPriority;
		_businessPricePriority = businessPricePriority;
		_businessTimePriority = businessTimePriority;
		_businessDestinationPriority = businessDestinationPriority;
		_businessDepartingPortPriority = businessDepartingPortPriority;
	}
	
	public int return_optimRequestID()
	{
		return _optimRequestID;
	}
	public double return_customerAccomodationPriority()
	{
		return _customerAccomodationPriority;
	}
	
	public double return_customerThingsToDoPriority()
	{
		return _customerThingsToDoPriority;
	}

	public double return_customerPricePriority()
	{
		return _customerPricePriority;
	}

	public double return_customerTimePriority()
	{
		return _customerTimePriority;
	}
	
	public double return_customerDestinationPriority()
	{
		return _customerDestinationPriority;
	}
	
	public double return_businessAccomodationPriority()
	{
		return _businessAccomodationPriority;
	}
	
	public double return_businessThingsToDoPriority()
	{
		return _businessThingsToDoPriority;
	}

	public double return_businessPricePriority()
	{
		return _businessPricePriority;
	}

	public double return_businessTimePriority()
	{
		return _businessTimePriority;
	}
	
	public double return_businessDestinationPriority()
	{
		return _businessDestinationPriority;
	}
	
	public double return_businessDepartingPortPriority()
	{
		return _businessDepartingPortPriority;
	}
}

