import java.util.Date;

public class ResidentialSite extends IntermediateObject {
	
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
		Dollars result = _zone.calculateSameResult(start, end, usage);
		return calculateDifferentResult(usage, result);
	}

	public int dayOfYear(Date arg) {
		return dayOfYear(arg);
	}
}
