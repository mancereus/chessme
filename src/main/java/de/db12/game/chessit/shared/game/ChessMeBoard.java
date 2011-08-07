package de.db12.game.chessit.shared.game;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import de.db12.game.chessit.shared.game.ChessMeGame.Color;
import de.db12.game.chessit.shared.game.ChessMeGame.Stone;
import de.db12.game.chessit.shared.game.ChessMeGame.Type;

public class ChessMeBoard implements Board {

	int boardsize = 30;

	protected Field[][] fields;

	private String comment;

	private Game game;
	private final List<Stone> trash = Lists.newArrayList();

	private int dec(int val) {
		return Math.max(0, val - 1);
	}

	private int inc(int val) {
		return Math.min(boardsize - 1, val + 1);
	}

	@Override
	public void init() {
		fields = new Field[boardsize][boardsize];
		for (int row = 0; row < boardsize; row++) {
			for (int col = 0; col < boardsize; col++) {
				fields[row][col] = new Field(this, row, col);
			}
		}
	}

	@Override
	public void initRound() {
	}

	public Field getField(int row, int col) {
		if (row < 0 || row >= boardsize || col < 0 || col >= boardsize)
			return null;
		return fields[row][col];

	}

	// public void addCheckField(Set<BoardField> ff, int row, int col) {
	// BoardField field = getField(row, col);
	// field.setReachable(true);
	// ff.add(field);
	// }

	public List<Field> getFieldsWithStones(Color color) {
		List<Field> ret = Lists.newArrayList();
		for (Field[] fieldrow : fields) {
			for (Field fieldcol : fieldrow) {
				if (!fieldcol.isEmpty() && fieldcol.getStone().color == color)
					ret.add(fieldcol);
			}
		}
		return ret;
	}

	public int getMiddleRow() {
		return boardsize / 2;
	}

	public int getMiddleCol() {
		return boardsize / 2;
	}

	@Override
	public Set<Field> updateReachableFields() {
		Set<Field> ffs = Sets.newHashSet();
		for (Field[] frow : fields) {
			for (Field fcol : frow) {
				fcol.setReachable(false);
			}
		}
		for (Field[] frow : fields) {
			for (Field fcol : frow) {
				if (fcol.isEmpty())
					continue;
				fcol.setReachable(true);
				ffs.add(fcol);
				Set<Field> neighbors = getNeighbors(fcol);
				for (Field boardField : neighbors) {
					boardField.setReachable(true);
				}
				ffs.addAll(neighbors);
			}
		}
		return ffs;
	}

	public Set<Field> getNeighbors(Field field) {
		Set<Field> targets = Sets.newHashSet();
		targets.add(getField(dec(field.getRow()), dec(field.getCol())));
		targets.add(getField(dec(field.getRow()), field.getCol()));
		targets.add(getField(dec(field.getRow()), inc(field.getCol())));
		targets.add(getField(field.getRow(), dec(field.getCol())));
		targets.add(getField(field.getRow(), inc(field.getCol())));
		targets.add(getField(inc(field.getRow()), dec(field.getCol())));
		targets.add(getField(inc(field.getRow()), field.getCol()));
		targets.add(getField(inc(field.getRow()), inc(field.getCol())));
		return targets;
	}

	public Set<Move> getMoves(Player player, Field from) {
		Set<Field> targets = updateReachableFields();
		if (targets.isEmpty())
			return Sets.newHashSet();
		List<Field> moves = from.getMoves();
		targets.retainAll(moves);
		Set<Move> ret = Sets.newHashSet();
		boolean moveok = false;
		int value = 0;
		for (Field target : targets) {
			if (!target.isEmpty() && target.getStone().color == player.getColor())
				continue;
			if (!target.isEmpty() && target.getStone().color != player.getColor()) {
				moveok = true;
				value = 5;
				if (target.getStone().type == Type.king)
					value = 99;
			} else {
				for (Field neighbor : getNeighbors(target)) {
					if (neighbor != from && !neighbor.isEmpty()) {
						moveok = true;
						value = 3;
					}
				}
			}
			if (moveok)
				ret.add(new Move(from, target, value + from.getStone().type.basicvalue));
		}
		return ret;
	}

	public void setStone(int row, int col, Stone takeStone) {
		getField(row, col).setStone(takeStone);
	}

	@Override
	public String toString() {
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

	public static class View {
		public int minrow = Integer.MAX_VALUE;
		public int mincol = Integer.MAX_VALUE;
		public int maxrow = 0;
		public int maxcol = 0;

		private final ChessMeBoard board;

		public View(ChessMeBoard board) {
			this.board = board;
			Set<Field> freeFields = board.updateReachableFields();
			for (Field ff : freeFields) {
				if (ff.getRow() < minrow)
					minrow = ff.getRow();
				if (ff.getRow() > maxrow)
					maxrow = ff.getRow();
				if (ff.getCol() < mincol)
					mincol = ff.getCol();
				if (ff.getCol() > maxcol)
					maxcol = ff.getCol();
			}
		}

		@Override
		public String toString() {
			StringBuffer buf = new StringBuffer("\n");

			for (int row = 0; row < board.fields.length; row++) {
				if (row < minrow || row > maxrow) {
					continue;
				}
				for (int col = 0; col < board.fields[row].length; col++) {
					if (col < mincol || col > maxcol) {
						continue;
					}
					buf.append(board.fields[row][col].toString() + " ");
				}
				buf.append("\n");
			}
			buf.append(board.comment);
			return buf.toString();
		}

		public int getMaxSize() {
			return Math.max(maxrow - minrow, maxcol - mincol) + 1;
		}

		public List<Field> getFields() {
			List<Field> fields = Lists.newArrayList();
			for (int row = 0; row < board.fields.length; row++) {
				if (row < minrow || row > maxrow) {
					continue;
				}
				for (int col = 0; col < board.fields[row].length; col++) {
					if (col < mincol || col > maxcol) {
						continue;
					}
					fields.add(board.fields[row][col]);
				}
			}
			return fields;
		}
	}

	public List<Stone> getTrash() {
		return trash;
	}

	public Set<Field> getInsertFields(Stone stone) {
		Set<Field> inserts = Sets.newHashSet();
		for (int row = 0; row < fields.length; row++) {
			for (int col = 0; col < fields[row].length; col++) {
				if (stone.type.isInsertable(this, stone, fields[row][col]))
					inserts.add(fields[row][col]);
			}
		}
		return inserts;
	}

	public void moveFromTo(Field source, Field target) {
		// TODO Auto-generated method stub

	}

}
