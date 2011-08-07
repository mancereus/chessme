package de.db12.game.chessit.client.online;

import de.db12.game.chessit.shared.game.Field;

public class FieldView extends StoneView {

	private final Field field;

	public FieldView(Field field, int pxsize) {
		super(field.getStone(), pxsize);
		this.field = field;
	}

	public Field getField() {
		return field;
	}
}
