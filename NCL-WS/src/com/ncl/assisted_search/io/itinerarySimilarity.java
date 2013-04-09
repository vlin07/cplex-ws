package com.ncl.assisted_search.io;

public class itinerarySimilarity {
	//3-11-2013 public int _optimRequestID;
	public String _itineraryID_1;
	public String _itineraryID_2;
	//public int _Similarity;
	
	public itinerarySimilarity(//3-11-2013 int optimRequestID,
			String itineraryID_1, String itineraryID_2)
	{
		//3-11-2013 _optimRequestID = optimRequestID;
		_itineraryID_1 = itineraryID_1;
		_itineraryID_2 = itineraryID_2;
	//	_Similarity = Similarity;
	}
	/*
	public int return_optimRequestID()
	{
		return _optimRequestID;
	}*/
	
	public String return_itineraryID_1()
	{
		return _itineraryID_1;
	}
	public String return_itineraryID_2()
	{
		return _itineraryID_2;
	}
	/*public int return_Similarity()
	{
		return _Similarity;
	}*/
}
