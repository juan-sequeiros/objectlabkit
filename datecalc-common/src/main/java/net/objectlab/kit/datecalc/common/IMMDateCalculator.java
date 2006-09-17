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
package net.objectlab.kit.datecalc.common;

import java.util.List;

/**
 * The IMMDates are defined as the 3rd Wednesday of March, June, September and
 * December.
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * @param <E>
 *            a representation of a date, typically JDK: Date, Calendar;
 *            Joda:LocalDate, YearMonthDay
 * 
 */
public interface IMMDateCalculator<E> {
    /**
     * Checks if a given date is an official IMM Date (3rd Wednesdays of
     * March/June/Sept/Dec.
     * 
     * @param date
     * @return true if that date is an IMM date.
     */
    boolean isIMMDate(final E date);

    /**
     * Given a start date, it will return the next IMM Date, even if the start
     * date is an IMM date (same as calling
     * getNextIMMDate(IMMPeriod.QUARTERLY)).
     * 
     * @param startDate
     * @return the next IMMDate based on current business date.
     */
    E getNextIMMDate(final E startDate);

    /**
     * Given a start date, it will return the next IMM Date based on the
     * IMMPeriod, even if the start date is an IMM date.
     * 
     * @param startDate
     * @param period
     *            specify when the "next" IMM is, if quarterly then it is the
     *            conventional algorithm.
     * @return the next IMMDate based on current date.
     */
    E getNextIMMDate(final E startDate, final IMMPeriod period);

    /**
     * Given a start date, it will return the previous IMM Date, even if the
     * start date is an IMM date.
     * 
     * @param startDate
     * @return the previous IMMDate based on current date.
     */
    E getPreviousIMMDate(final E startDate);

    /**
     * Given a start date, it will return the previous IMM Date based on the
     * IMMPeriod, even if the start date is an IMM date.
     * 
     * @param period
     *            specify when the "previous" IMM is, if quarterly then it is
     *            the conventional algorithm.
     * @return the previous IMMDate based on current date.
     */
    E getPreviousIMMDate(final E startDate, final IMMPeriod period);

    /**
     * Returns a list of IMM dates between 2 dates, it will exclude the start
     * date if it is an IMM date but would include the end date if it is an IMM
     * (same as as calling getIMMDates(start,end,IMMPeriod.QUARTERLY)).
     * 
     * @param start
     *            start of the interval, excluded
     * @param end
     *            end of the interval, may be included.
     * @return list of IMM dates
     */
    List<E> getIMMDates(final E start, final E end);

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
    List<E> getIMMDates(final E start, final E end, final IMMPeriod period);
}