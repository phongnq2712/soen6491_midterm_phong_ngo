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
		int usage = Math.min(fullUsage, CAP);
		Dollars result = _zone.calculateSameResult(start, end, usage);
		result = result.plus(new Dollars (Math.max(fullUsage - usage, 0) * 0.062));
		result = calculateRemainingResult(fullUsage, result).minus(FUEL_TAX_CAP).max(Dollars.ZERO);
		return result;
	}

	public int dayOfYear(Date arg) {
		return dayOfYear(arg);
	}
}
