package de.db12.game.chessit.shared.game;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import de.db12.game.chessit.shared.game.ChessMeGame.Color;
import de.db12.game.chessit.shared.game.ChessMeGame.Stone;

public class ChessMeBoard implements Board {

	int boardsize = 10;

	protected BoardField[] fields;

	private String comment;

	private Game game;

	private int dec(int val) {
		return Math.max(0, val - 1);
	}

	private int inc(int val) {
		return Math.min(boardsize - 1, val + 1);
	}

	@Override
	public void init() {
		fields = new BoardField[boardsize * boardsize];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new BoardField(this, i % boardsize, i / boardsize);
		}
	}

	@Override
	public void initRound() {
	}

	public BoardField getField(int x, int y) {
		return fields[y * boardsize + x];

	}

	public void addCheckField(Set<BoardField> ff, Color color, int x, int y) {
		BoardField field = getField(x, y);
		if (!field.isEmpty() && field.getStone().color == color)
			return;
		field.setReachable(true);
		ff.add(field);
	}

	public List<BoardField> getFieldsWithStones(Color color) {
		List<BoardField> ret = Lists.newArrayList();
		for (BoardField field : fields) {
			if (!field.isEmpty() && field.getStone().color == color)
				ret.add(field);
		}
		return ret;
	}

	public int getMiddleX() {
		return boardsize / 2;
	}

	public int getMiddleY() {
		return boardsize / 2;
	}

	@Override
	public Set<BoardField> getReachableFields(Color color) {
		Set<BoardField> ffs = Sets.newHashSet();
		for (BoardField f : fields) {
			f.setReachable(false);
		}
		for (BoardField f : fields) {
			if (f.isEmpty())
				continue;
			addCheckField(ffs, color, f.getX(), f.getY());
			addCheckField(ffs, color, dec(f.getX()), dec(f.getY()));
			addCheckField(ffs, color, dec(f.getX()), f.getY());
			addCheckField(ffs, color, dec(f.getX()), inc(f.getY()));
			addCheckField(ffs, color, f.getX(), dec(f.getY()));
			addCheckField(ffs, color, f.getX(), f.getY());
			addCheckField(ffs, color, f.getX(), inc(f.getY()));
			addCheckField(ffs, color, inc(f.getX()), dec(f.getY()));
			addCheckField(ffs, color, inc(f.getX()), f.getY());
			addCheckField(ffs, color, inc(f.getX()), inc(f.getY()));
		}
		return ffs;
	}

	public void setStone(int x, int y, Stone takeStone) {
		getField(x, y).setStone(takeStone);
	}

	@Override
	public String toString() {
		// StringBuffer buf = new StringBuffer();

		// for (int i = 0; i < fields.length; i++) {
		// if (i % boardsize == 0)
		// buf.append("\n");
		// buf.append(fields[i].toString() + " ");
		// }
		// return buf.toString();
		return new View(this).toString();
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	static class View {
		int minx = Integer.MAX_VALUE;

		int miny = Integer.MAX_VALUE;
		int maxx = 0;
		int maxy = 0;

		private final ChessMeBoard board;

		public View(ChessMeBoard board) {
			this.board = board;
			Set<BoardField> freeFields = board.getReachableFields(null);
			for (BoardField ff : freeFields) {
				if (ff.getX() < minx)
					minx = ff.getX();
				if (ff.getX() > maxx)
					maxx = ff.getX();
				if (ff.getY() < miny)
					miny = ff.getY();
				if (ff.getY() > maxy)
					maxy = ff.getY();
			}
		}

		@Override
		public String toString() {
			StringBuffer buf = new StringBuffer("\n");

			for (int i = 0; i < board.fields.length; i++) {
				if (i % board.boardsize < minx || i / board.boardsize < miny || i / board.boardsize > maxy) {
					continue;
				}
				if (i % board.boardsize > maxx || i % board.boardsize == 0) {
					buf.append("\n");
					continue;
				}
				buf.append(board.fields[i].toString() + " ");
			}
			buf.append(board.comment);
			return buf.toString();
		}
	}
}
