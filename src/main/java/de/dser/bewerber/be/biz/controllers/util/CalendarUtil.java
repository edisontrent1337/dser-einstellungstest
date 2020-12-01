package de.dser.bewerber.be.biz.controllers.util;

import java.util.Calendar;

public class CalendarUtil {

	public Calendar normalizeDate(Calendar in) {
		Calendar dateReturn = (Calendar)in.clone();
		dateReturn.set(Calendar.HOUR, 12);
		dateReturn.set(Calendar.MINUTE, 00);
		dateReturn.set(Calendar.SECOND, 00);
		dateReturn.set(Calendar.MILLISECOND, 00);
		return dateReturn;
	}
}
