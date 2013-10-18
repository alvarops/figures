package ie.nci.casavantesj.figure.test;

import static ie.nci.casavantesj.figure.Main.E;
import static ie.nci.casavantesj.figure.Main.N;
import static ie.nci.casavantesj.figure.Main.W;
import static org.junit.Assert.assertTrue;
import ie.nci.casavantesj.figure.Main;
import ie.nci.casavantesj.figure.Main.Point;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class MainTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreation() {
		Main main = new Main(1);
		assertTrue("Point is not valid " + main.currPosition, main.currPosition.equals(new Point(1,1, '0')));
		Point p = main.addPoint('N');
		assertTrue("Point is not valid " + p, p.equals(new Point(1,2, N)));
		p = main.addPoint('W');
		assertTrue("Point is not valid " + p, p.equals(new Point(0,2, W)));
		p = main.addPoint('E');
		assertTrue("Point is not valid " + p, p.equals(new Point(1,2, E)));
		ArrayList<Point> points = main.getPoints();
		assertTrue("Points not valid " + points, "[(1, 2 -> -1), (0, 2 -> -2), (1, 2 -> -1)]".equals(points.toString()));
	}
	
	@Test
	public void testSquare() {
		Main main = new Main(8);
		char vals[] = "NNWWSSEE".toCharArray();
		for (char c:vals) {
			main.addPoint(c);
		}
		//ArrayList<Point> points = main.getPoints();
		//System.out.println(points);
		//Collections.sort(points);
		//System.out.println(points);
		//main.cleanupPends();
		//System.out.println(main.pends.size());
		int result = main.countStrokes();
		assertTrue("Unexpected result: " + result, result == 3);
	}
	
	@Test
	public void testRaruno() {
		String test = "NNWWNNWWSSSSWWNWWSSSWWSSEEEEEEEENNNNEE";
		Main main = new Main(test.length());
		main.addPoints(test);
		ArrayList<Point> points = main.getPoints();
		System.out.println(points);
		//main.cleanupPends();
		System.out.println(main.pends.size());
		System.out.println(main.pends.values());
		int result = main.countStrokes();
		assertTrue("Unexpected result: " + result, result == 15);
	}

	
}