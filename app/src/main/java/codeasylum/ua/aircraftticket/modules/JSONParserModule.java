package codeasylum.ua.aircraftticket.modules;

import codeasylum.ua.aircraftticket.JSONParser;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Андрей on 03.12.2016.
 */

@Module
public class JSONParserModule {

    @Provides
    JSONParser provideJsonParser() {
        return new JSONParser();
    }
}
