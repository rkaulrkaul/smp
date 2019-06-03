/** NetId: rk493. Time spent: 4 hours, 10 minutes. */
/** An instance maintains info about the PhD of a person. */
public class PhD {

	/** name (a String). Name of the person with a PhD, a String of length > 0. */
	private String name;
	/** year PhD was awarded. Can be any integer. */
	private int year;
	/** month PhD was awarded. In range 1..12 with 1 being January, etc. */
	private int month;
	/**
	 * first advisor of this person. The first PhD advisor of this person —null if
	 * unknown.
	 */
	private PhD advisor1;
	/**
	 * second advisor of this person. The second advisor of this person —null if
	 * unknown or if the person has less than two advisors.
	 */
	private PhD advisor2;
	/** Number of PhD advisees of this person. */
	private int advisees;

	/**
	 * Constructor: a PhD with PhD month m, PhD year y, and name n.<br>
	 * The advisors are unknown, and there are 0 advisees.<br>
	 * Precondition: n has at least 1 char and m is in 1..12.
	 */
	public PhD(int m, int y, String n) {
		assert n != null && n.length() >= 1;
		assert m >= 1 && m <= 12;
		name = n;
		year = y;
		month = m;
		advisees = 0;
		advisor1 = null;
		advisor2 = null;
	}

	/**
	 * Constructor: a PhD with PhD month m, PhD year y, name n, <br>
	 * first advisor adv1, and no second advisor.<br>
	 * Precondition: n has at least 1 char, m is in 1..12, and adv1 is not null.
	 */
	public PhD(int m, int y, String n, PhD adv1) {
		assert n != null && n.length() >= 1;
		assert m >= 1 && m <= 12;
		assert adv1 != null;
		name = n;
		year = y;
		month = m;
		advisor1 = adv1;
		adv1.advisees += 1;
		advisor2 = null;
		advisees = 0;
	}

	/**
	 * Constructor: a PhD with PhD month m, PhD year y, name n, <br>
	 * first advisor adv1, and second advisor adv2.<br>
	 * Precondition: n has at least 1 char, m is in 1..12, <br>
	 * adv1 and adv2 are not null, and adv1 and adv2 are different.
	 */
	public PhD(int m, int y, String n, PhD adv1, PhD adv2) {
		assert n != null && n.length() >= 1;
		assert m >= 1 && m <= 12;
		assert adv1 != null && adv2 != null;
		assert adv1 != adv2;
		name = n;
		year = y;
		month = m;
		advisor1 = adv1;
		adv1.advisees += 1;
		advisor2 = adv2;
		adv2.advisees += 1;
		advisees = 0;
	}

	/** Return the name of this person. */
	public String name() {
		return name;
	}

	/** Return the year this person got their PhD. */
	public int year() {
		return year;
	}

	/** Return the month this person got their PhD. */
	public int month() {
		return month;
	}

	/** Return the first advisor of this PhD (null if unknown). */
	public PhD advisor1() {
		return advisor1;
	}

	/**
	 * Return the second advisor of this PhD (null if unknown or non-existent).
	 */
	public PhD advisor2() {
		return advisor2;
	}

	/** Return the number of PhD advisees of this person. */
	public int advisees() {
		return advisees;
	}

	/**
	 * Make p the first advisor of this person. <br>
	 * Precondition: the first advisor is unknown and p is not null.
	 */
	public void setAdvisor1(PhD p) {
		assert advisor1 == null;
		assert p != null;
		advisor1 = p;
		p.advisees += 1;
	}

	/**
	 * Make p the second advisor of this person. <br>
	 * Precondition: the first advisor is known, the second advisor is unknown, <br>
	 * p is not null, and p is different from the first advisor
	 */
	public void setAdvisor2(PhD p) {
		assert advisor1 != null;
		assert advisor2 == null;
		assert p != null;
		assert advisor1 != p;
		advisor2 = p;
		p.advisees += 1;
	}

	/**
	 * Return value of: this PhD got the PhD after p.<br>
	 * Precondition: p is not null.
	 */
	public boolean gotAfter(PhD p) {
		assert p != null;
		return month + year * 12 > p.month + p.year * 12;
	}

	/**
	 * Return value of: p is not null and this PhD and p are intellectual siblings.
	 */
	public boolean areSiblings(PhD p) {
		boolean pNull = p != null;
		boolean comAdv1 = pNull && p.advisor1 == advisor1;
		boolean comAdv2 = pNull && p.advisor2 == advisor2;
		boolean comAdvCross1 = pNull && p.advisor1 == advisor2 && advisor2 != null;
		boolean comAdvCross2 = pNull && p.advisor2 == advisor1 && p.advisor2 != null;
		boolean commonAdv = comAdv1 || comAdv2 || comAdvCross1 || comAdvCross2;
		boolean noAdvNull = pNull && p.advisor1 != null || advisor1 != null;
		return this != p && commonAdv && noAdvNull;
	}
}
