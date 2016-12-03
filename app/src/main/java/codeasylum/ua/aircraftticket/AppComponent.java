package codeasylum.ua.aircraftticket;

import codeasylum.ua.aircraftticket.modules.JSONParserModule;
import codeasylum.ua.aircraftticket.modules.RequestModule;
import dagger.Component;

/**
 * Created by Андрей on 03.12.2016.
 */

@Component(modules = {JSONParserModule.class, RequestModule.class})
public interface AppComponent {
    void mainActivityInject(MainActivity activity);
}
