// DO NOT EDIT: This file is automatically generated by ODM Enterprise.
// Any changes made directly to this file will be lost when it is
// automatically regenerated.

// Input

tuple AccommodationPreference {
  key string PreferenceType;
  key string Preference;
};

tuple AccommodationPreferenceScore {
  key string ShipID;
  key string RoomType;
  key string PreferenceType;
  key string Preference;
  key int StartDate;
  key int EndDate;
  key float Score;
};

tuple BusPenalty {
  key string PenType;
  key float DiffMin;
  key float DiffMax;
  key float PenWght;
};

tuple CruisePackage {
  key int CruisePackageID;
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
  float CabinCapacity;
};

tuple CruisePackagePrice {
  key int CruisePackageID;
  float PricePerGuest;
};

tuple DestinationRange {
  key string Destination;
};

tuple DurationRange {
  key float Min;
  key float Max;
};

tuple InterestDestinationScore {
  key string InterestID;
  key string Destination;
  key int StartDate;
  key int EndDate;
  key float Score;
};

tuple InterestMetaScore {
  key string InterestID;
  key string ShipID;
  key string Meta;
  key int StartDate;
  key int EndDate;
  key float Score;
};

tuple InterestRange {
  key string InterestID;
};

tuple InterestShipScore {
  key string InterestID;
  key string ShipID;
  key int StartDate;
  key int EndDate;
  key float Score;
};

tuple InterestThingsToDo {
  key string InterestID;
  key string ThingsToDoID;
  float DrupalWeight;
};

tuple ItinerarySimilarity {
  key string Itinerary1;
  key string Itinerary2;
};

tuple MetaRange {
  key string Meta;
};

tuple Parameters {
  key float CustomerAccommodationPriority;
  key float CustomerThingsToDoPriority;
  key float CustomerPricePriority;
  key float CustomerTimePriority;
  key float CustomerDestinationPriority;
  key float BusinessAccommodationPriority;
  key float BusinessThingsToDoPriority;
  key float BusinessPricePriority;
  key float BusinessTimePriority;
  key float BusinessDestinationPriority;
  key float BusinessDepartingPriority;
  key float RemommendationLimitPriority;
  key float ItineraryUniquenessPriority;
};

tuple PortRange {
  key string DepartingPort;
};

tuple PricePerGuestRange {
  key float Min;
  key float Max;
};

tuple RecommendationRequestLimit {
  key int NumPackageRecommendations;
  key int NumThingsToDo;
  key int NumDestinations;
  key int NumStateRoomTypes;
};

tuple SailDateRange {
  key float Min;
  key float Max;
};

tuple NumberOfGuests {
  int NumberOfGuests;
};

tuple ThingsToDo {
  key string ItineraryID;
  key string ThingsToDoID;
};

tuple ThingsToDoRange {
  key string ThingsToDoID;
};
float ConstraintViolationUpperBound [1..1] = ...;
{AccommodationPreference} Input_AccommodationPreference = ...;
{AccommodationPreferenceScore} Input_AccommodationPreferenceScore = ...;
{BusPenalty} Input_BusPenalty = ...;
{CruisePackage} Input_CruisePackage = ...;
{CruisePackagePrice} Input_CruisePackagePrice = ...;
{DestinationRange} Input_DestinationRange = ...;
{DurationRange} Input_DurationRange = ...;
{InterestDestinationScore} Input_InterestDestinationScore = ...;
{InterestMetaScore} Input_InterestMetaScore = ...;
{InterestRange} Input_InterestRange = ...;
{InterestShipScore} Input_InterestShipScore = ...;
{InterestThingsToDo} Input_InterestThingsToDo = ...;
{ItinerarySimilarity} Input_ItinerarySimilarity = ...;
{MetaRange} Input_MetaRange = ...;
{Parameters} Input_Parameters = ...;
{PortRange} Input_PortRange = ...;
{PricePerGuestRange} Input_PricePerGuestRange = ...;
{RecommendationRequestLimit} Input_RecommendationRequestLimit = ...;
{SailDateRange} Input_SailDateRange = ...;
{ThingsToDo} Input_ThingsToDo = ...;
{ThingsToDoRange} Input_ThingsToDoRange = ...;
{NumberOfGuests} Input_NumberOfGuests = ...;

// Output

tuple ConstraintViolation {
  key float PackageLimitViolation;
  key float ThingsToDoLimViolation;
  key float DestinationLimViolation;
  key float RoomTypeLimViolation;
  key float ItineraryUniquenessViolation;
};

tuple CruisePackageRecommendation {
  key string SailID;
  key string PackageID;
  key string DateID;
  key string ItineraryID;
  string Destination;
  key string RoomType;
  string Meta;
  float AccPrefContribution;
  float InterestPrefContribution;
  float RoomViolationValue;
  float ThingsToDoViolationValue;
  float PriceViolationValue;
  float DateViolationValue;
  float DurationViolationValue;
  float DestinationViolationValue;
  float PortViolationValue;
  float DrupalWeightContribution;
  float PricePerGuest;
  int IsRecommended;
};

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
};

tuple ThingsToDoRecommendation {
  key string ThingsToDoID;
  string InterestID;
  float InterestPrefContribution;
  float DrupalWeightContribution;
  int IsRecommended;
};
{ConstraintViolation} Output_ConstraintViolation;
{CruisePackageRecommendation} Output_CruisePackageRecommendation;
{Objectives} Output_Objective;
{ThingsToDoRecommendation} Output_ThingsToDoRecommendation;
