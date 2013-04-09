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
   
   // Use Cases referenced below
   // Use Case = 1: Recommend Top Stateroom Types (Web App Step 2)
   // Use Case = 2: Recommend Top Things to do (Web App Step 3)
   // Use Case = 3: Recommend Top Destinations (Web App Step 5)
   // Use Case = 4: Recommend Top Packages (Web App Step 6)
   
   // This tuple is used to determine the desired Use Case to be solved as well
   // as the number of requested results that are expected.
   // Note that this is a single row table
   
   //Table: BUSINFLWGHT: WB1~WB8   
   //User input: WC1~WC5
   // Relative order of magnitude WB7 >> WB* >> WB8 
   /* 
   tuple Parameters							// Note that this is a single row table
   {
     //3-11-2013 key int OptimRequestID;				// Optimization Request ID
     float CustomerAccommodationPriority;	// Customer Accommodation Priority (WC1)
     float CustomerThingsToDoPriority;		// Customer Things To Do Priority (WC2)
     float CustomerPricePriority;			// Customer Price Priority (WC3)
     float CustomerTimePriority;			// Customer Time Priority (WC4)
     float CustomerDestinationPriority;		// Customer Destination Priority (WC5)
     
     float BusinessAccommodationPriority;	// Business Accommodation Priority (WB1)
     float BusinessThingsToDoPriority;		// Business Things to Do Priority (WB2)
     float BusinessPricePriority;			// Business Price Priority (WB3)
     float BusinessTimePriority;			// Business Time Priority (WB4)
     float BusinessDestinationPriority;		// Business Destination Priority (WB5)
     float BusinessDepartingPriority;		// Business Departing Port Priority (WB6)
     float RemommendationLimitPriority;		//WB7: set to 10000
     float ItineraryUniquenessPriority;	    //WB8 : set to 0.001
   } 
   */
   //Table: ITNRTHNGTD																	      
   /*
   tuple ThingsToDo
   {
      key string ItineraryID;					//3-11-2013 Itinerary ID
      key string ThingsToDoID;  			    //3-11-2013  Things To Do ID     
   }
   */     
   //Table: CRUISE_PKG + Table: CRUISEPKG_PRICE
   //filter by User Input: <1> DateRange, <2> DiscountType, <3> # of Guests vs. MaxOccupancy
   /*
   tuple CruisePackage
   {
     key int CruisePackageID;
     string SailID;//3-11-2013     
     string PackageID;//3-11-2013
     string DateID;//3-11-2013
     int Date;							// Departure Date
     string ShipID;
     string ItineraryID;					// 3-11-2013 Itinerary ID 
     string Destination;			//3-11-2013      
     string DepartingPort;
     string RoomType;  					// Room Type  
     string Meta;//3-11-2013
     float Duration;
	 //float AccommodationPreference;			// Accommodation Preference (FP1)
	 //float InterestPreference;				// Interest Preference (FP21)
	 //float RoomTypeViolation;				// Room Type Violation (FC1)
	 //float ThingsToDoViolation;				// Things To Do Violation (FC2)
	 //float PriceViolation;					// Price Violation (FC3)
	 //float DateViolation;					// Date Violation (FC41)
	 //float DurationViolation;				// Duration Violation (FC42)
	 //float DestinationViolation;			// Destination Violation (FC5)
	 //float DepartingPortViolation;			// Departing Port Violation (FC6)
	 float DrupalWeight;					// Drupal Weight             
	 //string DiscountType;
	 //float PricePerGuest;                                                                                                               
   }
   
   tuple CruisePackagePrice
   {
     key int CruisePackageID;
     //string DiscountType;
     float PricePerGuest;
   }     
   */
   
   //Table: ITNRSIM  
   /* 
   tuple ItinerarySimilarity
   {
	 key string Itinerary1;					// 3-11-2013 First Itinerary
	 key string Itinerary2;					// 3-11-2013 Second Itinerary
	 //3-11-2013 key int OptimRequestID;				// Optimization Request ID	 
   }       
   */
   //Table: INTRTHNGTD
   /*
   tuple InterestThingsToDo
   {
     //3-11-2013 key int OptimRequestID;				// Optimization Request ID
     key string InterestID;					//3-11-2013 Interest ID
     key string ThingsToDoID;					// 3-11-2013 Things to do ID
     //int ThingsToDoPrefMatch;				// FP22j
     //3-11-2013 int ThingsToDoSelected;				// Unused?
	 //float ThingsToDoViolation;				// Things to do Violation (FC2j)     
     float DrupalWeight;					// Drupal Weight        
   }       
   */   
   //Table: ACCSTATERM
   /*
   tuple AccommodationPreferenceScore
   {
     string ShipID;
     string RoomType;
     string PreferenceType;
     string Preference;
     int StartDate;
     int EndDate;
     float Score;
   }     
   */
   //Table: INTRMETA
   /*
   tuple InterestMetaScore
   {
     string InterestID;
     string ShipID;
     string Meta;
     int StartDate;
     int EndDate;
     float Score;
   }
   */

   //Table: INTRDEST
   /*
   tuple InterestDestinationScore
   {
     string InterestID;
     string Destination;
     int StartDate;
     int EndDate;
     float Score;
   }
   */
   //Table: INTRSHIP
   /*
   tuple InterestShipScore
   {
     string InterestID;
     string ShipID;
     int StartDate;
     int EndDate;
     float Score;
   }
   */
   //Table: BUSPENALTY
   /*
   tuple BusPenalty
   {
     string PenType;
     float DiffMin;
     float DiffMax;
     float PenWght;
   }
   */   
   //User Input from Web App:
   /*
   tuple RecommendationRequestLimit
   {										
      int NumPackageRecommendations;		// Non-zero for Use Case 4: Top Cruise Packages
      int NumThingsToDo;					// Non-zero for Use Case 2: Top Things To Do
      int NumDestinations;					// Non-zero for Use Case 3: Top Destinations
      int NumStateRoomTypes;     			// Non-zero for Use Case 1: Top Stateroom Types
   }
   */
   /*
   tuple AccommodationPreference
   {
     string PreferenceType;
     string Preference;    
   }     
   */
   /*
   tuple InterestRange
   {
     string InterestID;
   }     
   */
   /*
   tuple ThingsToDoRange
   {
     string ThingsToDoID;
   }          
   */
   
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
   
   /*    
   tuple RangeMinMax
   {
     float Min;
     float Max;
   }     
   */
   
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
   //float ConstraintViolationUpperBound[1..1] = ...;	// Use to distinguish between implementation of:
   												// Model 0:  Upper bound on total constraint violations is 0.0
   												// Model 1:  Upper bound on total constraint violations is infinite
   												// Model 2:  Upper bound on total constraint violations is objective value for Model 1
 
   // Input Data
   //3-11-2014 {Itinerary} 						Input_Itinerary = ...;  
   //{InterestRange} Input_InterestRange=...;
   //{InterestMetaScore} Input_InterestMetaScore=...;   
   //{InterestDestinationScore} Input_InterestDestinationScore=...;
   //{InterestShipScore} Input_InterestShipScore=...;  
   //{ThingsToDo}	Input_ThingsToDo = ...;
   //{AccommodationPreferenceScore} Input_AccommodationPreferenceScore = ...;
   //{AccommodationPreference} Input_AccommodationPreference = ...;  
   //{CruisePackage} Input_CruisePackage = ...;
   //{CruisePackagePrice} Input_CruisePackagePrice = ...;
   //{Meta} Input_MetaRange = ...;
   //{Destination} Input_DestinationRange=...;
   //{DepartingPort} Input_PortRange = ...;
   //{ThingsToDoRange} Input_ThingsToDoRange = ...;
   //{BusPenalty} Input_BusPenalty = ...;  
   //{RangeMinMax} Input_DurationRange = ...; 
   //{RangeMinMax} Input_SailDateRange=...;
   //{RangeMinMax} Input_PricePerGuestRange=...;
   {AccommodationPreferenceScore} Input_AccommodationPreferenceScore1 = { <s.ShipID,s.RoomType,s.PreferenceType,s.Preference,s.StartDate,s.EndDate,s.Score> 
 									  |s in Input_AccommodationPreferenceScore, a in Input_AccommodationPreference: 
   														a.PreferenceType==s.PreferenceType && a.Preference==s.Preference };

   {Itinerary} Input_Itinerary = {<i.ItineraryID> | i in Input_ThingsToDo};
   int ItineraryCount=sum(i in Input_Itinerary) 1;
   int ItinerarySim[0..ItineraryCount][0..ItineraryCount];	
   	
   //dexpr float CruisePackageFP1[i in Input_CruisePackage]= sum(s in Input_AccommodationPreferenceScore1: 
   //														i.Date>=s.StartDate && i.Date<=s.EndDate && i.ShipID==s.ShipID && i.RoomType==s.RoomType ) s.Score/3.0;
   
   int CruisePackagePriceCount = sum(n in Input_CruisePackagePrice) 1;
   range CruisePkgIdRange = 1..CruisePackagePriceCount;
   CruisePackagePrice CruisePackagePrices[CruisePkgIdRange];
   	
   //string DiscountType[Input_CruisePackage];
   float PricePerGuest[Input_CruisePackage];
   float CruisePackageFP1[Input_CruisePackage];											        
   float InterestCount;// = sum(i in Input_InterestRange) 1;
   //dexpr float CruisePackageFP211[i in Input_CruisePackage]= sum(n in Input_InterestRange, m in Input_InterestMetaScore: n.Interest==m.Interest && 
   //																	i.ShipID==m.ShipID && i.Meta==m.Meta && i.Date>=m.StartDate && i.Date<=m.EndDate) m.Score/InterestCount;   																	
   float CruisePackageFP211[Input_CruisePackage];
   //dexpr float CruisePackageFP212[i in Input_CruisePackage]= sum(n in Input_InterestRange, m in Input_InterestDestinationScore: n.Interest==m.Interest && 
   //																	 i.Destination==m.Destination && i.Date>=m.StartDate && i.Date<=m.EndDate) m.Score/InterestCount;   
   float CruisePackageFP212[Input_CruisePackage];
   //dexpr float CruisePackageFP213[i in Input_CruisePackage]= sum(n in Input_InterestRange, m in Input_InterestShipScore: n.Interest==m.Interest && 
   //																	 i.ShipID==m.ShipID && i.Date>=m.StartDate && i.Date<=m.EndDate) m.Score/InterestCount;   																					   																	 																					   																	 
   float CruisePackageFP213[Input_CruisePackage];  																	 																 																 
   																	 
   // Tuples to be created dynamically from static data, and used for filtering in constraints below
   
   // Unique staterooms are required for results in Use Case 1
   // Create a set of StateRooms from the Cruise Package data 
   {Meta} Metas = { <i.Meta> | i in Input_CruisePackage };   
        
   {Meta} MetaOutOfRange = {<m.Meta> | m in Metas} 
   							diff
   						   {<e.Meta> | e in Input_MetaRange};
  									
   //dexpr int CruisePackageFC1[i in Input_CruisePackage]=sum(m in MetaOutOfRange: m.Meta==i.Meta) 1;
   float CruisePackageFC1[Input_CruisePackage];
   
   // Unique destinations are required for results in Use Case 3
      
   //3-11-2013 {Destination} Destinations = { <i.Destination> | i in Input_Itinerary };     
   {Destination} Destinations = { <i.Destination> | i in Input_CruisePackage };  
   //Meta out of user's range
   {Destination} DestinationOutOfRange = {<m.Destination> | m in Destinations} 
   							diff
   						   {<e.Destination> | e in Input_DestinationRange};     									   
   //dexpr int CruisePackageFC5[i in Input_CruisePackage]=sum(m in DestinationOutOfRange: m.Destination==i.Destination) 1;
   float CruisePackageFC5[Input_CruisePackage];
   // Single Row Table
   
   {DepartingPort} DepartingPorts = {<m.DepartingPort> | m in Input_CruisePackage};
   {DepartingPort} DepartingPortOutOfRange={<m.DepartingPort> | m in DepartingPorts}
   											diff
   											{<e.DepartingPort> | e in Input_PortRange};
   float CruisePackageFC6[Input_CruisePackage];//Departing Port Violation
    
   //{ThingsToDoRange} ThingsToDos = { <t.ThingsToDoID> | t in Input_ThingsToDo};
   //{ThingsToDoRange} ThingToDoOutOfRange = {<t.ThingsToDoID> | t in ThingsToDos}
   //											diff
   //											{<h.ThingsToDoID> | h in Input_ThingsToDoRange};
   float ThingsToDoCount;
   float CruisePackageFC2[Input_CruisePackage];
   float CruisePackageFC41[Input_CruisePackage];   
   float CruisePackageFC42[Input_CruisePackage];
   float CruisePackageFC3[Input_CruisePackage];
   int ItineraryIndex[Input_Itinerary];
   
   int CruisePackageItineraryIndex[Input_CruisePackage];
      
   //{Parameters}						Input_Parameters = ...;//3-11-2013 [ 1..1 ] = ...;
    
   //{ItinerarySimilarity}			Input_ItinerarySimilarity = ...;
   //{InterestThingsToDo}				Input_InterestThingsToDo = ...;
   float ThingsToDoFP22[Input_InterestThingsToDo];
       											     
   // Single Row Table
   //{RecommendationRequestLimit}		Input_RecommendationRequestLimit = ...; 
   
   // Determine the use case and associated recommendation limit, which is the number of results
   // to be returned
   execute INITIALIZE_MODEL
   {   
   	   var index=0; 
	   for(var si0 in Input_Itinerary){
	     index=index+1;
	     ItineraryIndex[si0]=index;
       }	     
       //writeln("index="+index+" ItineraryCount="+ItineraryCount);
       for( var cri in Input_CruisePackage){
         CruisePackageItineraryIndex[cri]=0;
         for(var si6 in Input_Itinerary){
           if(cri.ItineraryID==si6.ItineraryID){
             CruisePackageItineraryIndex[cri]=ItineraryIndex[si6];
             //writeln(cri.ItineraryID+" "+ItineraryIndex[si6]);
             break;
           }             
         }           
       }         
       ItinerarySim[0][0]=0;
   	   for(var si1 in Input_Itinerary){
   	    ItinerarySim[ItineraryIndex[si1]][0]=0;
   	   	for(var	si2 in Input_Itinerary){
   	   	  	ItinerarySim[0][ItineraryIndex[si2]]=0;
   	   	  	if(si1.ItineraryID==si2.ItineraryID){
	   	   		ItinerarySim[ItineraryIndex[si1]][ItineraryIndex[si2]]=1;
	   	   		//writeln(si1.ItineraryID+" index="+ItineraryIndex[si1]+" "+si2.ItineraryID+" index="+ItineraryIndex[si2]);
        	}	   	   		
	   	   	else
	   	   		ItinerarySim[ItineraryIndex[si1]][ItineraryIndex[si2]]=0;
        }   	   		
       }        
       for(var si3 in Input_ItinerarySimilarity){
         for(var si4 in Input_Itinerary){
           if(si3.Itinerary1==si4.ItineraryID){
             for(var si5 in Input_Itinerary){
               if(si3.Itinerary2==si5.ItineraryID){
                 ItinerarySim[ItineraryIndex[si4]][ItineraryIndex[si5]]=1;
                 ItinerarySim[ItineraryIndex[si5]][ItineraryIndex[si4]]=1;
                 //writeln(si4.ItineraryID+" index="+ItineraryIndex[si4]+" "+si5.ItineraryID+" index="+ItineraryIndex[si5]);
                 break;
               }                 
             }               
             break;
           }             
         }           
       }          
   	   var diff;
   	   for( var t0 in Input_InterestRange)      
	    	InterestCount = InterestCount+ 1;
   	   for( var t in Input_ThingsToDoRange)      
	    	ThingsToDoCount = ThingsToDoCount+ 1;	
       var cnt=0;
       for(var cr in Input_CruisePackagePrice){
         cnt=cnt+1;
         CruisePackagePrices[cnt]=cr;
       }                  
       cnt=1;
       //writeln("CruisePackagePriceCount="+CruisePackagePriceCount);
       for( var i in Input_CruisePackage){
      	    //DiscountType[i]="NONE";
      	    PricePerGuest[i]=0;
      	    if(CruisePackagePriceCount==0){
          	   writeln("Error: Empty Cruise Package Price Data Set!!!");     	      
      	       break;
            }      	       
      	    while(CruisePackagePrices[cnt].CruisePackageID<i.CruisePackageID && cnt<CruisePackagePriceCount){
      	      cnt=cnt+1;
      	      //writeln("cnt="+cnt);
            }      	      
      	    if(CruisePackagePrices[cnt].CruisePackageID==i.CruisePackageID){
      	        //writeln("i.CruisePackageID="+i.CruisePackageID+" c1="+CruisePackagePrices[cnt].CruisePackageID);
      	        //DiscountType[i]=CruisePackagePrices[cnt].DiscountType;
      	        PricePerGuest[i]=CruisePackagePrices[cnt].PricePerGuest;
            }
            else{
				writeln("Error: Can't find Price for Cruise Package ID="+i.CruisePackageID);     	      
      	       	break;              
            }                                           
      	  	CruisePackageFP1[i]=0;
      	  	CruisePackageFP211[i]=0;
       	  	CruisePackageFP212[i]=0;
       	  	CruisePackageFP213[i]=0;      	  	     	  	
      	  	for( var s in Input_AccommodationPreferenceScore1){
      	  	  	if(i.Date>=s.StartDate && i.Date<=s.EndDate && i.ShipID==s.ShipID && i.RoomType==s.RoomType)
      			CruisePackageFP1[i]	=  CruisePackageFP1[i]+s.Score/3.0;
      		}      		
   		   	for(var n in Input_InterestRange){
   		   	  	for(var  m in Input_InterestMetaScore){
   		   	  	  if(n.InterestID==m.InterestID && i.ShipID==m.ShipID && i.Meta==m.Meta && i.Date>=m.StartDate && i.Date<=m.EndDate)
   		   	  	  CruisePackageFP211[i]=CruisePackageFP211[i]+ m.Score/InterestCount;
           		}   		   	    
         	}   	
    		for(var n1 in Input_InterestRange){
   		   	  	for(var  m1 in Input_InterestDestinationScore){
   		   	  	  if(n1.InterestID==m1.InterestID && i.Destination==m1.Destination && i.Date>=m1.StartDate && i.Date<=m1.EndDate)
   		   	  	  CruisePackageFP212[i]=CruisePackageFP212[i]+ m1.Score/InterestCount;
           		}   		   	    
         	}  
   		   	for(var n2 in Input_InterestRange){
   		   	  	for(var  m2 in Input_InterestShipScore){
   		   	  	  if(n2.InterestID==m2.InterestID && i.ShipID==m2.ShipID  && i.Date>=m2.StartDate && i.Date<=m2.EndDate)
   		   	  	  CruisePackageFP213[i]=CruisePackageFP213[i]+ m2.Score/InterestCount;
           		}   		   	    
         	}    
         	CruisePackageFC1[i]=0;
         	for(var m3 in  MetaOutOfRange)
         	{
         	  	if(m3.Meta==i.Meta)
	         		CruisePackageFC1[i]= 1;     	   																					   	      	 	
        	} 
          	CruisePackageFC5[i]=0;
         	for(var m4 in  DestinationOutOfRange)
         	{
         	  	if(m4.Destination==i.Destination)
	         		CruisePackageFC5[i]= 1;     	   																					   	      	 	
        	}        	        	
          	CruisePackageFC6[i]=0;
         	for(var m5 in DepartingPortOutOfRange)
         	{
         	  	if(m5.DepartingPort==i.DepartingPort)
	         		CruisePackageFC6[i]= 1;     	   																					   	      	 	
        	}  
        	CruisePackageFC2[i]=ThingsToDoCount;    
        	if(ThingsToDoCount>0.001){
	        	for(var t1 in Input_ThingsToDo){
    	    	  	if(i.ItineraryID==t1.ItineraryID){
	    	    		for(var t2 in Input_ThingsToDoRange){
    	    		  		if(t1.ThingsToDoID == t2.ThingsToDoID)
        			  			CruisePackageFC2[i]=CruisePackageFC2[i]-1;
              			}	        			  			
             		}        		  			
         		}   	
         		CruisePackageFC2[i]=CruisePackageFC2[i]/ThingsToDoCount;
        	} 
        	 
        	CruisePackageFC3[i]=0;
	       	diff=9999999;
	       	for(var d5 in Input_PricePerGuestRange){
	       	  	//writeln("min="+d3.Min+" max="+d3.Max);
   	  	  	  	if(PricePerGuest[i]>=d5.Min && PricePerGuest[i]<=d5.Max){
   	  	  	  	  	//writeln("break");
   	  	  	  	  	diff=0;
   	  	  	    	break;
          		}
          		else if(PricePerGuest[i]<d5.Min && diff>d5.Min-PricePerGuest[i]){
   	  	  	      diff=d5.Min-PricePerGuest[i];
   	  	  		}	       	  	  	     
           	  	else if(PricePerGuest[i]>d5.Max && diff>PricePerGuest[i]-d5.Max){
           		  diff= PricePerGuest[i]-d5.Max;
              	}                   		   	 
          	}          		     		
          	//writeln("price="+PricePerGuest[i]+" diff="+diff);
            for(var d6 in Input_BusPenalty){	
            	if(d6.PenType=="PRICE"){ 
            		//writeln("DiffMin="+d4.DiffMin);
            		if(diff>d6.DiffMin && diff<=d6.DiffMax){
            		   CruisePackageFC3[i]=d6.PenWght;
            		   //writeln("PenEght="+CruisePackageFC3[i]);
            		   break;
               		}            		    
           		}            	
           	}	      	
           	//writeln("CruisePackageFC3="+CruisePackageFC3[i]);
           	        	              
        	CruisePackageFC41[i]=0;
	       	diff=9999999;
	       	for(var d3 in Input_SailDateRange){
	       	  	//writeln("min="+d3.Min+" max="+d3.Max);
   	  	  	  	if(i.Date>=d3.Min && i.Date<=d3.Max){
   	  	  	  	  	//writeln("break");
   	  	  	  	  	diff=0;
   	  	  	    	break;
          		}
          		else if(i.Date<d3.Min && diff>d3.Min-i.Date){
   	  	  	      diff=d3.Min-i.Date;
   	  	  		}	       	  	  	     
           	  	else if(i.Date>d3.Max && diff>i.Date-d3.Max){
           		  diff= i.Date-d3.Max;
              	}                   		   	 
          	}          		     		
          	//writeln("date="+i.Date+" diff="+diff);
            for(var d4 in Input_BusPenalty){	
            	if(d4.PenType=="SAILDATE"){ 
            		//writeln("DiffMin="+d4.DiffMin);
            		if(diff>d4.DiffMin && diff<=d4.DiffMax){
            		   CruisePackageFC41[i]=d4.PenWght;
            		   //writeln("PenEght="+CruisePackageFC41[i]);
            		   break;
               		}            		    
           		}            	
           	}	      	
           	//writeln("CruisePackageFC41="+CruisePackageFC41[i]);
	       	CruisePackageFC42[i]=0;
	       	diff=9999999;
	       	for(var d1 in Input_DurationRange){
	       	  //writeln("min="+d1.Min+" max="+d1.Max);
   	  	  	  	if(i.Duration>=d1.Min && i.Duration<=d1.Max){
   	  	  	  	  	       	  //writeln("break");
   	  	  	  	  	diff=0;
   	  	  	    	break;
          		}
          		else if(i.Duration<d1.Min && diff>d1.Min-i.Duration){
   	  	  	      diff=d1.Min-i.Duration;
   	  	  		}	       	  	  	     
           	  	else if(i.Duration>d1.Max && diff>i.Duration-d1.Max){
           		  diff= i.Duration-d1.Max;
              	}                   		   	 
          	}          		     		
          	//writeln("duration="+i.Duration+" diff="+diff);
            for(var d2 in Input_BusPenalty){	
            	if(d2.PenType=="DURATION"){ 
            		//writeln("DiffMin="+d2.DiffMin);
            		if(diff>d2.DiffMin && diff<=d2.DiffMax){
            		   CruisePackageFC42[i]=d2.PenWght;
            		   //writeln("PenEght="+CruisePackageFC42[i]);
            		   break;
               		}            		    
           		}            	
           	}	       	           	         		
        }          				
        
        for( var i1 in Input_InterestThingsToDo){
             ThingsToDoFP22[i1]=0;
             for(var i2 in Input_InterestRange){
               if(i1.InterestID==i2.InterestID)
               	ThingsToDoFP22[i1]=1;
             }               
        }          
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
  
  // Make sure that we have a use case selected and a desired number of 
  // results to be returned
  /*
  assert
  {
    Selected_Use_Case_Not_Set_to_Valid_Value:
    SelectedUseCase > 0;
  }
  */
  /*
  assert
  {        
    Number_of_Requested_Recommendations_Must_Be_Greater_Than_Zero:
    RequestedRecommendations > 0;    
  }      
  */
   // Optimization Variables
   
   // The objective function value to be optimized   
   dvar float Objective;
      
   // Total Constraint Violations
   dvar float TotalConstraintViolations;
      
   // X = 1 if a cruise package is selected, otherwise X = 0.
   dvar boolean X[ Input_CruisePackage ];
   
   // Y = 1 if a thing to do is selected, otherwise Y = 0.
   dvar boolean Y[ Input_InterestThingsToDo ];
   
   // Z_s = 1 if a Stateroom is selected, otherwise Z_s = 0.  (Use Case 1)
   dvar boolean Z_s[ Metas ];
       
   // Z_d1 = 1 if a destination name is selected, otherwise Z_d1 = 0.  (Use Case 3)
   dvar boolean Z_d1[ Destinations ];
   
   //dvar boolean CruisePackageFC1[Input_CruisePackage];// = sum(m in Input_MetaRange:m.Meta==i.Meta) 1;	  
   //dvar boolean CruisePackageFC5[Input_CruisePackage];
   
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
   //dvar float+ ItinSlack[ Input_CruisePackage][Input_CruisePackage];
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
   //dexpr  float ThingsToDoConstraintViolation2	 = sum( i in Input_CruisePackage ) X[ i ] ;//* CruisePackageFC2[i];
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
 
   dexpr float OBJ_AccommodationPreferenceContribution = (1-UnBounded) * AccommodationPreferenceContribution; 
   dexpr float OBJ_ThingsToDoPreferenceContribution = (1-UnBounded) * ThingsToDoPreferenceContribution;
   dexpr float OBJ_DrupalWeightContribution =  (1-UnBounded) * DrupalWeightContribution;  
   dexpr float OBJ_AccommodationConstraintViolation =  - UnBounded * AccommodationConstraintViolation;
   dexpr float OBJ_ThingsToDoConstraintViolation =  - UnBounded * ThingsToDoConstraintViolation;
   dexpr float OBJ_PriceConstraintViolation = - UnBounded * PriceConstraintViolation ;
   dexpr float OBJ_DurationConstraintViolation =  - UnBounded * DurationConstraintViolation;
   dexpr float OBJ_DestinationConstraintViolation = - UnBounded * DestinationConstraintViolation;
   dexpr float OBJ_DepartingPortConstraintViolation = - UnBounded * DepartingPortConstraintViolation;
   dexpr float OBJ_TotalRecommendationLimitViolation = - UnBounded * TotalRecommendationLimitViolation;
   dexpr float OBJ_TotalItineraryUniquenessLimitViolation = - UnBounded * TotalItineraryUniquenessLimitViolation;  
    		     			  
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
    		     						   		p.BusinessTimePriority * ( FC41 + FC42 );
   
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
    if ( ModelID != 1 )
    {     
      // Determination of Accommodation Preference FP1 and Interest Preference FP2: depends on Use Case
      if ( SelectedUseCase != 2 )
      {
        CalcFP1:		RequestedRecommendations * FP1 == sum( i in Input_CruisePackage)  X[ i ] * CruisePackageFP1[i];
        CalcFP2:		RequestedRecommendations * FP2 == sum( i in Input_CruisePackage )  X[ i ] *(CruisePackageFP211[i]+CruisePackageFP212[i]+CruisePackageFP213[i])/3.0;     
      }       
    
      else
      {
        // SelectedUseCase = 2
        CalcFP1_2:	FP1 == 0;
        CalcFP2_2:	RequestedRecommendations * FP2 == sum( j in Input_InterestThingsToDo ) Y[ j ] * ThingsToDoFP22[j];            
      }       
    }    
    
 
    // Constraint violations
    
    // Determination of FC1: Stateroom Constraint Violation
    //StateRoomViolation:				
    RequestedRecommendations * FC1 == sum( i in Input_CruisePackage)  X[ i ]*CruisePackageFC1[i];
    
    // Determination of FC2: Things To Do Constraint Violation
    ThingsToDoViolation:	   												
    RequestedRecommendations * FC2 == sum( i in Input_CruisePackage ) X[ i ] * CruisePackageFC2[i];
    
    // Determination of FC3: Price Constraint Violation
    PriceViolation:					
    RequestedRecommendations * FC3 == sum( i in Input_CruisePackage )  X[ i ] * CruisePackageFC3[i];
        
    // Determination of FC41: Date Violation
    DateViolation:					
    RequestedRecommendations * FC41 == sum( i in Input_CruisePackage ) X[ i ] * CruisePackageFC41[i];
    
    // Determination of FC42: Duration Violation
    DurationViolation:				
    RequestedRecommendations * FC42 == sum( i in Input_CruisePackage ) X[ i ] * CruisePackageFC42[i];
      
    // Determination of FC5: Destination Constraint Violation
    DestinationViolation:	
    RequestedRecommendations * FC5 == sum( i in Input_CruisePackage,m in DestinationOutOfRange:m.Destination==i.Destination )  X[ i ]*CruisePackageFC5[i];
        
    // Determination of FC6: Departing Port Constraint Violation
    DepartingPortViolation:	
    RequestedRecommendations * FC6 == sum( i in Input_CruisePackage )  X[ i ] * CruisePackageFC6[i];
    
    // Calculation of Recommendation Limit Violations (slack variables)
    /* bug    
    Package_Recommendation_Slack:
	    sum( i in Input_CruisePackage ) X[ i ] + PackageRecommendationSlack == RequestedRecommendations;
    
    Things_To_Do_Slack:
    	sum( j in Input_InterestThingsToDo ) Y[ j ] + ThingsToDoSlack == RequestedRecommendations;
    
    Destination_Slack:  
   		 sum( i in Input_CruisePackage ) X[ i ] + DestinationSlack == RequestedRecommendations;
    
    Stateroom_Slack:
    	sum( i in Input_CruisePackage ) X[ i ] + StateroomSlack == RequestedRecommendations;
    */ 
     
    // Itinerary Uniqueness Violation
     
    //Itinerary_Slack_a:
    //bug sum( i in Input_CruisePackage ) X[ i ] + ItinerarySlack == RequestedRecommendations;
    
    /* too slow:
    Itinerary_Slack_b:
    if ( SelectedUseCase == 4 )
    {
	    //forall( s in Input_ItinerarySimilarity, i in Input_CruisePackage, j in Input_CruisePackage : s.Itinerary1 == i.ItineraryID && s.Itinerary2 == j.ItineraryID )
    	//{
      	//	X[ i ] + X[ j ] - ItinSlack[ s ] <= 1;
    	//}
 	    forall(i in Input_CruisePackage, j in Input_CruisePackage )
    	{
    	  if(ItinerarySim[CruisePackageItineraryIndex[i]][CruisePackageItineraryIndex[j]]==1)
      		X[ i ] + X[ j ] - ItinSlack[ i ][j] <= 1;
    	}   	
    } */
                  
    // Calculation of Itinerary Uniqueness Slack.  Here, we only sum the terms
    // that were used in the constraint above.  Other elements of ItinSlack are not used
    // as the variable has dimensions of [ Input_CruisePackage ][ Input_CruisePackage ].  This generates an OPL warning 
    // that can safely be ignored.
    Itinerary_Slack_c:
    //ItinerarySlack == sum( s in Input_ItinerarySimilarity ) ItinSlack[ s ];
    if(SelectedUseCase == 4)
	    ItinerarySlack == sum( i in Input_CruisePackage) 
    					sum(j in Input_CruisePackage: ItinerarySim[CruisePackageItineraryIndex[i]][CruisePackageItineraryIndex[j]]==1
    							 && i.CruisePackageID<j.CruisePackageID)
      							maxl( 0, X[i]+X[j]-1);
      							
    // Additional constraints, based on the specified use case
    if ( SelectedUseCase == 1 )
    {
      // Top Stateroom results are required, with no duplicates
      
      // Must recommend the specified number of results                       
      
   	  NumberOfSelectedPackages_1:       
      sum( i in Input_CruisePackage ) X[ i ] <= RequestedRecommendations;
      
      NumberOfSelectedStaterooms_1://bug: missing slack           
	  sum( k in Metas ) Z_s[ k ] + StateroomSlack == RequestedRecommendations;
   
      //This constraint selects the room type for each selected package      
      forall( k in Metas )
      {  
        RoomTypeSelection_1:
        Z_s[ k ] <= sum( i in Input_CruisePackage: i.Meta == k.Meta ) X[ i ];
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
      sum( j in Input_InterestThingsToDo ) Y[ j ] + ThingsToDoSlack == RequestedRecommendations;
      
      // Constrain Y[ j ] to be things to do associated with X[ i ] - See slide 25      
	  forall( j in Input_InterestThingsToDo )
      {
        TopThingsToDo_2a:
              Y[ j ] <= sum( t in Input_ThingsToDo : t.ThingsToDoID == j.ThingsToDoID ) sum( i in Input_CruisePackage :  t.ItineraryID == i.ItineraryID ) X[ i ];
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
      sum( i in Input_CruisePackage ) X[ i ] <= RequestedRecommendations;
            
      NumberOfSelectedPackages_3a:
      sum( d in Destinations ) Z_d1[ d ] + DestinationSlack == RequestedRecommendations;  
                
      // This constraint selects the destination name for each selected destination ID.
      // Note that this constraint is not required if each itinerary ID is associated with
      // a unique destination name.  
      forall( d in Destinations )
      { 
        DestinationSelection_3:        
        //3-11-2013 Z_d1[ d ] <= sum( k in Input_Itinerary: k.Destination == d.Destination )  sum( i in Input_CruisePackage: i.ItineraryID == k.ItineraryID ) X[ i ]; 
        Z_d1[ d ] <=  sum( i in Input_CruisePackage: i.Destination == d.Destination ) X[ i ];               
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
      sum( i in Input_CruisePackage ) X[ i ] + PackageRecommendationSlack == RequestedRecommendations;
                      
    } 
    else{
      PackageRecommendationSlack==0;
    }      

    
    // Drupal Weight Contributions for all Use Cases except 2
    if ( ModelID == 0 || ModelID == 2 ){
	    if ( SelectedUseCase != 2 )
	    {
	      // Drupal Weight Contribution is determined from weights in Input_CruisePackage
	      DrupalWeightContribution_i:
	      DrupalWeightContribution == sum( i in Input_CruisePackage )  X[ i ] * i.DrupalWeight;    
	    }          
	    else
	    {
	      // Use Case 2 has been selected
	      
	      // Drupal Weight Contribution is determined from weights in Input_InterestThingsToDo
	      DrupalWeightContribution_j:
	      DrupalWeightContribution == sum( j in Input_InterestThingsToDo ) Y[ j ] * j.DrupalWeight;      
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
   tuple CruisePackageRecommendation
   {     
     string SailID;
     string PackageID;
     key string DateID;								// Date
     key string ItineraryID;						// Itinerary ID
     //3-11-2013 key int OptimRequestID;
     string Destination;						// Destination     
     key string RoomType;						// Room Type
     string Meta;
     float AccPrefContribution;					// Accommodation Preference Objective Contribution
     float InterestPrefContribution;			// Interests Preference Objective Contribution
     float RoomViolationValue;					// Stateroom Constraint Violation Value
     
     float ThingsToDoViolationValue;			// Things To Do Violation Value
     
     float PriceViolationValue;					// Price Constraint Violation Value
     float DateViolationValue;					// Date Constraint Violation Value
     float DurationViolationValue;				// Duration Constraint Violation Value
     float DestinationViolationValue;			// Destination Constraint Violation Value
     float PortViolationValue;					// Departing Port Constraint Violation Value
     float DrupalWeightContribution;			// Business Influence Objective Contribution
     //string DiscountType;
     float PricePerGuest;
     int IsRecommended;     					// Recommendation Flag     
   }
   */
   {CruisePackageRecommendation}		
   output_CruisePackageRecommendation = 	
   											{<i.SailID,i.PackageID,i.DateID,i.ItineraryID,i.Destination,i.RoomType,i.Meta,
   												n.CustomerAccommodationPriority * n.BusinessAccommodationPriority * CruisePackageFP1[i],
   												n.CustomerThingsToDoPriority * n.BusinessThingsToDoPriority *(CruisePackageFP211[i]+CruisePackageFP212[i]+CruisePackageFP213[i])/3.0,
   												n.CustomerAccommodationPriority * n.BusinessAccommodationPriority * CruisePackageFC1[i],
												n.CustomerThingsToDoPriority * n.BusinessThingsToDoPriority * CruisePackageFC2[i],
												n.CustomerPricePriority * n.BusinessPricePriority * CruisePackageFC3[i],   
												n.CustomerTimePriority * n.BusinessTimePriority * CruisePackageFC41[i],
												n.CustomerTimePriority * n.BusinessTimePriority * CruisePackageFC42[i],		
												n.CustomerDestinationPriority * n.BusinessDestinationPriority * CruisePackageFC5[i],
   												n.BusinessDepartingPriority * CruisePackageFC6[i],			 
   												i.DrupalWeight, //DiscountType[i],
   												PricePerGuest[i],1> 
   												| i in Input_CruisePackage: X[i]>0, n in Input_Parameters }
   											union 
    										{<i.SailID,i.PackageID,i.DateID,i.ItineraryID,i.Destination,i.RoomType,i.Meta,
   												n.CustomerAccommodationPriority * n.BusinessAccommodationPriority * CruisePackageFP1[i],
   												n.CustomerThingsToDoPriority * n.BusinessThingsToDoPriority * (CruisePackageFP211[i]+CruisePackageFP212[i]+CruisePackageFP213[i])/3.0,
   												0.0,
												0.0,
												0.0,   
												0.0,
												0.0,		
												0.0,
   												0.0,			 
   												i.DrupalWeight, //DiscountType[i],
   												PricePerGuest[i],0> 
   												| i in Input_CruisePackage: X[i]<1 &&
   												CruisePackageFC1[i] + CruisePackageFC2[i] + CruisePackageFC3[i] +
        					 					CruisePackageFC41[i] + CruisePackageFC42[i] +CruisePackageFC5[i] +
        					 					CruisePackageFC6[i] < 0.00001,
   												n in Input_Parameters  };  												
   /*
   tuple ThingsToDoRecommendation
   {
    //3-11-2013 key int OptimRequestID;
     //key string ItineraryID;
     key string ThingsToDoID;
     string InterestID;
     float InterestPrefContribution;			// Interests Preference Objective Contribution (WC2 * WB2 * FP22j)
     float DrupalWeightContribution;			// Business Influence Objective Contribution				
     			     				    
     int IsRecommended;     					// Recommendation Flag (1= Recommended, 0 = Not Recommended)
   } 
   */
   
   {ThingsToDoRecommendation}			output_ThingsToDoRecommendation =
   										{<j.ThingsToDoID,j.InterestID,
   											i.CustomerThingsToDoPriority * i.BusinessThingsToDoPriority * ThingsToDoFP22[j],
   											j.DrupalWeight, 1>
   										 	| j in Input_InterestThingsToDo: Y[j]>0, i in Input_Parameters }
   										union
   										{<j.ThingsToDoID,j.InterestID,
   											i.CustomerThingsToDoPriority * i.BusinessThingsToDoPriority * ThingsToDoFP22[j],
   											j.DrupalWeight, 0>
   										 	| j in Input_InterestThingsToDo: Y[j]<1, i in Input_Parameters,
   										 	c in Input_CruisePackage: CruisePackageFC1[c]+
   										 	CruisePackageFC2[c]+CruisePackageFC3[c]+CruisePackageFC41[c]+CruisePackageFC42[c]+CruisePackageFC5[c]
   										 	+CruisePackageFC6[c]<=0.00001
   										 	, t in Input_ThingsToDo: c.ItineraryID==t.ItineraryID && t.ThingsToDoID==j.ThingsToDoID }; 

   /*
   tuple ConstraintViolation
   {
	 //3-11-2013 key int OptimRequestID;
	 
	 float PackageLimitViolation;
	 float ThingsToDoLimViolation;
	 float DestinationLimViolation;
	 float RoomTypeLimViolation;
	 float ItineraryUniquenessViolation;         
   } 
   */									 	
   {ConstraintViolation}	output_ConstraintViolation =
   								{
									<PackageRecommendationSlack,ThingsToDoSlack,DestinationSlack,StateroomSlack,ItinerarySlack>								  										 	 										     
                                };   
	/*
	tuple Objectives {
	  float Total;
	  float AccommodationPreferenceContribution;
	  float ThingsToDoPreferenceContribution;
	  float DrupalWeightContribution;
	  float AccommodationConstraintViolation;
	  float ThingsToDoConstraintViolation;
	  float PriceConstraintViolation;
	  float DurationConstraintViolation;
	  float DestinationConstraintViolation;
	  float DepartingPortConstraintViolation;
	}
	*/
   	
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
	   }     
	   for(var oc3 in output_ThingsToDoRecommendation){
	     Output_ThingsToDoRecommendation.add(oc3);
	   }      
	   for(var oc4 in output_Objective){
	     Output_Objective.add(oc4);
	   } 	   
   	   /*
   	  // Index for tuple position
   	  var position = 0;
   	  
      // Cruise Package Recommendation: here we will return only those packages that were recommended
      for( var i in Input_CruisePackage )
      {
        var TotalViolation = i.RoomTypeViolation + i.ThingsToDoViolation + i.PriceViolation +
        					 i.DateViolation + i.DurationViolation + i.DestinationViolation +
        					 i.DepartingPortViolation;
        
		if ( X[ i ] == 1 || ( X[ i ] == 0 && TotalViolation == 0.0 ) )
		{
        	// Date
       		Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].DateID 		= i.DateID;
       	
       		// Itinerary
       		Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].ItineraryID = i.ItineraryID;
       	
       		// Stateroom Type
       		Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].RoomType 	= i.RoomType;
       	
       		// Destination       
       		Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].Destination 	= i.Destination;		
       		
       		Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].SailID 	= i.SailID;		
       		
       		Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].PackageID 	= i.PackageID;
       		Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].discountType 	= i.discountType;
         	Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].pricePerGuest 	= i.pricePerGuest;     		       			      		
       		//for ( var d in Input_Itinerary )
       		//{
       		//  if ( d.ItineraryID == i.ItineraryID )
       		//  {
       		//  	Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].Destination = d.Destination;
       		//  	break;
            //  }       		  	       		  
            //}       		  
       	
       		// Accommodation Preference Objective Contribution (WC1 * WB1 * FP1i)
       		Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].AccPrefContribution = Input_Parameters.CustomerAccommodationPriority * 
       																	  	Input_Parameters.BusinessAccommodationPriority * 
       																	  	i.AccommodationPreference;
   
   			// Interests Preference Objective Contribution (WC2 * WB2 * FP21i)
       		Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].InterestPrefContribution = Input_Parameters.CustomerThingsToDoPriority * 
       																	    Input_Parameters.BusinessThingsToDoPriority * 
       																	    i.InterestPreference;
   		
   			// Stateroom Constraint Violation Value (WC1 * WB1 * FC1i)
   			Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].RoomViolationValue = Input_Parameters.CustomerAccommodationPriority *
   																		 	Input_Parameters.BusinessAccommodationPriority *
   																		 	i.RoomTypeViolation;
   																		 	
   			// Stateroom Constraint Violation Value (WC2 * WB2 * FC2i)
   			Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].ThingsToDoViolationValue = Input_Parameters.CustomerThingsToDoPriority *
   																		 	Input_Parameters.BusinessThingsToDoPriority *
   																		 	i.ThingsToDoViolation;
   																		 	
   		
   			// Price Constraint Violation Value (WC3 * WB3 * FC3i)
   			Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].PriceViolationValue = Input_Parameters.CustomerPricePriority *
   																		  	Input_Parameters.BusinessPricePriority *
   																		  	i.PriceViolation;
  
  			// Date Constraint Violation Value (WC4 * WB4 * FC41i)
   			Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].DateViolationValue 	= Input_Parameters.CustomerTimePriority *
   																		  	Input_Parameters.BusinessTimePriority *
   																		  	i.DateViolation;
   																	 
			// Duration Constraint Violation Value (WC4 * WB4 * FC42i)   																	 
   			Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].DurationViolationValue = Input_Parameters.CustomerTimePriority *
   																		    Input_Parameters.BusinessTimePriority *
   																		    i.DurationViolation;
  		  
  			// Destination Constraint Violation Value (WC5 * WB5 * FC5i)
   			Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].DestinationViolationValue = Input_Parameters.CustomerDestinationPriority *
   																		    Input_Parameters.BusinessDestinationPriority *
   																		    i.DestinationViolation;

			// Departing Port Constraint Violation Value (WB6 * FC6i)
   			Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].PortViolationValue = Input_Parameters.BusinessDepartingPriority *
   																		 	i.DepartingPortViolation;
		
			// Business Influence Objective Contribution (Drupal Weight for package)
			Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].DrupalWeightContribution = i.DrupalWeight;
			
			
			
			// Logic for Recommendation Flag
			
			// Recommendation Flag
			if ( X[ i ] == 1 )
			{
				Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].IsRecommended = 1;
  			}
  			
  			else
  			{
				Output_CruisePackageRecommendation[ Opl.item( Input_CruisePackage, position ) ].IsRecommended = 0;
      		}  			  				
						
			position++;
 		}	
 		
		 		  			
	  }	*/	
		



      // Things To Do Recommendations: List all Things To Do where Yj = 1 or there is no constraint violation
   		
 	  // Reset the tuple position indicator  
      /*var position = 0;
   
	  for( var j in Input_InterestThingsToDo )
      {
        // Write data for recommended things as well as feasible things to do
        if ( Y[ j ] == 1 || j.ThingsToDoPrefMatch == 1 )
        {                                 
          // Interest ID
          Output_ThingsToDoRecommendation[ Opl.item( Input_InterestThingsToDo, position ) ].InterestID = 
          																  j.InterestID;    
                    
          // Things To Do
          Output_ThingsToDoRecommendation[ Opl.item( Input_InterestThingsToDo, position ) ].ThingsToDoID = 
          																  j.ThingsToDoID;

          
          // Interest Preference Objective Contribution (WC2 * WB2 * FP22j)
          Output_ThingsToDoRecommendation[ Opl.item( Input_InterestThingsToDo, position ) ].InterestPrefContribution = 
          																  Input_Parameters.CustomerThingsToDoPriority * 
          																  Input_Parameters.BusinessThingsToDoPriority *
          																  j.ThingsToDoPrefMatch;
                    
          // Business Influence Objective Contribution - Drupal Weight
          Output_ThingsToDoRecommendation[ Opl.item( Input_InterestThingsToDo, position ) ].DrupalWeightContribution = 
          																  j.DrupalWeight;
                    
          // Recommendation: this will be one if the item is selected, otherwise it will be zero
          if ( Y[ j ] == 1 )
          {
          	Output_ThingsToDoRecommendation[ Opl.item( Input_InterestThingsToDo, position ) ].IsRecommended = 1;
          }
            
          else
          {
          	Output_ThingsToDoRecommendation[ Opl.item( Input_InterestThingsToDo, position ) ].IsRecommended = 0;              
          }                        		  
          
          // Point to the next tuple to be written  
          position++;                      
        }                              
      }*/          


				
	  // Recommendation Limit Violations
	  	  		
	  /*	  		
	  // Package Limit Violation
	  Output_ConstraintViolation[ 1 ].PackageLimitViolation = PackageRecommendationSlack;
		
	  // Things to do Limit Violation
	  Output_ConstraintViolation[ 1 ].ThingsToDoLimViolation = ThingsToDoSlack;
		
	  // Destination Limit Violation
	  Output_ConstraintViolation[ 1 ].DestinationLimViolation = DestinationSlack;
		
	  // Accommodation Limit Violation
	  Output_ConstraintViolation[ 1 ].RoomTypeLimViolation = StateroomSlack;
		
	  // Uniqueness of Itinerary Violation
	  Output_ConstraintViolation[ 1 ].ItineraryUniquenessViolation = ItinerarySlack;			    
	  */ 
   }     
