package de.db12.game.chessit.client.online;

import com.google.gwt.event.shared.EventBus;

public class FieldDropController { // extends SimpleDropController {
	private static BoardResources res = BoardResources.INSTANCE;
	private StoneView image;
	private EventBus eventbus;
	private StoneView field;

	// public FieldDropController(EventBus eventBus, StoneView field) {
	// super(field);
	// this.field = field;
	// this.eventbus = eventBus;
	//
	// }
	//
	// @Override
	// public void onEnter(DragContext context) {
	// super.onEnter(context);
	// image = (StoneView) context.draggable;
	// }
	//
	// @Override
	// public void onDrop(DragContext context) {
	// super.onDrop(context);
	// // eventbus.fireEvent(new MoveStoneEvent(image.getStone(), getStone(),
	// // field.getPlace()));
	// }
}
