package de.db12.game.chessit.client.online;

public class BoardSize {
	private int minx = Integer.MAX_VALUE;
	private int maxx;
	private int miny = Integer.MAX_VALUE;
	private int maxy;

	public BoardSize(int minrow, int maxrow, int mincol, int maxcol) {
		// TODO Auto-generated constructor stub
	}

	public void updateX(int x) {
		if (minx > x)
			minx = x;
		if (maxx < x)
			maxx = x;
	}

	public void updateY(int y) {
		if (miny > y)
			miny = y;
		if (maxy < y)
			maxy = y;
	}

}
