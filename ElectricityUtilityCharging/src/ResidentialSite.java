import java.util.Date;

public class ResidentialSite extends IntermediateObject {
	private static final double TAX_RATE = 0.05;
	private Zone _zone;

	ResidentialSite (Zone zone) {
		_zone = zone;
	}

	public void addReading(Reading newReading) {
		addReadingBase(newReading);
	}

	public Dollars charge() {
		return chargeBase();
	}

	protected Dollars charge(int usage, Date start, Date end) {
		Dollars result;
		double summerFraction;
		// Find out how much of period is in the summer
		if (start.after(_zone.summerEnd()) || end.before(_zone.summerStart()))
			summerFraction = 0;
		else if (!start.before(_zone.summerStart()) && !start.after(_zone.summerEnd()) &&
				!end.before(_zone.summerStart()) && !end.after(_zone.summerEnd()))
			summerFraction = 1;
		else { // part in summer part in winter
			double summerDays;
			if (start.before(_zone.summerStart()) || start.after(_zone.summerEnd())) {
				// end is in the summer
				summerDays = dayOfYear(end) - dayOfYear (_zone.summerStart()) + 1;
			} else {
				// start is in summer
				summerDays = dayOfYear(_zone.summerEnd()) - dayOfYear (start) + 1;
			}
			summerFraction = summerDays / (dayOfYear(end) - dayOfYear(start) + 1);
		}
		result = new Dollars ((usage * _zone.summerRate() * summerFraction) +
				(usage * _zone.winterRate() * (1 - summerFraction)));
		result = result.plus(result.times(TAX_RATE));
		Dollars fuel = new Dollars(usage * 0.0175);
		result = result.plus(fuel);
		result = result.plus(fuel.times(TAX_RATE));
		return result;
	}

	public int dayOfYear(Date arg) {
		return dayOfYear(arg);
	}
}
