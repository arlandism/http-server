import com.arlandis.FeatureToggler;
import mocks.MockFeatureParser;
import com.arlandis.interfaces.FeatureParser;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FeatureTogglerTest {

    private MockFeatureParser mockParser = new MockFeatureParser();
    private FeatureParser parser = mockParser;

    @Test
    public void testTogglerQueriesForPing(){
        mockParser.setPing(true);
        FeatureToggler toggler = new FeatureToggler(parser);
        assertTrue(toggler.isEnabled("GET /ping"));
    }

    @Test
    public void testTogglerQueriesForForm(){
        mockParser.setForm(false);
        FeatureToggler toggler = new FeatureToggler(parser);
        assertFalse(toggler.isEnabled("GET /form"));
    }

    @Test
    public void testTogglerQueriesForPostForm(){
        mockParser.setPostForm(true);
        FeatureToggler toggler = new FeatureToggler(parser);
        assertTrue(toggler.isEnabled("POST /form"));
    }

    @Test
    public void testTogglerQueriesForSleep(){
        mockParser.setSleepValue(false);
        FeatureToggler toggler = new FeatureToggler(parser);
        assertFalse(toggler.isEnabled("GET /ping?sleep"));
    }

    @Test
    public void testTogglerQueriesForGame(){
        mockParser.setGameValue(true);
        FeatureToggler toggler = new FeatureToggler(parser);
        assertTrue(toggler.isEnabled("GET /game"));
    }

    @Test
    public void testTogglerQueriesParserForBrowse(){
        mockParser.setBrowseValue(false);
        FeatureToggler toggler = new FeatureToggler(parser);
        assertFalse(toggler.isEnabled("GET /browse"));
    }
}
