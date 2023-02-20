import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class BusinessSiteTest {

	private BusinessSite _subject;

	@Before
	public void setUp() throws Exception {
		new Zone ("A", 0.06, 0.07, new Date ("15 May 1997"), new Date ("10 Sep 1997")).register();
		new Zone ("B", 0.07, 0.06, new Date ("5 Jun 1997"), new Date ("31 Aug 1997")).register();
		new Zone ("C", 0.065, 0.065, new Date ("5 Jun 1997"), new Date ("31 Aug	1997")).register();
		_subject = new BusinessSite();
	}

	@Test
	public void testZero() {
		_subject.addReading(new Reading (10, new Date ("1 Jan 1997")));
		_subject.addReading(new Reading (10, new Date ("1 Feb 1997")));
		assertEquals (Dollars.ZERO, _subject.charge());
	}

	@Test
	public void test100() {
		_subject.addReading(new Reading (10, new Date ("1 Jan 1997")));
		_subject.addReading(new Reading (110, new Date ("1 Feb 1997")));
		assertEquals (new Dollars(7.27), _subject.charge());
	}

	@Test
	public void test99() {
		_subject.addReading(new Reading (100, new Date ("1 Jan 1997")));
		_subject.addReading(new Reading (199, new Date ("1 Feb 1997")));
		assertEquals (new Dollars(7.19), _subject.charge());
	}

	@Test
	public void test101() {
		_subject.addReading(new Reading (1000, new Date ("1 Jan 1997")));
		_subject.addReading(new Reading (1101, new Date ("1 Feb 1997")));
		assertEquals (new Dollars(7.34), _subject.charge());
	}

	@Test
	public void test199() {
		_subject.addReading(new Reading (10000, new Date ("1 Jan 1997")));
		_subject.addReading(new Reading (10199, new Date ("1 Feb 1997")));
		assertEquals (new Dollars(14.4), _subject.charge());
	}

	@Test
	public void test200() {
		_subject.addReading(new Reading (0, new Date ("1 Jan 1997")));
		_subject.addReading(new Reading (200, new Date ("1 Feb 1997")));
		assertEquals (new Dollars(14.48), _subject.charge());
	}

	@Test
	public void test201() {
		_subject.addReading(new Reading (50, new Date ("1 Jan 1997")));
		_subject.addReading(new Reading (251, new Date ("1 Feb 1997")));
		assertEquals (new Dollars(14.55), _subject.charge());
	}

	@Test
	public void testMax() {
		_subject.addReading(new Reading (0, new Date ("1 Jan 1997")));
		_subject.addReading(new Reading (Integer.MAX_VALUE, new Date ("1 Feb 1997")));
		assertEquals (new Dollars(1.5220290473E8), _subject.charge());
	}

	@Test(expected = NullPointerException.class)
	public void testNoReadings() {
		_subject.charge();
	}
}
