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
		return _zone.summerFraction(start, end);
	}

	public int dayOfYear(Date arg) {
		return dayOfYear(arg);
	}
}
