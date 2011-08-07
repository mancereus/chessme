package de.db12.game.chessit.shared.game;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChessMeGame extends BaseGame<ChessMeBoard> {
	public enum Color {
		white, black;

		public String desc() {
			if (this == white)
				return "w";
			return "b";
		}

		public int dir() {
			if (this == white)
				return -1;
			return 1;
		}
	}

	public enum Stone {
		whiteking(Color.white, Type.king), whitequeen(Color.white, Type.queen), whitepawn1(Color.white, Type.pawn), whitepawn2(
				Color.white, Type.pawn), whitepawn3(Color.white, Type.pawn), whitepawn4(Color.white, Type.pawn), whitepawn5(
				Color.white, Type.pawn), whitepawn6(Color.white, Type.pawn), whitepawn7(Color.white, Type.pawn), whitepawn8(
				Color.white, Type.pawn), whiterook1(Color.white, Type.rook), whiterook2(Color.white, Type.rook), whitebishop1(
				Color.white, Type.bishop), whitebishop2(Color.white, Type.bishop), whitehorse1(Color.white, Type.horse), whitehorse2(
				Color.white, Type.horse), blackking(Color.black, Type.king), blackqueen(Color.black, Type.queen), blackpawn1(
				Color.black, Type.pawn), blackpawn2(Color.black, Type.pawn), blackpawn3(Color.black, Type.pawn), blackpawn4(
				Color.black, Type.pawn), blackpawn5(Color.black, Type.pawn), blackpawn6(Color.black, Type.pawn), blackpawn7(
				Color.black, Type.pawn), blackpawn8(Color.black, Type.pawn), blackrook1(Color.black, Type.rook), blackrook2(
				Color.black, Type.rook), blackbishop1(Color.black, Type.bishop), blackbishop2(Color.black, Type.bishop), blackhorse1(
				Color.black, Type.horse), blackhorse2(Color.black, Type.horse);
		public static List<Stone> getAllStones(final Color color) {
			List<Stone> ret = new ArrayList<Stone>();
			for (Stone stone : values()) {
				if (stone.color == color)
					ret.add(stone);
			}
			return ret;
		}

		public Color color;

		Type type;

		Stone(Color color, Type type) {
			this.color = color;
			this.type = type;
		}

		@Override
		public String toString() {
			return color.desc() + type.desc();
		}

		public Type getType() {
			return type;
		}
	}

	public enum Type {
		king("k", -1) {
			@Override
			public List<Field> getFields(ChessMeBoard board, Field source, int dir) {
				List<Field> targets = new ArrayList<Field>();
				checkField(targets, board, source.getRow(), source.getCol() - 1);
				checkField(targets, board, source.getRow(), source.getCol() + 1);
				checkField(targets, board, source.getRow() + dir, source.getCol() - 1);
				checkField(targets, board, source.getRow() + dir, source.getCol() + 1);
				checkField(targets, board, source.getRow() + dir, source.getCol());
				Color color = source.getStone().color;
				checkFieldOnlyForKill(targets, board, source.getRow() - dir, source.getCol() - 1, color);
				checkFieldOnlyForKill(targets, board, source.getRow() - dir, source.getCol() + 1, color);
				checkFieldOnlyForKill(targets, board, source.getRow() - dir, source.getCol(), color);
				return targets;
			}

		},
		queen("q", 5) {
			@Override
			public List<Field> getFields(ChessMeBoard board, Field source, int dir) {
				List<Field> targets = new ArrayList<Field>();
				for (int i = 0; i < 8; i++) {
					if (!checkField(targets, board, source.getRow() - i, source.getCol() - i))
						break;
				}
				for (int i = 0; i < 8; i++) {
					if (!checkField(targets, board, source.getRow() - i, source.getCol()))
						break;
				}
				for (int i = 0; i < 8; i++) {
					if (!checkField(targets, board, source.getRow() - i, source.getCol() + i))
						break;
				}
				for (int i = 0; i < 8; i++) {
					if (!checkField(targets, board, source.getRow(), source.getCol() - i))
						break;
				}
				for (int i = 0; i < 8; i++) {
					if (!checkField(targets, board, source.getRow(), source.getCol() + i))
						break;
				}
				for (int i = 0; i < 8; i++) {
					if (!checkField(targets, board, source.getRow() + i, source.getCol() - i))
						break;
				}
				for (int i = 0; i < 8; i++) {
					if (!checkField(targets, board, source.getRow() + i, source.getCol()))
						break;
				}
				for (int i = 0; i < 8; i++) {
					if (!checkField(targets, board, source.getRow() + i, source.getCol() + i))
						break;
				}
				return targets;
			}

		},
		pawn("p", 5) {
			@Override
			public List<Field> getFields(ChessMeBoard board, Field source, int dir) {
				List<Field> targets = new ArrayList<Field>();
				Color color = source.getStone().color;
				checkFieldOnlyForKill(targets, board, source.getRow() + dir, source.getCol() - 1, color);
				checkField(targets, board, source.getRow() + dir, source.getCol());
				checkFieldOnlyForKill(targets, board, source.getRow() + dir, source.getCol() + 1, color);
				return targets;
			}

			@Override
			public boolean isInsertable(ChessMeBoard board, Stone stone, Field field) {
				if (!field.isEmpty())
					return false;
				Field sidefieldleft = board.getField(field.getRow(), field.getCol() - 1);
				if (sidefieldleft != null && !sidefieldleft.isEmpty() && sidefieldleft.getStone().color == stone.color
						&& sidefieldleft.getStone().type == Type.pawn)
					return true;
				Field sidefieldright = board.getField(field.getRow(), field.getCol() + 1);
				if (sidefieldright != null && !sidefieldright.isEmpty()
						&& sidefieldright.getStone().color == stone.color
						&& sidefieldright.getStone().type == Type.pawn)
					return true;
				return false;
			}

		},
		rook("r", 4) {
			@Override
			public List<Field> getFields(ChessMeBoard board, Field source, int dir) {
				List<Field> targets = new ArrayList<Field>();
				for (int i = 0; i < 8; i++) {
					if (!checkField(targets, board, source.getRow() - i, source.getCol()))
						break;
				}
				for (int i = 0; i < 8; i++) {
					if (!checkField(targets, board, source.getRow(), source.getCol() - i))
						break;
				}
				for (int i = 0; i < 8; i++) {
					if (!checkField(targets, board, source.getRow(), source.getCol() + i))
						break;
				}
				for (int i = 0; i < 8; i++) {
					if (!checkField(targets, board, source.getRow() + i, source.getCol()))
						break;
				}
				return targets;
			}

		},
		bishop("b", 4) {
			@Override
			public List<Field> getFields(ChessMeBoard board, Field source, int dir) {
				List<Field> targets = new ArrayList<Field>();
				for (int i = 0; i < 8; i++) {
					if (!checkField(targets, board, source.getRow() - i, source.getCol() - i))
						break;
				}
				for (int i = 0; i < 8; i++) {
					if (!checkField(targets, board, source.getRow() - i, source.getCol() + i))
						break;
				}
				for (int i = 0; i < 8; i++) {
					if (!checkField(targets, board, source.getRow() + i, source.getCol() - i))
						break;
				}
				for (int i = 0; i < 8; i++) {
					if (!checkField(targets, board, source.getRow() + i, source.getCol() + i))
						break;
				}
				return targets;
			}

		},
		horse("h", 4) {
			@Override
			public List<Field> getFields(ChessMeBoard board, Field source, int dir) {
				List<Field> targets = new ArrayList<Field>();
				targets.add(board.getField(source.getRow() - 2, source.getCol() + 1));
				targets.add(board.getField(source.getRow() - 2, source.getCol() - 1));
				targets.add(board.getField(source.getRow() + 2, source.getCol() + 1));
				targets.add(board.getField(source.getRow() + 2, source.getCol() - 1));
				targets.add(board.getField(source.getRow() - 1, source.getCol() + 2));
				targets.add(board.getField(source.getRow() - 1, source.getCol() - 2));
				targets.add(board.getField(source.getRow() + 1, source.getCol() + 2));
				targets.add(board.getField(source.getRow() + 1, source.getCol() - 2));
				return targets;
			}

		};

		private String desc;
		int basicvalue;

		Type(String desc, int value) {
			this.desc = desc;
			this.basicvalue = value;
		}

		protected boolean checkField(List<Field> targets, ChessMeBoard board, int row, int col) {
			if (row < 0 || row >= board.boardsize || col < 0 || col >= board.boardsize)
				return false;
			Field field = board.getField(row, col);
			if (field.isReachable())
				targets.add(field);
			if (field.isEmpty())
				return true;
			return false;

		}

		protected void checkFieldOnlyForKill(List<Field> targets, ChessMeBoard board, int row, int col, Color color) {
			if (row < 0 || row >= board.boardsize || col < 0 || col >= board.boardsize)
				return;
			Field field = board.getField(row, col);
			if (field.isReachable() && !field.isEmpty() && field.getStone().color != color)
				targets.add(field);

		}

		public String desc() {
			return desc;
		}

		public List<Field> getFields(ChessMeBoard board, Field source, int dir) {
			List<Field> targets = new ArrayList<Field>();

			return targets;
		}

		public boolean isInsertable(ChessMeBoard board, Stone stone, Field field) {
			if (!field.isEmpty())
				return false;
			Field forfield = board.getField(field.getRow() + stone.color.dir(), field.getCol());
			if (forfield != null && !forfield.isEmpty() && forfield.getStone().color == stone.color
					&& forfield.getStone().type == Type.pawn)
				return true;
			return false;
		}
	}

	private static final Logger log = LoggerFactory.getLogger(BaseGame.class);

	public static void main(String[] args) {
		List<Player> players = new ArrayList<Player>();
		ChessMeBoard board = new ChessMeBoard();
		players.add(new ChessMePlayer(Color.white, board));
		players.add(new ChessMePlayer(Color.black, board));
		ChessMeGame game = new ChessMeGame(board, players);
		game.init();
		game.start();
	}

	public ChessMeGame(ChessMeBoard board, List<Player> players) {
		super(board, players);
		board.setGame(this);
	}

	@Override
	public void finish() {
		log.info(toString());

	}

	@Override
	public void init() {
		getBoard().init();
		for (Player player : getPlayers()) {
			player.init();
		}
	}

	@Override
	public boolean isFinished() {
		return finished || round > 100;
	}

	@Override
	public String toString() {
		return getBoard().toString() + "\nround: " + round + "\n" + getPlayers().get(0).showHand() + "    "
				+ getPlayers().get(1).showHand();
	}
}
