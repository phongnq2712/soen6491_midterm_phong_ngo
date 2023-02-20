import java.util.Date;

public class DisabilitySite extends IntermediateObject {
	private static final Dollars FUEL_TAX_CAP = new Dollars (0.10);
	private static final double TAX_RATE = 0.05;
	private Zone _zone;
	private static final int CAP = 200;

	public DisabilitySite(Zone _zone) {
		this._zone = _zone;
	}

	public void addReading(Reading newReading) {
		addReadingBase(newReading);
	}

	public Dollars charge() {
		return chargeBase();
	}

	protected Dollars charge(int fullUsage, Date start, Date end) {
		Dollars result;
		double summerFraction = summerFraction(start, end);
		int usage = Math.min(fullUsage, CAP);
		result = new Dollars ((usage * _zone.summerRate() * summerFraction) +
				(usage * _zone.winterRate() * (1 - summerFraction)));
		result = result.plus(new Dollars (Math.max(fullUsage - usage, 0) * 0.062));
		result = result.plus(result.times(TAX_RATE));
		Dollars fuel = new Dollars(fullUsage * 0.0175);
		result = result.plus(fuel);
		result = result.plus(fuel.times(TAX_RATE).minus(FUEL_TAX_CAP)).max(Dollars.ZERO);
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
