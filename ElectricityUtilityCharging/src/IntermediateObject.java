import java.util.Date;

public abstract class IntermediateObject extends Object {
	private static final double TAX_RATE = 0.05;
	public IntermediateObject() {
		super();
	}

	protected Reading[] _readings = new Reading[1000];

	protected void addReadingBase(Reading newReading) {
		int i;
		for (i = 0; _readings[i] != null; i++);
		_readings[i] = newReading;
	}
	
	public int dayOfYear(Date arg) {
		int result;
		switch (arg.getMonth()) {
		case 0:
			result = 0;
			break;
		case 1:
			result = 31;
			break;
		case 2:
			result = 59;
			break;
		case 3:
			result = 90;
			break;
		case 4:
			result = 120;
			break;
		case 5:
			result = 151;
			break;
		case 6:
			result = 181;
			break;
		case 7:
			result = 212;
			break;
		case 8:
			result = 243;
			break;
		case 9:
			result = 273;
			break;
		case 10:
			result = 304;
			break;
		case 11:
			result = 334;
			break;
		default :
			throw new IllegalArgumentException();
		}
		result += arg.getDate();
		//check leap year
		if ((arg.getYear()%4 == 0) && ((arg.getYear() % 100 != 0) ||
				((arg.getYear() + 1900) % 400 == 0)))
		{
			result++;
		}
		return result;
	}

	protected abstract Dollars charge(int fullUsage, Date start, Date end);

	protected Dollars chargeBase() {
		int i;
		for (i = 0; _readings[i] != null; i++);
		int usage = _readings[i - 1].amount() - _readings[i - 2].amount();
		Date end = _readings[i - 1].date();
		Date start = _readings[i - 2].date();
		start.setDate(start.getDate() + 1);
		return charge(usage, start, end);
	}
	
	protected Dollars calculateDifferentResult(int usage, Dollars result) {
		result = result.plus(result.times(TAX_RATE));
		Dollars fuel = new Dollars(usage * 0.0175);
		result = result.plus(fuel);
		result = result.plus(fuel.times(TAX_RATE));
		return result;
	}
}