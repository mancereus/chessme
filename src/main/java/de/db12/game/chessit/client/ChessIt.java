package de.db12.game.chessit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

public class ChessIt implements EntryPoint {
	
	public final MyGinjector ginjector = GWT.create(MyGinjector.class);

	public void onModuleLoad() {

		DelayedBindRegistry.bind(ginjector);

		ginjector.getPlaceManager().revealCurrentPlace();
	}
}
