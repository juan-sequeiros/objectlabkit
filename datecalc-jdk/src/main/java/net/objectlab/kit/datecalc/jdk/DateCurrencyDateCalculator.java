package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;
import java.util.Date;

import net.objectlab.kit.datecalc.common.CurrencyDateCalculatorBuilder;
import net.objectlab.kit.datecalc.common.NonWorkingDayChecker;
import net.objectlab.kit.datecalc.common.Utils;
import net.objectlab.kit.datecalc.common.ccy.AbstractCurrencyDateCalculator;

/**
 * JDK Date implementation for currency date calculator.
 * @author Benoit Xhenseval
 * @since 1.4.0
 */
public class DateCurrencyDateCalculator extends AbstractCurrencyDateCalculator<Date> {

    public DateCurrencyDateCalculator(final CurrencyDateCalculatorBuilder<Date> builder) {
        super(builder);
    }

    @Override
    protected Date addMonths(final Date calc, final int unit) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(calc);
        cal.add(Calendar.MONTH, unit);
        return cal.getTime();
    }

    @Override
    protected Date calculateNextDay(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    @Override
    protected int calendarWeekDay(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    @Override
    protected Date max(final Date d1, final Date d2) {
        return d1.compareTo(d2) > 0 ? d1 : d2;
    }

    @Override
    protected Date lastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    @Override
    public Date adjustDate(final Date startDate, final int increment, final NonWorkingDayChecker<Date> checker) {
        final Calendar cal = (Calendar) Utils.getCal(calculateNextDay(startDate)).clone();
        final Calendar startDateCalendar = (Calendar) Utils.getCal(startDate).clone();
        int step = increment;
        final int month = startDateCalendar.get(Calendar.MONTH);

        while (checker.isNonWorkingDay(cal.getTime())) {
            cal.add(Calendar.DAY_OF_MONTH, step);
            if (month != cal.get(Calendar.MONTH)) {
                // switch direction and go back
                step *= -1;
                cal.add(Calendar.DAY_OF_MONTH, step);
            }
        }

        return cal.getTime();
    }

    @Override
    protected Boolean equal(Date d1, Date d2) {
        return d1.compareTo(d2) == 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
