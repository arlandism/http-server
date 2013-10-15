import com.arlandis.CommandLineParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandLineParserTest {

    @Test
    public void testPortNum(){
        String[] args = new String[] { "-p", "6500" };
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
        String[] args = new String[] {"-d", "foo/bar"};
        CommandLineParser parser = new CommandLineParser(args);
        assertEquals("foo/bar/", parser.browsePath());
    }

    @Test
    public void testDefaultBrowsePath(){
        String[] args = new String[0];
        CommandLineParser parser = new CommandLineParser(args);
        assertEquals("/", parser.browsePath());
    }

    @Test
    public void testBothFlags(){
        String[] args = new String[] {"-d", "foo/bar", "-p", "6700"};
        CommandLineParser parser = new CommandLineParser(args);
        assertEquals(Integer.valueOf(6700), parser.portNum());
    }

}
