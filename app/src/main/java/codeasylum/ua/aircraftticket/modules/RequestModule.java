package codeasylum.ua.aircraftticket.modules;

import codeasylum.ua.aircraftticket.Requests.Request;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Андрей on 03.12.2016.
 */

@Module
public class RequestModule {
    @Provides
    Request provideRequest() {
        return new Request();
    }


}
