import com.arlandis.FeatureInventory;
import mocks.MockFeatureParser;
import com.arlandis.interfaces.FeatureParser;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FeatureInventoryTest {

    private MockFeatureParser mockParser = new MockFeatureParser();
    private FeatureParser parser = mockParser;
    private Map<Boolean, String> parserValueToRoute = new HashMap<Boolean, String>();

    @Before
    public void setUp(){
        mockParser.setPing(true);
        mockParser.setForm(true);
        mockParser.setPostForm(true);
        mockParser.setSleepValue(false);
        mockParser.setGameValue(false);
        mockParser.setBrowseValue(false);
        parserValueToRoute.put(true, "GET /ping");
        parserValueToRoute.put(true, "GET /form");
        parserValueToRoute.put(true, "POST /form");
        parserValueToRoute.put(false, "GET /ping?sleep");
        parserValueToRoute.put(false, "GET /game");
        parserValueToRoute.put(false, "GET /browse");
    }

    @Test
    public void testInventoryQueriesParserForRoutes(){
        FeatureInventory featureInventory = new FeatureInventory(parser);
        for (Boolean val: parserValueToRoute.keySet()){
            String route = parserValueToRoute.get(val);
            if (val){
                assertTrue(featureInventory.isEnabled(route));
            }
            else
                assertFalse(featureInventory.isEnabled(route));
        }
    }

    @Test
    public void testInventoryRespondsFalseToNonsensicalRoutes(){
        FeatureInventory featureInventory = new FeatureInventory(parser);
        assertFalse(featureInventory.isEnabled("GET /foo"));
    }
}
