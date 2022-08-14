package enfo.crm.util;

import java.util.*;

public class DateDiff
{
	// set some constants that allow pretty accurate estimates over
	//  about 2,000 years before or after the present.  These are
	//  slight overestimates since that's what we want (and need!)
	private static final double DAY_MILLIS = 1000 * 60 * 60 * 24.0015;
	private static final double WEEK_MILLIS = DAY_MILLIS * 7;
	private static final double MONTH_MILLIS = DAY_MILLIS * 30.43675;
	private static final double YEAR_MILLIS = WEEK_MILLIS * 52.2;

	public static String getOLEDate(Date date)
	{
		if (date == null)
			return "Date";
		else
			return Integer.toString(getDateDiff(Calendar.DATE, date, java.sql.Date.valueOf("1899-12-30")));
	}

	public static String getOLEDate(Integer date)
	{
		Date d;
		if (date == null || date.intValue() == 0)
			d = null;
		else
		{
			String sYear = date.toString().substring(0, 4);
			String sMonth = date.toString().substring(4, 6);
			String sDay = date.toString().substring(6, 8);
			d = java.sql.Date.valueOf(sYear + "-" + sMonth + "-" + sDay);
		}
		return getOLEDate(d);
	}
	
	public static int getDateDiff(int calUnit, Date d2, Date d1)
	{
		// swap if d1 later than d2
		boolean neg = false;
		if (d1.after(d2))
		{
			Date temp = d1;
			d1 = d2;
			d2 = temp;
			neg = true;
		}

		// estimate the diff.  d1 is now guaranteed <= d2
		int estimate = (int) getEstDiff(calUnit, d1, d2);

		// convert the Dates to GregorianCalendars
		GregorianCalendar c1 = new GregorianCalendar();
		c1.setTime(d1);
		GregorianCalendar c2 = new GregorianCalendar();
		c2.setTime(d2);

		// add 2 units less than the estimate to 1st date,
		//  then serially add units till we exceed 2nd date
		c1.add(calUnit, (int) estimate - 2);
		for (int i = estimate - 1;; i++)
		{
			c1.add(calUnit, 1);
			if (c1.after(c2))
				return neg ? 1 - i : i - 1;
		}
	}

	private static int getEstDiff(int calUnit, Date d1, Date d2)
	{
		long diff = d2.getTime() - d1.getTime();
		switch (calUnit)
		{
			case Calendar.DAY_OF_WEEK_IN_MONTH :
			case Calendar.DAY_OF_MONTH :
				//      case Calendar.DATE :      // codes to same int as DAY_OF_MONTH
				return (int) (diff / DAY_MILLIS + .5);
			case Calendar.WEEK_OF_YEAR :
				return (int) (diff / WEEK_MILLIS + .5);
			case Calendar.MONTH :
				return (int) (diff / MONTH_MILLIS + .5);
			case Calendar.YEAR :
				return (int) (diff / YEAR_MILLIS + .5);
			default :
				return 0;
		} /* endswitch */
	}

	public static void main(String[] args)
	{
		Date now = new Date();
		Date then = java.sql.Date.valueOf("1899-12-30");
		int diff = DateDiff.getDateDiff(Calendar.DATE, now, then);
		diff = DateDiff.getDateDiff(Calendar.WEEK_OF_YEAR, now, then);
		diff = DateDiff.getDateDiff(Calendar.MONTH, then, then);
		diff = DateDiff.getDateDiff(Calendar.YEAR, now, then);
	}
	
	
	
}
