package ie.nci.casavantesj.figure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class Main {
	public static final char	N	= 'N';
	public static final char	W	= 'W';
	public static final char	S	= 'S';
	public static final char	E	= 'E';

	private int	positions;
	public Map<Integer, ArrayList<Point>> pends;
	public ArrayList<Point> points;
	public Point currPosition;
	
	public Main(int positions) {
		this.positions = positions;
		currPosition = new Point(positions, positions, '0');
		points = new ArrayList<Point>(positions);
		pends = new HashMap<Integer, ArrayList<Point>>();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str;
		try {
			str = br.readLine();
			int size = Integer.parseInt(str);
			str = br.readLine();
			Main main = new Main(size);
			main.addPoints(str);
				
			System.out.println(main.countStrokes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Point addPoint(char c) {
		Point next;
		switch(c) {
		case N:
			next = new Point(currPosition.x, currPosition.y + 1, c);
			break;
		case W:
			next = new Point(currPosition.x - 1, currPosition.y, c);
			break;
		case E:
			next = new Point(currPosition.x + 1, currPosition.y, c);
			break;
		default://S
			next = new Point(currPosition.x, currPosition.y - 1, c);
			break;
		
		}
		points.add(next);
		checkCorner(currPosition, next);
		currPosition = next;
		return next;
	}

	private void checkCorner(Point current, Point next) {
		char prev = current.c;
		char curr = next.c;
		if (prev == curr 
				|| prev == N && curr == W
				|| prev == W && curr == N
				|| prev == S && curr == E
				|| prev == E && curr == S) {
			if (pends.containsKey(current.pend)) {
				pends.get(current.pend).add(current);
			} else {
				ArrayList<Point> currp = new ArrayList<Point>();
				currp.add(current);
				pends.put(current.pend, currp);
			}
		}
	}

	public void addPoints(String test) {
		char vals[] = test.toCharArray();
		for (char c:vals) {
			addPoint(c);
		}
		checkCorner(points.get(points.size() - 1), points.get(0));
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}
	
	public void cleanupPends() {
		ArrayList<Integer> toremove = new ArrayList<Integer>();
		for (Integer key: pends.keySet()) {
			ArrayList<Point> curr = pends.get(key);
			if (curr.size() < 2) {
				toremove.add(key);
			}
		}
		
		for (Integer key:toremove) {
			pends.remove(key);
		}
	}
	
	public int countStrokes() {
		int total = 0;
		for(ArrayList<Point> array:pends.values()) {
			//Collections.sort(array, currPosition);
			final double size = Math.floor(array.size()/2);
			total += size;
		}
		return total;
	}
	
	public static class Point implements Comparable<Point>, Comparator<Point> {
		int x;
		int y;
		int pend;
		boolean disabled;
		char	c;

		public Point(int x, int y, char c) {
			this.x = x;
			this.y = y;
			this.pend = x - y;
			this.c = c;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Point) {
				Point p = (Point)obj;
				return p.x == x && p.y == y;
			} return false;
		}
		
		@Override
		public String toString() {
			return String.format("(%d, %d -> %d)", x, y, pend);
		}

		@Override
		public int compareTo(Point p) {
			return new Integer(pend).compareTo(p.pend);
		}

		@Override
		public int compare(Point o1, Point o2) {
			return new Integer(o1.x).compareTo(o2.x);
		}
	}
}
