package ie.nci.casavantesj.figure;

import java.util.ArrayList;
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
		currPosition = new Point(positions, positions);
		points = new ArrayList<Point>(positions);
		pends = new HashMap<Integer, ArrayList<Point>>();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

	public Point addPoint(char c) {
		Point next;
		switch(c) {
		case N:
			next = new Point(currPosition.x, currPosition.y + 1);
			break;
		case W:
			next = new Point(currPosition.x - 1, currPosition.y);
			break;
		case E:
			next = new Point(currPosition.x + 1, currPosition.y);
			break;
		default://S
			next = new Point(currPosition.x, currPosition.y - 1);
			break;
		
		}
		points.add(next);
		if (pends.containsKey(next.pend)) {
			pends.get(next.pend).add(next);
		} else {
			ArrayList<Point> currp = new ArrayList<Point>();
			currp.add(next);
			pends.put(next.pend, currp);
		}
		
		currPosition = next;
		return next;
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
			final double size = Math.floor(array.size()/2);
			System.out.println("array :" + array + ": " + size);
			total += size;
		}
		return total;
	}
	
	public static class Point implements Comparable<Point> {
		int x;
		int y;
		int pend;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
			this.pend = x - y;
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
	}
}
