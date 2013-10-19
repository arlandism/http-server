import com.arlandis.FileReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class FileReaderTest {
    private File file = new File("test/tmp/test.txt");
    private BufferedWriter writer;
    private FileReader reader;

    @Before
    public void setUp() throws IOException {
      (new File("test/tmp")).mkdir();
      file.createNewFile();
      writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
      reader = new FileReader();
    }

    @After
    public void tearDown(){
        file.delete();
    }

    @Test
    public void testRetrieveWithRealFile() throws IOException {
        String testFileContent = "Testing...123...Testing...4567";
        writer.write(testFileContent);
        writer.close();
        assertEquals(testFileContent, reader.retrieve("test/tmp/test.txt"));
    }

    @Test
    public void testRetrieveDir() throws IOException {
      (new File("test/tmp/bar.txt")).createNewFile();
      (new File("test/tmp/bar.txt")).createNewFile();
      String[] expected = {
              "bar.txt",
              "test.txt"
      };
      assertEquals(expected, reader.retrieveDirContents("test/tmp/"));
    }

}
