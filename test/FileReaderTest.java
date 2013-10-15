import com.arlandis.FileReader;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class FileReaderTest {
    private String testFileContent = "Testing...123...Testing...4567";
    private File file = new File("test/test.txt");
    private BufferedWriter writer;

    @Before
    public void setUp() throws IOException {
      file.createNewFile();
      writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
    }

    @Test
    public void testRetrieveWithRealFile() throws IOException {
        writer.write(testFileContent);
        writer.close();
        FileReader reader = new FileReader();
        assertEquals(testFileContent, reader.retrieve("test/test.txt"));
    }
}
