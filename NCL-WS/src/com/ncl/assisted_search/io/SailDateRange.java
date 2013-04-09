package com.ncl.assisted_search.io;
import java.util.Date;

public class SailDateRange {
	public Date _earliestDate;
	public Date _latestDate;
	
	public SailDateRange(
			Date earliestDate, Date latestDate)
	{
		_latestDate = latestDate;
		_earliestDate = earliestDate;
	}
}