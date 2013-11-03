import com.arlandis.CommandLineParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommandLineParserTest {

    String[] emptyArgList = new String[0];
    private CommandLineParser parserWithNoArgs = new CommandLineParser(emptyArgList);

    @Test
    public void testPortNum(){
        String[] args = { "-p", "6500" };
        CommandLineParser parser = new CommandLineParser(args);
        assertEquals(Integer.valueOf(6500), parser.portNum());
        assertEquals(Integer.valueOf(8000), parserWithNoArgs.portNum());
    }

    @Test
    public void testBrowsePath(){
        String currentWorkingDirectory = System.getProperty("user.dir");
        String[] args = {"-d", "foo/bar"};
        CommandLineParser parser = new CommandLineParser(args);
        assertEquals("foo/bar", parser.browsePath());
        assertEquals(currentWorkingDirectory, parserWithNoArgs.browsePath());
    }

    @Test
    public void testServicePort(){
        String[] args = {"-s", "8800"};
        CommandLineParser parser = new CommandLineParser(args);
        assertEquals(8800, parser.servicePort());
        assertEquals(5000, parserWithNoArgs.servicePort());
    }

    @Test
    public void testPingValue(){
        String[] args = {"--ping=false"};
        CommandLineParser parser = new CommandLineParser(args);
        assertFalse(parser.pingValue());
        assertTrue(parserWithNoArgs.pingValue());
    }

    @Test
    public void testFormValue(){
        String[] args = {"--form=false"};
        CommandLineParser parser = new CommandLineParser(args);
        assertFalse(parser.formValue());
        assertTrue(parserWithNoArgs.formValue());
    }

    @Test
    public void testPostFormValue(){
        String[] args = {"--post-form=false"};
        CommandLineParser parser = new CommandLineParser(args);
        assertFalse(parser.postFormValue());
        assertTrue(parserWithNoArgs.postFormValue());
    }

    @Test
    public void testSleepValue(){
        String[] args = {"--sleep=false"};
        CommandLineParser parser  = new CommandLineParser(args);
        assertFalse(parser.sleepValue());
        assertTrue(parserWithNoArgs.sleepValue());
    }

    @Test
    public void testGameValue(){
        String[] args = {"--game=false"};
        CommandLineParser parser = new CommandLineParser(args);
        assertFalse(parser.gameValue());
        assertTrue(parserWithNoArgs.gameValue());
    }

    @Test
    public void testBrowseValue(){
        String[] args = {"--browse=false"};
        CommandLineParser parser = new CommandLineParser(args);
        assertFalse(parser.browseValue());
        assertTrue(parserWithNoArgs.browseValue());
    }

    @Test
    public void testMultipleFlags(){
        String[] args = {"-d", "foo/bar", "-p", "6700", "--browse=true"};
        CommandLineParser parser = new CommandLineParser(args);
        assertEquals(Integer.valueOf(6700), parser.portNum());
        assertEquals("foo/bar", parser.browsePath());
        assertTrue(parser.browseValue());
    }

}