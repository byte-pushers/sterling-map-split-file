package software.bytepushers.util;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class SterlingSplitterTest {
    private static final String RESOURCES_PATH = "src/test/resources";

    @Test
    public void testOverTheNumberOfLines() throws Exception {
        String filename = "OVER.AMAZ";
        SterlingSplitter underTest = new SterlingSplitter(Paths.get(RESOURCES_PATH, filename), 20);
        underTest.run();

        String[] fileExt = filename.split("\\.");

        for (int i = 0; i < 3; ++i) {
            assertFileHasLines(fileExt, i, 20);
        }

        assertFileHasLines(fileExt, 3, 2);


        assertThat(getFileAsPath(fileExt, 4).toFile()).doesNotExist();
    }

    @Test
    public void testUnderTheNumberOfLines() throws Exception {
        String filename = "UNDER.AMAZ";
        SterlingSplitter underTest = new SterlingSplitter(Paths.get(RESOURCES_PATH, filename), 20);
        underTest.run();

        String[] fileExt = filename.split("\\.");

        for (int i = 0; i < 2; ++i) {
            assertFileHasLines(fileExt, i, 20);
        }

        assertFileHasLines(fileExt, 2, 16);

        assertThat(getFileAsPath(fileExt, 3).toFile()).doesNotExist();
    }


    @Test
    public void testExactlyTheNumberOfLines() throws Exception {
        String filename = "EXACTLY.AMAZ";
        SterlingSplitter underTest = new SterlingSplitter(Paths.get(RESOURCES_PATH, filename), 20);
        underTest.run();

        String[] fileExt = filename.split("\\.");

        for (int i = 0; i < 3; ++i) {
            assertFileHasLines(fileExt, i, 20);
        }


        assertThat(getFileAsPath(fileExt, 3).toFile()).doesNotExist();
    }

    private Path getFileAsPath(String[] fileExt, int fileNum){
        return Paths.get(SterlingSplitter.nextFileName(fileExt,fileNum));
    }

    private void assertFileHasLines(String[] fileExt, int filenum, int numLines) throws Exception {
        Path p = getFileAsPath(fileExt, filenum);
        assertThat(p.toFile()).exists();
        assertThat(Files.readAllLines(p).size()).isEqualTo(numLines);
        p.toFile().deleteOnExit();
    }

}
