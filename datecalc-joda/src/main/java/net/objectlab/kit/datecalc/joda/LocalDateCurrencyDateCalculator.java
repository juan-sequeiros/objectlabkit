package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.CurrencyDateCalculatorBuilder;
import net.objectlab.kit.datecalc.common.NonWorkingDayChecker;
import net.objectlab.kit.datecalc.common.ccy.AbstractCurrencyDateCalculator;

import org.joda.time.LocalDate;

/**
 * Joda LocalDate implementation for currency date calculator.
 * @author Benoit Xhenseval
 * @since 1.4.0
 */
public class LocalDateCurrencyDateCalculator extends AbstractCurrencyDateCalculator<LocalDate> {

    public LocalDateCurrencyDateCalculator(final CurrencyDateCalculatorBuilder<LocalDate> builder) {
        super(builder);
    }

    @Override
    protected LocalDate addMonths(LocalDate date, int unit) {
        return date.plusMonths(unit);
    }

    @Override
    protected LocalDate calculateNextDay(final LocalDate date) {
        date.dayOfMonth().withMaximumValue();
        return date.plusDays(1);
    }

    @Override
    protected int calendarWeekDay(final LocalDate date) {
        return JodaWorkingWeek.jodaToCalendarDayConstant(date);
    }

    @Override
    protected LocalDate max(final LocalDate d1, final LocalDate d2) {
        return d1.isAfter(d2) ? d1 : d2;
    }

    @Override
    protected LocalDate lastDayOfMonth(LocalDate date) {
        return date.dayOfMonth().withMaximumValue();
    }

    @Override
    public LocalDate adjustDate(LocalDate startDate, int increment, NonWorkingDayChecker<LocalDate> checker) {
        LocalDate date = this.calculateNextDay(startDate);
        final int month = startDate.getMonthOfYear();
        int stepToUse = increment;
        while (checker.isNonWorkingDay(date)) {
            date = date.plusDays(stepToUse);
            if (date.getMonthOfYear() != month) {
                // flick to backward
                stepToUse *= -1;
                date = date.plusDays(stepToUse);
            }
        }
        return date;
    }

    @Override
    protected Boolean equal(LocalDate d1, LocalDate d2) {
        return d1.isEqual(d2);
    }

}
