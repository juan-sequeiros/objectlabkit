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

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;

import org.joda.time.LocalDate;

/**
 * A modified preceeding handler will move the date backward if it falls on a
 * non working day BUT, if the new date falls into another month, it will revert
 * to moving forward until it finds a working day.
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public class LocalDateModifiedPreceedingHandler extends LocalDateModifiedFollowingHandler {

    /**
     * Give the type name for this algorithm.
     * 
     * @return algorithm name.
     */
    @Override
    public String getType() {
        return HolidayHandlerType.MODIFIED_PRECEEDING;
    }

    /**
     * If the current date of the give calculator is a non-working day, it will
     * be moved according to the algorithm implemented.
     * 
     * @param calculator
     *            the calculator
     * @return the date which may have moved.
     */
    @Override
    public LocalDate moveCurrentDate(final DateCalculator<LocalDate> calculator) {
        return move(calculator, -1);
    }
}
