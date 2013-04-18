/*********************************************
 * OPL 12.4 Model
 * Author: Chuck Teeter    
 * Optimal Solutions, Inc
 * Creation Date: Dec 18, 2012 at 1:13:20 PM
 *********************************************/
  include "NCLRecommender_odm.mod";
   // The default is to use CPLEX, but it's good modeling practice state explicitly.  The
   // other option is to specify CP.   
  using CPLEX;
   
  float ViolationWeight=1000;

   execute INITIALIZE_RANGE
   {  
   	   var index=0;
   	   for(var pr in Input_PricePerGuestRange)
   	   {
   	     index=1;
   	     break;
       } 
       if(index==0){
          Input_PricePerGuestRange.add(0,100000);  	     	     
          writeln("Default Price Range: 0, 100000");
       }  
   	   index=0;
   	   for(var pr1 in Input_SailDateRange)
   	   {
   	     index=1;
   	     break;
       } 
       if(index==0){
          Input_SailDateRange.add(-100000,100000);  	     	     
          writeln("Sail Date Range: -100000, 100000");
       }                
       index=0;
   	   for(var pr2 in Input_DurationRange)
   	   {
   	     index=1;
   	     break;
       } 
       if(index==0){
          Input_DurationRange.add(0,100000);  	     	     
          writeln("Duration Range: 0, 100000");
       }
   }       
   // Use Cases referenced below
   // Use Case = 1: Recommend Top Stateroom Types (Web App Step 2)
   // Use Case = 2: Recommend Top Things to do (Web App Step 3)
   // Use Case = 3: Recommend Top Destinations (Web App Step 5)
   // Use Case = 4: Recommend Top Packages (Web App Step 6)
   
   tuple Meta
   {
     string Meta;
   }         			 
   
   tuple Destination
   {
     string Destination;
   }      							  
   
   tuple DepartingPort
   {
     string DepartingPort;
   }     
   
   tuple Itinerary
   {
     string ItineraryID;
   }        
   
   // Other model parameters
   int SelectedUseCase;				// The use case as determined from Input_RecommendationRequestLimit
   int RequestedRecommendations;	// The number of recommendations requested
   
   int ModelID;						// This value is determined by the value of 
   									// ConstraintViolationUpperBound that is passed in
   									// and is used to perform model-specific
   									// constraint logic
   int UnBounded;//=0 if ModelID=0 or 2 for bounded run; =1 of ModelID=1 for unbounded Run

  execute WRITE_FP{
  	writeln("FP..."); 
  } 
	      
  tuple CruisePackagePrice0 {
	  int CruisePackageID;
	  string SailID;
	  string PackageID;
	  string DateID;
	  int Date;
	  string ShipID;
	  string ItineraryID;
	  string Destination;
	  string DepartingPort;
	  string RoomType;
	  string Meta;
	  float Duration;
	  float DrupalWeight;
	  float PricePerGuest;  
  };
  {CruisePackagePrice0} Input_CruisePackagePrice1={
    <i.CruisePackageID,i.SailID,i.PackageID,i.DateID,i.Date,i.ShipID,i.ItineraryID,i.Destination,i.DepartingPort,i.RoomType,i.Meta,i.Duration,i.DrupalWeight,p.PricePerGuest>
	  |i in Input_CruisePackage, n in Input_NumberOfGuests: i.CabinCapacity>=n.NumberOfGuests, p in Input_CruisePackagePrice: i.CruisePackageID==p.CruisePackageID};
  /*
  int PkgCount = 0;//sum(i in Input_CruisePackagePrice1) 1;  
  execute INIT_PKG{
    //for(var r in Input_SailDateRange) writeln("Min="+r.Min+ " Max="+r.Max);
  	writeln("Pkg="+PkgCount);
  }
  */    
  {DestinationRange} Input_Destination0={<i.Destination>|i in Input_CruisePackagePrice1};
   
  //Destinations not in package
  {DestinationRange} Input_DestinationNotInPackage={<d2.Destination> | d2 in Input_InterestDestinationScore,  p in Input_RecommendationRequestLimit: p.NumDestinations>0.1}
   				diff {<d.Destination> | d in Input_Destination0};
   				
  {DestinationRange} Input_Destinations={<i.Destination>|i in Input_Destination0}
  						union {<i.Destination>|i in Input_DestinationNotInPackage};


  {MetaRange} Input_Meta0={<i.Meta>|i in Input_CruisePackagePrice1};
   
  //Meta not in package
  {MetaRange} Input_MetaNotInPackage={<d2.Meta> | d2 in Input_InterestMetaScore,  p in Input_RecommendationRequestLimit: p.NumStateRoomTypes>0.1}
   				diff {<d.Meta> | d in Input_Meta0};
   				
  {MetaRange} Input_Metas={<i.Meta>|i in Input_Meta0}
  						union {<i.Meta>|i in Input_MetaNotInPackage};
  						
  {CruisePackagePrice0} Input_CruisePackagePrice0={
    <i.CruisePackageID,i.SailID,i.PackageID,i.DateID,i.Date,i.ShipID,i.ItineraryID,i.Destination,i.DepartingPort,i.RoomType,i.Meta,i.Duration,i.DrupalWeight,i.PricePerGuest>
	  |i in Input_CruisePackagePrice1}
	union
	{<0,"NONE","NONE","NONE",999,"NONE",d.Destination,d.Destination,"NONE","NONE","NONE",999,0,99998> | d in Input_DestinationNotInPackage}  
	union
	{<0,"NONE","NONE","NONE",999,"NONE",d.Meta,"NONE","NONE","NONE",d.Meta,999,0,99998> | d in Input_MetaNotInPackage}; 	
      
  float InterestCount = maxl(sum(i in Input_InterestRange) 1,1);  

  {Meta} Metas = { <i.Meta> | i in Input_CruisePackage}
   					union
   				  { <i.Meta> | i in Input_InterestMetaScore};   
        
  {Meta} MetaOutOfRange = {<m.Meta> | m in Metas, e in Input_MetaRange}//case when Meta range is empty 
   							diff
   						   {<e.Meta> | e in Input_MetaRange};

  {Itinerary} Input_Itinerary0={<i.ItineraryID>|i in Input_CruisePackagePrice0};
  
  float ThingsToDoCount=maxl(sum(t in Input_ThingsToDoRange) 1,1);
  {ThingsToDo} ItineraryTTD={<i.ItineraryID,t1.ThingsToDoID>|i in Input_Itinerary0, t1 in Input_ThingsToDoRange,
  					t2 in Input_ThingsToDo:t2.ItineraryID==i.ItineraryID && t2.ThingsToDoID==t1.ThingsToDoID};

  {Destination} Destinations0 = { <i.Destination> | i in Input_CruisePackagePrice0 };
  {Destination} DestinationOutOfRange = {<m.Destination> | m in Destinations0,  e in Input_DestinationRange}//case when Destination range is empty 
   							diff
   						   {<e.Destination> | e in Input_DestinationRange};   
  {DepartingPort} DepartingPorts0 = {<m.DepartingPort> | m in Input_CruisePackagePrice0};
  {DepartingPort} DepartingPortOutOfRange={<m.DepartingPort> | m in DepartingPorts0, e in Input_PortRange}
   											diff
  											{<e.DepartingPort> | e in Input_PortRange};
  tuple CruisePackageScore
  {
    int CruisePackageID;
    float Value;
    float Score;
  }    

  {CruisePackageScore} CruisePackagePriceScore0=
  	{<i.CruisePackageID,i.PricePerGuest,0>|i in Input_CruisePackagePrice0, p in Input_PricePerGuestRange: i.PricePerGuest>=p.Min && i.PricePerGuest<=p.Max};
  tuple CruisePackage_ID
  {
    int CruisePackageID;
    float Value;
  }  
  {CruisePackage_ID} CruisePackageID0={<i.CruisePackageID,i.PricePerGuest>|i in Input_CruisePackagePrice0}
  						diff
						{<i.CruisePackageID,i.Value>|i in CruisePackagePriceScore0};  
  {CruisePackageScore} CruisePackagePriceScore1={<i.CruisePackageID,i.Value,min(p in Input_PricePerGuestRange) minl(abs(i.Value-p.Min),abs(i.Value-p.Max))>|i in CruisePackageID0}; 						  
  {CruisePackageScore} CruisePackagePriceScore2={<i.CruisePackageID,i.Value,sum(b in Input_BusPenalty:b.PenType=="PRICE" && i.Score>b.DiffMin && i.Score<=b.DiffMax) b.PenWght>|i in CruisePackagePriceScore1};
  {CruisePackageScore} CruisePackagePriceScore={<i.CruisePackageID,i.Value,i.Score>|i in CruisePackagePriceScore0} union {<i.CruisePackageID,i.Value,i.Score>|i in CruisePackagePriceScore2};
  
  {CruisePackageScore} CruisePackageSailDateScore0=
  {<i.CruisePackageID,i.Date,0>|i in Input_CruisePackagePrice0, p in Input_SailDateRange: i.Date>=p.Min && i.Date<=p.Max};

  {CruisePackage_ID} CruisePackageDateID0={<i.CruisePackageID,i.Date>|i in Input_CruisePackagePrice0}
  						diff
						{<i.CruisePackageID,i.Value>|i in CruisePackageSailDateScore0};  
  {CruisePackageScore} CruisePackageSailDateScore1={<i.CruisePackageID,i.Value,min(p in Input_SailDateRange) minl(abs(i.Value-p.Min),abs(i.Value-p.Max))>|i in CruisePackageDateID0}; 						  
  {CruisePackageScore} CruisePackageSailDateScore2={<i.CruisePackageID,i.Value,sum(b in Input_BusPenalty:b.PenType=="SAILDATE" && i.Score>b.DiffMin && i.Score<=b.DiffMax) b.PenWght>|i in CruisePackageSailDateScore1};
  {CruisePackageScore} CruisePackageSailDateScore={<i.CruisePackageID,i.Value,i.Score>|i in CruisePackageSailDateScore0} union {<i.CruisePackageID,i.Value,i.Score>|i in CruisePackageSailDateScore2};

  {CruisePackageScore} CruisePackageDurationScore0=
  {<i.CruisePackageID,i.Duration,0>|i in Input_CruisePackagePrice0, p in Input_DurationRange: i.Duration>=p.Min && i.Duration<=p.Max};  
  {CruisePackage_ID} CruisePackageDurationID0={<i.CruisePackageID,i.Duration>|i in Input_CruisePackagePrice0}
  						diff
						{<i.CruisePackageID,i.Value>|i in CruisePackageDurationScore0};  
  {CruisePackageScore} CruisePackageDurationScore1={<i.CruisePackageID,i.Value,min(p in Input_DurationRange) minl(abs(i.Value-p.Min),abs(i.Value-p.Max))>|i in CruisePackageDurationID0}; 						  
  {CruisePackageScore} CruisePackageDurationScore2={<i.CruisePackageID,i.Value,sum(b in Input_BusPenalty:b.PenType=="DURATION" && i.Score>b.DiffMin && i.Score<=b.DiffMax) b.PenWght>|i in CruisePackageDurationScore1};
  {CruisePackageScore} CruisePackageDurationScore={<i.CruisePackageID,i.Value,i.Score>|i in CruisePackageDurationScore0} union {<i.CruisePackageID,i.Value,i.Score>|i in CruisePackageDurationScore2};
		      
   tuple FPFC{
	  int CruisePackageID;
	  string SailID;
	  string PackageID;
	  string DateID;
	  int Date;
	  string ShipID;
	  string ItineraryID;
	  string Destination;
	  string DepartingPort;
	  string RoomType;
	  string Meta;
	  float Duration;
	  float DrupalWeight;
	  float PricePerGuest;
      float FP1;
      float FP211;
      float FP212;
      float FP213;
      float FC1;
      float FC2;
      float FC3;
      float FC41;
      float FC42;
      float FC5;
      float FC6;
  }
  {FPFC} CruisePackage_FC1//CruisePackage_FP2130
  			={<i.CruisePackageID,i.SailID,i.PackageID,i.DateID,i.Date,i.ShipID,i.ItineraryID,i.Destination,i.DepartingPort,i.RoomType,i.Meta,i.Duration,i.DrupalWeight,i.PricePerGuest,//i.FP1,i.FP211,i.FP212,i.FP213,
  		  		sum(a in Input_AccommodationPreferenceScore: a.ShipID==i.ShipID && a.RoomType==i.RoomType, 
  					c in Input_AccommodationPreference: c.Preference==a.Preference && c.PreferenceType==a.PreferenceType) a.Score/maxl(1,sum(c in Input_AccommodationPreference) 1),
	  			sum(n in Input_InterestRange, m in Input_InterestMetaScore: n.InterestID==m.InterestID && 
	   				i.ShipID==m.ShipID && i.Meta==m.Meta && i.Date>=m.StartDate && i.Date<=m.EndDate) m.Score/InterestCount,
	  			sum(n in Input_InterestRange, m in Input_InterestDestinationScore: n.InterestID==m.InterestID && i.Destination==m.Destination && ((i.Date>=m.StartDate && i.Date<=m.EndDate) || i.ItineraryID==i.Destination )) m.Score/InterestCount,
	  			sum(n in Input_InterestRange, m in Input_InterestShipScore: n.InterestID==m.InterestID && i.ShipID==m.ShipID && i.Date>=m.StartDate && i.Date<=m.EndDate) m.Score/InterestCount,	  						
 				sum(m in MetaOutOfRange: m.Meta==i.Meta) 1,
 				1-(sum(t in ItineraryTTD:t.ItineraryID==i.ItineraryID) 1)/ThingsToDoCount,
 				sum(p in CruisePackagePriceScore: i.CruisePackageID==p.CruisePackageID) p.Score,
 				sum(p in CruisePackageSailDateScore: i.CruisePackageID==p.CruisePackageID) p.Score,
 				sum(p in CruisePackageDurationScore: i.CruisePackageID==p.CruisePackageID) p.Score, 				
 				sum(m in DestinationOutOfRange: m.Destination==i.Destination) 1,
 				sum(m in DepartingPortOutOfRange: m.DepartingPort==i.DepartingPort) 1>										
  				| i in Input_CruisePackagePrice0};	
  //|i in CruisePackage_FP213};
          	  
   tuple FP_FC{
	  int CruisePackageID;
	  string SailID;
	  string PackageID;
	  string DateID;
	  int Date;
	  string ShipID;
	  string ItineraryID;
	  string Destination;
	  string DepartingPort;
	  string RoomType;
	  string Meta;
	  float Duration;
	  float DrupalWeight;
	  float PricePerGuest;
      float FP1;
      float FP211;
      float FP212;
      float FP213;
      float FC1;
      float FC2;
      float FC3;
      float FC41;
      float FC42;
      float FC5; 
      float FC6;
      float Score;
  }
   
  {FP_FC} CruisePackage_FP_FC//CruisePackage_FP2130
  			={<i.CruisePackageID,i.SailID,i.PackageID,i.DateID,i.Date,i.ShipID,i.ItineraryID,i.Destination,i.DepartingPort,i.RoomType,i.Meta,i.Duration,i.DrupalWeight,i.PricePerGuest,//i.FP1,i.FP211,i.FP212,i.FP213,
  		  		i.FP1,i.FP211,i.FP212,i.FP213,i.FC1,i.FC2,i.FC3,i.FC41,i.FC42,i.FC5,i.FC6,
  		  		p.CustomerAccommodationPriority*p.BusinessAccommodationPriority*i.FP1+
  		  		p.CustomerThingsToDoPriority*p.BusinessThingsToDoPriority*(i.FP211+i.FP212+i.FP213)/3.0-
				ViolationWeight * p.CustomerAccommodationPriority*p.BusinessAccommodationPriority*i.FC1-
				ViolationWeight * p.CustomerThingsToDoPriority*p.BusinessThingsToDoPriority * i.FC2-
				ViolationWeight * p.CustomerPricePriority*p.BusinessPricePriority * i.FC3-			
				ViolationWeight * p.CustomerTimePriority*p.BusinessTimePriority * (i.FC41+i.FC42)/2.0-					
				ViolationWeight * p.CustomerDestinationPriority*p.BusinessDestinationPriority * i.FC5-
				ViolationWeight * p.CustomerDestinationPriority*p.BusinessDepartingPriority * i.FC6							
				>  		  							
  				| i in CruisePackage_FC1,p in Input_Parameters};	
  
  tuple ItineraryMaxScore{
	  string ItineraryID;
      float Score;
  }      	
  {ItineraryMaxScore} Input_ItineraryMaxScore={<i.ItineraryID,max(i2 in CruisePackage_FP_FC: i.ItineraryID==i2.ItineraryID) i2.Score>
 						 |i in Input_Itinerary0, r in Input_RecommendationRequestLimit: r.NumPackageRecommendations>0.1 || r.NumThingsToDo>0.1};
  
  tuple ItineraryMaxScoreID{
	  string ItineraryID;	
      int CruisePackageID;
  }  
  {ItineraryMaxScoreID} Input_ItineraryMaxScoreID={<i.ItineraryID,min(i2 in CruisePackage_FP_FC: i.ItineraryID==i2.ItineraryID && i.Score<=i2.Score) i2.CruisePackageID>|i in Input_ItineraryMaxScore};  

  tuple DestinationMaxScore{
	  string Destination;
      float Score;
  }      	
  {DestinationMaxScore} Input_DestinationMaxScore={<i.Destination,max(i2 in CruisePackage_FP_FC: i.Destination==i2.Destination) i2.Score>
 						 |i in Input_Destinations, r in Input_RecommendationRequestLimit: r.NumDestinations>0.1 };
  
  tuple DestinationMaxScoreID{
	  string Destination;	
      int CruisePackageID;
  }  
  {DestinationMaxScoreID} Input_DestinationMaxScoreID={<i.Destination,min(i2 in CruisePackage_FP_FC: i.Destination==i2.Destination && i.Score<=i2.Score) i2.CruisePackageID>|i in Input_DestinationMaxScore};  

  tuple MetaMaxScore{
	  string Meta;
      float Score;
  }      	

  {MetaMaxScore} Input_MetaMaxScore={<i.Meta,max(i2 in CruisePackage_FP_FC: i.Meta==i2.Meta) i2.Score>
 						 |i in Input_Metas, r in Input_RecommendationRequestLimit: r.NumStateRoomTypes>0.1 };
  
  tuple MetaMaxScoreID{
	  string Meta;	
      int CruisePackageID;
  }  
  {MetaMaxScoreID} Input_MetaMaxScoreID={<i.Meta,min(i2 in CruisePackage_FP_FC: i.Meta==i2.Meta && i.Score<=i2.Score) i2.CruisePackageID>|i in Input_MetaMaxScore};  


  {FP_FC} Input_CruisePackage2//CruisePackage_FP2130
  			={<i.CruisePackageID,i.SailID,i.PackageID,i.DateID,i.Date,i.ShipID,i.ItineraryID,i.Destination,i.DepartingPort,i.RoomType,i.Meta,i.Duration,i.DrupalWeight,i.PricePerGuest,//i.FP1,i.FP211,i.FP212,i.FP213,
  		  		i.FP1,i.FP211,i.FP212,i.FP213,i.FC1,i.FC2,i.FC3,i.FC41,i.FC42,i.FC5,i.FC6,i.Score>  		  							
  				| i in CruisePackage_FP_FC,p in Input_ItineraryMaxScoreID: i.ItineraryID==p.ItineraryID && i.CruisePackageID==p.CruisePackageID}
  				union
				{<i.CruisePackageID,i.SailID,i.PackageID,i.DateID,i.Date,i.ShipID,i.ItineraryID,i.Destination,i.DepartingPort,i.RoomType,i.Meta,i.Duration,i.DrupalWeight,i.PricePerGuest,//i.FP1,i.FP211,i.FP212,i.FP213,
  		  		i.FP1,i.FP211,i.FP212,i.FP213,i.FC1,i.FC2,i.FC3,i.FC41,i.FC42,i.FC5,i.FC6,i.Score>  		  							
  				| i in CruisePackage_FP_FC,p in Input_DestinationMaxScoreID: i.Destination==p.Destination && i.CruisePackageID==p.CruisePackageID}  					
  				union
				{<i.CruisePackageID,i.SailID,i.PackageID,i.DateID,i.Date,i.ShipID,i.ItineraryID,i.Destination,i.DepartingPort,i.RoomType,i.Meta,i.Duration,i.DrupalWeight,i.PricePerGuest,//i.FP1,i.FP211,i.FP212,i.FP213,
  		  		i.FP1,i.FP211,i.FP212,i.FP213,i.FC1,i.FC2,i.FC3,i.FC41,i.FC42,i.FC5,i.FC6,i.Score>  		  							
  				| i in CruisePackage_FP_FC,p in Input_MetaMaxScoreID: i.Meta==p.Meta && i.CruisePackageID==p.CruisePackageID};	
      				   
  int ItnCount = 0;//sum(i in Input_CruisePackage2) 1;  
  execute END_FP{
    //for(var r in Input_SailDateRange) writeln("Min="+r.Min+ " Max="+r.Max);
  	//writeln("FP Completed: Itn="+ItnCount);
  	//for(var i in Input_CruisePackage2)
  	//if(i.Destination=="Destination1")
  	//if(i.Meta=="Meta1")
  	//writeln("pkgid="+i.CruisePackageID+" Itn="+i.ItineraryID+" PORT="+i.DepartingPort+" Room="+i.RoomType+" Destination="+i.Destination+" ship="+i.ShipID+" meta="+i.Meta+" FP1="+i.FP1+" FP211="+i.FP211+" FP212="+i.FP212+" FP213="+i.FP213+" FC1="+i.FC1+
  	//" FC2="+i.FC2+" price="+i.PricePerGuest+" FC3="+i.FC3+" Date="+i.Date+" FC41="+i.FC41+" Duration="+i.Duration+" FC42="+i.FC42+" FC5="+i.FC5+" FC6="+i.FC6+" Score="+i.Score);
  }  	

   tuple ThingsToDoScore
   {
     string ThingsToDoID;
     float Score;
   }        
   //TTD not in Itinerary
   {ThingsToDoScore} Input_ThingsToDo3={<d2.ThingsToDoID,0> | d2 in Input_InterestThingsToDo,  p in Input_RecommendationRequestLimit: p.NumThingsToDo>0.1}
   				diff {<d.ThingsToDoID,0> | d in Input_ThingsToDo};
   //{ThingsToDoScore} Input_ThingsToDo4={<d2.ThingsToDo,0> | d in Input_InterestThingsToDoScore, d2 in Input_ThingsToDo3: d.ThingsToDo==d2.ThingsToDo, i in Input_InterestRange: i.InterestID==d.InterestID};			    
   //TTD not in Itinerary, but in Interest Range
   {ThingsToDoScore} Input_ThingsToDo0={<d2.ThingsToDoID,0.5> |  d2 in Input_ThingsToDo3, d in Input_InterestThingsToDo: d2.ThingsToDoID==d.ThingsToDoID, d1 in Input_InterestRange: d.InterestID==d1.InterestID};
   
   {ThingsToDoScore} Input_ThingsToDo1={<d.ThingsToDoID,0> | d in Input_ThingsToDo3}
   				diff {<d.ThingsToDoID,0> | d in Input_ThingsToDo0};
   //TTD not in Itinerary				
   {ThingsToDoScore} Input_ThingsToDo2={<d.ThingsToDoID,d.Score>|d in Input_ThingsToDo0} 
   				union	{<d.ThingsToDoID,d.Score>|d in Input_ThingsToDo1};

   tuple InterestThingsToDoScore {
	  key string InterestID;
	  key string ThingsToDoID;
	  float DrupalWeight;
	  float FP22;
   };
   				
   {InterestThingsToDoScore} Input_InterestThingsToDo1={<i.InterestID,i.ThingsToDoID,i.DrupalWeight,0>| i in Input_InterestThingsToDo};
   //TTD not in Itinerary: Score=0.5 if in Interest range; Score =0 if not in Interest range
   {InterestThingsToDoScore} Input_InterestThingsToDo2={<i.InterestID,i.ThingsToDoID,i.DrupalWeight,r.Score>| 
   								i in Input_InterestThingsToDo, r in Input_ThingsToDo2: i.ThingsToDoID==r.ThingsToDoID};
   //TTD in Itinerary								
   {InterestThingsToDoScore} Input_InterestThingsToDo3={<i.InterestID,i.ThingsToDoID,i.DrupalWeight,0>| i in Input_InterestThingsToDo1}
   														diff
   														{<i.InterestID,i.ThingsToDoID,i.DrupalWeight,0>| i in Input_InterestThingsToDo2};   														
   //TTD in Itinerary in Interest Range														
   {InterestThingsToDoScore} Input_InterestThingsToDo4={<i.InterestID,i.ThingsToDoID,i.DrupalWeight,1>| 
   								i in Input_InterestThingsToDo3, r in Input_InterestRange: i.InterestID==r.InterestID};
   //TTD in Itinerary not in Interest Range								
   {InterestThingsToDoScore} Input_InterestThingsToDo5={<i.InterestID,i.ThingsToDoID,i.DrupalWeight,0>| i in Input_InterestThingsToDo3}
   														diff
   														{<i.InterestID,i.ThingsToDoID,i.DrupalWeight,0>| i in Input_InterestThingsToDo4};   														 								
   //Complete Score for TTD														
   {InterestThingsToDoScore} Input_InterestThingsToDo0={<i.InterestID,i.ThingsToDoID,i.DrupalWeight,i.FP22>| i in Input_InterestThingsToDo2}
   														union
   														{<i.InterestID,i.ThingsToDoID,i.DrupalWeight,i.FP22>| i in Input_InterestThingsToDo4}
   														union 
  														{<i.InterestID,i.ThingsToDoID,i.DrupalWeight,i.FP22>| i in Input_InterestThingsToDo5};
   tuple CruisePackageSimilarity {
	  FP_FC CruisePackage1;
	  FP_FC CruisePackage2;
   };
  														
   {Itinerary} Input_Itinerary = {<i.ItineraryID> | i in Input_CruisePackage2};//Input_CruisePackage2};//Input_ThingsToDo};
     
   //int ItineraryCount=sum(i in Input_Itinerary) 1;
   //int ItinerarySim[0..ItineraryCount][0..ItineraryCount];
   {ItinerarySimilarity} Input_ItinerarySimilarity1={<i.Itinerary1,i.Itinerary2>
   							|i in Input_ItinerarySimilarity, i1 in Input_Itinerary: i.Itinerary1==i1.ItineraryID
   							,i2 in Input_Itinerary: i.Itinerary1==i2.ItineraryID };	 
      							
   {CruisePackageSimilarity} Input_ItinerarySimilarity0={<i1,i2>
  		 	|i in Input_ItinerarySimilarity1, i1 in Input_CruisePackage2: i1.ItineraryID==i.Itinerary1, 
  		 	 i2 in Input_CruisePackage2: i2.ItineraryID==i.Itinerary2 && i1.CruisePackageID<i2.CruisePackageID}
  		 	union
			{<i1,i2> |i in Input_ItinerarySimilarity1, i1 in Input_CruisePackage2: i1.ItineraryID==i.Itinerary2, 
  		 	 i2 in Input_CruisePackage2: i2.ItineraryID==i.Itinerary1 && i1.CruisePackageID<i2.CruisePackageID}  	
  		 	union	 	
  		 	{<i1,i2> |i1 in Input_CruisePackage2,i2 in Input_CruisePackage2: i1.ItineraryID==i2.ItineraryID && i1.CruisePackageID<i2.CruisePackageID};
  
   execute INITIALIZE_MODEL
   {  
   		for( var p in Input_RecommendationRequestLimit){
	   		if( p.NumPackageRecommendations > 0 )
   			{
   	   			// Top cruise packages 
       			RequestedRecommendations = p.NumPackageRecommendations;
       			SelectedUseCase = 4;
   			}
	   		else if ( p.NumThingsToDo > 0 )
   			{
   	   			// Top things to do
   	   			RequestedRecommendations = p.NumThingsToDo;
   	   			SelectedUseCase = 2;
    		}
	   		else if ( p.NumDestinations > 0 )
   			{
   	   			// Top destinations
   	   			RequestedRecommendations = p.NumDestinations;
   	   			SelectedUseCase = 3;
	    	}
	    	else if ( p.NumStateRoomTypes > 0 )
    		{
       			// Top staterooms
       			RequestedRecommendations = p.NumStateRoomTypes;
       			SelectedUseCase = 1;     
    		}
   		}   
   		//writeln("SelectedUseCase="+SelectedUseCase);
    	// Compute the value of ModelID as the constraint logic below depends on it
    	if ( ConstraintViolationUpperBound[1] <= 0.0001 && ConstraintViolationUpperBound[1] >= -0.0001 )
    	{
    	  // This is Model 0, the base optimization model
    	  ModelID = 0;
    	  UnBounded = 0;
        }
        else if ( ConstraintViolationUpperBound[1] < -0.0001 )
        {
          // This is Model 1, where we find a feasible solution that has
          // the lowest level of total customer constraint violations
          ModelID = 1;  
          UnBounded = 1;        
        }
        else
        {          
          // This is model 2, where we find the best solution while maintaining the lowest
          // level of total customer constraint violations
          ModelID = 2;  
          UnBounded = 0;        
        }                    
       //writeln("ModelID="+ModelID+" RequestedRecommendations="+RequestedRecommendations); 
  }   	     
  
   // Optimization Variables
   
   // The objective function value to be optimized   
   dvar float Objective;
      
   // Total Constraint Violations
   dvar float TotalConstraintViolations;
      
   // X = 1 if a cruise package is selected, otherwise X = 0.
   dvar boolean X[ Input_CruisePackage2 ];
   
   // Y = 1 if a thing to do is selected, otherwise Y = 0.
   dvar boolean Y[ Input_InterestThingsToDo0 ];
   
   // Z_s = 1 if a Stateroom is selected, otherwise Z_s = 0.  (Use Case 1)
   dvar boolean Z_s[ Metas ];
       
   // Z_d1 = 1 if a destination name is selected, otherwise Z_d1 = 0.  (Use Case 3)
   dvar boolean Z_d1[ Input_Destinations];//Destinations ];
   
   // Other components of the objective function
   dvar float+ FP1;		// Accommodation Preference
   dvar float+ FP2;		// Interest Preference

   dvar float+ FC1;		// Stateroom Constraint Violation
   dvar float+ FC2;		// Things To Do Constraint Violation
   dvar float+ FC3;		// Price Constraint Violation
   dvar float+ FC41;	// Date Constraint Violation
   dvar float+ FC42;	// Cruise Duration Constraint Violation
   dvar float+ FC5;		// Destination Constraint Violation
   dvar float+ FC6;		// Departing Port Constraint Violation   
   
   // Drupal weight contribution that is added to the objective function.  This can be negative to provide 
   // disincentives for selection, if desired by the business.
   dvar float DrupalWeightContribution;	
   
   
   // Slack variables used for calculation of limit violations
   dvar float+ PackageRecommendationSlack;			// Slack variable for Package recommendation limit violation amount
   dvar float+ ThingsToDoSlack;						// Slack variable for Things To Do limit violation amount
   dvar float+ DestinationSlack;						// Slack variable for Destination limit violation amount
   dvar float+ StateroomSlack;						// Slack variable for Stateroom type limit violation amount
   
   //dvar float+ ItinSlack[ Input_ItinerarySimilarity ];	// Intermediate slack variable for itinerary uniqueness violation   
   //dvar float+ ItinSlack[ Input_CruisePackage2][Input_CruisePackage2];
   dvar float+ ItinerarySlack;							// Slack variable for itinerary uniqueness violation amount
   
   
   // Decision Variable Expressions
   
   // Total Recommendation Limit Violations
   dvar float+ TotalRecommendationLimitViolation; 

   // Total Uniqueness of Itinerary Violations
   dvar float+ TotalItineraryUniquenessLimitViolation ;
   
   // Contribution attributed to accommodation 
   // 	WC1 * WB1 * FP1 
   dvar float+ AccommodationPreferenceContribution;

   // Contribution attributed to Things To Do
   //   WC2 * WB2 * FP2    
   dvar float+ ThingsToDoPreferenceContribution ;
   // WC1*WB1* FC1
   dvar float+ AccommodationConstraintViolation;
   //WC2*WB2*FC2 		         		       
   dvar float+ ThingsToDoConstraintViolation;
   //dexpr  float ThingsToDoConstraintViolation2	 = sum( i in Input_CruisePackage2 ) X[ i ] ;//* CruisePackageFC2[i];
   //sum(p in Input_Parameters) p.CustomerThingsToDoPriority *
   // 		     						   			 p.BusinessThingsToDoPriority * FC2;	     				 
   // Contribution attributed to Price violation
   //   WC3 * WB3 * FC3
   dvar float+ PriceConstraintViolation ;
   
   // Contribution attributed to Cruise Duration violation
   //   WC4 * WB4 * ( FC41 + FC42 )    
   dvar float+ DurationConstraintViolation ;
   
   // Contribution attributed to Destination violation
   //   WC5 * WB5 * FC5   
   dvar float+ DestinationConstraintViolation;
   
   // Contribution attributed to Departing Port violation
   //   WB6 * FC6   
   dvar float+ DepartingPortConstraintViolation;     
   
   dexpr float OBJ_AccommodationPreferenceContribution =  AccommodationPreferenceContribution; 
   dexpr float OBJ_ThingsToDoPreferenceContribution =  ThingsToDoPreferenceContribution;
   dexpr float OBJ_DrupalWeightContribution =   DrupalWeightContribution;  
   dexpr float OBJ_AccommodationConstraintViolation =  -ViolationWeight * AccommodationConstraintViolation;
   dexpr float OBJ_ThingsToDoConstraintViolation =  -ViolationWeight * ThingsToDoConstraintViolation;
   dexpr float OBJ_PriceConstraintViolation = -ViolationWeight * PriceConstraintViolation ;
   dexpr float OBJ_DurationConstraintViolation =  -ViolationWeight * DurationConstraintViolation;
   dexpr float OBJ_DestinationConstraintViolation = -ViolationWeight * DestinationConstraintViolation;
   dexpr float OBJ_DepartingPortConstraintViolation = -ViolationWeight * DepartingPortConstraintViolation;
   dexpr float OBJ_TotalRecommendationLimitViolation = -ViolationWeight * TotalRecommendationLimitViolation;
   dexpr float OBJ_TotalItineraryUniquenessLimitViolation = -ViolationWeight * TotalItineraryUniquenessLimitViolation;    		     			  

   execute INITIALIZE_OBJ
   {  
   	writeln("Define OBJ...");   
   }      
  // Optimization model
  //maximize Objective;
  maximize 
  	OBJ_AccommodationPreferenceContribution + 
  	OBJ_ThingsToDoPreferenceContribution + 
  	OBJ_DrupalWeightContribution +
    OBJ_AccommodationConstraintViolation +
    OBJ_ThingsToDoConstraintViolation +
    OBJ_PriceConstraintViolation +
    OBJ_DurationConstraintViolation + 
    OBJ_DestinationConstraintViolation +
    OBJ_DepartingPortConstraintViolation + 
    OBJ_TotalRecommendationLimitViolation +
    OBJ_TotalItineraryUniquenessLimitViolation;
  
  subject to
  {   
	// Contribution attributed to accommodation 
    // 	WC1 * WB1 * FP1 
    AccommodationPreferenceContribution == sum(p in Input_Parameters) p.CustomerAccommodationPriority * 
    			 						   			 p.BusinessAccommodationPriority * FP1;

    // Contribution attributed to Things To Do
    //   WC2 * WB2 * FP2    
    ThingsToDoPreferenceContribution ==    sum(p in Input_Parameters) p.CustomerThingsToDoPriority *
    		     						   			 p.BusinessThingsToDoPriority * FP2;  	
    //WC1*WB1*FC1   
    AccommodationConstraintViolation ==  sum(p in Input_Parameters) p.CustomerAccommodationPriority *
   													 p.BusinessAccommodationPriority * FC1;
   	//WC2*WB2*FC2 		         		       
    ThingsToDoConstraintViolation == sum(p in Input_Parameters) p.CustomerThingsToDoPriority *
    		     						   			 p.BusinessThingsToDoPriority * FC2;
   // Contribution attributed to Price violation
   //   WC3 * WB3 * FC3
   PriceConstraintViolation ==  sum(p in Input_Parameters) p.CustomerPricePriority *
    		     						   p.BusinessPricePriority * FC3;
    
   // Contribution attributed to Cruise Duration violation
   //   WC4 * WB4 * ( FC41 + FC42 )    
   DurationConstraintViolation == 	sum(p in Input_Parameters) p.CustomerTimePriority * 
    		     						   		p.BusinessTimePriority * ( FC41 + FC42 )/2.0;
   
   // Contribution attributed to Destination violation
   //   WC5 * WB5 * FC5   
   DestinationConstraintViolation == sum(p in Input_Parameters) p.CustomerDestinationPriority *
    		     						   		p.BusinessDestinationPriority * FC5;
   
   // Contribution attributed to Departing Port violation
   //   WB6 * FC6   
   DepartingPortConstraintViolation == sum(p in Input_Parameters) p.BusinessDepartingPriority * FC6;
   
   TotalRecommendationLimitViolation == ( PackageRecommendationSlack + ThingsToDoSlack +
   											 	     DestinationSlack + StateroomSlack ) * sum(p in Input_Parameters) p.RemommendationLimitPriority;
   TotalItineraryUniquenessLimitViolation == sum(p in Input_Parameters) ItinerarySlack * p.ItineraryUniquenessPriority;

   
          		     						   			 												 		     						   			 
    // Calculate Total Constraint Violations here
    Total_Constraint_Violations:
    TotalConstraintViolations == 
    		         		         		         		     
    		         		     // Stateroom Constraint Violations (FC1)
    		         		     AccommodationConstraintViolation
    		         		     //sum( p in Input_Parameters) p.CustomerAccommodationPriority *
    		         		     //sum ( p in Input_Parameters) p.BusinessAccommodationPriority * FC1
    		         		       
    		         		  	 // Things To Do Violations (FC2)
   							  +  ThingsToDoConstraintViolation
   							  	 //sum(p in Input_Parameters) p.CustomerThingsToDoPriority *
    		     				 //sum(p in Input_Parameters) p.BusinessThingsToDoPriority * FC2
    		         		  	     		         		    
    		     				 // Price Violations (FC3)
    		     			  +  PriceConstraintViolation 
    		     
    		     				 // Time (Cruise Duration) Violations (FC41 + FC42)
    		     			  +	 DurationConstraintViolation
    		     
    		     				 // Destination Violation (FC5)
    		     			  +	 DestinationConstraintViolation
    		     
    		     				 // Departing Port Violation (FC6)
    		     			  +  DepartingPortConstraintViolation
    		     			  
    		     			     // Total Recommendation Limit Violation
    		     			  +  TotalRecommendationLimitViolation
    		     			  
    		     			     // Total Uniqueness of Itinerary Violations
    		     			  +  TotalItineraryUniquenessLimitViolation;  
    		     			     
    		     			     
    
    
    // The upper bound on Total Constraint Violations
    Total_Constraint_Violation_Limit:
    if ( ModelID == 0 || ModelID == 2 )
	   TotalConstraintViolations <= ConstraintViolationUpperBound[1];
        
    // Determine the form of the objective function from the Model ID
    /*
    if ( ModelID == 0 || ModelID == 2 )
    {
      Objective_02:
   	  Objective == 
    			 				// Accommodation Preference Contribution
    		     				AccommodationPreferenceContribution 
    		     
    		     				// Things To Do Preference Contribution
    		     			 +	ThingsToDoPreferenceContribution
    		         		         		     				
    		     				// Drupal Weight Contribution
    		     			 +	DrupalWeightContribution
    		     			 ;
    	
    }    		   
    else 
    {
      // Model ID = 1
            
      // The objective for Model 1 is to minimize Total Customer Constraint Violations.
      // Note that since our objective function is to be maximized, we must change the sense of the objective
      // function.
      Objective_1:
      Objective == 	- TotalConstraintViolations;      
    }
    */
    Objective==
   	OBJ_AccommodationPreferenceContribution + 
  	OBJ_ThingsToDoPreferenceContribution + 
  	OBJ_DrupalWeightContribution +
    OBJ_AccommodationConstraintViolation +
    OBJ_ThingsToDoConstraintViolation +
    OBJ_PriceConstraintViolation +
    OBJ_DurationConstraintViolation + 
    OBJ_DestinationConstraintViolation +
    OBJ_DepartingPortConstraintViolation + 
    OBJ_TotalRecommendationLimitViolation +
    OBJ_TotalItineraryUniquenessLimitViolation;

   	    
    // If Model ID == 1, we don't calculate values of FP1 and FP2.  We do these for Model ID == 0 and
    // Model ID == 2 only.
    if(ModelID>=0)//4-9-2013 if ( ModelID != 1 )
    
    {     
      // Determination of Accommodation Preference FP1 and Interest Preference FP2: depends on Use Case
      if ( SelectedUseCase != 2 )
      {
        CalcFP1:		RequestedRecommendations * FP1 == sum( i in Input_CruisePackage2)  X[ i ] * i.FP1;//CruisePackageFP1[i];
        CalcFP2:		RequestedRecommendations * FP2 == sum( i in Input_CruisePackage2 )  X[ i ] * (i.FP211+i.FP212+i.FP213)/3.0;//(CruisePackageFP211[i]+CruisePackageFP212[i]+CruisePackageFP213[i])/3.0;     
      }       
    
      else
      {
        // SelectedUseCase = 2
        CalcFP1_2:	FP1 == 0;
        CalcFP2_2:	RequestedRecommendations * FP2 == sum( j in Input_InterestThingsToDo0 ) Y[ j ] * j.FP22;//ThingsToDoFP22[j];            
      }       
    }    
    
 
    // Constraint violations
    
    // Determination of FC1: Stateroom Constraint Violation
    //StateRoomViolation:				
    RequestedRecommendations * FC1 == sum( i in Input_CruisePackage2)  X[ i ]*i.FC1;//CruisePackageFC1[i];
    
    // Determination of FC2: Things To Do Constraint Violation
    ThingsToDoViolation:	   												
    RequestedRecommendations * FC2 == sum( i in Input_CruisePackage2 ) X[ i ] * i.FC2;//CruisePackageFC2[i];
    
    // Determination of FC3: Price Constraint Violation
    PriceViolation:					
    RequestedRecommendations * FC3 == sum( i in Input_CruisePackage2 )  X[ i ] * i.FC3;//CruisePackageFC3[i];
        
    // Determination of FC41: Date Violation
    DateViolation:					
    RequestedRecommendations * FC41 == sum( i in Input_CruisePackage2 ) X[ i ] * i.FC41;//CruisePackageFC41[i];
    
    // Determination of FC42: Duration Violation
    DurationViolation:				
    RequestedRecommendations * FC42 == sum( i in Input_CruisePackage2 ) X[ i ] * i.FC42;//CruisePackageFC42[i];
      
    // Determination of FC5: Destination Constraint Violation
    DestinationViolation:	
    RequestedRecommendations * FC5 == sum( i in Input_CruisePackage2 )  X[ i ] * i.FC5;//CruisePackageFC5[i];
        
    // Determination of FC6: Departing Port Constraint Violation
    DepartingPortViolation:	
    RequestedRecommendations * FC6 == sum( i in Input_CruisePackage2 )  X[ i ] * i.FC6;//CruisePackageFC6[i];
 
    // Calculation of Itinerary Uniqueness Slack.  Here, we only sum the terms
    // that were used in the constraint above.  Other elements of ItinSlack are not used
    // as the variable has dimensions of [ Input_CruisePackage2 ][ Input_CruisePackage2 ].  This generates an OPL warning 
    // that can safely be ignored.
    
    Itinerary_Slack_c:
    if(SelectedUseCase == 4)
	    ItinerarySlack == sum(k in Input_ItinerarySimilarity0: k.CruisePackage1.CruisePackageID<k.CruisePackage2.CruisePackageID) 
							maxl( 0, X[k.CruisePackage1]+X[k.CruisePackage2]-1);
	   					   //sum( i in Input_CruisePackage2) 
	    				  //sum(j in Input_CruisePackage2:i.CruisePackageID<j.CruisePackageID)
	    				  //sum(k in Input_ItinerarySimilarity0: i==k.CruisePackage1 && j==k.CruisePackage2) 
							//maxl( 0, X[i]+X[j]-1);
     							
    // Additional constraints, based on the specified use case
    if ( SelectedUseCase == 1 )
    {
      // Top Stateroom results are required, with no duplicates
      
      // Must recommend the specified number of results                       
      
   	  NumberOfSelectedPackages_1:       
      sum( i in Input_CruisePackage2 ) X[ i ] <= RequestedRecommendations;
      
      NumberOfSelectedStaterooms_1://bug: missing slack           
	  sum( k in Metas ) Z_s[ k ] + StateroomSlack == RequestedRecommendations;
   
      //This constraint selects the room type for each selected package      
      forall( k in Metas )
      {  
        RoomTypeSelection_1:
        Z_s[ k ] <= sum( i in Input_CruisePackage2: i.Meta == k.Meta ) X[ i ];
      }        
    }      
    else{
      StateroomSlack==0;
    }      
   
   
    if ( SelectedUseCase == 2 )
    {          
      // Top Things To Do results are required      
      TopThingsToDo_2:
        
      // Must recommend the specified number of results
      sum( j in Input_InterestThingsToDo0 ) Y[ j ] + ThingsToDoSlack == RequestedRecommendations;
      
      // Constrain Y[ j ] to be things to do associated with X[ i ] - See slide 25      
	  forall( j in Input_InterestThingsToDo0 )
      {
        TopThingsToDo_2a:
              Y[ j ] <= sum( t in Input_ThingsToDo://Input_ThingsToDo5 : 
              					t.ThingsToDoID == j.ThingsToDoID ) sum( i in Input_CruisePackage2 :  t.ItineraryID == i.ItineraryID ) X[ i ];
      }       
    } 
    else
    {
      ThingsToDoSlack ==0;
    }      
    
    if ( SelectedUseCase == 3 )
    {
      // Top Destination results are required, with no duplicates      
      
      // Must recommend the specified number of results                 
      
   	  NumberOfSelectedPackages_3:       
      sum( i in Input_CruisePackage2 ) X[ i ] <= RequestedRecommendations;
            
      NumberOfSelectedPackages_3a:
      sum( d in Input_Destinations)//Destinations ) 
      	Z_d1[ d ] + DestinationSlack == RequestedRecommendations;  
                
      // This constraint selects the destination name for each selected destination ID.
      // Note that this constraint is not required if each itinerary ID is associated with
      // a unique destination name.  
      forall( d in Input_Destinations)//Destinations )
      { 
        DestinationSelection_3:        
        //3-11-2013 Z_d1[ d ] <= sum( k in Input_Itinerary: k.Destination == d.Destination )  sum( i in Input_CruisePackage2: i.ItineraryID == k.ItineraryID ) X[ i ]; 
        Z_d1[ d ] <=  sum( i in Input_CruisePackage2: i.Destination == d.Destination ) X[ i ];               
      }                          
    } 
    else{
      DestinationSlack==0;
    }      
    
    if ( SelectedUseCase == 4 )
    {
      // Top Package results are required, each with unique itineraries
      
      // Must recommend the specified number of results                       
      NumberOfSelectedPackages_4://bug: missing slack      
      sum( i in Input_CruisePackage2 ) X[ i ] + PackageRecommendationSlack == RequestedRecommendations;
                      
    } 
    else{
      PackageRecommendationSlack==0;
    }      

    
    // Drupal Weight Contributions for all Use Cases except 2
    if(ModelID>=0){//4-9-2013 if ( ModelID == 0 || ModelID == 2 ){
	    if ( SelectedUseCase != 2 )
	    {
	      // Drupal Weight Contribution is determined from weights in Input_CruisePackage2
	      DrupalWeightContribution_i:
	      DrupalWeightContribution == sum( i in Input_CruisePackage2 )  X[ i ] * i.DrupalWeight;    
	    }          
	    else
	    {
	      // Use Case 2 has been selected
	      
	      // Drupal Weight Contribution is determined from weights in Input_InterestThingsToDo
	      DrupalWeightContribution_j:
	      DrupalWeightContribution == sum( j in Input_InterestThingsToDo0 ) Y[ j ] * j.DrupalWeight;      
	    }       
    }   
    else{
      DrupalWeightContribution == 0;
    }        
  }  // End of Constraints  
  
  
  
  // Post-processing to populate output arrays here
   // Output Data
      // Output Tuples **************************************************************************************
  /*    
  {DestinationRange} Output_Destination0= {<i.Destination> 
   												| i in Input_CruisePackage2: X[i]>0, p in Input_RecommendationRequestLimit: p.NumDestinations>0.1  };
  {DestinationRange} Output_Destination1={<i.Destination> 
   												| i in Input_CruisePackage2: X[i]<1 && i.CruisePackageID>0  &&
   												i.FC1+i.FC2+i.FC3+i.FC41+i.FC42+i.FC5+i.FC6 < 0.00001,
   												//CruisePackageFC1[i] + CruisePackageFC2[i] + CruisePackageFC3[i] +
        					 					//CruisePackageFC41[i] + CruisePackageFC42[i] +CruisePackageFC5[i] +
        					 					//CruisePackageFC6[i] < 0.00001,
   												n in Input_Parameters, p in Input_RecommendationRequestLimit: p.NumDestinations>0.1  }
   											diff
   											{<i.Destination>| i in Output_Destination0};
  {DestinationRange} Output_Destination2={<i.Destination> 
   												| i in Input_CruisePackage2: X[i]<1 && i.CruisePackageID>0  &&
  												i.FC1+i.FC2+i.FC3+i.FC41+i.FC42+i.FC5+i.FC6 >= 0.00001,   												
   												//CruisePackageFC1[i] + CruisePackageFC2[i] + CruisePackageFC3[i] +
        					 					//CruisePackageFC41[i] + CruisePackageFC42[i] +CruisePackageFC5[i] +
        					 					//CruisePackageFC6[i] >= 0.00001,
   												n in Input_Parameters, p in Input_RecommendationRequestLimit: p.NumDestinations>0.1  }
   											diff
   											({<i.Destination>| i in Output_Destination0} union {<i.Destination>| i in Output_Destination1});
   {DestinationRange} Output_Destination3={<i.Destination> 
   												| i in Input_CruisePackage2: X[i]<1 && i.CruisePackageID==0,
   												n in Input_Parameters, p in Input_RecommendationRequestLimit: p.NumDestinations>0.1  }
   											diff
   											({<i.Destination>| i in Output_Destination0} union {<i.Destination>| i in Output_Destination1}
   												union {<i.Destination>| i in Output_Destination2});  	
   																						

  {MetaRange} Output_Meta0= {<i.Meta> | i in Input_CruisePackage2: X[i]>0, p in Input_RecommendationRequestLimit: p.NumStateRoomTypes>0.1  };
  {MetaRange} Output_Meta1= {<i.Meta> | i in Input_CruisePackage2: X[i]<1 && i.CruisePackageID>0  &&
  												i.FC1+i.FC2+i.FC3+i.FC41+i.FC42+i.FC5+i.FC6 < 0.00001,   												
   												//CruisePackageFC1[i] + CruisePackageFC2[i] + CruisePackageFC3[i] +
        					 					//CruisePackageFC41[i] + CruisePackageFC42[i] +CruisePackageFC5[i] +
        					 					//CruisePackageFC6[i] < 0.00001,
   												 p in Input_RecommendationRequestLimit: p.NumStateRoomTypes>0.1  }
   											diff
   											{<i.Meta>| i in Output_Meta0};
  {MetaRange} Output_Meta2={<i.Meta> | i in Input_CruisePackage2: X[i]<1 && i.CruisePackageID>0  &&
  												i.FC1+i.FC2+i.FC3+i.FC41+i.FC42+i.FC5+i.FC6 >= 0.00001,   												
   												//CruisePackageFC1[i] + CruisePackageFC2[i] + CruisePackageFC3[i] +
        					 					//CruisePackageFC41[i] + CruisePackageFC42[i] +CruisePackageFC5[i] +
        					 					//CruisePackageFC6[i] >= 0.00001,
   												 p in Input_RecommendationRequestLimit: p.NumStateRoomTypes>0.1  }
   											diff
   											({<i.Meta>| i in Output_Meta0} union {<i.Meta>| i in Output_Meta1});
   {MetaRange} Output_Meta3={<i.Meta> | i in Input_CruisePackage2: X[i]<1 && i.CruisePackageID==0,
   												n in Input_Parameters, p in Input_RecommendationRequestLimit: p.NumStateRoomTypes>0.1  }
   											diff
   											({<i.Meta>| i in Output_Meta0} union {<i.Meta>| i in Output_Meta1}
   												union {<i.Meta>| i in Output_Meta2});  	
   */												   												      
   
   {CruisePackageRecommendation}		
   output_CruisePackageRecommendation = 	
   											{<i.SailID,i.PackageID,i.DateID,i.ItineraryID,i.Destination,i.RoomType,i.Meta,
   												n.CustomerAccommodationPriority * n.BusinessAccommodationPriority * i.FP1,//CruisePackageFP1[i],
   												n.CustomerThingsToDoPriority * n.BusinessThingsToDoPriority *(i.FP211+i.FP212+i.FP213)/3.0,//(CruisePackageFP211[i]+CruisePackageFP212[i]+CruisePackageFP213[i])/3.0,
   												n.CustomerAccommodationPriority * n.BusinessAccommodationPriority * i.FC1,//CruisePackageFC1[i],
												n.CustomerThingsToDoPriority * n.BusinessThingsToDoPriority * i.FC2,//CruisePackageFC2[i],
												n.CustomerPricePriority * n.BusinessPricePriority * i.FC3,//CruisePackageFC3[i],   
												n.CustomerTimePriority * n.BusinessTimePriority * i.FC41,//CruisePackageFC41[i],
												n.CustomerTimePriority * n.BusinessTimePriority * i.FC42,//CruisePackageFC42[i],		
												n.CustomerDestinationPriority * n.BusinessDestinationPriority * i.FC5,//CruisePackageFC5[i],
   												n.BusinessDepartingPriority * i.FC6,//CruisePackageFC6[i],			 
   												i.DrupalWeight, //DiscountType[i],
   												i.PricePerGuest,1>//PricePerGuest[i],1> 
   												| i in Input_CruisePackage2: X[i]>0, n in Input_Parameters, p in Input_RecommendationRequestLimit: p.NumThingsToDo<0.1 }
											union
												{<i.SailID,i.PackageID,i.DateID,i.ItineraryID,i.Destination,i.RoomType,i.Meta,
   												n.CustomerAccommodationPriority * n.BusinessAccommodationPriority * i.FP1,//CruisePackageFP1[i],
   												n.CustomerThingsToDoPriority * n.BusinessThingsToDoPriority *(i.FP211+i.FP212+i.FP213)/3.0,//(CruisePackageFP211[i]+CruisePackageFP212[i]+CruisePackageFP213[i])/3.0,
   												n.CustomerAccommodationPriority * n.BusinessAccommodationPriority * i.FC1,//CruisePackageFC1[i],
												n.CustomerThingsToDoPriority * n.BusinessThingsToDoPriority * i.FC2,//CruisePackageFC2[i],
												n.CustomerPricePriority * n.BusinessPricePriority * i.FC3,//CruisePackageFC3[i],   
												n.CustomerTimePriority * n.BusinessTimePriority * i.FC41,//CruisePackageFC41[i],
												n.CustomerTimePriority * n.BusinessTimePriority * i.FC42,//CruisePackageFC42[i],		
												n.CustomerDestinationPriority * n.BusinessDestinationPriority * i.FC5,//CruisePackageFC5[i],
   												n.BusinessDepartingPriority * i.FC6,//CruisePackageFC6[i],			 
   												i.DrupalWeight, //DiscountType[i],
   												i.PricePerGuest,0>//PricePerGuest[i],0> 
   												| i in Input_CruisePackage2: X[i]<1 && //i.CruisePackageID>0 &&
   												i.FC1+i.FC2+i.FC3+i.FC41+i.FC42+i.FC5+i.FC6 < 0.00001, 
   												//CruisePackageFC1[i] + CruisePackageFC2[i] + CruisePackageFC3[i] +
        					 					//CruisePackageFC41[i] + CruisePackageFC42[i] +CruisePackageFC5[i] +
        					 					//CruisePackageFC6[i] < 0.00001,
   												n in Input_Parameters, p in Input_RecommendationRequestLimit: p.NumThingsToDo<0.1 }
 											union
												{<i.SailID,i.PackageID,i.DateID,i.ItineraryID,i.Destination,i.RoomType,i.Meta,
   												n.CustomerAccommodationPriority * n.BusinessAccommodationPriority * i.FP1,//CruisePackageFP1[i],
   												n.CustomerThingsToDoPriority * n.BusinessThingsToDoPriority *(i.FP211+i.FP212+i.FP213)/3.0,//(CruisePackageFP211[i]+CruisePackageFP212[i]+CruisePackageFP213[i])/3.0,
   												n.CustomerAccommodationPriority * n.BusinessAccommodationPriority * i.FC1,//CruisePackageFC1[i],
												n.CustomerThingsToDoPriority * n.BusinessThingsToDoPriority * i.FC2,//CruisePackageFC2[i],
												n.CustomerPricePriority * n.BusinessPricePriority * i.FC3,//CruisePackageFC3[i],   
												n.CustomerTimePriority * n.BusinessTimePriority * i.FC41,//CruisePackageFC41[i],
												n.CustomerTimePriority * n.BusinessTimePriority * i.FC42,//CruisePackageFC42[i],		
												n.CustomerDestinationPriority * n.BusinessDestinationPriority * i.FC5,//CruisePackageFC5[i],
   												n.BusinessDepartingPriority * i.FC6,//CruisePackageFC6[i],			 
   												i.DrupalWeight, //DiscountType[i],
   												i.PricePerGuest,-1>//PricePerGuest[i],-1> 
   												| i in Input_CruisePackage2: X[i]<1 && //i.CruisePackageID>0 &&
   												i.FC1+i.FC2+i.FC3+i.FC41+i.FC42+i.FC5+i.FC6 >= 0.00001, 
   												//CruisePackageFC1[i] + CruisePackageFC2[i] + CruisePackageFC3[i] +
        					 					//CruisePackageFC41[i] + CruisePackageFC42[i] +CruisePackageFC5[i] +
        					 					//CruisePackageFC6[i] >= 0.00001,
   												n in Input_Parameters, p in Input_RecommendationRequestLimit: p.NumThingsToDo<0.1 } 												
   											/*union 
    										{<"NONE","NONE","NONE",d.Destination,d.Destination,"NONE","NONE",
   												0,0,0,0,0,0,0,0,0,0,0,0> | 
   												d in Output_Destination1, p in Input_RecommendationRequestLimit: p.NumDestinations>0.1  }
   											union	
     										{<"NONE","NONE","NONE",i.Destination,i.Destination,"NONE","NONE",
   												0,0,0,0,0,0,0,0,0,0,0,-1> | i in Output_Destination2, p in Input_RecommendationRequestLimit: p.NumDestinations>0.1 }
    										
    										union	
     										{<"NONE","NONE","NONE",i.Destination,i.Destination,"NONE","NONE",
   												0,0,0,0,0,0,0,0,0,0,0,-2> 
   												| i in Output_Destination3, p in Input_RecommendationRequestLimit: p.NumDestinations>0.1 }  				
   											union 
    										{<"NONE","NONE","NONE",d.Meta,"NONE","NONE",d.Meta,
   												0,0,0,0,0,0,0,0,0,0,0,0> | 
   												d in Output_Meta1, p in Input_RecommendationRequestLimit: p.NumStateRoomTypes>0.1  }
   											union	
     										{<"NONE","NONE","NONE",i.Meta,"NONE","NONE",i.Meta,
   												0,0,0,0,0,0,0,0,0,0,0,-1> | i in Output_Meta2, p in Input_RecommendationRequestLimit: p.NumStateRoomTypes>0.1 }
    										
    										union	
     										{<"NONE","NONE","NONE",i.Meta,"NONE","NONE",i.Meta,
   												0,0,0,0,0,0,0,0,0,0,0,-2> 
   												| i in Output_Meta3, p in Input_RecommendationRequestLimit: p.NumStateRoomTypes>0.1 } 												   																				
   											*/	
   											;
   
   {ThingsToDoRecommendation}			output_ThingsToDoRecommendation0 =
   										{<j.ThingsToDoID,j.InterestID,
   											i.CustomerThingsToDoPriority * i.BusinessThingsToDoPriority * j.FP22,//ThingsToDoFP22[j],
   											j.DrupalWeight, 1>
   										 	| j in Input_InterestThingsToDo0: Y[j]>0, i in Input_Parameters, p in Input_RecommendationRequestLimit: p.NumThingsToDo>0.1 };
   										 	
   {ThingsToDoRecommendation}			output_ThingsToDoRecommendation1 =
   										{<j.ThingsToDoID,j.InterestID,
   											i.CustomerThingsToDoPriority * i.BusinessThingsToDoPriority * j.FP22,//ThingsToDoFP22[j],
   											j.DrupalWeight, 0>
   										 	| j in Input_InterestThingsToDo0: Y[j]<1, i in Input_Parameters,
   										 	c in Input_CruisePackage2: c.CruisePackageID>0 && 
   										 	c.FC1+c.FC2+c.FC3+c.FC41+c.FC42+c.FC5+c.FC6 <= 0.00001 
   										 	//CruisePackageFC1[c]+
   										 	//CruisePackageFC2[c]+CruisePackageFC3[c]+CruisePackageFC41[c]+CruisePackageFC42[c]+CruisePackageFC5[c]
   										 	//+CruisePackageFC6[c]<=0.00001
   										 	, t in Input_ThingsToDo://Input_ThingsToDo5: 
   										 	c.ItineraryID==t.ItineraryID && t.ThingsToDoID==j.ThingsToDoID, p in Input_RecommendationRequestLimit: p.NumThingsToDo>0.1  }; 
   {ThingsToDoRecommendation}			output_ThingsToDoRecommendation2 =
   										{<j.ThingsToDoID,j.InterestID,
   											i.CustomerThingsToDoPriority * i.BusinessThingsToDoPriority * j.FP22,//ThingsToDoFP22[j],
   											j.DrupalWeight, -1>
   										 	| j in Input_InterestThingsToDo0: Y[j]<1, i in Input_Parameters,
   										 	c in Input_CruisePackage2: c.CruisePackageID>0 && 
   										 	c.FC1+c.FC2+c.FC3+c.FC41+c.FC42+c.FC5+c.FC6 > 0.00001 
   										 	//CruisePackageFC1[c]+
   										 	//CruisePackageFC2[c]+CruisePackageFC3[c]+CruisePackageFC41[c]+CruisePackageFC42[c]+CruisePackageFC5[c]
   										 	//+CruisePackageFC6[c]>0.00001
   										 	, t in Input_ThingsToDo://Input_ThingsToDo5: 
   										 	c.ItineraryID==t.ItineraryID && t.ThingsToDoID==j.ThingsToDoID, p in Input_RecommendationRequestLimit: p.NumThingsToDo>0.1  };   										 	
  {InterestThingsToDo}					output_ThingsToDo =
   										{<j.InterestID,j.ThingsToDoID,j.DrupalWeight>
   										 	| j in Input_InterestThingsToDo0: Y[j]<1, p in Input_RecommendationRequestLimit: p.NumThingsToDo>0.1 }
   										diff
   										{<j.InterestID,j.ThingsToDoID,j.DrupalWeightContribution>| j in output_ThingsToDoRecommendation1};
  {InterestThingsToDo}					output_ThingsToDo2 =
   										{<j.InterestID,j.ThingsToDoID,j.DrupalWeight>| j in output_ThingsToDo, p in Input_RecommendationRequestLimit: p.NumThingsToDo>0.1  }
   										diff
   										{<j.InterestID,j.ThingsToDoID,j.DrupalWeightContribution>| j in output_ThingsToDoRecommendation2};  										
  
  {ThingsToDoRecommendation}			output_ThingsToDoRecommendation =
										{<j.ThingsToDoID,j.InterestID,j.InterestPrefContribution,j.DrupalWeightContribution,j.IsRecommended>| j in output_ThingsToDoRecommendation0, p in Input_RecommendationRequestLimit: p.NumThingsToDo>0.1}
										union
										{<j.ThingsToDoID,j.InterestID,j.InterestPrefContribution,j.DrupalWeightContribution,j.IsRecommended>| j in output_ThingsToDoRecommendation1, p in Input_RecommendationRequestLimit: p.NumThingsToDo>0.1}
										union 
										{<j.ThingsToDoID,j.InterestID,j.InterestPrefContribution,j.DrupalWeightContribution,j.IsRecommended>| j in output_ThingsToDoRecommendation2, p in Input_RecommendationRequestLimit: p.NumThingsToDo>0.1}
										union 										
   										{<j.ThingsToDoID,j.InterestID,
   											i.CustomerThingsToDoPriority * i.BusinessThingsToDoPriority * j2.Score,
   											j.DrupalWeight, -2>
   										 	| j in output_ThingsToDo2 , j2 in Input_ThingsToDo2: j.ThingsToDoID==j2.ThingsToDoID, i in Input_Parameters , p in Input_RecommendationRequestLimit: p.NumThingsToDo>0.1 };
   																		 	
   {ConstraintViolation}	output_ConstraintViolation =
   								{
									<PackageRecommendationSlack,ThingsToDoSlack,DestinationSlack,StateroomSlack,ItinerarySlack>								  										 	 										     
                                };   
   	
   {Objectives} output_Objective = {< 
      Objective,
	  AccommodationPreferenceContribution,
	  ThingsToDoPreferenceContribution,
	  DrupalWeightContribution,
	  AccommodationConstraintViolation,
	  ThingsToDoConstraintViolation,
	  PriceConstraintViolation,
	  DurationConstraintViolation,
	  DestinationConstraintViolation,
	  DepartingPortConstraintViolation
	  >};							  										 	 										     
   
   execute BUILD_OUTPUT_TUPLES
   {  
   	   //writeln("ttd="+ThingsToDoConstraintViolation);
	   for(var oc in output_ConstraintViolation){
	     //Output_ConstraintViolation.add(oc.PackageLimitViolation,oc.ThingsToDoLimViolation,oc.DestinationLimViolation,oc.RoomTypeLimViolation,oc.ItineraryUniquenessViolation);
	     Output_ConstraintViolation.add(oc);
	   }     
	   for(var oc2 in output_CruisePackageRecommendation){
	     Output_CruisePackageRecommendation.add(oc2);
	     //if(oc2.IsRecommended==1)
	     //writeln("SailID="+oc2.SailID+" Date="+oc2.DateID+" Desti="+oc2.Destination+" Port="+oc2.DepartingPort);
	   }     
	   for(var oc3 in output_ThingsToDoRecommendation){
	     Output_ThingsToDoRecommendation.add(oc3);
	   }      
	   for(var oc4 in output_Objective){
	     Output_Objective.add(oc4);
	   } 	   
   }     
