package codeasylum.ua.aircraftticket.modules;

import org.json.JSONObject;

import codeasylum.ua.aircraftticket.JSONParser;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Андрей on 03.12.2016.
 */

@Module
public class JSONParserModule {

    @Provides
    public JSONParser provideJsonParser(){
        return new JSONParser();
    }
}
