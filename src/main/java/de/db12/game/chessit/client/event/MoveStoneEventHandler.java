package de.db12.game.chessit.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface MoveStoneEventHandler extends EventHandler {

    void onStoneMoved(MoveStoneEvent event);
}
