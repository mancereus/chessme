package de.db12.game.chessit.client.online;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class BoardDropController {// extends GridConstrainedDropController {
	private static BoardResources res = BoardResources.INSTANCE;
	private StoneView image;
	private final HandlerManager eventbus;

	public BoardDropController(AbsolutePanel dropTarget, HandlerManager eventbus, int gridX, int gridY) {
		// super(dropTarget, gridX, gridY);
		this.eventbus = eventbus;

	}

	// @Override
	// public void onEnter(DragContext context) {
	// super.onEnter(context);
	// image = (StoneView) context.draggable;
	// }
	//
	// @Override
	// public void onDrop(DragContext context) {
	// super.onDrop(context);
	// // eventbus.fireEvent(new MoveStoneEvent(((StoneView)
	// // this.getDropTarget()).getField(), image.getField()
	// // .getStone()));
	// }
}
