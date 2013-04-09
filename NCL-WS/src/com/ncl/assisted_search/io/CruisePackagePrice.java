package com.ncl.assisted_search.io;

public class CruisePackagePrice {
	public int _cruisePackageID;
	public String _priceType;
	public double _price;
	
	public CruisePackagePrice(int cruisePackageID, 
			String priceType, double price)
	{
		_cruisePackageID = cruisePackageID;
		_priceType = priceType;
		_price = price;
	}
}
