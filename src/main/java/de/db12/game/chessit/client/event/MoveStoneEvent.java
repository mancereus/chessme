package de.db12.game.chessit.client.event;

import com.google.gwt.event.shared.GwtEvent;

import de.db12.game.chessit.client.online.model.Field;
import de.db12.game.chessit.client.online.model.Place;
import de.db12.game.chessit.client.online.model.Stone;

public class MoveStoneEvent extends GwtEvent<MoveStoneEventHandler> {
    private static final Type TYPE = new Type<MoveStoneEventHandler>();

    private final Place origin;
    private final Place target;

    private final Stone stone;

    public MoveStoneEvent(Place origin, Stone stone, Place target) {
        this.origin = origin;
        this.target = target;
        this.stone = stone;
    }

    public static Type getType() {
        return TYPE;
    }

    public Place getTarget() {
        return target;
    }
    public Place getOrigin() {
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