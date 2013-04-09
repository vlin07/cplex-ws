package com.ncl.assisted_search.io;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserInput {
	
	public ArrayList<DurationRange> _durationRangeList = new ArrayList();
	public ArrayList<PriceRange> _priceRangeList = new ArrayList();
	public ArrayList<SailDateRange> _sailDateRangeList = new ArrayList();
	public ArrayList<String> _thingsToDoList = new ArrayList();
	public ArrayList<String> _interestList = new ArrayList();	
	public ArrayList<String> _departingPortList = new ArrayList();	
	public ArrayList<String> _destinationList = new ArrayList();
	public ArrayList<String> _metaList = new ArrayList();
	public int _numberOfGuests;
	public ArrayList<AccommodationPreference> _accommodationPreference = new ArrayList();
	public UserWeight _userWeight;
	public ArrayList<String> _discountTypeList = new ArrayList();
	public RecommendationRequestLimit _recommendationRequestLimit;
	//public static Connection _DatabaseConnection;
	public UserInput() {
		// TODO Auto-generated constructor stub
	}
	
	public UserInput(ArrayList<DurationRange> durationRangeList,
			                       ArrayList<PriceRange> priceRangeList,
			                       ArrayList<SailDateRange> sailDateRangeList,
			                       ArrayList<String> thingsToDoList,
			                       ArrayList<String> interestList,
			                       ArrayList<String> departingPortList,
			                       ArrayList<String> destinationList,
			                       ArrayList<String> metaList,
			                       int numberOfGuest,
			                       ArrayList<AccommodationPreference> accommodationPreference,
			                       UserWeight userWeight,
			                       ArrayList<String> discountTypeList,
			                       RecommendationRequestLimit recommendationRequestLimit)
	{
		_durationRangeList = durationRangeList;
		_priceRangeList = priceRangeList;
		_sailDateRangeList = sailDateRangeList;
		_thingsToDoList = thingsToDoList;
		_interestList = interestList;
		_departingPortList = departingPortList;
		_destinationList = destinationList;
		_metaList = metaList;
		_numberOfGuests = numberOfGuest;
		_accommodationPreference = accommodationPreference;
		_userWeight = userWeight;
		_discountTypeList = discountTypeList;
		_recommendationRequestLimit = recommendationRequestLimit;
	}
	
	//test data set: For testing only
	public UserInput(boolean Debug) throws Exception {
		try{
			/*
	    	Class.forName("oracle.jdbc.driver.OracleDriver");
	        DriverManager.registerDriver(new OracleDriver());
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			_DatabaseConnection = DriverManager.getConnection(
	        		 url,       // URL
	        		 "ncl_ws",//"SYSTEM",       // username
	        		 "ncl_ws_password" //
	        );
			*/
			_userWeight=new UserWeight(0.2, 0.4, 0.6,0.8,1.0);
			_recommendationRequestLimit = new RecommendationRequestLimit(2,0,0,0);	
		
			
	        DateFormat df = new SimpleDateFormat("yyyyMMdd");
	        Date ed = df.parse("20130301");
	        Date ld = df.parse("20130305");
			SailDateRange sdr=new SailDateRange(ed,ld);
			_sailDateRangeList.add(sdr);
	        ed = df.parse("20130310");
	        ld = df.parse("20130321");
			sdr= new SailDateRange(ed,ld);
			_sailDateRangeList.add(sdr);
			_numberOfGuests=2;
			_discountTypeList.add("BEST-FAIR");
			_discountTypeList.add("AARP");
			_accommodationPreference.add(new AccommodationPreference("Space","NoPreference"));
			_accommodationPreference.add(new AccommodationPreference("View","MoreImportant"));	
			_accommodationPreference.add(new AccommodationPreference("Location","LessImportant"));	
			_interestList.add("Family_Fun");
			_interestList.add("Relaxing");
			_thingsToDoList.add("adventure");
			_thingsToDoList.add("wildlife_tours");
			_metaList.add("BALCONY");
			_metaList.add("SUITE");	
			_destinationList.add("PANAMA_CANAL");
			_destinationList.add("BAHAMAS_FLORIDA");
			_departingPortList.add("BCN");
			_departingPortList.add("MIA");
			_durationRangeList.add(new DurationRange(1,6));
			_durationRangeList.add(new DurationRange(9,11));
			_priceRangeList.add(new PriceRange(100,200));
			_priceRangeList.add(new PriceRange(250,2000));			
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
}

