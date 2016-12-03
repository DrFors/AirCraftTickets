package codeasylum.ua.aircraftticket;

import android.app.Application;

/**
 * Created by Андрей on 03.12.2016.
 */

public class App extends Application {
    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.create();
    }

    public static AppComponent getComponent() {
        return component;
    }
}
