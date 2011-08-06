package de.db12.game.chessit.shared.game;

public class Move implements Comparable<Move> {
	private Field from;
	private Field to;
	private Integer value;

	public Move(Field from, Field target, int value) {
		this.from = from;
		this.to = target;
		this.value = value;
	}

	public Field getFrom() {
		return from;
	}

	public void setFrom(Field from) {
		this.from = from;
	}

	public Field getTo() {
		return to;
	}

	public void setTo(Field to) {
		this.to = to;
	}

	@Override
	public int compareTo(Move o) {
		return o.getValue().compareTo(getValue());
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
