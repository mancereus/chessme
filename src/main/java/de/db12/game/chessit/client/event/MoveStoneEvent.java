package de.db12.game.chessit.client.event;

import com.google.gwt.event.shared.GwtEvent;

import de.db12.game.chessit.shared.game.ChessMeGame.Stone;
import de.db12.game.chessit.shared.game.Field;

public class MoveStoneEvent extends GwtEvent<MoveStoneEventHandler> {
	private static final Type TYPE = new Type<MoveStoneEventHandler>();

	private final Field origin;
	private final Field target;

	private final Stone stone;

	public MoveStoneEvent(Field origin, Stone stone, Field target) {
		this.origin = origin;
		this.target = target;
		this.stone = stone;
	}

	public static Type getType() {
		return TYPE;
	}

	public Field getTarget() {
		return target;
	}

	public Field getOrigin() {
		return origin;
	}

	public Stone getStone() {
		return stone;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MoveStoneEventHandler handler) {
		handler.onStoneMoved(this);

	}
}