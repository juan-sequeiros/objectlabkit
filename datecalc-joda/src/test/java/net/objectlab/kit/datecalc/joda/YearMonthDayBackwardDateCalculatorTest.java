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
package net.objectlab.kit.datecalc.joda;

import java.util.Set;

import net.objectlab.kit.datecalc.common.AbstractBackwardDateCalculatorTest;
import net.objectlab.kit.datecalc.common.DateCalculatorFactory;
import net.objectlab.kit.datecalc.common.WorkingWeek;

import org.joda.time.YearMonthDay;

/**
 * 
 * @author xhensevb
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public class YearMonthDayBackwardDateCalculatorTest extends AbstractBackwardDateCalculatorTest<YearMonthDay> {

    @Override
    protected YearMonthDay newDate(final String date) {
        return new YearMonthDay(date);
    }

    @Override
    protected void registerHolidays(final String name, final Set<YearMonthDay> holidays) {
        DefaultYearMonthDayCalculatorFactory.getDefaultInstance().registerHolidays(name, holidays);
    }

    @Override
    protected WorkingWeek getWorkingWeek(final WorkingWeek ww) {
        return new JodaWorkingWeek(ww);
    }

    @Override
    protected DateCalculatorFactory<YearMonthDay> getDateCalculatorFactory() {
        return DefaultYearMonthDayCalculatorFactory.getDefaultInstance();
    }

}