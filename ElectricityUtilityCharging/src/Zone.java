import java.util.Date;

public class Zone {
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
}
