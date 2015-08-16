Experiment with a maven `AbstractMavenLifecycleParticipant`. Steps:

1. `mvn install` on the root project to install the extensions into $HOME/.m2/repository
2. `cd src/it/tweak-my-version` to change into a test project that uses the extension via `.mvn/extensions.xml`
3. `mvn package` in this directory to see the extensions run

Variations on step 3.

* `mvn help:effective-pom` to see the updated version
* `mvn package -Dtweaked.version=MISSING` to see a fail because commons-io can't be found
* `mvn package -Dwrite.model=true` to dump the modified pom.xml

Based on Maven 3 lifecycle extensions [guide](http://maven.apache.org/examples/maven-3-lifecycle-extensions.html).

Extension activated using `.mvn/extensions.xml` from takara [write-up](http://takari.io/2015/03/19/core-extensions.html).