package y;

import org.jdom2.JDOMException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PomUpdaterTest {

    @Test
    public void itUpdatesAVersionSomewhatCleanly() throws JDOMException, IOException {
        PomUpdater updater = new PomUpdater();
        VersionUpdateSource updateSource =  new VersionUpdateSource() {
            @Override
            public String newVersionFor(String groupId, String artifactId) {
                if ("commons-io".equals(artifactId)) {
                    return "42.9-SNAPSHOT";
                }
                return null;
            }
        };

        String updated = updater.update("src/test/resources/test-pom.xml", updateSource);

        assertThat(updated, is(contentsOf("expected-out-pom.xml")));
    }

    private String contentsOf(String fileName) throws IOException {
        return new String(Files.readAllBytes(new File("src/test/resources/" + fileName).toPath()));
    }
}