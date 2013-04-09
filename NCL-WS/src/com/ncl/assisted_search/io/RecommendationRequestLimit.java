package com.ncl.assisted_search.io;

public class RecommendationRequestLimit {
	
	public int _numPackageRecommendations;
	public int _numThingsToDo;
	public int _numDestinations;
	public int _numStateRoomTypes;
	
	public RecommendationRequestLimit(int numPackageRecommendations, int numThingsToDo, int numDestinations, int numStateRoomTypes)
	{
		_numPackageRecommendations = numPackageRecommendations;
		_numThingsToDo = numThingsToDo;
		_numDestinations = numDestinations;
		_numDestinations = numDestinations;
		_numStateRoomTypes = numStateRoomTypes;
	}
	
	public int return_numPackageRecommendations()
	{
		return _numPackageRecommendations;
	}
	
	public int return_numThingsToDo()
	{
		return _numThingsToDo;
	}
	
	public int return_numDestinations()
	{
		return _numDestinations;
	}
	
	public int return_numStateRoomTypes()
	{
		return _numStateRoomTypes;
	}

}
