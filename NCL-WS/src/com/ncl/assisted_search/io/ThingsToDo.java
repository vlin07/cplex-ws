package com.ncl.assisted_search.io;

public class ThingsToDo {

	public String _itineraryID;
	
	public String _thingsToDoID;
	
	public ThingsToDo(String itineraryID, String thingsToDoID)
	{
		
		_itineraryID = itineraryID;
		_thingsToDoID = thingsToDoID;
	}
	
	public String return_itineraryID()
	{
		return _itineraryID;
	}
	
	public String return_thingsToDoID()
	{
		return _thingsToDoID;
	}


}
