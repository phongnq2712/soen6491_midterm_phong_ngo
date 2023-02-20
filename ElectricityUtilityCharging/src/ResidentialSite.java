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
		double summerFraction = summerFraction(start, end);
		result = new Dollars ((usage * _zone.summerRate() * summerFraction) +
				(usage * _zone.winterRate() * (1 - summerFraction)));
		result = result.plus(result.times(TAX_RATE));
		Dollars fuel = new Dollars(usage * 0.0175);
		result = result.plus(fuel);
		result = result.plus(fuel.times(TAX_RATE));
		return result;
	}

	private double summerFraction(Date start, Date end) {
		double summerFraction;
		if (start.after(_zone.summerEnd()) || end.before(_zone.summerStart()))
			summerFraction = 0;
		else if (!start.before(_zone.summerStart()) && !start.after(_zone.summerEnd())
				&& !end.before(_zone.summerStart()) && !end.after(_zone.summerEnd()))
			summerFraction = 1;
		else {
			double summerDays = summerDays(start, end);
			summerFraction = summerDays / (dayOfYear(end) - dayOfYear(start) + 1);
		}
		return summerFraction;
	}

	private double summerDays(Date start, Date end) {
		double summerDays;
		if (start.before(_zone.summerStart()) || start.after(_zone.summerEnd())) {
			summerDays = dayOfYear(end) - dayOfYear(_zone.summerStart()) + 1;
		} else {
			summerDays = dayOfYear(_zone.summerEnd()) - dayOfYear(start) + 1;
		}
		return summerDays;
	}

	public int dayOfYear(Date arg) {
		return dayOfYear(arg);
	}
}
