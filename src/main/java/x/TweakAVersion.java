package x;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Dependency;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;

@Component(role = AbstractMavenLifecycleParticipant.class, hint = "tweakVersion")
public class TweakAVersion extends AbstractMavenLifecycleParticipant {

    @Override
    public void afterProjectsRead(MavenSession session) throws MavenExecutionException {
        super.afterProjectsRead(session);
        // 2.2 is in the pom
        String newVersion = session.getSystemProperties().getProperty("tweaked.version", "2.4");
        System.out.println("Hi from TweakAVersion. Writing " + newVersion);
        MavenProject project = session.getCurrentProject();
        for (Dependency dependency : project.getModel().getDependencies()) {
            System.out.println(dependency);
            dependency.setVersion(newVersion);
        }
    }

}
