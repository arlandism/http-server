import com.arlandis.FileReader;
import com.arlandis.ServerLogger;
import com.arlandis.interfaces.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class ServerLoggerTest {

    private File testLogFile = new File("test/tmp/log_test.txt");
    private FileReader fileReader = new FileReader();
    private ServerLogger logger = ServerLogger.instance(testLogFile.getAbsolutePath());

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

    @Test
    public void testLoggerWritesToFile() throws IOException {
        testLogFile.createNewFile();
        logger.log("Foo");
        assertEquals("Foo", fileReader.retrieve(testLogFile.getName()));
        testLogFile.delete();
    }

    @Test
    public void testOnlyOneLoggerExists(){
        ServerLogger loggerOne = ServerLogger.instance(testLogFile.getName());
        ServerLogger loggerTwo = ServerLogger.instance(testLogFile.getName());
        assertSame(loggerOne, loggerTwo);
    }

    @Test(expected = InstantiationException.class)
    public void testHasPrivateConstructor() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class logger = Class.forName("com.arlandis.ServerLogger");
        logger.newInstance();
    }

    @Test
    public void testMultiThreadedWritesDontInterleave() throws IOException, InterruptedException {
        testLogFile.delete();
        testLogFile.createNewFile();
        Thread threadOne = new Thread(new TestRunnable(logger));
        Thread threadTwo = new Thread(new TestRunnable(logger));
        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();
        assertEquals("Hello World!\nHello World Again!\nHello World!\nHello World Again!", fileReader.retrieve(testLogFile.getAbsolutePath()));
        testLogFile.delete();
    }

}
