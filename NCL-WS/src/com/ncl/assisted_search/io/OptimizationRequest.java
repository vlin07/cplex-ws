package com.ncl.assisted_search.io;
import ilog.concert.*;
import ilog.cplex.*;
import ilog.opl.*;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.sql.*;
import java.io.*;
import java.text.SimpleDateFormat;
import oracle.jdbc.OracleDriver;

import com.ncl.assisted_search.io.AccommodationPreference;
import com.ncl.assisted_search.io.UserInput;


//Recover code of 4-9-2013 to run objDef=2 if needed
public class OptimizationRequest {
	private static String databaseURL="";//"jdbc:oracle:thin:@localhost:1521:xe";
	private static String databaseUserID="";//"ncl_ws";
	private static String databaseSchemaName="";//"ncl_ws";
	private static String databasePassword="";//"ncl_ws_password";
	private static String oplInputDataFile ="";//Cruise & User Data
	private static String oplDataSource="";//data in Table Cruise_Pkg
	private static String oplTempDirectory="";//Temp folder to store user's input
	//private static IloOplFactory oplF = new IloOplFactory();
	//private static IloOplDataSource dataSource = null;//oplF.createOplDataSource("NCL Assisted Search.dat");//input text data file for opl model testing		
    //private static IloOplErrorHandler errHandler = oplF.createOplErrorHandler();
    //private static IloOplModelSource modelSource = null;//oplF.createOplModelSource("NCL Assisted Search.mod");
    //private static IloOplSettings settings = oplF.createOplSettings(errHandler);
    //private static IloOplModelDefinition def = null;//oplF.createOplModelDefinition(modelSource, settings);
    //private static IloOplDataSource dataSource = oplF.createOplDataSource("NCL Assisted Search - Spreadsheet.dat");
	private static String dummy_opl_input = "Input_CruisePackage={};Input_CruisePackagePrice={};Input_PricePerGuestRange={};Input_SailDateRange={};Input_BusPenalty={};Input_DurationRange={};Input_AccommodationPreferenceScore={};Input_PortRange={};Input_MetaRange={};Input_DestinationRange={};Input_ThingsToDoRange={};Input_InterestRange={};Input_AccommodationPreference={};Input_InterestDestinationScore={};Input_InterestMetaScore={};Input_InterestShipScore={};Input_ThingsToDo={};Input_Parameters={};Input_ItinerarySimilarity={};Input_InterestThingsToDo={};Input_RecommendationRequestLimit={};ConstraintViolationUpperBound=[0];";
	private static String dummy_opl_input_name = "Dummy_Opl_Input";    
    //private static double constraintViolationAmount;//1/08/2013
    //private static ArrayList<CruisePackageRecommendation> cruisePackageData = new ArrayList();
    //private static ArrayList<ConstraintViolation> constraintViolationData = new ArrayList();
    //private static ArrayList<ThingsToDoRecommendation> ttdrData = new ArrayList();
	private static Properties properties = null;

 public OptimizationRequest()

 {//default class constructor
	
 }

 private static String init(String DEBUG)
 {
	 
	 if(properties == null)
	 {
		properties = new Properties();
		
		try {
			InputStream inputStream = OptimizationRequest.class.getClassLoader().getResourceAsStream("app.properties");
			if(DEBUG.equals("1")) System.out.println("Attempting create new properties() object");
			if(DEBUG.equals("1")) System.out.println("Attempting to load the inputStream into the properties Object");
			properties.load(inputStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 }

	databaseURL=properties.getProperty("databaseURL");
	databaseUserID=properties.getProperty("databaseUserID");
	databasePassword=properties.getProperty("databasePassword");
	databaseSchemaName=properties.getProperty("databaseSchemaName");
	oplInputDataFile=properties.getProperty("oplInputDataFile");
	oplDataSource=properties.getProperty("OplDataSource");	
	oplTempDirectory=properties.getProperty("oplTempDirectory");
	DEBUG = properties.getProperty("debug");
	System.out.println("Set DEBUG MODE:"+DEBUG);

	//if(DEBUG.equals("1")) System.out.println("Attempting createDataSource " + properties.getProperty("OplDataSource"));
	
	//dataSource = oplF.createOplDataSource(properties.getProperty("OplDataSource"));		

	//if(DEBUG.equals("1"))  System.out.println("Attempting createModelSource " + properties.getProperty("OplModelSource"));

	//		modelSource = oplF.createOplModelSource(properties.getProperty("OplModelSource"));

	//if(DEBUG.equals("1")) System.out.println("Finish modelSource ");

	//def = oplF.createOplModelDefinition(modelSource, settings);

	//if(DEBUG.equals("1")) System.out.println("Finish def ");
	
	dummy_opl_input = properties.getProperty("dummy_opl_input");
	dummy_opl_input_name = properties.getProperty("dummy_opl_input_name");    
	//System.out.println("Return DEBUG="+DEBUG);
	 
	return DEBUG;
 }
 
 
 //dataFresh="1": re-fetch data from Table Cruise_Pkg; otherwise use cache data
 //public static OptimizationResponseData Recommender(OptimizationRequestData ord0) throws Exception
 public OptimizationResponseData Recommender(UserInput userInput) throws Exception
 {
	String DEBUG="1";//turn it on to save input data in File oplInputDataFile;
	//String dataRefresh=properties.getProperty("dataFresh");//=1: re-fetch data from Table Cruise_Pkg =0: use cache data
	if(DEBUG.equals("1")) System.out.println("Start Recommender: "+ Calendar.getInstance().getTime());
	DEBUG=init(DEBUG);	 
	System.out.println("DEBUG MODE After init:"+DEBUG);
	IloOplFactory oplF = new IloOplFactory();
    IloOplErrorHandler errHandler = oplF.createOplErrorHandler();
    IloOplModelSource modelSource = oplF.createOplModelSource(properties.getProperty("OplModelSource"));//oplF.createOplModelSource("NCL Assisted Search.mod");
    IloOplSettings settings = oplF.createOplSettings(errHandler);
    IloOplModelDefinition def = oplF.createOplModelDefinition(modelSource, settings);//oplF.createOplModelDefinition(modelSource, settings);
    if(DEBUG.equals("1")) System.out.println("OPLModelDef Completed");
    if(DEBUG.equals("1")) IloOplFactory.setDebugMode(true);
    else IloOplFactory.setDebugMode(false);
	//IloCplex cplex0 = oplF.createCplex();
	//cplex0.setOut(null);
    //IloOplModel opl0 = oplF.createOplModel(def, cplex0);
    //IloOplDataSource dummyDataSource = oplF.createOplDataSourceFromString(dummy_opl_input, dummy_opl_input_name);
	//opl0.addDataSource(dummyDataSource);
	//opl0.generate();
	IloOplDataElements dataElements=null;// = opl0.makeDataElements();
    
	//writer2: store Cruise Data
    Writer writer2 = null;
    String dataRefresh="0";
	File cruisePkgFile = new File(oplDataSource);
	if(!cruisePkgFile.exists()){
        writer2 = new BufferedWriter(new FileWriter(cruisePkgFile));        	
        dataRefresh="1";
        if(DEBUG.equals("1")) System.out.println("Set dataRefresh=1: Re-Fetch Cruise Data From Staging Database");
        String[] tempFiles;
        File  tempDirectory= new File(properties.getProperty("oplTempDirectory")); 
        if(tempDirectory.isDirectory()){  
            tempFiles = tempDirectory.list();  
            for (int i=0; i<tempFiles.length; i++) {  
                File tempFile = new File(tempDirectory, tempFiles[i]);   
                tempFile.delete();  
                if(DEBUG.equals("1")) System.out.println("Delete Temp File: "+tempFile.getName());
            }  
         }  
	}
	
	//writer: store User Input Data
 	Writer writer = null;  
    File userInputFile = null;
	int session=0;
	boolean getNewSession=false;
	String tempOplFile=null;
	while(!getNewSession){
		session=session+1;
		tempOplFile=oplTempDirectory+"/NCLRecommender"+session+".dat";
    	userInputFile = new File(tempOplFile);
    	if(!userInputFile.exists()){
           writer = new BufferedWriter(new FileWriter(userInputFile));   
           if(DEBUG.equals("1")) System.out.println("Create User Input Data File: "+userInputFile.getName());
           getNewSession=true;
    	}
	}

	OptimizationResponseData ord1 = new OptimizationResponseData();
	// code for adding more input data to dataElements
	//inputData(dataElements,ord0);
	if(DEBUG.equals("1")) System.out.println("Running inputData()...");
	if(DEBUG.equals("1")) System.out.println("Write OplInputDataFile..."); 
	
	inputData(dataElements,userInput, DEBUG, writer, writer2, dataRefresh);
	try
 	{
 		 // generate plan:
 		ord1=generateCruiseRecommendation(dataElements, oplF, def, DEBUG, tempOplFile);
 		if(DEBUG.equals("1")){
 			File userInputFile2 = new File(properties.getProperty("oplInputDataFile"));
 			if(userInputFile2.exists()) userInputFile2.delete();
 			userInputFile.renameTo(userInputFile2);
 		}
 		else{
 			userInputFile.delete();
 		}
 	}
 	catch (Exception e)
 	{
 		System.out.println(" Exception: "+ e);
 	}
 	//opl0.end();
 	//cplex0.end();
 	oplF.end();
 	return ord1;
 }
 
 //test data from Oracle database
 public static void main_oracle(String[] args) throws Exception
 {	 
	String DEBUG="1";//turn it on to save input data in File oplInputDataFile; 
	DEBUG=init(DEBUG);	 	 
	try
 	{
		OptimizationRequest ORequest = new OptimizationRequest();
		UserInput userInput= new UserInput(DEBUG);
 		 // generate plan:
		OptimizationResponseData response=ORequest.Recommender(userInput);
 	}
 	catch (Exception e)
 	{
 		 System.out.println(" Exception: "+ e);
 	}
 }

 //test data from dat file: dataSource: for example: = oplF.createOplDataSource("NCL Assisted Search Debug.dat");
 
 public static void main(String[] args) throws Exception
 {
	String DEBUG="1";//turn it on to save input data in File oplInputDataFile; 
	DEBUG=init(DEBUG);	 	 
	System.out.println("DEBUG="+DEBUG);
	if(DEBUG.equals("1")) System.out.println("Start Recommender: "+ Calendar.getInstance().getTime());
	IloOplFactory oplF = new IloOplFactory();
	//IloOplDataSource dataSource = oplF.createOplDataSource(properties.getProperty("OplDataSource"));//oplF.createOplDataSource("NCL Assisted Search.dat");//input text data file for opl model testing		
    IloOplErrorHandler errHandler = oplF.createOplErrorHandler();
    IloOplModelSource modelSource = oplF.createOplModelSource(properties.getProperty("OplModelSource"));//oplF.createOplModelSource("NCL Assisted Search.mod");
    //System.out.println("oplmodelsource="+properties.getProperty("OplModelSource"));
    IloOplSettings settings = oplF.createOplSettings(errHandler);
    IloOplModelDefinition def = oplF.createOplModelDefinition(modelSource, settings);//oplF.createOplModelDefinition(modelSource, settings);
    	 
    if(DEBUG.equals("1")) IloOplFactory.setDebugMode(true);
    else IloOplFactory.setDebugMode(false);       
	//IloCplex cplex0 = oplF.createCplex();
    //IloOplModel opl0 = oplF.createOplModel(def, cplex0);
    //IloOplDataSource dummyDataSource = oplF.createOplDataSourceFromString(dummy_opl_input, dummy_opl_input_name);   
    //if(DEBUG.equals("1")) System.out.println("Adding dataSource: "+ Calendar.getInstance().getTime());
	//opl0.addDataSource(dummyDataSource);	        
	if(DEBUG.equals("1")) System.out.println("Generating OPL: "+ Calendar.getInstance().getTime());
	//opl0.generate();
	//if(DEBUG.equals("1")) System.out.println("Makeing DataElements: "+ Calendar.getInstance().getTime());
	IloOplDataElements dataElements = null;//opl0.makeDataElements();
    OptimizationResponseData ord1 = new OptimizationResponseData();
	// code for adding more input data to dataElements
	//inputData(dataElements,ord0);
    //System.out.println("generate");
	try
 	{
 		 // generate plan:
		OptimizationRequest OReguest = new OptimizationRequest();
		String tempOplFile=properties.getProperty("oplInputDataFile");
 		ord1=OReguest.generateCruiseRecommendation(dataElements, oplF, def, DEBUG,tempOplFile);
 	}
 	catch (Exception e)
 	{
 		if(DEBUG.equals("1")) System.out.println(" Exception: "+ e);
 	}
 	//opl0.end();
 	//cplex0.end();
 	oplF.end();
	 
 	//return ord1;
	 
 }
 
 public IloOplDataElements inputData(IloOplDataElements dataElements, UserInput userInput, String DEBUG, Writer writer, Writer writer2, String dataRefresh) throws Exception 
 {
	 //step 1:
	 //filter cruisepackge by date, maxoccupancy 
	 //filter discounttypeprice by date,discounttype and # of guests
	 //step 2: take about total 0.12 seconds for both sorts of 10000 records:
	 //sort cruisepackage by key: cruisepackageid
	 //sort discounttypeprice by cruisepackageid
     try {
        if(DEBUG.equals("1")) System.out.println("Define JDBC Calss...");
    	Class.forName("oracle.jdbc.driver.OracleDriver");
        DriverManager.registerDriver(new OracleDriver());        	        
    	//String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        Connection conn = DriverManager.getConnection(
        		 databaseURL,//url,       // URL
        		 databaseUserID,//"ncl_ws",//"SYSTEM",       // username
        		 databasePassword// "ncl_ws_password" //"28344nO5"        // password
        );
        if(DEBUG.equals("1")) System.out.println("Database Connected");
         /*
          jdbc:oracle:thin:@[host][:port]:SID			
			  username - The login user name defined in the Oracle server.
			  password - The password for the login user.
			  host - The host name where Oracle server is running. 
			         Default is 127.0.0.1 - the IP address of localhost.	
			  port - The port number where Oracle is listening for connection.
			         Default is 1521.
			  SID  - System ID of the Oracle server database instance. 
			         SID is a required value. By default, Oracle Database 10g Express 
			         Edition creates one database instance called XE.       
          */
        
         //IloTupleBuffer buf;
    	 Date currentTime = Calendar.getInstance().getTime();
         SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMdd");
         //System.out.println("time="+ft.format(currentTime.getTime()));
    	 
         Date yearStart = Calendar.getInstance().getTime();
         SimpleDateFormat ft0 = new SimpleDateFormat ("yyyy0101");
         String yearStartSt=ft0.format(yearStart.getTime());
         //System.out.println("yearStart="+yearStartSt);
         Date yearStartDate= (Date)ft.parse(yearStartSt);
         //int days0=(int) Math.ceil((currentTime.getTime()-yearStartDate.getTime())/86400000.0);
         //System.out.println("days0="+days0);         
    	 Statement statement = //userInput._DatabaseConnection.createStatement(
    			 conn.createStatement(
                 ResultSet.TYPE_SCROLL_INSENSITIVE,
                 ResultSet.CONCUR_UPDATABLE);
    	 if(DEBUG.equals("1")) System.out.println("Statement Defined");
    	 boolean firstRange=true;
    	 //CruisePackage:
    	 /*
    	 int dateAllowance=30;//allow 30 days deviation from user's preferred sail date range
		 for (Iterator itr = userInput._sailDateRangeList.iterator(); itr.hasNext();)
         {
			 firstRange=false;
        	 SailDateRange sdr=(SailDateRange) itr.next();
        	 double latestDays=0;
        	 if((sdr._latestDate.getTime()-currentTime.getTime())<0){
        		 latestDays=0;
         	 }
        	 else{
        		 latestDays=(sdr._latestDate.getTime()-currentTime.getTime())/86400000.0;
        	 }
        	 double earliestDays=0;
        	 if((sdr._earliestDate.getTime()-currentTime.getTime())<0){
        		 earliestDays=0;
         	 }
        	 else{
        		 earliestDays=(sdr._earliestDate.getTime()-currentTime.getTime())/86400000.0;
        	 }        	 
        	 if(latestDays-earliestDays>=28){
        		 dateAllowance=0;
        		 break;
        	 }
        	 else {
        			 if( (30-(latestDays-earliestDays)) < dateAllowance)
        				 dateAllowance= (int) Math.ceil((30-(latestDays-earliestDays)));
        	 }
        	 if(DEBUG.equals("1")) System.out.println(" ed="+sdr._earliestDate+" ld="+sdr._latestDate+ "eDays="+earliestDays+" lDays="+latestDays+
        			 " dateAllowance="+dateAllowance);
     	 }
		 dateAllowance=dateAllowance/2+1;
		 */
    	 int dateAllowance=30;
		 String query = "SELECT cruisepkg_id, itnrary, DC_SAIL_START, SAIL_ID, PACKAGE_ID, ship_code, META_NAME, CABIN_CATEGORY, SAIL_DAYS, PORT_FROM, destination, CABIN_CAPACITY, drupalwght FROM "+ databaseSchemaName +".CRUISE_PKG ";
		 String query1 = "";//"WHERE CABIN_CAPACITY>="+userInput._numberOfGuests;
		 
		 //String query3="";
		 //write complete 2-year cruise data to oplDataSource
		 //filter price data
		 String query3 =" AND (";
		 firstRange=true;
		 for (Iterator itr = userInput._sailDateRangeList.iterator(); itr.hasNext();)
         {
			 if (firstRange==false) query3=query3+" OR";
        	 SailDateRange sdr=(SailDateRange) itr.next();
        	 query3 = query3+" (DC_SAIL_START-TO_DATE("+ft.format(sdr._earliestDate)+", 'YYYYMMDD')>"+"-"+dateAllowance;
        	 query3 = query3+" AND DC_SAIL_START-TO_DATE("+ft.format(sdr._latestDate)+", 'YYYYMMDD')<"+dateAllowance+")";
        	 firstRange=false;
     	 }		 
		 if(userInput._sailDateRangeList.size()==0){
        	 query3 = query3+" (DC_SAIL_START-TO_DATE("+ft.format(currentTime.getTime())+", 'YYYYMMDD')>=0";
        	 query3 = query3+" AND DC_SAIL_START-TO_DATE("+ft.format(currentTime.getTime())+", 'YYYYMMDD')<=180)";
		 }
		 query3=query3+") ";
		 
		 
		 firstRange=true;
		 String query4 ="";
		 
		 /*
		 for (Iterator itr = userInput._departingPortList.iterator(); itr.hasNext();)
         {		
			 if(firstRange==true) query4=query4+" AND (";
			 if (firstRange==false) query4=query4+" OR ";
			 String pref=(String) itr.next();			 
			 query4=query4+"PORT_FROM =\'"+pref+"\'";
			 firstRange=false;
    	 }*/
		 /*
		 for (Iterator itr = userInput._destinationList.iterator(); itr.hasNext();)
         {		
			 if(firstRange==true) query4=query4+" AND (";
			 if (firstRange==false) query4=query4+" OR ";
			 String pref=(String) itr.next();			 
			 query4=query4+"destination =\'"+pref+"\'";
			 firstRange=false;
    	 }*/		 
		 if(firstRange==false) query4=query4+") ";
		 String query2 = " ORDER BY cruisepkg_id";
		 //query = query + query1+ query3+ query4;//+ query2;
		 query = query + query1+ query4;
		 // executing a query string and storing it into the resultSet object
		 if(DEBUG.equals("1")) System.out.println("Query="+query);
		 //ArrayList<Integer> pkgList = new ArrayList();
		 //ArrayList<String> itnList = new ArrayList();		 
		 //ArrayList<String> shipList = new ArrayList();			
		 //ArrayList<String> destList = new ArrayList();
		 /*
		 for(int i=10001;i<=40000;i++){
			String qry="insert into ncl_ws.cruise_pkg (cruisepkg_id, itnrary, DC_SAIL_START, SAIL_ID, PACKAGE_ID, SHIP_CODE, META_NAME, CABIN_CATEGORY, SAIL_DAYS, PORT_FROM, destination, CABIN_CAPACITY, drupalwght) values("
				+i+", 'SPIRIT9BCNFNCSCTACEAGPBCN', to_date('20130330', 'yyyymmdd'), 'SAIL_ID', 'PACKAGE_ID', 'SPIRIT', 'INTERIOR', 'I1', 9, 'BCN', 'EUROPE', 4, .7)";		
			statement.executeQuery(qry);
		 }*/
		 Date currentTime1 = Calendar.getInstance().getTime();
		 if(DEBUG.equals("1")) System.out.println("Query Cruise_PKG");
		 ResultSet resultSet=null;
		 if(dataRefresh.equals("1"))
			 resultSet=statement.executeQuery(query);

		 if(DEBUG.equals("1")) System.out.println("Query Cruise_PKG Complete");		 
		 //if(DEBUG.equals("1")){
		 if(dataRefresh.equals("1"))
			writer2.write("Input_CruisePackage={");
		 //}
		 /*	
		 Date currentTime1 = Calendar.getInstance().getTime();
		 System.out.println( Calendar.getInstance().getTime()+"Before reading Cruise_PKG");
		 while (resultSet.next()) {	
		 
		 }*/
		 Date currentTime2 = Calendar.getInstance().getTime();
		 System.out.println("After reading Cruise_PKG:"+(currentTime2.getTime()-currentTime1.getTime()));
		 
		 //resultSet.absolute(1);
	     if(dataRefresh.equals("1"))
		 while (resultSet.next()) {		
			
			//pkgList.add(resultSet.getInt("cruisepkg_id"));
			//itnList.add(resultSet.getString("itnrary"));
			/*if(!shipList.contains(resultSet.getString("SHIP_CODE"))){
				shipList.add(resultSet.getString("SHIP_CODE"));
				//System.out.println("ship="+resultSet.getString("SHIP_CODE"));
			}*/
	        Date saildate=resultSet.getDate("DC_SAIL_START");
        	
	        //int days=(int) Math.ceil((saildate.getTime()-currentTime.getTime())/86400000.0);
	        int days=(int) Math.ceil((saildate.getTime()-yearStartDate.getTime())/86400000.0);
	        
        	//if(DEBUG.equals("1")) System.out.println(" pkgid="+resultSet.getInt("cruisepkg_id")+ " Itnry="+resultSet.getString("itnrary")+
	        //		" Ship=" + resultSet.getString("SHIP_CODE")
	        //                 + ", Stateroom- " + resultSet.getString("CABIN_CATEGORY")+" time="+ resultSet.getDate("DC_SAIL_START")+" days="+days);
	        /*
			buf = dataElements.getElement("Input_CruisePackage").asTupleSet().makeTupleBuffer(-1);
			
			buf.setIntValue("CruisePackageID",resultSet.getInt("cruisepkg_id"));
			buf.setSymbolValue("SailID", resultSet.getString("SAIL_ID"));//3-11-2013
			buf.setSymbolValue("PackageID", resultSet.getString("PACKAGE_ID"));//3-11-2013		
			buf.setSymbolValue("ShipID", resultSet.getString("SHIP_CODE"));
			buf.setSymbolValue("DepartingPort", resultSet.getString("PORT_FROM"));					
			buf.setIntValue("Date", days);
			buf.setNumValue("Duration",resultSet.getInt("SAIL_DAYS"));					
			buf.setSymbolValue("DateID", resultSet.getString("DC_SAIL_START"));			
			buf.setSymbolValue("ItineraryID", resultSet.getString("itnrary"));
			buf.setSymbolValue("RoomType", resultSet.getString("CABIN_CATEGORY"));
			buf.setSymbolValue("Destination", resultSet.getString("destination"));//3-11-2013			
			buf.setSymbolValue("Meta", resultSet.getString("META_NAME"));//3-11-2013			
			buf.setNumValue("DrupalWeight", resultSet.getDouble("drupalwght"));			
			buf.commit();
			*/
	        
	        //if(DEBUG.equals("1")){
				writer2.write("<"+resultSet.getInt("cruisepkg_id")+",\""+resultSet.getString("SAIL_ID")+"\",\""+resultSet.getString("PACKAGE_ID")+
						"\",\""+resultSet.getString("DC_SAIL_START")+"\","+days+",\""+resultSet.getString("SHIP_CODE")+"\",\""+resultSet.getString("itnrary")+"\",\""+
						resultSet.getString("destination")+"\",\""+resultSet.getString("PORT_FROM")+"\",\""+resultSet.getString("CABIN_CATEGORY")+"\",\""+
						resultSet.getString("META_NAME")+"\","+resultSet.getInt("SAIL_DAYS")+","+resultSet.getDouble("drupalwght")+","+resultSet.getDouble("CABIN_CAPACITY")+">,\n");
			
        	//}
		 }
		 Date currentTime3 = Calendar.getInstance().getTime();
		 if(DEBUG.equals("1")) System.out.println("Write Cruise_PKG Complete:"+(currentTime3.getTime()-currentTime2.getTime()));
		 //if(dataRefresh.equals("1")) writer2.write("};\n");
    	 //Price
		 /*
		 for(int i=10001;i<=80000;i++){
				String qry="insert into ncl_ws.cruisepkg_price (cruisepkg_id, DC_SAIL_START, pricetype, price1, price2, price3, price4, price5, price6, price7, price8, price9) values ("
					+i+", to_date('20130320', 'yyyymmdd'),'BEST-FAIR', 449.00, 459.00,469.00, 499.00, 499.00, 475.00, 475.00, 475.00, 450.00)"; 
				statement.executeQuery(qry);
		 }*/
		 
		 int numberOfGuests=2;
		 if(userInput._numberOfGuests<1) numberOfGuests=2;
		 else if(userInput._numberOfGuests>9) numberOfGuests=9;
		 else numberOfGuests=userInput._numberOfGuests;
		 //if(DEBUG.equals("1")) 
			 writer.write("Input_CruisePackagePrice={\n");		 
		 query = "SELECT cruisepkg_id,MIN(price"+numberOfGuests+") price FROM "+ databaseSchemaName +".CRUISEPKG_PRICE WHERE ";
		 //databaseSchemaName+".CruisePkg_Price.cruisepkg_id in (SELECT cruisepkg_id from "+databaseSchemaName+".cruise_pkg) AND";
		 if(userInput._discountTypeList.size()>0) query1=" (";
		 else query1="";
		 firstRange=true;
		 for (Iterator itr = userInput._discountTypeList.iterator(); itr.hasNext();)
         {
			 if (firstRange==false) query1=query1+" OR";
        	 String discountType=(String) itr.next();
        	 query1=query1+" pricetype="+"\'"+discountType+"\'";
        	 firstRange=false;
     	 }		 
		 if(userInput._discountTypeList.size()>0) query1=query1+") ";
		 query2 = " GROUP BY cruisepkg_id";//  ORDER BY cruisepkg_id";
		 query=query+query1+query3+query2;
		 if(DEBUG.equals("1")) System.out.println("Query="+query);
		 if(DEBUG.equals("1")) System.out.println("Query CruisePackagePrice");			 
		 resultSet = statement.executeQuery(query);
		 if(DEBUG.equals("1")) System.out.println("Query CruisePackagePrice Complete");	
	
		 /*
		 currentTime1 = Calendar.getInstance().getTime();
		 System.out.println( Calendar.getInstance().getTime()+"Before reading CruisePrice");
		 while (resultSet.next()) {	
		 }*/
		 Date currentTime4 = Calendar.getInstance().getTime();
		 System.out.println("After reading CruisePrice:"+(currentTime4.getTime()-currentTime3.getTime()));
		 /*
		 resultSet.absolute(1);
		 System.out.println("set to row 1");*/
		 while (resultSet.next()) {
			//if(pkgList.contains(resultSet.getInt("cruisepkg_id"))){
	    		//System.out.println(" # of guests="+numberOfGuests+" price - " +  resultSet.getDouble("price")
	            //             //+ " DiscountType- " + pPriceType
	            //             +" pkgid="+ resultSet.getInt("cruisepkg_id"));
	    		/*
				buf = dataElements.getElement("Input_CruisePackagePrice").asTupleSet().makeTupleBuffer(-1);
				buf.setIntValue("CruisePackageID",resultSet.getInt("cruisepkg_id"));//pPkgId);
				//buf.setSymbolValue("DiscountType", "NONE");//pPriceType);			
				buf.setNumValue("PricePerGuest", resultSet.getDouble("price"));// pPrice);					
				buf.commit();
				*/
	    		//if(DEBUG.equals("1")){
					writer.write("<"+resultSet.getInt("cruisepkg_id")+","+resultSet.getDouble("price")+">,\n");
				//}
			//}
		 }	 
		 Date currentTime5 = Calendar.getInstance().getTime();
		 System.out.println("Write CruisePackagePrice Complete: "+(currentTime5.getTime()-currentTime4.getTime()));			 	
		 //ThingsToDo
		 //if(DEBUG.equals("1")) 
		 if(dataRefresh.equals("1")) writer2.write("};\nInput_ThingsToDo={\n");
		 query = "SELECT itnrary, thingstd FROM " + databaseSchemaName + ".ITNRTHNGTD";
			
		 if(dataRefresh.equals("1")) resultSet=statement.executeQuery(query);
		 if(dataRefresh.equals("1")) 
		 while (resultSet.next()) 
		 {
			 //if(itnList.contains(resultSet.getString("itnrary"))){
			    /*
				buf = dataElements.getElement("Input_ThingsToDo").asTupleSet().makeTupleBuffer(-1);
				buf.setSymbolValue("ItineraryID",resultSet.getString("itnrary"));
				buf.setSymbolValue("ThingsToDoID",resultSet.getString("thingstd"));
				if(DEBUG.equals("1")) System.out.println("ItineraryID="+resultSet.getString("itnrary")+" ThingsToDoID="+resultSet.getString("thingstd"));
				buf.commit();*/
			 	//if(dataRefresh.equals("1")) {
					writer2.write("<\""+resultSet.getString("itnrary")+"\",\""+resultSet.getString("thingstd")+"\">,\n");
				//}
			 //}
		 }
		 
		 //ItinerarySimilarity
		 if(dataRefresh.equals("1")) 
			 writer2.write("};\nInput_ItinerarySimilarity={\n");
		 query = "SELECT itnrary, itnrarysim FROM " + databaseSchemaName + ".ITNRSIM";
			
		 if(dataRefresh.equals("1")) resultSet=statement.executeQuery(query);
		 if(dataRefresh.equals("1")) 
		 while (resultSet.next()) 
		 {
			 //if(itnList.contains(resultSet.getString("itnrary")) && itnList.contains(resultSet.getString("itnrarysim")) ){
				/*
			 	buf = dataElements.getElement("Input_ItinerarySimilarity").asTupleSet().makeTupleBuffer(-1);
				buf.setSymbolValue("Itinerary1",resultSet.getString("itnrary"));
				buf.setSymbolValue("Itinerary2",resultSet.getString("itnrarysim"));
				if(DEBUG.equals("1")) System.out.println("ItineraryID="+resultSet.getString("itnrary")+" ItinararySim="+resultSet.getString("itnrarysim"));
				buf.commit();*/
				//if(DEBUG.equals("1")){
					writer2.write("<\""+resultSet.getString("itnrary")+"\",\""+resultSet.getString("itnrarysim")+"\">,\n");
				//}				
			 //}
		 }
		 
		 //InterestThingsToDo
		 if(dataRefresh.equals("1")) 
			 writer2.write("};\nInput_InterestThingsToDo={\n");		 
		 query = "SELECT interest, thingstd, drupalwght FROM " + databaseSchemaName + ".INTRTHNGTD";
			
		 if(dataRefresh.equals("1")) resultSet=statement.executeQuery(query);
		 if(dataRefresh.equals("1")) 
		 while (resultSet.next()) 
		 {
			    /*
				buf = dataElements.getElement("Input_InterestThingsToDo").asTupleSet().makeTupleBuffer(-1);
				//buf.setIntValue("OptimRequestID",ittd1.return_optimRequestID());
				buf.setSymbolValue("ThingsToDoID", resultSet.getString("thingstd"));
				buf.setSymbolValue("InterestID", resultSet.getString("interest"));
				buf.setNumValue("DrupalWeight", resultSet.getDouble("drupalwght"));
				if(DEBUG.equals("1")) System.out.println("Interest="+resultSet.getString("interest")+" TTD="+resultSet.getString("thingstd")+" wght="+resultSet.getDouble("drupalwght"));
				buf.commit();*/
				//if(DEBUG.equals("1")){
					writer2.write("<\""+resultSet.getString("interest")+"\",\""+resultSet.getString("thingstd")+
							"\","+ resultSet.getDouble("drupalwght")+">,\n");
				//}				
		 }
		
		 //AccommodationPreferenceScore
		 if(dataRefresh.equals("1"))  
			 writer2.write("};\nInput_AccommodationPreferenceScore={\n");			 
		 query = "SELECT SHIP_CODE, CABIN_CATEGORY, preftype, userpref, startdt, enddt, calcscore FROM " + databaseSchemaName + ".ACCSTATERM ";
		 /*
		 if(userInput._accommodationPreference.size()>0) query1="WHERE";
		 else query1="";
		 firstRange=true;
		 for (Iterator itr = userInput._accommodationPreference.iterator(); itr.hasNext();)
         {
			 if (firstRange==false) query1=query1+" OR";
        	 AccommodationPreference pref=(AccommodationPreference) itr.next();
        	 query1=query1+" (preftype="+"\'"+pref._preferenceType+"\'";
        	 query1=query1+" AND userpref="+"\'"+pref._preference+"\')";        	 
        	 firstRange=false;
     	 }
     	 */		 
		 //query=query+query1;
		 if(DEBUG.equals("1")) System.out.println("query="+query);
		 if(dataRefresh.equals("1")) resultSet=statement.executeQuery(query);
		 if(dataRefresh.equals("1")) 
		 while (resultSet.next()) 
		 {
			//if(shipList.contains(resultSet.getString("SHIP_CODE"))){
				Date sdate=resultSet.getDate("startdt");
		        //int sdays=(int) Math.ceil((sdate.getTime()-currentTime.getTime())/86400000.0);
				int sdays=(int) Math.ceil((sdate.getTime()-yearStartDate.getTime())/86400000.0);		
				Date edate=resultSet.getDate("enddt");
		        //int edays=(int) Math.ceil((edate.getTime()-currentTime.getTime())/86400000.0);
		        int edays=(int) Math.ceil((edate.getTime()-yearStartDate.getTime())/86400000.0);
				/*buf = dataElements.getElement("Input_AccommodationPreferenceScore").asTupleSet().makeTupleBuffer(-1);
				buf.setSymbolValue("ShipID", resultSet.getString("SHIP_CODE"));
				buf.setSymbolValue("RoomType", resultSet.getString("CABIN_CATEGORY"));
				buf.setSymbolValue("PreferenceType", resultSet.getString("preftype"));
				buf.setSymbolValue("Preference", resultSet.getString("userpref"));				
				buf.setIntValue("StartDate", sdays);
				buf.setIntValue("EndDate", edays);
				buf.setNumValue("Score", resultSet.getDouble("calcscore"));
				if(DEBUG.equals("1")) System.out.println("Ship="+resultSet.getString("SHIP_CODE")+" RoomType="+resultSet.getString("CABIN_CATEGORY")+
						" PrefType="+resultSet.getString("preftype")+" Pref="+resultSet.getString("userpref")+
						" sdays="+sdays+" edays="+edays+" score="+resultSet.getDouble("calcscore"));
				buf.commit();*/
				//if(DEBUG.equals("1")){
					writer2.write("<\""+resultSet.getString("SHIP_CODE")+"\",\""+resultSet.getString("CABIN_CATEGORY")+"\",\""+
							resultSet.getString("preftype")+"\",\""+resultSet.getString("userpref")+"\","+sdays+","+edays+
							","+ resultSet.getDouble("calcscore")+">,\n");
				//}	
			//}
		 }

		 //InterestMetaScore
		 if(dataRefresh.equals("1")) 
			 writer2.write("};\nInput_InterestMetaScore={\n");			 
		 query = "SELECT SHIP_CODE, META_NAME, interest, startdt, enddt, busscore FROM "+ databaseSchemaName +".intrmeta";

		 if(dataRefresh.equals("1")) resultSet=statement.executeQuery(query);
		 if(dataRefresh.equals("1")) 
		 while (resultSet.next()) 
		 {
			//if(shipList.contains(resultSet.getString("SHIP_CODE"))){			 
				Date sdate=resultSet.getDate("startdt");
		        //int sdays=(int) Math.ceil((sdate.getTime()-currentTime.getTime())/86400000.0);
				int sdays=(int) Math.ceil((sdate.getTime()-yearStartDate.getTime())/86400000.0);		
				Date edate=resultSet.getDate("enddt");
		        //int edays=(int) Math.ceil((edate.getTime()-currentTime.getTime())/86400000.0);
		        int edays=(int) Math.ceil((edate.getTime()-yearStartDate.getTime())/86400000.0);
		        
				/*buf = dataElements.getElement("Input_InterestMetaScore").asTupleSet().makeTupleBuffer(-1);
				buf.setSymbolValue("ShipID", resultSet.getString("SHIP_CODE"));
				buf.setSymbolValue("Meta", resultSet.getString("META_NAME"));
				buf.setSymbolValue("InterestID", resultSet.getString("interest"));			
				buf.setIntValue("StartDate", sdays);
				buf.setIntValue("EndDate", edays);
				buf.setNumValue("Score", resultSet.getDouble("busscore"));
				if(DEBUG.equals("1")) System.out.println("Ship="+resultSet.getString("SHIP_CODE")+" meta="+resultSet.getString("META_NAME")+
						" Interest="+resultSet.getString("interest")+
						" sdays="+sdays+" edays="+edays+" score="+resultSet.getDouble("busscore"));
				buf.commit();*/
				//if(DEBUG.equals("1")){
					writer2.write("<\""+resultSet.getString("interest")+"\",\""+resultSet.getString("SHIP_CODE")+"\",\""+resultSet.getString("META_NAME")+
							"\","+sdays+","+edays+","+ resultSet.getDouble("busscore")+">,\n");
				//}
			//}
		 }
		 
		 //InterestDestinationScore
		 if(dataRefresh.equals("1"))  
			 writer2.write("};\nInput_InterestDestinationScore={\n");				 
		 query = "SELECT destination, interest, startdt, enddt, busscore FROM "+ databaseSchemaName +".intrdest";

		 if(dataRefresh.equals("1")) resultSet=statement.executeQuery(query);
		 if(dataRefresh.equals("1")) 
		 while (resultSet.next()) 
		 {
			//if(destList.contains(resultSet.getString("destination"))){			 
				Date sdate=resultSet.getDate("startdt");   
		        //int sdays=(int) Math.ceil((sdate.getTime()-currentTime.getTime())/86400000.0);
				int sdays=(int) Math.ceil((sdate.getTime()-yearStartDate.getTime())/86400000.0);		
				Date edate=resultSet.getDate("enddt");
		        //int edays=(int) Math.ceil((edate.getTime()-currentTime.getTime())/86400000.0);
		        int edays=(int) Math.ceil((edate.getTime()-yearStartDate.getTime())/86400000.0);
		        
				/*buf = dataElements.getElement("Input_InterestDestinationScore").asTupleSet().makeTupleBuffer(-1);
				buf.setSymbolValue("Destination", resultSet.getString("destination"));
				buf.setSymbolValue("InterestID", resultSet.getString("interest"));			
				buf.setIntValue("StartDate", sdays);
				buf.setIntValue("EndDate", edays);
				buf.setNumValue("Score", resultSet.getDouble("busscore"));
				if(DEBUG.equals("1")) System.out.println("Destination="+resultSet.getString("destination")+
						" Interest="+resultSet.getString("interest")+
						" sdays="+sdays+" edays="+edays+" score="+resultSet.getDouble("busscore"));
				buf.commit();*/
				//if(DEBUG.equals("1")){
					writer2.write("<\""+resultSet.getString("interest")+"\",\""+resultSet.getString("destination")+"\","+
							sdays+","+edays+","+ resultSet.getDouble("busscore")+">,\n");
				//}				
			 //}
		 }
		 
		 //InterestShipScore
		 if(dataRefresh.equals("1")) 
			 writer2.write("};\nInput_InterestShipScore={\n");					 
		 query = "SELECT SHIP_CODE, interest, startdt, enddt, busscore FROM "+ databaseSchemaName +".intrship";
		 if(dataRefresh.equals("1")) resultSet=statement.executeQuery(query);
		 if(dataRefresh.equals("1")) 
		 while (resultSet.next()) 
		 {
			//if(shipList.contains(resultSet.getString("SHIP_CODE"))){			 
				Date sdate=resultSet.getDate("startdt");		        
		        //int sdays=(int) Math.ceil((sdate.getTime()-currentTime.getTime())/86400000.0);
				int sdays=(int) Math.ceil((sdate.getTime()-yearStartDate.getTime())/86400000.0);		
				Date edate=resultSet.getDate("enddt");
		        //int edays=(int) Math.ceil((edate.getTime()-currentTime.getTime())/86400000.0);
		        int edays=(int) Math.ceil((edate.getTime()-yearStartDate.getTime())/86400000.0);
		        
				/*buf = dataElements.getElement("Input_InterestShipScore").asTupleSet().makeTupleBuffer(-1);
				buf.setSymbolValue("ShipID", resultSet.getString("SHIP_CODE"));
				buf.setSymbolValue("InterestID", resultSet.getString("interest"));			
				buf.setIntValue("StartDate", sdays);
				buf.setIntValue("EndDate", edays);
				buf.setNumValue("Score", resultSet.getDouble("busscore"));
				if(DEBUG.equals("1")) System.out.println("Ship="+resultSet.getString("SHIP_CODE")+
						" Interest="+resultSet.getString("interest")+
						" sdays="+sdays+" edays="+edays+" score="+resultSet.getDouble("busscore"));
				buf.commit();*/
				//if(DEBUG.equals("1")){
					writer2.write("<\""+resultSet.getString("interest")+"\",\""+resultSet.getString("SHIP_CODE")+"\","+
							sdays+","+edays+","+ resultSet.getDouble("busscore")+">,\n");
				//}
			//}
		 }
	 
		 //BusPenalty
		 if(dataRefresh.equals("1"))  
			 writer2.write("};\nInput_BusPenalty={\n");			 
		 query = "SELECT pentype, diffmin, diffmax, penwght FROM "+ databaseSchemaName +".buspenalty";
			
		 if(dataRefresh.equals("1")) resultSet=statement.executeQuery(query);
		 if(dataRefresh.equals("1")) 
		 while (resultSet.next()) 
		 {	 	        
				/*buf = dataElements.getElement("Input_BusPenalty").asTupleSet().makeTupleBuffer(-1);
				buf.setSymbolValue("PenType", resultSet.getString("pentype"));			
				buf.setNumValue("PenWght", resultSet.getDouble("penwght"));
				buf.setNumValue("DiffMin", resultSet.getDouble("diffmin"));
				buf.setNumValue("DiffMax", resultSet.getDouble("diffmax"));				
				if(DEBUG.equals("1")) System.out.println("PenType="+resultSet.getString("pentype")+
						" diffmin="+resultSet.getDouble("diffmin")+" diffmax="+resultSet.getDouble("diffmax")+" weight="+resultSet.getDouble("penwght"));
				buf.commit();*/
				//if(DEBUG.equals("1")){
					writer2.write("<\""+resultSet.getString("pentype")+"\","+resultSet.getDouble("diffmin")+","+resultSet.getDouble("diffmax")+","
							+ resultSet.getDouble("penwght")+">,\n");
				//}				
		 }		 
		 
		 //AccommodationPreference
		 //if(DEBUG.equals("1")) 
			 writer.write("};\nInput_AccommodationPreference={\n");		 
		 for (Iterator itr = userInput._accommodationPreference.iterator(); itr.hasNext();)
         {
			 //buf = dataElements.getElement("Input_AccommodationPreference").asTupleSet().makeTupleBuffer(-1);
        	 AccommodationPreference pref=(AccommodationPreference) itr.next();
 			 //buf.setSymbolValue("PreferenceType", pref._preferenceType);
			 //buf.setSymbolValue("Preference", pref._preference);	
			 //if(DEBUG.equals("1")) System.out.println("PrefType="+pref._preferenceType+" Pref="+pref._preference);
			 //buf.commit();
			 //if(DEBUG.equals("1")){
					writer.write("<\""+pref._preferenceType+"\",\""+pref._preference+"\">,\n");
			 //}					 
     	 }		 
		 
		 //BUSINFLWGHT
		 //if(DEBUG.equals("1")) 
		 if(dataRefresh.equals("1")) 	 
			 writer2.write("};\nInput_Parameters={\n");		 
		 query = "SELECT wb1, wb2, wb3, wb4, wb5, wb6, wb7, wb8 FROM "+ databaseSchemaName + ".BUSINFLWGHT";			
		 if(dataRefresh.equals("1")) resultSet=statement.executeQuery(query);
		 if(dataRefresh.equals("1")) 
		 while (resultSet.next()) 
		 {
				/*buf = dataElements.getElement("Input_Parameters").asTupleSet().makeTupleBuffer(-1);
				buf.setNumValue("CustomerAccommodationPriority", userInput._userWeight._WC1);
				buf.setNumValue("CustomerThingsToDoPriority", userInput._userWeight._WC2);
				buf.setNumValue("CustomerPricePriority", userInput._userWeight._WC3);
				buf.setNumValue("CustomerTimePriority", userInput._userWeight._WC4);
				buf.setNumValue("CustomerDestinationPriority", userInput._userWeight._WC5);
				if(DEBUG.equals("1")) System.out.println("WC="+userInput._userWeight._WC1+" "+userInput._userWeight._WC2+" "+
						userInput._userWeight._WC3+" "+userInput._userWeight._WC4+" "+userInput._userWeight._WC5);
				buf.setNumValue("BusinessAccommodationPriority", resultSet.getDouble("wb1"));
				buf.setNumValue("BusinessThingsToDoPriority", resultSet.getDouble("wb2"));
				buf.setNumValue("BusinessPricePriority", resultSet.getDouble("wb3"));
				buf.setNumValue("BusinessTimePriority", resultSet.getDouble("wb4"));
				buf.setNumValue("BusinessDestinationPriority", resultSet.getDouble("wb5"));
				buf.setNumValue("BusinessDepartingPriority", resultSet.getDouble("wb6"));
				buf.setNumValue("RemommendationLimitPriority", resultSet.getDouble("wb7"));
				buf.setNumValue("ItineraryUniquenessPriority", resultSet.getDouble("wb8"));	
				if(DEBUG.equals("1")) System.out.println("WB="+" "+resultSet.getDouble("wb1")+" "+resultSet.getDouble("wb2")+" "+
						resultSet.getDouble("wb3")+" "+resultSet.getDouble("wb4")+" "+resultSet.getDouble("wb5")+" "+
						resultSet.getDouble("wb6")+" "+resultSet.getDouble("wb7")+" "+resultSet.getDouble("wb8"));
				buf.commit();*/
				//if(DEBUG.equals("1")){
						writer2.write("<"+userInput._userWeight._WC1+","+userInput._userWeight._WC2+","+userInput._userWeight._WC3+","+
								userInput._userWeight._WC4+","+userInput._userWeight._WC5+","+resultSet.getDouble("wb1")+","+resultSet.getDouble("wb2")
								+","+resultSet.getDouble("wb3")+","+resultSet.getDouble("wb4")+","+resultSet.getDouble("wb5")+","+resultSet.getDouble("wb6")
								+","+resultSet.getDouble("wb7")+","+resultSet.getDouble("wb8")+">,\n");
				//}				
		 }
		 
		//Request Parameters:
		//if(DEBUG.equals("1")) 
			writer.write("};\nInput_RecommendationRequestLimit={\n");			 
		/*buf = dataElements.getElement("Input_RecommendationRequestLimit").asTupleSet().makeTupleBuffer(-1);
		buf.setIntValue("NumPackageRecommendations",userInput._recommendationRequestLimit._numPackageRecommendations);
		buf.setNumValue("NumThingsToDo", userInput._recommendationRequestLimit._numThingsToDo);
		buf.setNumValue("NumDestinations", userInput._recommendationRequestLimit._numDestinations);
		buf.setNumValue("NumStateRoomTypes", userInput._recommendationRequestLimit._numStateRoomTypes);
		buf.commit();*/
		//if(DEBUG.equals("1")) System.out.println("Request Limits="+userInput._recommendationRequestLimit._numPackageRecommendations+" "+userInput._recommendationRequestLimit._numThingsToDo
		//	+" "+userInput._recommendationRequestLimit._numDestinations+" "+userInput._recommendationRequestLimit._numStateRoomTypes);
		//if(DEBUG.equals("1")){
			writer.write("<"+userInput._recommendationRequestLimit._numPackageRecommendations+","+userInput._recommendationRequestLimit._numThingsToDo
					+","+userInput._recommendationRequestLimit._numDestinations+","+userInput._recommendationRequestLimit._numStateRoomTypes+">,\n");
		//}
		
		//InterestRange
		//if(DEBUG.equals("1")) 
			writer.write("};\nInput_InterestRange={\n");			
		for (Iterator itr = userInput._interestList.iterator(); itr.hasNext();)
        {
			 //buf = dataElements.getElement("Input_InterestRange").asTupleSet().makeTupleBuffer(-1);
			 String pref=(String) itr.next();
			 //buf.setSymbolValue("InterestID", pref);
			 //if(DEBUG.equals("1")) System.out.println("Interest="+pref);
			 //buf.commit();	
			 //if(DEBUG.equals("1")){
				writer.write("<\""+pref+"\">,\n");
			 //}			 
    	}
		
		//ThingsToDoRange
		//if(DEBUG.equals("1")) 
			writer.write("};\nInput_ThingsToDoRange={\n");		
		for (Iterator itr = userInput._thingsToDoList.iterator(); itr.hasNext();)
        {
			 //buf = dataElements.getElement("Input_ThingsToDoRange").asTupleSet().makeTupleBuffer(-1);
			 String pref=(String) itr.next();
			 //buf.setSymbolValue("ThingsToDoID", pref);
			 //if(DEBUG.equals("1")) System.out.println("TTD="+pref);
			 //buf.commit();			
			 //if(DEBUG.equals("1")){
					writer.write("<\""+pref+"\">,\n");
			 //}			 
    	}		 

		//MetaRange
		//if(DEBUG.equals("1")) 
			writer.write("};\nInput_MetaRange={\n");		
		for (Iterator itr = userInput._metaList.iterator(); itr.hasNext();)
        {
			 //buf = dataElements.getElement("Input_MetaRange").asTupleSet().makeTupleBuffer(-1);
			 String pref=(String) itr.next();
			 //buf.setSymbolValue("Meta", pref);
			 //if(DEBUG.equals("1")) System.out.println("Meta="+pref);
			 //buf.commit();	
			 //if(DEBUG.equals("1")){
					writer.write("<\""+pref+"\">,\n");
			 //}				 
    	}		 
		
		//DestinationRange
		//if(DEBUG.equals("1")) 
			writer.write("};\nInput_DestinationRange={\n");			
		for (Iterator itr = userInput._destinationList.iterator(); itr.hasNext();)
        {
			 //buf = dataElements.getElement("Input_DestinationRange").asTupleSet().makeTupleBuffer(-1);
			 String pref=(String) itr.next();
			 //buf.setSymbolValue("Destination", pref);
			 //if(DEBUG.equals("1")) System.out.println("Destination="+pref);
			 //buf.commit();	
			 //if(DEBUG.equals("1")){
					writer.write("<\""+pref+"\">,\n");
			 //}				 
    	}
		
		//DepartingPortRange
		//if(DEBUG.equals("1")) 
			writer.write("};\nInput_PortRange={\n");	
		for (Iterator itr = userInput._departingPortList.iterator(); itr.hasNext();)
        {
			 //buf = dataElements.getElement("Input_PortRange").asTupleSet().makeTupleBuffer(-1);
			 String pref=(String) itr.next();
			 //buf.setSymbolValue("DepartingPort", pref);
			 //if(DEBUG.equals("1")) System.out.println("Port="+pref);
			 //buf.commit();		
			 //if(DEBUG.equals("1")){
					writer.write("<\""+pref+"\">,\n");
			 //}				 
    	}
		
		//SailDateRange
		//if(DEBUG.equals("1")) 
			writer.write("};\nInput_SailDateRange={\n");			
		for (Iterator itr = userInput._sailDateRangeList.iterator(); itr.hasNext();)
        {
			//buf = dataElements.getElement("Input_SailDateRange").asTupleSet().makeTupleBuffer(-1);			
        	SailDateRange sdr=(SailDateRange) itr.next();
			Date sdate=sdr._earliestDate;
	        //int sdays=(int) Math.ceil((sdate.getTime()-currentTime.getTime())/86400000.0);
			int sdays=(int) Math.ceil((sdate.getTime()-yearStartDate.getTime())/86400000.0);		
			Date edate=sdr._latestDate;
	        //int edays=(int) Math.ceil((edate.getTime()-currentTime.getTime())/86400000.0);
	        int edays=(int) Math.ceil((edate.getTime()-yearStartDate.getTime())/86400000.0);	        
	        //buf.setNumValue("Min",sdays);
			//buf.setNumValue("Max",edays);			
			if(DEBUG.equals("1")) System.out.println("Sail Date min="+sdays+" max="+edays+" sdate="+sdate+" edate="+edate);
			//buf.commit();
			//if(DEBUG.equals("1")){
					writer.write("<"+sdays+","+edays+">,\n");
			//}			
     	}

		//DurationRange
		//if(DEBUG.equals("1")) 
			writer.write("};\nInput_DurationRange={\n");		
		for (Iterator itr = userInput._durationRangeList.iterator(); itr.hasNext();)
        {
			//buf = dataElements.getElement("Input_DurationRange").asTupleSet().makeTupleBuffer(-1);			
        	DurationRange sdr=(DurationRange) itr.next();
			//buf.setNumValue("Min",sdr._minDays);
			//buf.setNumValue("Max",sdr._maxDays);			
			//if(DEBUG.equals("1")) System.out.println("Duration min="+sdr._minDays+" max="+sdr._maxDays);
			//buf.commit();
			//if(DEBUG.equals("1")){
				writer.write("<"+sdr._minDays+","+sdr._maxDays+">,\n");
			//}				
     	}
		
		//PriceRange
		//if(DEBUG.equals("1")) 
			writer.write("};\nInput_PricePerGuestRange={\n");		
		for (Iterator itr = userInput._priceRangeList.iterator(); itr.hasNext();)
        {
			//buf = dataElements.getElement("Input_PricePerGuestRange").asTupleSet().makeTupleBuffer(-1);			
        	PriceRange sdr=(PriceRange) itr.next();
			//buf.setNumValue("Min",sdr._minPrice);
			//buf.setNumValue("Max",sdr._maxPrice);			
			//if(DEBUG.equals("1")) System.out.println("Price min="+sdr._minPrice+" max="+sdr._maxPrice);
			//buf.commit();
			//if(DEBUG.equals("1")){
				writer.write("<"+sdr._minPrice+","+sdr._maxPrice+">,\n");
			//}			
     	}		
		
		writer.write("};\nInput_NumberOfGuests={<"+userInput._numberOfGuests+">");
		//if(DEBUG.equals("1")) 
			writer.write("};\nConstraintViolationUpperBound =[-1];\nOutput_Parameters={};\n");
		if(dataRefresh.equals("1")) writer2.write("};\n"); 
		 // setting the row to we have to update
		 if(DEBUG.equals("1")) System.out.println("Data Fetching Complete..."+Calendar.getInstance().getTime());
		 
		 //sample update code:
		 //resultSet.first();
		 //resultSet.updateString("SHIP_CODE", "SHIP1");//this will not work with wild card *: "select *": for example: String query = "SELECT * FROM databaseSchemaName.ACCSTATERM";
		 // Updating the first row
		 // resultSet.updateRow();
		 
		 //userInput._DatabaseConnection.close();
         conn.close();
         //if (DEBUG.equals("1") && writer != null) {
             writer.close();
         //}
         if(dataRefresh.equals("1") && writer2 != null){
        	 writer2.close();    
         }
         return dataElements;
     } catch (Exception e) {
         System.err.println(e.getMessage());
         return null;
     }
 }
 
 /*
    public static IloOplDataElements inputData(IloOplDataElements dataElements, OptimizationRequestData ord) throws Exception {
		
    	IloTupleBuffer buf;
		ArrayList <CruisePackage> cruisePackageList=ord.return_cruisePackageList();
		
		// iterate through, add each record to dataElements
		for (Iterator it = cruisePackageList.iterator(); it.hasNext();)
		{
			CruisePackage cP = (CruisePackage) it.next();
			buf = dataElements.getElement("CruisePackage").asTupleSet().makeTupleBuffer(-1);
			buf.setIntValue("CruisePackageID",cP._cruisePackageID);
			buf.setSymbolValue("SailID", cP.return_SailID());//3-11-2013
			buf.setSymbolValue("PackageID", cP.return_PackageID());//3-11-2013		
			buf.setSymbolValue("ShipID", cP._shipID);
			buf.setIntValue("Date", cP.return_Date());
			buf.setSymbolValue("DateID", cP.return_DateID());			
			buf.setSymbolValue("ItineraryID", cP.return_itineraryID());
			buf.setSymbolValue("RoomType", cP.return_roomType());
			buf.setSymbolValue("Destination", cP.return_destination());//3-11-2013			
			buf.setSymbolValue("Meta", cP.return_meta());//3-11-2013			
			buf.setNumValue("AccomodationPreference", cP.return_accomodationPreference());
			buf.setNumValue("InterestPreference", cP.return_interestPreference());
			buf.setNumValue("RoomTypeViolation", cP.return_roomTypeViolation());
			buf.setNumValue("PriceViolation", cP.return_priceViolation());
			buf.setNumValue("DestinationViolation", cP.return_destinationViolation());
			buf.setNumValue("DateViolation", cP.return_dateViolation());
			buf.setNumValue("DurationViolation", cP.return_durationViolation());
			buf.setNumValue("DepartingPortViolation", cP.return_departingPortViolation());
			//buf.setSymbolValue("discountType", cP.return_discountType());//3-11-2013			
			buf.setNumValue("pricePerGuest", cP.return_pricePerGuest());			
			buf.setNumValue("DrupalWeight", cP.return_drupalWeight());
			buf.commit();
		}
		
		ArrayList <ThingsToDo> ttdList=ord.return_ttdList();
		
		
		// iterate through, add each record to dataElements
		for (Iterator it = ttdList.iterator(); it.hasNext();)
		{
			ThingsToDo ttd1 = (ThingsToDo) it.next();
			buf = dataElements.getElement("ThingsToDo").asTupleSet().makeTupleBuffer(-1);
			buf.setSymbolValue("ItineraryID",ttd1.return_itineraryID());
			buf.setSymbolValue("ThingsToDoID", ttd1.return_thingsToDoID());
			buf.commit();
		}
		
		ArrayList <itinerarySimilarity> isList1=ord.return_isList1();
		
		
		// iterate through, add each record to dataElements
		for (Iterator it = isList1.iterator(); it.hasNext();)
		{
			itinerarySimilarity is2 = (itinerarySimilarity) it.next();
			buf = dataElements.getElement("ItinerarySimilarity").asTupleSet().makeTupleBuffer(-1);
			//buf.setIntValue("OptimRequestID",is2.return_optimRequestID());
			buf.setSymbolValue("Itinerary1",is2.return_itineraryID_1());
			buf.setSymbolValue("Itinerary2",is2.return_itineraryID_2());
			buf.commit();
		}
		
		
		ArrayList <InterestThingsToDo> ittdList=ord.return_ittdList();
		
		
		// iterate through, add each record to dataElements
		for (Iterator it = ittdList.iterator(); it.hasNext();)
		{
			InterestThingsToDo ittd1 = (InterestThingsToDo) it.next();
			buf = dataElements.getElement("InterestThingsToDo").asTupleSet().makeTupleBuffer(-1);
			//buf.setIntValue("OptimRequestID",ittd1.return_optimRequestID());
			buf.setSymbolValue("ThingsToDoID", ittd1.return_thingsToDoID());
			buf.setSymbolValue("InterestID", ittd1.return_interestID());
			buf.setIntValue("ThingsToDoPrefMatch", ittd1.return_thingsToDoPrefMatch());
			//buf.setIntValue("ThingsToDoSelected", ittd1.return_thingsToDoSelected());
			buf.setNumValue("ThingsToDoViolation", ittd1.return_thingsToDoViolation());
			buf.setNumValue("DrupalWeight", ittd1.return_drupalWeight());
			buf.commit();
		}
		
		Parameters params =ord.return_params();
		
		buf = dataElements.getElement("InterestThingsToDo").asTupleSet().makeTupleBuffer(-1);
		//buf.setIntValue("OptimRequestID",params.return_optimRequestID());
		buf.setNumValue("CustomerAccomodationPriority", params.return_customerAccomodationPriority());
		buf.setNumValue("CustomerThingsToDoPriority", params.return_customerThingsToDoPriority());
		buf.setNumValue("CustomerPricePriority", params.return_customerPricePriority());
		buf.setNumValue("CustomerTimePriority", params.return_customerTimePriority());
		buf.setNumValue("CustomerDestinationPriority", params.return_customerDestinationPriority());
		buf.setNumValue("BusinessAccomodationPriority", params.return_businessAccomodationPriority());
		buf.setNumValue("BusinessThingsToDoPriority", params.return_businessThingsToDoPriority());
		buf.setNumValue("BusinessPricePriority", params.return_businessPricePriority());
		buf.setNumValue("BusinessTimePriority", params.return_businessTimePriority());
		buf.setNumValue("BusinessDestinationPriority", params.return_businessDestinationPriority());
		buf.setNumValue("BusinessDepartingPortPriority", params.return_businessDepartingPortPriority());
		buf.commit();
		
		RecommendationRequestLimit rrl = ord.return_rrl();
		
		buf = dataElements.getElement("RecommendationRequestLimit").asTupleSet().makeTupleBuffer(-1);
		buf.setIntValue("numPackageRecommendations",rrl.return_numPackageRecommendations());
		buf.setNumValue("numThingsToDo", rrl.return_numThingsToDo());
		buf.setNumValue("numDestinations", rrl.return_numDestinations());
		buf.setNumValue("numStateRoomTypes", rrl.return_numStateRoomTypes());
		
		return dataElements;
		
    }
*/

	public OptimizationResponseData generateCruiseRecommendation(IloOplDataElements dataElements, IloOplFactory oplF, IloOplModelDefinition def, String DEBUG, String tempOplFile) throws Exception {
	
	    ArrayList<CruisePackageRecommendation> cruisePackageData = new ArrayList();
	    ArrayList<ConstraintViolation> constraintViolationData = new ArrayList();
	    ArrayList<ThingsToDoRecommendation> ttdrData = new ArrayList();
	    double [] constraintViolationAmount = new double[1];
		OptimizationResponseData ord1 = new OptimizationResponseData();
		boolean unBounded=false;//allow contract violation
		int objDef=0;//=1: cost, =2: contract violation
		int status=solve(unBounded,objDef,dataElements, oplF, def, cruisePackageData, constraintViolationData, ttdrData,constraintViolationAmount, DEBUG, tempOplFile);
		int status2=0;
		if(DEBUG.equals("1")) System.out.println("Solved Original Model: Status="+status);
		
		//if model is infeasible:
		if(status==2 )//=1, optimal, =2: infeasible. = 3 failed
		{
			//allow contract violation
			unBounded=true;
			objDef=1;
			if(DEBUG.equals("1")) System.out.println("Original Model Infeasible. Set unBounded="+unBounded+" objDef="+objDef);
			if(DEBUG.equals("1")) System.out.println("Searching for infeasible recommendations min/max constraints...");
			if(solve(unBounded,objDef,dataElements, oplF, def, cruisePackageData, constraintViolationData, ttdrData, constraintViolationAmount, DEBUG, tempOplFile)==1){//solve the model with obj = contract violation amount
				/* 4-9-2013
				objDef=2;
				unBounded=false;
				if(DEBUG.equals("1")) System.out.println("objDef="+objDef+" constraintViolationAmount="+constraintViolationAmount[0]);
				status2=solve(unBounded,objDef,dataElements, oplF, def, cruisePackageData, constraintViolationData, ttdrData, constraintViolationAmount);
				if(status2!=1){//solve the model with obj = cost with upper bound on contract violation amount
					if(DEBUG.equals("1")) System.out.println("Error: Model failed after fixing the contract violation amount!!!");
				}*/
			}
		}
		ord1.set_cruisePackageData(cruisePackageData);
		ord1.set_constraintViolationData(constraintViolationData);
		ord1.set_ttdrData(ttdrData);
		return ord1;
	}

	
	
	private int solve(boolean unBounded,int objDef,IloOplDataElements dataElements, IloOplFactory oplF, IloOplModelDefinition def,
		    ArrayList<CruisePackageRecommendation> cruisePackageData,
		    ArrayList<ConstraintViolation> constraintViolationData,
		    ArrayList<ThingsToDoRecommendation> ttdrData, double [] constraintViolationAmount, String DEBUG, String tempOplFile) throws Exception
	{	
		//init();
        //System.out.println("CPLEX");
        IloCplex cplex = oplF.createCplex();
        //cplex.setOut(null);
        //if(DEBUG.equals("1")) System.out.println("Define cplex");        
        IloOplModel opl = oplF.createOplModel(def, cplex);
        if(DEBUG.equals("1")) System.out.println("Define DataSource");  
        IloOplDataSource dataSource = oplF.createOplDataSource(tempOplFile);//properties.getProperty("oplInputDataFile"));
        IloOplDataSource dataSource2 = oplF.createOplDataSource(properties.getProperty("OplDataSource"));      
        
        opl.addDataSource(dataSource);
        opl.addDataSource(dataSource2);
        //IloOplDataSource dataSource3 = oplF.createOplDataSource("../NCLRecommender_opl/NCLRecommender_export.dat");
        //opl.addDataSource(dataSource3);
        if(DEBUG.equals("1")) System.out.println("Add Data Source: "+tempOplFile);
        if(DEBUG.equals("1")) System.out.println("Add Data Source: "+properties.getProperty("OplDataSource"));
        //opl allows multiple sources
        //IloOplDataSource dataSource2 = oplF.createOplDataSource("../NCLRecommender_opl/b.dat");
        //opl.addDataSource(dataSource2);
        if(DEBUG.equals("1")) System.out.println("Define OPL");
       	//IloNumMap constraintBound= dataElements.getElement("ConstraintViolationUpperBound").asNumMap();
        //System.out.println("CPLEX");      	
        //if(objDef==0){
        //	constraintBound.set(1, 0);
        //}
        //else if (objDef==1){
        //	constraintBound.set(1, -1);        	
        //}
        //else if (objDef==2){
        //	constraintBound.set(1, constraintViolationAmount[0]);
        //}
        //if(DEBUG.equals("1")) System.out.println("dataElements before: "+ Calendar.getInstance().getTime());	
        
        //opl.addDataSource(dataElements);
        //if(DEBUG.equals("1")) System.out.println("dataElements After: "+ Calendar.getInstance().getTime());	       
        opl.generate();
        if(DEBUG.equals("1")) System.out.println("complete OPL: "+ Calendar.getInstance().getTime());
       	IloNumMap constraintBound2= opl.getElement("ConstraintViolationUpperBound").asNumMap();
       	if(DEBUG.equals("1")) System.out.println("constraintBound="+constraintBound2.get(1));
       	//constraintBound2.set(1,0);
       	//cplex.getd
        if(DEBUG.equals("1")) System.out.println("Generated OPL...");
        cplex.setParam(IloCplex.DoubleParam.EpOpt, 0.001);
        if(DEBUG.equals("1")) System.out.println("Solving Model...");
        
        //cplex.exportModel("exportModel.sav");
        
		if (cplex.solve()){
			if(DEBUG.equals("1")) System.out.println(cplex.getStatus().toString());
	        opl.postProcess();
	        if(DEBUG.equals("1")) System.out.println("objDef="+objDef+" Time="+ Calendar.getInstance().getTime());
			if(objDef==0 || objDef==1){//4-9-2013 objDef==2){
				if(DEBUG.equals("1")) System.out.println("Output CruisePackageRecommendation...");
				if(DEBUG.equals("1")) System.out.println("FC3="+opl.getElement("FC3").asNum()+" TotalConstraintViolation="+opl.getElement("TotalConstraintViolations").asNum());
				for (Iterator it1 = opl.getElement("Output_CruisePackageRecommendation").asTupleSet().iterator(); it1.hasNext();)
				{
					
					IloTuple cruiseTuple = (IloTuple) it1.next();
					//3-11-2013 int _optimRequestID = cruiseTuple.getIntValue("OptimRequestID");
					String _itineraryID = cruiseTuple.getStringValue("ItineraryID");
					String _roomType = cruiseTuple.getStringValue("RoomType");
					String _destination = cruiseTuple.getStringValue("Destination");
					String _PackageID = cruiseTuple.getStringValue("PackageID");//3-11-2013
					String _SailID = cruiseTuple.getStringValue("SailID");//3-11-2013
					String _DateID = cruiseTuple.getStringValue("DateID");
					String _meta = cruiseTuple.getStringValue("Meta");
					int _isRecommended = cruiseTuple.getIntValue("IsRecommended");
					double _accomodationPrefContribution = cruiseTuple.getNumValue("AccPrefContribution");
					double  _interestPrefContribution= cruiseTuple.getNumValue("InterestPrefContribution");
					double _roomViolationValue = cruiseTuple.getNumValue("RoomViolationValue");
					double _thingsToDoViolationValue = cruiseTuple.getNumValue("ThingsToDoViolationValue");
					double _priceViolationValue = cruiseTuple.getNumValue("PriceViolationValue");
					double _dateViolationValue = cruiseTuple.getNumValue("DateViolationValue");
					double _durationViolationValue = cruiseTuple.getNumValue("DurationViolationValue");
					double _destinationViolationValue = cruiseTuple.getNumValue("DestinationViolationValue");
					double _portViolationValue = cruiseTuple.getNumValue("PortViolationValue");
					double _drupalWeightViolationValue = cruiseTuple.getNumValue("DrupalWeightContribution");
					//String _discountType = cruiseTuple.getStringValue("DiscountType");//3-11-2013
					double _pricePerGuest = cruiseTuple.getNumValue("PricePerGuest");//3-11-2013
					CruisePackageRecommendation cruiseRecommendationRecord = 
						new CruisePackageRecommendation(_SailID, _PackageID, //3-11-2013 _optimRequestID,
								_DateID, _itineraryID, _destination,
								_roomType, _meta, 
								_accomodationPrefContribution,
							_interestPrefContribution, _roomViolationValue, _thingsToDoViolationValue, _priceViolationValue, _dateViolationValue, _durationViolationValue, _destinationViolationValue, 
							_portViolationValue, _drupalWeightViolationValue, //_discountType,
							_pricePerGuest,_isRecommended);
					cruisePackageData.add(cruiseRecommendationRecord);	
					//if(DEBUG.equals("1")) System.out.println("Itinerary="+_itineraryID);
				}
				if(DEBUG.equals("1"))
				for (Iterator itr = cruisePackageData.iterator(); itr.hasNext();)
				{
					CruisePackageRecommendation cpr = (CruisePackageRecommendation) itr.next();
					System.out.println("Itinerary="+cpr._itineraryID+" date="+cpr._DateID+" room="+cpr._roomType
							+" meta="+cpr._Meta+" destination="+cpr._Destination+" price="+cpr._pricePerGuest+
							" recommended="+cpr._isRecommended+" AccommodationScore="+cpr._accomodationPrefContribution
							+" InterestScore="+cpr._interestPrefContribution+" Meta Violation="+cpr._roomViolationValue+
							" TTD violation="+cpr._thingsToDoViolationValue+" Destination Violation="+cpr._destinationViolationValue+" port violation="+cpr._portViolationValue
							+" Duration Violation="+cpr._durationViolationValue+" Date Violation="+cpr._dateViolationValue+
							" Price Violation="+cpr._priceViolationValue);
				}
				if(DEBUG.equals("1")) System.out.println("Output ThingsToDoRecommendation...");				
				for (Iterator it1 = opl.getElement("Output_ThingsToDoRecommendation").asTupleSet().iterator(); it1.hasNext();)
				{
					
					IloTuple ttdrTuple = (IloTuple) it1.next();
					//3-11-2013 int _optimRequestID = ttdrTuple.getIntValue("OptimRequestID");
					//3-11-2013 String _itineraryID = ttdrTuple.getStringValue("ItineraryID");
					String _thingsToDoID = ttdrTuple.getStringValue("ThingsToDoID");
					String _interestID = ttdrTuple.getStringValue("InterestID");
					int _isRecommended = ttdrTuple.getIntValue("IsRecommended");
					double  _interestPrefContribution= ttdrTuple.getNumValue("InterestPrefContribution");
					double _drupalWeightContribution = ttdrTuple.getNumValue("DrupalWeightContribution");
					ThingsToDoRecommendation ttdrRecord = new ThingsToDoRecommendation(//3-11-2013 _optimRequestID.,_itineraryID, 
							_thingsToDoID,_interestID, _interestPrefContribution, _drupalWeightContribution, _isRecommended);
					ttdrData.add(ttdrRecord);	
				}
				if(DEBUG.equals("1"))
				for (Iterator itr = ttdrData.iterator(); itr.hasNext();)
				{
					ThingsToDoRecommendation ttd = (ThingsToDoRecommendation) itr.next();
				    System.out.println("ThingsToDo="+ttd._thingsToDoID+" Interest="+ttd._interestID+" InterestContri="+ttd._interestPrefContribution
							+" drupal="+ttd._drupalWeightContribution+
							" recommended="+ttd._isRecommended);
				}
				
				//if(DEBUG.equals("1")) System.out.println("Objective="+opl.getElement("objective"));
			}
			if(objDef==0 || objDef==1)//4-9-2013 objDef==2)
			{	
				for (Iterator it1 = opl.getElement("Output_ConstraintViolation").asTupleSet().iterator(); it1.hasNext();)
				{
					IloTuple iTuple = (IloTuple) it1.next();
					//3-11-2013 int _optimRequestID = iTuple.getIntValue("OptimRequestID");
					double _packageLimitViolation = iTuple.getNumValue("PackageLimitViolation");
					double _thingsToDoLimitViolation = iTuple.getNumValue("ThingsToDoLimViolation");
					double _destinationLimitViolation = iTuple.getNumValue("DestinationLimViolation");
					double _roomTypeLimitViolation = iTuple.getNumValue("RoomTypeLimViolation");
					double _itineraryUniquenessViolation = iTuple.getNumValue("ItineraryUniquenessViolation");
					ConstraintViolation iRecord = new ConstraintViolation(//3-11-2013 _optimRequestID, 
							_packageLimitViolation, _thingsToDoLimitViolation, _destinationLimitViolation, _roomTypeLimitViolation, _itineraryUniquenessViolation);
					constraintViolationData.add(iRecord);
					if(DEBUG.equals("1")) System.out.println("Violation Package="+_packageLimitViolation+" TTD="+_thingsToDoLimitViolation+
							" Desti="+_destinationLimitViolation+ " Room="+_roomTypeLimitViolation+ " ItnrySimilarity="+_itineraryUniquenessViolation);
				}
			}
			
	        if(unBounded && objDef==1)
	        {
	        	constraintViolationAmount[0]=-cplex.getObjValue();	  //bug
	        	if(DEBUG.equals("1")) System.out.println("Set constraintViolationAmount="+constraintViolationAmount[0]);
	        	/*
	        	for (Iterator it1 = opl.getElement("Output_ThingsToDoRecommendation").asTupleSet().iterator(); it1.hasNext();)
	        	{
				
				IloTuple ttdrTuple = (IloTuple) it1.next();
				//3-11-2013 int _optimRequestID = ttdrTuple.getIntValue("OptimRequestID");
				String _itineraryID = ttdrTuple.getStringValue("ItineraryID");
				String _thingsToDoID = ttdrTuple.getStringValue("ThingsToDoID");
				String _interestID = ttdrTuple.getStringValue("InterestID");
				int _isRecommended = ttdrTuple.getIntValue("IsRecommended");
				double  _interestPrefContribution= ttdrTuple.getNumValue("InterestPrefContribution");
				double _drupalWeightContribution = ttdrTuple.getNumValue("DrupalWeightContribution");
				ThingsToDoRecommendation ttdrRecord = new ThingsToDoRecommendation(//3-11-2013 _optimRequestID, itineraryID, 
						_thingsToDoID,_interestID, _interestPrefContribution, _drupalWeightContribution, _isRecommended);
				ttdrData.add(ttdrRecord);
	        	}*/
			}
			
	       	opl.end();
			cplex.end();
			return 1;
		}
		else if(cplex.getStatus().equals(IloCplex.Status.Infeasible) ||
					cplex.getStatus().equals(IloCplex.Status.InfeasibleOrUnbounded)){
			/*
			for (Iterator it1 = opl.getElement("Output_ThingsToDoRecommendation").asTupleSet().iterator(); it1.hasNext();)
			{
				
				IloTuple ttdrTuple = (IloTuple) it1.next();
				//3-11-2013 int _optimRequestID = ttdrTuple.getIntValue("OptimRequestID");
				String _itineraryID = ttdrTuple.getStringValue("ItineraryID");
				String _thingsToDoID = ttdrTuple.getStringValue("ThingsToDoID");
				String _interestID = ttdrTuple.getStringValue("InterestID");
				int _isRecommended = ttdrTuple.getIntValue("IsRecommended");
				double  _interestPrefContribution= ttdrTuple.getNumValue("InterestPrefContribution");
				double _drupalWeightContribution = ttdrTuple.getNumValue("DrupalWeightContribution");
				ThingsToDoRecommendation ttdrRecord = new ThingsToDoRecommendation(//3-11-2013_ optimRequestID,_itineraryID, 
						_thingsToDoID,_interestID, _interestPrefContribution, _drupalWeightContribution, _isRecommended);
				ttdrData.add(ttdrRecord);	
			}*/
			
	        if(DEBUG.equals("1")) System.out.println(cplex.getStatus().toString());
			opl.end();
			cplex.end();
			return 2;
		}
		else{
			for (Iterator it1 = opl.getElement("Output_ThingsToDoRecommendation").asTupleSet().iterator(); it1.hasNext();)
			{
				
				IloTuple ttdrTuple = (IloTuple) it1.next();
				//3-11-2013 int _optimRequestID = ttdrTuple.getIntValue("OptimRequestID");
				String _itineraryID = ttdrTuple.getStringValue("ItineraryID");
				String _thingsToDoID = ttdrTuple.getStringValue("ThingsToDoID");
				String _interestID = ttdrTuple.getStringValue("InterestID");
				int _isRecommended = ttdrTuple.getIntValue("IsRecommended");
				double  _interestPrefContribution= ttdrTuple.getNumValue("InterestPrefContribution");
				double _drupalWeightContribution = ttdrTuple.getNumValue("DrupalWeightContribution");
				ThingsToDoRecommendation ttdrRecord = new ThingsToDoRecommendation(//3-11-2013 _optimRequestID,_itineraryID, 
						_thingsToDoID,_interestID, _interestPrefContribution, _drupalWeightContribution, _isRecommended);
				ttdrData.add(ttdrRecord);	
			}
			
			if(DEBUG.equals("1")) System.out.println("CPLEX Failed:"+cplex.getStatus().toString());
			opl.end();
			cplex.end();
			return 3;
		}
	}
}