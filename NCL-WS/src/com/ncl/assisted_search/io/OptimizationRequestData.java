/* Created by: Arun Aryasomayajula, Optimal Solutions Inc.
 * 01/05/2013
 * */
package com.ncl.assisted_search.io;

import java.util.ArrayList;

public class OptimizationRequestData {
	
	private static ArrayList<CruisePackage> _cruisePackageList = new ArrayList();
	//3-11-2013 private static ArrayList<Itinerary> _itineraryList = new ArrayList();
	private static ArrayList<ThingsToDo> _ttdList = new ArrayList();
	private static ArrayList<itinerarySimilarity> _isList1 = new ArrayList();
	private static ArrayList<InterestThingsToDo> _ittdList = new ArrayList();
	private static Parameters _params;
	private static RecommendationRequestLimit _rrl;
	
	public OptimizationRequestData(ArrayList<CruisePackage> cruisePackageList,
			                       //3-11-2013 ArrayList<Itinerary> itineraryList,
			                       ArrayList<ThingsToDo> ttdList,
			                       ArrayList<itinerarySimilarity> isList1,
			                       ArrayList<InterestThingsToDo> ittdList,
			                       Parameters params,
			                       RecommendationRequestLimit rrl)
	{
		_cruisePackageList = cruisePackageList;
		//3-11-2013 _itineraryList = itineraryList;
		_ttdList = ttdList;
		_isList1 = isList1;
		_ittdList = ittdList;
		_params = params;
		_rrl = rrl;
	}
	
	public OptimizationRequestData() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<CruisePackage> return_cruisePackageList()
	{
		return _cruisePackageList;
	}
	
	/*3-11-2013
	public ArrayList<Itinerary> return_itineraryList()
	{
		return _itineraryList;
	}*/
	
	public ArrayList<ThingsToDo> return_ttdList()
	{
		return _ttdList;
	}
	
	public ArrayList<itinerarySimilarity> return_isList1()
	{
		return _isList1;
	}
	
	public ArrayList<InterestThingsToDo> return_ittdList()
	{
		return _ittdList;
	}
	
	public Parameters return_params()
	{
		return _params;
	}
	
	public RecommendationRequestLimit return_rrl()
	{
		return _rrl;
	}
	
	public void set_cruisePackageList(ArrayList<CruisePackage> cruisePackageList)
	{
	_cruisePackageList = cruisePackageList;
	}
	
	/*3-11-2013
	public void set_itineraryList(ArrayList<Itinerary> itineraryList)
	{
	_itineraryList = itineraryList;
	}*/
	
	public void set_ttdList( ArrayList<ThingsToDo> ttdList)
	{
		_ttdList = ttdList;
	}
	
	public void set_isList1( ArrayList<itinerarySimilarity> isList1)
	{
		_isList1 = isList1;
	}
	
	public void set_ittdList( ArrayList<InterestThingsToDo> ittdList)
	{
		_ittdList = ittdList;
	}
	
	public void set_params( Parameters params)
	{
		_params = params;
	}
	
	public void set_rrl( RecommendationRequestLimit rrl)
	{
		_rrl = rrl;
	}
}
