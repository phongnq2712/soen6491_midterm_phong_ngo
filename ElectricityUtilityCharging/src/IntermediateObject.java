

public abstract class IntermediateObject extends Object {
	public IntermediateObject() {
		super();
	}

	protected Reading[] _readings = new Reading[1000];

	protected void addReadingBase(Reading newReading) {
		int i;
		for (i = 0; _readings[i] != null; i++);
		_readings[i] = newReading;
	}
}