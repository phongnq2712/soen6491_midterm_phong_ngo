import java.util.Date;

public class Zone extends IntermediateObject {
	private String _name;
	private Date _summerEnd;
	private Date _summerStart;
	private double _winterRate;
	private double _summerRate;

	public Zone (String name, double summerRate, double winterRate,
			Date summerStart, Date summerEnd) {
		_name = name;
		_summerRate = summerRate;
		_winterRate = winterRate;
		_summerStart = summerStart;
		_summerEnd = summerEnd;
	}

	public Zone register() {
		Registry.getInstance().addZone(this);
		return this;
	}

	public static Zone get (String name) {
		return Registry.getInstance().getZone(name);
	}

	public String name() {
		return _name;
	}

	public Date summerEnd() {
		return _summerEnd;
	}

	public Date summerStart() {
		return _summerStart;
	}

	public double winterRate() {
		return _winterRate;
	}

	public double summerRate() {
		return _summerRate;
	}
	
	protected double summerFraction(Date start, Date end) {
		double summerFraction;
		if (start.after(this.summerEnd()) || end.before(this.summerStart()))
			summerFraction = 0;
		else if (!start.before(this.summerStart()) && !start.after(this.summerEnd())
				&& !end.before(this.summerStart()) && !end.after(this.summerEnd()))
			summerFraction = 1;
		else {
			double summerDays = summerDays(start, end);
			summerFraction = summerDays / (dayOfYear(end) - dayOfYear(start) + 1);
		}
		return summerFraction;
	}
	
	protected double summerDays(Date start, Date end) {
		double summerDays;
		if (start.before(this.summerStart()) || start.after(this.summerEnd())) {
			summerDays = dayOfYear(end) - dayOfYear(this.summerStart()) + 1;
		} else {
			summerDays = dayOfYear(this.summerEnd()) - dayOfYear(start) + 1;
		}
		return summerDays;
	}

	@Override
	protected Dollars charge(int fullUsage, Date start, Date end) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected Dollars calculateSameResult(Date start, Date end, int usage) {
		Dollars result;
		double summerFraction = summerFraction(start, end);
		result = new Dollars ((usage * this.summerRate() * summerFraction) +
				(usage * this.winterRate() * (1 - summerFraction)));
		return result;
	}
}
