package x;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.DefaultModelWriter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Configuration;
import org.codehaus.plexus.component.annotations.Requirement;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

@Component(role = AbstractMavenLifecycleParticipant.class, hint = "writeModel")
public class WriteModel extends AbstractMavenLifecycleParticipant {

    @Override
    public void afterProjectsRead(MavenSession session) throws MavenExecutionException {
        super.afterProjectsRead(session);
        String write = session.getSystemProperties().getProperty("write.model");
        System.out.println("Hi from WriteModel. -Dwrite.model=" + write);
        if ("true".equals(write)) {
            Model model = session.getCurrentProject().getModel();
            savePom(model);
        }
    }

    // Writes a fully resolved model. Not really the desired outcome.
    private void savePom(Model model) {
        DefaultModelWriter writer = new DefaultModelWriter();
        try {
            // avoid fail in DefaultModelWriter.java:59 when output.getParentFile() == null
            File file = new File("updated-pom.xml").getAbsoluteFile();
            writer.write(file, Collections.<String, Object>emptyMap(), model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
