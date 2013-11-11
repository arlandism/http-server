import com.arlandis.FileReader;
import com.arlandis.ServerLogger;
import com.arlandis.interfaces.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class ServerLoggerTest {

    private File testLogFile = new File("test/tmp/log_test.txt");
    private FileReader fileReader = new FileReader();
    private ServerLogger logger = ServerLogger.instance();

    public class TestRunnable implements Runnable {

        private Logger logger;

        public TestRunnable(Logger logger){
            this.logger = logger;
        }

        @Override
        public void run() {
            logger.log("Hello World!\nHello World Again!\n");
        }
    }

    @Before
    public void setUp() throws IOException {
        testLogFile.createNewFile();
        logger.setLogFile(testLogFile.getAbsolutePath());
    }

    @After
    public void tearDown(){
        testLogFile.delete();
    }

    @Test
    public void testLoggerWritesToFile() throws IOException {
        logger.log("Foo");
        assertEquals("Foo", fileReader.retrieve(testLogFile.getAbsolutePath()));
    }

    @Test
    public void testOnlyOneLoggerExists(){
        ServerLogger loggerOne = ServerLogger.instance();
        ServerLogger loggerTwo = ServerLogger.instance();
        assertSame(loggerOne, loggerTwo);
    }

    @Test(expected = IllegalAccessException.class)
    public void testHasPrivateConstructor() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class logger = Class.forName("com.arlandis.ServerLogger");
        logger.newInstance();
    }

    @Test
    public void testMultiThreadedWritesDontInterleave() throws IOException, InterruptedException {
        Thread threadOne = new Thread(new TestRunnable(logger));
        Thread threadTwo = new Thread(new TestRunnable(logger));
        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();
        assertEquals("Hello World!\nHello World Again!\nHello World!\nHello World Again!",
                     fileReader.retrieve(testLogFile.getAbsolutePath()));
    }

}
