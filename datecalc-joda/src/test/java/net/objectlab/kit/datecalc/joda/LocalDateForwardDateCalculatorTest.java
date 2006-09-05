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

import net.objectlab.kit.datecalc.common.AbstractForwardDateCalculatorTest;
import net.objectlab.kit.datecalc.common.DateCalculatorFactory;
import net.objectlab.kit.datecalc.common.WorkingWeek;

import org.joda.time.LocalDate;

/**
 * 
 * @author xhensevb
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public class LocalDateForwardDateCalculatorTest extends AbstractForwardDateCalculatorTest<LocalDate> {

    @Override
    protected LocalDate newDate(final String date) {
        return new LocalDate(date);
    }

    @Override
    protected WorkingWeek getWorkingWeek(final WorkingWeek ww) {
        return new JodaWorkingWeek(ww);
    }

    @Override
    protected DateCalculatorFactory<LocalDate> getDateCalculatorFactory() {
        return DefaultLocalDateCalculatorFactory.getDefaultInstance();
    }
}