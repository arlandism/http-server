
import org.junit.Test;

import com.arlandis.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class ConfigTest {

    @Test
    public void testOnlyOneCanBeInstantiated(){
        Config configuration = Config.instance();
        Config secondConfiguration = Config.instance();
        assertSame(configuration, secondConfiguration);
    }

    @Test
    public void testPortNum(){
        Config.instance().setPortNum(8000);
        assertEquals(8000, Config.instance().getPortNum());
    }

    @Test
    public void testRootDir(){
        Config.instance().setRootDir("foo");
        assertEquals("foo/", Config.instance().getRootDir());
    }

}
