package de.golfgl.gdxjamgame.oneroom.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.GwtGraphics;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;

import de.golfgl.gdxjamgame.oneroom.GdxJamGame;

public class HtmlLauncher extends GwtApplication {

         // PADDING is to avoid scrolling in iframes, set to 20 if you have problems
         private static final int PADDING = 10;
         private GwtApplicationConfiguration cfg;

         @Override
         public GwtApplicationConfiguration getConfig() {
             int w = Window.getClientWidth() - PADDING;
             int h = Window.getClientHeight() - PADDING;
             cfg = new GwtApplicationConfiguration(w, h);
             cfg.usePhysicalPixels = true;
             cfg.fullscreenOrientation = GwtGraphics.OrientationLockType.LANDSCAPE;
             double density = GwtGraphics.getNativeScreenDensity();
             cfg.width = (int) (cfg.width * density);
             cfg.height = (int) (cfg.height * density);

             Window.enableScrolling(false);
             Window.setMargin("0");
             Window.addResizeHandler(new ResizeListener());
             return cfg;
         }

         class ResizeListener implements ResizeHandler {
             @Override
             public void onResize(ResizeEvent event) {
                 int width = event.getWidth() - PADDING;
                 int height = event.getHeight() - PADDING;
                 getRootPanel().setWidth("" + width + "px");
                 getRootPanel().setHeight("" + height + "px");
                 if (cfg.usePhysicalPixels) {
                     double density = GwtGraphics.getNativeScreenDensity();
                     width = (int) (width * density);
                     height = (int) (height * density);
                 }
                 getApplicationListener().resize(width, height);
                 Gdx.graphics.setWindowedMode(width, height);
             }
         }

        @Override
        public ApplicationListener createApplicationListener () {
                return new GdxJamGame();
        }

    @Override
    public Preloader.PreloaderCallback getPreloaderCallback() {
        return createPreloaderPanel(GWT.getHostPageBaseURL() + "title.png");
    }
}