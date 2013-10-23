import com.arlandis.CommandLineParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandLineParserTest {

    @Test
    public void testPortNum(){
        String[] args = { "-p", "6500" };
        CommandLineParser parser = new CommandLineParser(args);
        assertEquals(Integer.valueOf(6500), parser.portNum());
    }

    @Test
    public void testDefaultPort(){
        String[] args = new String[0];
        CommandLineParser parser = new CommandLineParser(args);
        assertEquals(Integer.valueOf(8000), parser.portNum());
    }

    @Test
    public void testBrowsePath(){
        String[] args = {"-d", "foo/bar"};
        CommandLineParser parser = new CommandLineParser(args);
        assertEquals("foo/bar", parser.browsePath());
    }

    @Test
    public void testDefaultBrowsePath(){
        String[] args = new String[0];
        CommandLineParser parser = new CommandLineParser(args);
        assertEquals(System.getProperty("user.dir"), parser.browsePath());
    }

    @Test
    public void testServicePort(){
        String[] args = {"-s", "8800"};
        CommandLineParser parser = new CommandLineParser(args);
        assertEquals(8800, parser.servicePort());
    }

    @Test
    public void testDefaultServicePort(){
        String[] args = new String[0];
        CommandLineParser parser = new CommandLineParser(args);
        assertEquals(5000, parser.servicePort());
    }

    @Test
    public void testBothFlags(){
        String[] args = {"-d", "foo/bar", "-p", "6700"};
        CommandLineParser parser = new CommandLineParser(args);
        assertEquals(Integer.valueOf(6700), parser.portNum());
    }

}
