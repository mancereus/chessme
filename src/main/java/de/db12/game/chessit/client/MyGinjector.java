package de.db12.game.chessit.client;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;

import de.db12.game.chessit.client.main.MainPresenter;
import de.db12.game.chessit.client.online.BoardPresenter;

@GinModules( { MyModule.class })
public interface MyGinjector extends Ginjector {
    PlaceManager getPlaceManager();

    EventBus getEventBus();

    ProxyFailureHandler getProxyFailureHandler();

    AsyncProvider<BoardPresenter> getBoardPresenter();

    AsyncProvider<MainPresenter> getMainPresenter();
}