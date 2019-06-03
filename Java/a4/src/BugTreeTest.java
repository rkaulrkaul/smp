import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class BugTreeTest {

	private static Network n;
	private static Person[] people;
	private static Person personA;
	private static Person personB;
	private static Person personC;
	private static Person personD;
	private static Person personE;
	private static Person personF;
	private static Person personG;
	private static Person personH;
	private static Person personI;
	private static Person personJ;
	private static Person personK;
	private static Person personL;

	@BeforeClass
	public static void setup() {
		n = new Network();
		people = new Person[] { new Person("A", 0, n), new Person("B", 0, n), new Person("C", 0, n),
				new Person("D", 0, n), new Person("E", 0, n), new Person("F", 0, n), new Person("G", 0, n),
				new Person("H", 0, n), new Person("I", 0, n), new Person("J", 0, n), new Person("K", 0, n),
				new Person("L", 0, n) };
		personA = people[0];
		personB = people[1];
		personC = people[2];
		personD = people[3];
		personE = people[4];
		personF = people[5];
		personG = people[6];
		personH = people[7];
		personI = people[8];
		personJ = people[9];
		personK = people[10];
		personL = people[11];
	}

	@Test
	public void testBuiltInGetters() {
		BugTree st = new BugTree(personB);
		assertEquals("B", toStringBrief(st));
	}

	/**
	 * Create a BugTree with structure A[B[D E F[G[H[I]]]] C] <br>
	 * This is the tree
	 *
	 * <pre>
	 *            A
	 *          /   \
	 *         B     C
	 *       / | \
	 *      D  E  F
	 *            |
	 *            G
	 *            |
	 *            H
	 *            |
	 *            I
	 * </pre>
	 */
	private BugTree makeTree1() {
		BugTree dt = new BugTree(personA); // A
		dt.insert(personA, personB); // A, B
		dt.insert(personA, personC); // A, C
		dt.insert(personB, personD); // B, D
		dt.insert(personB, personE); // B, E
		dt.insert(personB, personF); // B, F
		dt.insert(personF, personG); // F, G
		dt.insert(personG, personH); // G, H
		dt.insert(personH, personI); // H, I
		return new BugTree(dt);
	}

	@Test
	public void testMakeTree1() {
		BugTree dt = makeTree1();
		assertEquals("A[B[D E F[G[H[I]]]] C]", toStringBrief(dt));
	}

	@Test
	public void test1insert() {
		BugTree st = new BugTree(personB);

		// Test add to root
		BugTree dt2 = st.insert(personB, personC);
		assertEquals("B[C]", toStringBrief(st)); // test tree
		BugTree dt3 = st.insert(personB, personD);
		assertEquals("B[C D]", toStringBrief(st));
		BugTree dt4 = st.insert(personB, personE);
		BugTree dt5 = st.insert(personB, personF);
		BugTree dt6 = st.insert(personF, personG);
		assertEquals("B[C D E F[G]]", toStringBrief(st));
		assertEquals(personC, dt2.getRoot()); // test return value
		assertEquals(personG, dt6.getRoot());

		BugTree ak = new BugTree(personA);
		BugTree a1 = ak.insert(personA, personD);
		BugTree a2 = ak.insert(personD, personE);
		BugTree a3 = ak.insert(personE, personF);
		BugTree a4 = ak.insert(personF, personG);
		BugTree a5 = ak.insert(personG, personH);
		BugTree a6 = ak.insert(personH, personI);
		assertEquals("A[D[E[F[G[H[I]]]]]]", toStringBrief(ak));

	}

	@Test
	public void test2size() {
		BugTree st = new BugTree(personC);
		assertEquals(1, st.size());
		BugTree dt2 = st.insert(personC, personD);
		BugTree dt3 = st.insert(personD, personE);
		BugTree dt4 = st.insert(personD, personF);
		BugTree dt5 = st.insert(personD, personG);
		BugTree dt6 = st.insert(personD, personH);
		assertEquals("C[D[E F G H]]", toStringBrief(st));
		assertEquals(6, st.size());

		BugTree ak = new BugTree(personA);
		BugTree a1 = ak.insert(personA, personD);
		BugTree a2 = ak.insert(personD, personE);
		BugTree a3 = ak.insert(personE, personF);
		BugTree a4 = ak.insert(personF, personG);
		BugTree a5 = ak.insert(personG, personH);
		BugTree a6 = ak.insert(personH, personI);
		assertEquals(7, ak.size());
	}

	@Test
	public void test3contains() {
		BugTree st = new BugTree(personC);
		assertEquals(true, st.contains(personC));
		BugTree dt2 = st.insert(personC, personD);
		BugTree dt3 = st.insert(personD, personE);
		BugTree dt4 = st.insert(personD, personF);
		BugTree dt5 = st.insert(personD, personG);
		BugTree dt6 = st.insert(personD, personH);
		assertEquals(true, st.contains(personD));
		assertEquals(true, st.contains(personE));
		assertEquals(true, st.contains(personF));
		assertEquals(true, st.contains(personG));
		assertEquals(false, st.contains(personI));

	}

	@Test
	public void test4depthOf() {
		BugTree st = new BugTree(personB);
		BugTree dt2 = st.insert(personB, personC);
		st.insert(personC, personD);
		assertEquals(0, st.depthOf(personB));
		BugTree dt3 = st.insert(personD, personE);
		BugTree dt4 = st.insert(personD, personF);
		BugTree dt5 = st.insert(personF, personG);
		BugTree dt6 = st.insert(personG, personH);
		assertEquals(2, st.depthOf(personD));
		assertEquals(3, st.depthOf(personE));
		assertEquals(3, st.depthOf(personF));
		assertEquals(5, st.depthOf(personH));
		assertEquals(-1, st.depthOf(personI));
		BugTree ak = new BugTree(personA);
		BugTree a1 = ak.insert(personA, personD);
		BugTree a2 = ak.insert(personD, personE);
		BugTree a3 = ak.insert(personE, personF);
		BugTree a4 = ak.insert(personF, personG);
		BugTree a5 = ak.insert(personG, personH);
		BugTree a6 = ak.insert(personH, personI);
		BugTree a7 = ak.insert(personA, personB);
		BugTree a8 = ak.insert(personA, personC);
		assertEquals(6, ak.depthOf(personI));
		assertEquals(1, ak.depthOf(personC));

	}

	@Test
	public void test5WidthAtDepth() {
		BugTree st = new BugTree(personB);
//		assertEquals(1, st.widthAtDepth(0));
		BugTree dt2 = st.insert(personB, personD);
		BugTree dt3 = st.insert(personD, personE);
		BugTree dt4 = st.insert(personD, personF);
		BugTree dt5 = st.insert(personD, personG);
		BugTree dt6 = st.insert(personD, personH);
//		assertEquals(1, st.widthAtDepth(1));
		assertEquals(4, st.widthAtDepth(2));

	}

	@Test
	public void test6bugRouteTo() {
		BugTree st = new BugTree(personB);
		BugTree dt2 = st.insert(personB, personC);
		List route = st.bugRouteTo(personD);
		route = st.bugRouteTo(personB);
		assertEquals("[B]", getNames(route));

	}

	/**
	 * Return the names of Persons in sp, separated by ", " and delimited by [ ].
	 * Precondition: No name is the empty string.
	 */
	private String getNames(List<Person> sp) {
		String res = "[";
		for (Person p : sp) {
			if (res.length() > 1)
				res = res + ", ";
			res = res + p.getName();
		}
		return res + "]";
	}

	@Test
	public void test7sharedAncestorOf() {
		BugTree st = new BugTree(personB);
		BugTree dt2 = st.insert(personB, personC);
		Person p = st.sharedAncestorOf(personC, personC);
		assertEquals(personC, p);

	}

	@Test
	public void test8equals() {
		BugTree tree1 = new BugTree(personB);
		BugTree tree2 = new BugTree(personB);
		assertEquals(true, tree1.equals(tree2));
	}

	// ===================================
	// ==================================

	/**
	 * Return a representation of this tree. This representation is: <br>
	 * (1) the name of the Person at the root, followed by <br>
	 * (2) the representations of the children <br>
	 * . (in alphabetical order of the children's names). <br>
	 * . There are two cases concerning the children.
	 *
	 * . No children? Their representation is the empty string. <br>
	 * . Children? Their representation is the representation of each child, <br>
	 * . with a blank between adjacent ones and delimited by "[" and "]". <br>
	 * <br>
	 * Examples: One-node tree: "A" <br>
	 * root A with children B, C, D: "A[B C D]" <br>
	 * root A with children B, C, D and B has a child F: "A[B[F] C D]"
	 */
	public static String toStringBrief(BugTree t) {
		String res = t.getRoot().getName();

		Object[] childs = t.copyOfChildren().toArray();
		if (childs.length == 0)
			return res;
		res = res + "[";
		selectionSort1(childs);

		for (int k = 0; k < childs.length; k = k + 1) {
			if (k > 0)
				res = res + " ";
			res = res + toStringBrief((BugTree) childs[k]);
		}
		return res + "]";
	}

	/**
	 * Sort b --put its elements in ascending order. <br>
	 * Sort on the name of the Person at the root of each BugTree.<br>
	 * Throw a cast-class exception if b's elements are not BugTree
	 */
	public static void selectionSort1(Object[] b) {
		int j = 0;
		// {inv P: b[0..j-1] is sorted and b[0..j-1] <= b[j..]}
		// 0---------------j--------------- b.length
		// inv : b | sorted, <= | >= |
		// --------------------------------
		while (j != b.length) {
			// Put into p the index of smallest element in b[j..]
			int p = j;
			for (int i = j + 1; i != b.length; i++) {
				String bi = ((BugTree) b[i]).getRoot().getName();
				String bp = ((BugTree) b[p]).getRoot().getName();
				if (bi.compareTo(bp) < 0) {
					p = i;

				}
			}
			// Swap b[j] and b[p]
			Object t = b[j];
			b[j] = b[p];
			b[p] = t;
			j = j + 1;
		}
	}

}
