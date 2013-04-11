package com.ncl.assisted_search.services;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;

import com.ncl.assisted_search.io.AccommodationPreference;
import com.ncl.assisted_search.io.DurationRange;
import com.ncl.assisted_search.io.OptimizationRequest;
import com.ncl.assisted_search.io.OptimizationResponseData;
import com.ncl.assisted_search.io.PriceRange;
import com.ncl.assisted_search.io.RecommendationRequestLimit;
import com.ncl.assisted_search.io.SailDateRange;
import com.ncl.assisted_search.io.UserInput;
import com.ncl.assisted_search.io.UserWeight;
import com.ncl.assisted_search.messages.AccomodationSet;
import com.ncl.assisted_search.messages.DeparturePortSet;
import com.ncl.assisted_search.messages.DestinationSet;
import com.ncl.assisted_search.messages.DiscTypes;
import com.ncl.assisted_search.messages.DurationSet;
import com.ncl.assisted_search.messages.FlexDatesSet;
import com.ncl.assisted_search.messages.GetAssistedSearchRecommendation;
import com.ncl.assisted_search.messages.GetAssistedSearchRecommendationResponse;
import com.ncl.assisted_search.messages.ObjectFactory;
import com.ncl.assisted_search.messages.InterestSet;
import com.ncl.assisted_search.messages.PriceRangeSet;
import com.ncl.assisted_search.messages.PromoCodeSet;
import com.ncl.assisted_search.messages.RecommReqLimitSet;
import com.ncl.assisted_search.messages.ThingToDoSet;
import com.ncl.assisted_search.messages.UserWeightSet;



@javax.jws.WebService (endpointInterface="com.ncl.assisted_search.services.AssistedSearchServicesPortType", targetNamespace="http://assisted-search.ncl.com/services/", serviceName="AssistedSearchServices", portName="AssistedSearchServicesSOAP")
public class AssistedSearchServicesSOAPImpl{

	 private final static Logger LOGGER = Logger.getLogger(GetAssistedSearchRecommendationResponse.class.getName());
	 
    public GetAssistedSearchRecommendationResponse getAssistedSearchRecommendations(GetAssistedSearchRecommendation getAssistedSearchRecommendation) {
        // TODO Auto-generated method stub

		Properties properties = new Properties();
		try {
			InputStream inputStream = this.getClass().getClassLoader()
			.getResourceAsStream("app.properties");
			LOGGER.finest("attempting create ne properties() object");
			LOGGER.finest("attempting to load the inputStream into the properties Object");
			properties.load(inputStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	
/*** punch out inboundXML **/
// TODO need to transition to logger
		LOGGER.finest("xmlInbound = " + getAssistedSearchRecommendation.getAccomodationSet());
		LOGGER.finest("xmlInbound = " + getAssistedSearchRecommendation.getStateroomLocation());
		LOGGER.finest("xmlInbound = " + getAssistedSearchRecommendation.getStateroomView());
		LOGGER.finest("xmlInbound = " + getAssistedSearchRecommendation.getStateroomSpace());

    	
    	/*** punch out inboundXML **/

    	UserInput usrInp = new UserInput();
    	
    	// Set up dates #1
		usrInp._sailDateRangeList.clear();
    	try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        	FlexDatesSet flDtSt = getAssistedSearchRecommendation.getFlexDatesSet();
        	if(flDtSt != null)
        	{
                Date enddt = df.parse(flDtSt.getEndDate());
                Date startdt = df.parse(flDtSt.getStartDate());
                
                LOGGER.fine("enddt = " + enddt.toString() + "startdt = " + startdt.toString());
                
        		SailDateRange sdr=new SailDateRange(startdt,enddt);
        		
        		LOGGER.fine("sdr = " + sdr._earliestDate.toString() + " -- " + sdr._latestDate.toString());

        		usrInp._sailDateRangeList.add(sdr);
        	}
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
		// Set up - guestCount #2
		usrInp._numberOfGuests = 0;
		BigInteger guests = getAssistedSearchRecommendation.getNumberOfGuests();
		if(guests != null)
		{
			usrInp._numberOfGuests = guests.intValue();
		}
		
		// Set up - DiscTypes #3 
		usrInp._discountTypeList.clear();
		
		// leverage the "static string style" in future return to the "discTypes" 
		PromoCodeSet promoCodes = getAssistedSearchRecommendation.getPromoCodeSet();
		if(promoCodes != null)
		{
			usrInp._discountTypeList.addAll(promoCodes.getPromoCode());
		}
		
		// Set up - UserWeight #4 
		usrInp._userWeight = null;
		UserWeightSet usrWghtSet = getAssistedSearchRecommendation.getUserWeightSet();
		if(usrWghtSet != null)
		{
			UserWeight wsUsrWght = new UserWeight(usrWghtSet.getWc1(),usrWghtSet.getWc2(),usrWghtSet.getWc3(),usrWghtSet.getWc4(),usrWghtSet.getWc5());
			usrInp._userWeight = wsUsrWght;
		}
		
		// Set up - RecommReqLimit #5
		usrInp._recommendationRequestLimit = null;
		RecommReqLimitSet recommReqSet = getAssistedSearchRecommendation.getRecommReqLimitSet();
		if(recommReqSet != null)
		{
			RecommendationRequestLimit wsRecommReqLimit = 
				new RecommendationRequestLimit(recommReqSet.getNumPackageRecomm(), recommReqSet.getNumThingsToDo(),recommReqSet.getNumDest(),recommReqSet.getNumStateRoomTypes());
			usrInp._recommendationRequestLimit = wsRecommReqLimit;
		}
		
		// Set up - AccommPrefs #6
		usrInp._accommodationPreference.clear();
		String statermLoc = getAssistedSearchRecommendation.getStateroomLocation();
    	if(statermLoc != null)
    	{
    		usrInp._accommodationPreference.add(new AccommodationPreference(properties.getProperty("AccomPref.LOCATION"), statermLoc));
    	}
		String statermSpace = getAssistedSearchRecommendation.getStateroomSpace();
    	if(statermSpace != null)
    	{
    		usrInp._accommodationPreference.add(new AccommodationPreference(properties.getProperty("AccomPref.SPACE"), statermSpace));
    	}
		String statermView = getAssistedSearchRecommendation.getStateroomView();
    	if(statermView != null)
    	{
    		usrInp._accommodationPreference.add(new AccommodationPreference(properties.getProperty("AccomPref.VIEW"), statermView));
    	}

		
		// Set up - InterestList #7
		usrInp._interestList.clear();

    	InterestSet intSet = getAssistedSearchRecommendation.getInterestSet();
    	if(intSet != null)
    	{
    		usrInp._interestList.addAll(intSet.getInterest());
    	}
		
		// Set up - ThingsTodo #8
		usrInp._thingsToDoList.clear();

		ThingToDoSet thingsSet = getAssistedSearchRecommendation.getThingToDoSet();
    	if(thingsSet != null)
    	{
    		usrInp._thingsToDoList.addAll(thingsSet.getThingToDo());
    	}

		// Set up - Meta #9
    	
		// Set up - ThingsTodo #8
		usrInp._metaList.clear();

    	AccomodationSet accomSet = getAssistedSearchRecommendation.getAccomodationSet();
    	if(accomSet != null)
    	{
    		usrInp._metaList.addAll(accomSet.getAccomodation());
    	}
    	
		// Set up - Destination #10
		usrInp._destinationList.clear();

    	DestinationSet destSet = getAssistedSearchRecommendation.getDestinationSet();
    	if(destSet != null)
    	{
    		usrInp._destinationList.addAll(destSet.getDestination());
    	}
		
		// Set up - DepartPort #11
		usrInp._departingPortList.clear();

    	DeparturePortSet departSet = getAssistedSearchRecommendation.getDeparturePortSet();
    	if(destSet != null)
    	{
    		usrInp._departingPortList.addAll(departSet.getDeparturePort());
    	}

		// Set up - Duration #12
		usrInp._durationRangeList.clear();

    	DurationSet durationSet = getAssistedSearchRecommendation.getDurationSet();
    	if(durationSet != null)
    	{
    		Iterator<String> durIter = durationSet.getDuration().iterator();
    		while(durIter.hasNext())
    		{
    			String fullDurVal = durIter.next();
    			String[] durs = fullDurVal.split(","); 
    			int firstParm = 0;
    			int lastParm = -1;

    			if(durs[0] != null)
    			{
        			firstParm = Integer.parseInt(durs[0]);
    			}
    			if(durs[1] != null)
    			{
        			lastParm = Integer.parseInt(durs[1]);
    			}
    			DurationRange durRnge = new DurationRange(firstParm, lastParm);
        		usrInp._durationRangeList.add(durRnge);
    		}
    	}
    	
		// Set up - InterestList #13
		usrInp._priceRangeList.clear();

    	PriceRangeSet priceSet = getAssistedSearchRecommendation.getPriceRangeSet();
    	if(priceSet != null)
    	{
    		Iterator<String> priceIter = priceSet.getPriceRange().iterator();
    		while(priceIter.hasNext())
    		{
    			String fullPriceVal = priceIter.next();
    			String[] prices = fullPriceVal.split(","); 
    			double firstParm = 0;
    			double lastParm = -1;

    			if(prices[0] != null)
    			{
        			firstParm = Double.parseDouble(prices[0]);
    			}
    			if(prices[1] != null)
    			{
        			lastParm = Double.parseDouble(prices[1]);
    			}
    			PriceRange priceRnge = new PriceRange(firstParm, lastParm);
        		usrInp._priceRangeList.add(priceRnge);
    		}
    	}

    	LOGGER.finest("usrInp - set prior to Recommender request");
    	
    	GetAssistedSearchRecommendationResponse resp = new GetAssistedSearchRecommendationResponse();

    	try {
    		OptimizationRequest ORequest = new OptimizationRequest();
    		OptimizationResponseData rspnData = ORequest.Recommender(usrInp);
    		//OptimizationResponseData rspnData = OptimizationRequest.Recommender(usrInp);
			
			// Set up CruisePkg Response #1 
			Iterator cruisePkgRespIter = rspnData.return_cruisePackageData().iterator(); 
			ArrayList<com.ncl.assisted_search.messages.CruisePackageRecommendation> cruisePkgRecommList = new ArrayList();
			while(cruisePkgRespIter.hasNext())
			{

				com.ncl.assisted_search.io.CruisePackageRecommendation cruisePkgCPLEX = (com.ncl.assisted_search.io.CruisePackageRecommendation) cruisePkgRespIter.next();
				
				com.ncl.assisted_search.messages.CruisePackageRecommendation cruisePkgWS = new com.ncl.assisted_search.messages.CruisePackageRecommendation();

				cruisePkgWS.setSailID(cruisePkgCPLEX._SailID);
				cruisePkgWS.setPackageID(cruisePkgCPLEX._PackageID);
				cruisePkgWS.setDateID(cruisePkgCPLEX._DateID);
				cruisePkgWS.setItineraryID(cruisePkgCPLEX._itineraryID);
				cruisePkgWS.setDestination(cruisePkgCPLEX._Destination);
				cruisePkgWS.setRoomType(cruisePkgCPLEX._roomType);
				cruisePkgWS.setMeta(cruisePkgCPLEX._Meta);
				cruisePkgWS.setInterestPrefContrib(cruisePkgCPLEX._interestPrefContribution);
				cruisePkgWS.setAccomodationPrefContrib(cruisePkgCPLEX._accomodationPrefContribution);
				cruisePkgWS.setRoomViolationValue(cruisePkgCPLEX._roomViolationValue);
				cruisePkgWS.setThingsToDoViolationValue(cruisePkgCPLEX._thingsToDoViolationValue);
				cruisePkgWS.setPriceViolationValue(cruisePkgCPLEX._priceViolationValue);
				cruisePkgWS.setDateViolationValue(cruisePkgCPLEX._dateViolationValue);
				cruisePkgWS.setDurationViolationValue(cruisePkgCPLEX._durationViolationValue);
				cruisePkgWS.setDestinationViolationValue(cruisePkgCPLEX._destinationViolationValue);
				cruisePkgWS.setPortViolationValue(cruisePkgCPLEX._portViolationValue);
				cruisePkgWS.setDrupalWeightContrib(cruisePkgCPLEX._drupalWeightContribution);
				cruisePkgWS.setIsRecommended(cruisePkgCPLEX._isRecommended);
				cruisePkgWS.setPricePerGuest(cruisePkgCPLEX._pricePerGuest);
				
				cruisePkgRecommList.add(cruisePkgWS);
				
				LOGGER.finest("cruisPkgWs = aprcon = " + cruisePkgWS.getAccomodationPrefContrib());
				LOGGER.finest("cruisPkgWs = setDateID = " + cruisePkgWS.getDateID());
				LOGGER.finest("cruisPkgWs = setDateViolationValue = " + cruisePkgWS.getDateViolationValue());
				LOGGER.finest("cruisPkgWs = setDestination = " + cruisePkgWS.getDestination());
				LOGGER.finest("cruisPkgWs = setDestinationViolationValue = " + cruisePkgWS.getDestinationViolationValue());
				LOGGER.finest("cruisPkgWs = setDrupalWeightContrib = " + cruisePkgWS.getDrupalWeightContrib());
				LOGGER.finest("cruisPkgWs = setDurationViolationValue = " + cruisePkgWS.getDurationViolationValue());
				LOGGER.finest("cruisPkgWs = setInterestPrefContrib = " + cruisePkgWS.getInterestPrefContrib());
				LOGGER.finest("cruisPkgWs = setIsRecommended = " + cruisePkgWS.getIsRecommended());
				LOGGER.finest("cruisPkgWs = setItineraryID = " + cruisePkgWS.getItineraryID());
				LOGGER.finest("cruisPkgWs = setMeta = " + cruisePkgWS.getMeta());
				LOGGER.finest("cruisPkgWs = setPackageID = " + cruisePkgWS.getPackageID());
				LOGGER.finest("cruisPkgWs = setPortViolationValue = " + cruisePkgWS.getPortViolationValue());
				LOGGER.finest("cruisPkgWs = setPricePerGuest = " + cruisePkgWS.getPricePerGuest());
				LOGGER.finest("cruisPkgWs = setPriceViolationValue = " + cruisePkgWS.getPriceViolationValue());
				LOGGER.finest("cruisPkgWs = setRoomType = " + cruisePkgWS.getRoomType());
				LOGGER.finest("cruisPkgWs = setRoomViolationValue = " + cruisePkgWS.getRoomViolationValue());
				LOGGER.finest("cruisPkgWs = setSailID = " + cruisePkgWS.getSailID());
				LOGGER.finest("cruisPkgWs = setThingsToDoViolationValue = " + cruisePkgWS.getThingsToDoViolationValue());

			}
			if(cruisePkgRecommList!=null && !cruisePkgRecommList.isEmpty())
			{
				resp.getCruisePackageRecommendation().addAll(cruisePkgRecommList);
			}
			
			// Set up Constraint Response #2
			Iterator constraintRespIter = rspnData.return_constraintViolationData().iterator(); 
			ArrayList<com.ncl.assisted_search.messages.ConstraintViolation> constraintViolationList = new ArrayList();
			while(constraintRespIter.hasNext())
			{
				com.ncl.assisted_search.io.ConstraintViolation constraintCPLEX = (com.ncl.assisted_search.io.ConstraintViolation) constraintRespIter.next();
				
				com.ncl.assisted_search.messages.ConstraintViolation constraintWS = new com.ncl.assisted_search.messages.ConstraintViolation();
				
				constraintWS.setPackageLimitViolation(constraintCPLEX._packageLimitViolation);
				constraintWS.setThingsToDoLimitViolation(constraintCPLEX._thingsToDoLimitViolation);
				constraintWS.setDestinationViolation(constraintCPLEX._destinationLimitViolation);
				constraintWS.setRoomTypeLimitViolation(constraintCPLEX._roomTypeLimitViolation);
				constraintWS.setItineraryUniquenessViolation(constraintCPLEX._itineraryUniquenessViolation);
				
				LOGGER.finest("constraintWS = constraintWS.setPackageLimitViolation" + constraintWS.getPackageLimitViolation());
				LOGGER.finest("constraintWS = constraintWS.setThingsToDoLimitViolation" + constraintWS.getThingsToDoLimitViolation());
				LOGGER.finest("constraintWS = constraintWS.setDestinationViolation" + constraintWS.getDestinationViolation());
				LOGGER.finest("constraintWS = constraintWS.setRoomTypeLimitViolation" + constraintWS.getRoomTypeLimitViolation());
				LOGGER.finest("constraintWS = constraintWS.setItineraryUniquenessViolation" + constraintWS.getItineraryUniquenessViolation());
								
				constraintViolationList.add(constraintWS);

			}
			if(constraintViolationList!=null && !constraintViolationList.isEmpty())
			{
				resp.getConstraintViolation().addAll(constraintViolationList);
			}

			
			// Set up ThingsToDo Response #3 
			Iterator thingsRespIter = rspnData.return_ttdrData().iterator(); 
			ArrayList<com.ncl.assisted_search.messages.ThingsToDoRecommendation> thingsWSList = new ArrayList();
			while(thingsRespIter.hasNext())
			{
				com.ncl.assisted_search.io.ThingsToDoRecommendation thingsCPLEX = (com.ncl.assisted_search.io.ThingsToDoRecommendation) thingsRespIter.next();
				
				com.ncl.assisted_search.messages.ThingsToDoRecommendation thingsWS = new com.ncl.assisted_search.messages.ThingsToDoRecommendation();
				
				thingsWS.setDrupalWeightContrib(thingsCPLEX._drupalWeightContribution);
				thingsWS.setInterestID(thingsCPLEX._interestID);
				thingsWS.setInterestPrefContrib(thingsCPLEX._interestPrefContribution);
				thingsWS.setIsRecommended(thingsCPLEX._isRecommended);
				thingsWS.setThingsToDoID(thingsCPLEX._thingsToDoID);
				
				LOGGER.finest("thinsgWS = thingsWS.setDrupalWeightContrib = " + thingsWS.getDrupalWeightContrib());
				LOGGER.finest("thinsgWS = thingsWS.setInterestID = " + thingsWS.getInterestID());
				LOGGER.finest("thinsgWS = thingsWS.setInterestPrefContrib = " + thingsWS.getInterestPrefContrib());
				LOGGER.finest("thinsgWS = thingsWS.setIsRecommended = " + thingsWS.getIsRecommended());
				LOGGER.finest("thinsgWS = thingsWS.setThingsToDoID = " + thingsWS.getThingsToDoID());
				
				
				thingsWSList.add(thingsWS);

			}
			if(thingsWSList!=null && !thingsWSList.isEmpty())
			{
				resp.getThingsToDoRecommendation().addAll(thingsWSList);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return resp;
    }
}