import java.math.BigInteger;
import java.util.Currency;

public class Dollars {
	private BigInteger amount;
	private Currency currency;
	public static final Dollars ZERO = new Dollars(0);
	
	private Dollars (double amount, Currency currency) {
		this.amount = BigInteger.valueOf(Math.round (amount * 100));
		this.currency = currency;
	}
	
	public Dollars(double amount) {
		//ISO 4217 Currency num for USD is 840
		this(amount, Currency.getInstance("USD"));
	}

	public double amount() {
		return amount.doubleValue() / 100;
	}

	public Currency currency() {
		return currency;
	}

	public Dollars plus (Dollars arg) {
		assertSameCurrencyAs(arg);
		return new Dollars (amount.add(arg.amount), currency, true);
	}

	public Dollars minus (Dollars arg) {
		return this.plus(arg.negate());
	}

	private void assertSameCurrencyAs(Dollars arg) {
		assert currency.equals(arg.currency);
	}

	private Dollars (BigInteger amountInPennies, Currency currency, boolean privacyMarker) {
		assert amountInPennies != null;
		assert currency != null;
		this.amount = amountInPennies;
		this.currency = currency;
	}

	public Dollars negate() {
		return new Dollars (amount.negate(), currency, true);
	}

	public Dollars times (double arg) {
		return new Dollars (amount() * arg, currency);
	}

	public Dollars[] divide(int denominator) {
		BigInteger bigDenominator = BigInteger.valueOf(denominator);
		Dollars[] result = new Dollars[denominator];
		BigInteger simpleResult = amount.divide(bigDenominator);
		for (int i = 0; i < denominator ; i++) {
			result[i] = new Dollars(simpleResult, currency, true);
		}
		int remainder = amount.subtract(simpleResult.multiply(bigDenominator)).intValue();
		for (int i=0; i < remainder; i++) {
			result[i] = result[i].plus(new Dollars(BigInteger.valueOf(1), currency, true));
		}
		return result;
  	}

	public Dollars max(Dollars other) {
		int compare = this.compareTo(other);
		if(compare == -1)
			return other;
		else if(compare == 1)
			return this;
		return this;
	}

	public Dollars min(Dollars other) {
		int compare = this.compareTo(other);
		if(compare == -1)
			return this;
		else if(compare == 1)
			return other;
		return this;
	}

	public int compareTo (Object arg) {
		Dollars moneyArg = (Dollars) arg;
		assertSameCurrencyAs(moneyArg);
		return amount.compareTo(moneyArg.amount);
	}

	public boolean equals(Object arg) {
		if (!(arg instanceof Dollars)) return false;
		Dollars other = (Dollars) arg;
		return (currency.equals(other.currency) && (amount.equals(other.amount)));
	}

	public int hashCode() {
		return amount.hashCode();
	}

	public boolean isGreaterThan(Dollars other) {
		return this.compareTo(other) == 1;
	}
}
