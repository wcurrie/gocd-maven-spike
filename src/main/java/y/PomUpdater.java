package y;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.LineSeparator;
import org.jdom2.output.XMLOutputter;

import java.io.*;

public class PomUpdater {

    public String update(String pomFileName, VersionUpdateSource updateSource) throws JDOMException, IOException {
        Document document = parseXml(pomFileName);
        applyUpdates(document, updateSource);
        return backToAString(document);
    }

    private Document parseXml(String pomFileName) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        return builder.build(new File(pomFileName));
    }

    private void applyUpdates(Document document, VersionUpdateSource updateSource) {
        for (Element dependency : document.getDescendants(new ElementFilter("dependency"))) {
            String groupId = getField(dependency, "groupId");
            String artifactId = getField(dependency, "artifactId");
            String newVersion = updateSource.newVersionFor(groupId, artifactId);
            if (newVersion != null) {
                Element version = dependency.getChild("version", dependency.getNamespace());
                version.setText(newVersion);
            }
        }
    }

    private String backToAString(Document document) throws IOException {
        try (StringWriter out = new StringWriter()) {
            // some quirk in LineSeparator.java:164 means we get \r\n by default...
            Format format = Format.getRawFormat().setLineSeparator(LineSeparator.SYSTEM);
            XMLOutputter novelSpelling = new XMLOutputter(format);
            novelSpelling.output(document, out);
            out.flush();
            return out.toString();
        }
    }

    private String getField(Element dependency, String element) {
        return dependency.getChild(element, dependency.getNamespace()).getText();
    }
}
