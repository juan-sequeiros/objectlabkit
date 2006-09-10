/*
 * $Id$
 * 
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.objectlab.kit.datecalc.jdk;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.objectlab.kit.datecalc.common.AbstractIMMDateCalculator;
import net.objectlab.kit.datecalc.common.IMMPeriod;

public class CalendarIMMDateCalculator extends AbstractIMMDateCalculator<Calendar> {
    private static final int NUMBER_DAYS_IN_WEEK = 7;

    /**
     * Returns a list of IMM dates between 2 dates, it will exclude the start
     * date if it is an IMM date but would include the end date if it is an IMM.
     * 
     * @param start
     *            start of the interval, excluded
     * @param end
     *            end of the interval, may be included.
     * @param period
     *            specify when the "next" IMM is, if quarterly then it is the
     *            conventional algorithm.
     * @return list of IMM dates
     */
    public List<Calendar> getIMMDates(final Calendar start, final Calendar end, final IMMPeriod period) {

        final List<Calendar> dates = new ArrayList<Calendar>();
        Calendar cal = (Calendar) start.clone();
        while (true) {
            cal = getNextIMMDate(true, cal, period);
            if (!cal.after(end)) {
                dates.add(cal);
            } else {
                break;
            }
        }

        return dates;
    }

    @Override
    protected Calendar getNextIMMDate(final boolean requestNextIMM, final Calendar startDate, final IMMPeriod period) {

        Calendar cal = (Calendar) startDate.clone();

        if (isIMMMonth(cal)) {
            moveToIMMDay(cal);
            // TODO simplify this if condition
            // if (forward ^ cal.getTime().before(startDate) ||
            // cal.getTime().equals(startDate)) {
            if ((requestNextIMM && cal.after(startDate)) || (!requestNextIMM && cal.before(startDate))) {
                return cal;
            }
        }

        final int delta = (requestNextIMM ? 1 : -1);
        do {
            cal.add(Calendar.MONTH, delta);
        } while (!isIMMMonth(cal));

        moveToIMMDay(cal);

        final int month = cal.get(Calendar.MONTH);
        if ((period == IMMPeriod.BI_ANNUALY_JUN_DEC && (Calendar.MARCH == month || Calendar.SEPTEMBER == month))
                || (period == IMMPeriod.BI_ANNUALY_MAR_SEP && (Calendar.JUNE == month || Calendar.DECEMBER == month))) {
            // need to move to the next one.
            cal = getNextIMMDate(requestNextIMM, cal, period);
        } else if (period == IMMPeriod.ANNUALLY) {
            // second jump
            cal = getNextIMMDate(requestNextIMM, cal, IMMPeriod.QUARTERLY);
            // third jump
            cal = getNextIMMDate(requestNextIMM, cal, IMMPeriod.QUARTERLY);
            // fourth jump
            cal = getNextIMMDate(requestNextIMM, cal, IMMPeriod.QUARTERLY);
            // fifth jump
            cal = getNextIMMDate(requestNextIMM, cal, IMMPeriod.QUARTERLY);
        }

        return cal;
    }

    private boolean isIMMMonth(final Calendar cal) {
        final int month = cal.get(Calendar.MONTH);

        switch (month) {
        case Calendar.MARCH:
        case Calendar.JUNE:
        case Calendar.SEPTEMBER:
        case Calendar.DECEMBER:
            return true;
        default:
            return false;
        }
    }

    /**
     * Assumes that the month is correct, get the day for the 3rd wednesday.
     * 
     * @param first
     * @return
     */
    private void moveToIMMDay(final Calendar cal) {
        cal.set(Calendar.DAY_OF_MONTH, 1);

        // go to 1st wed
        final int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek < Calendar.WEDNESDAY) {
            cal.add(Calendar.DAY_OF_MONTH, Calendar.WEDNESDAY - dayOfWeek);
        } else if (dayOfWeek > Calendar.WEDNESDAY) {
            cal.add(Calendar.DAY_OF_MONTH, (Calendar.WEDNESDAY + NUMBER_DAYS_IN_WEEK) - dayOfWeek);
        }

        // go to 3rd wednesday - i.e. move 2 weeks forward
        cal.add(Calendar.DAY_OF_MONTH, NUMBER_DAYS_IN_WEEK * 2);
    }

    public boolean isIMMDate(final Calendar date) {
        // TODO a slightly crude implementation - revisit
        final Calendar cal = (Calendar) date.clone();
        moveToIMMDay(cal);
        return cal.equals(date);
    }
}
