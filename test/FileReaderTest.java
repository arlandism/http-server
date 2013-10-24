import com.arlandis.FileReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class FileReaderTest {
    private File tmpTest = new File("test/tmp/test.txt");
    private File tmpBar = new File("test/tmp/bar.txt");
    private File fooDir = new File("test/tmp/foo/");
    private BufferedWriter writer;
    private FileReader reader;
    private File[] fileList = {tmpTest, tmpBar, fooDir};

    @Before
    public void setUp() throws IOException {
      (new File("test/tmp")).mkdir();
      tmpTest.createNewFile();
      writer = new BufferedWriter(new FileWriter(tmpTest.getAbsoluteFile()));
      reader = new FileReader();
    }

    @After
    public void tearDown(){
        for (File f: fileList){
            f.delete();
        }
    }

    @Test
    public void testRetrieveWithRealFile() throws IOException {
        String testFileContent = "Testing...123...Testing...4567";
        writer.write(testFileContent);
        writer.close();
        assertEquals(testFileContent, reader.retrieve("test/tmp/test.txt"));
    }

    @Test
    public void testRetrieveDirWithFiles() throws IOException {
      tmpBar.createNewFile();
      String[] expected = {
              "bar.txt",
              "test.txt"
      };
      assertEquals(expected[0], reader.retrieveDirContents("test/tmp/")[0]);
      assertEquals(expected[1], reader.retrieveDirContents("test/tmp/")[1]);
    }

    @Test
    public void testRetrieveDirWithDirectories() {
        fooDir.mkdir();
        String[] expected = {
                "foo/"
        };
        assertEquals(expected[0], reader.retrieveDirContents("test/tmp/")[0]);
    }

}
