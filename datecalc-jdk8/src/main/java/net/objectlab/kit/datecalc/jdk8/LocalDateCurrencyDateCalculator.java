package net.objectlab.kit.datecalc.jdk8;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.temporal.TemporalAdjusters;

import net.objectlab.kit.datecalc.common.CurrencyDateCalculatorBuilder;
import net.objectlab.kit.datecalc.common.NonWorkingDayChecker;
import net.objectlab.kit.datecalc.common.ccy.AbstractCurrencyDateCalculator;

/**
 * JDK8 Date implementation for currency date calculator.
 * @author Benoit Xhenseval
 * @since 1.4.0
 */
public class LocalDateCurrencyDateCalculator extends AbstractCurrencyDateCalculator<LocalDate> {

    public LocalDateCurrencyDateCalculator(final CurrencyDateCalculatorBuilder<LocalDate> builder) {
        super(builder);
    }

    @Override
    protected LocalDate addMonths(LocalDate calc, int unit) {
        return calc.plusMonths(unit);
    }

    @Override
    protected LocalDate calculateNextDay(final LocalDate date) {
        return date.plusDays(1);
    }

    @Override
    protected int calendarWeekDay(final LocalDate date) {
        return Jdk8WorkingWeek.jdk8ToCalendarDayConstant(date.getDayOfWeek());
    }

    @Override
    protected LocalDate max(final LocalDate d1, final LocalDate d2) {
        return d1.isAfter(d2) ? d1 : d2;
    }

    @Override
    protected LocalDate lastDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    @Override
    public LocalDate adjustDate(LocalDate startDate, int increment, NonWorkingDayChecker<LocalDate> checker) {
        LocalDate date = startDate;
        final int month = date.getMonthValue();
        int stepToUse = increment;
        while (checker.isNonWorkingDay(date)) {
            date = date.plusDays(stepToUse);
            if (date.getMonthValue() != month) {
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
