package convert;

import convert.access.FileModelGetter;
import convert.access.NeoTransducer;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

public class RdfToNeoImporter {

  public static void main(String... args) throws IOException {

    var props = PropsGetter.getProperties("application.yaml");

    Map<String, Object> neoProps = (Map<String, Object>) props.get("neo");
    String neoUser = neoProps.get("username").toString();
    String neoPwd = neoProps.get("password").toString();
    String host = neoProps.get("host").toString();

    String iriField = props.get("irifield").toString();

    DirectoryStream<Path> rdfFiles =
        Files.newDirectoryStream(Paths.get(props.get("filesFolder").toString()), "*.{ttl}");

    FileModelGetter modelGetter = new FileModelGetter(rdfFiles, RDFFormat.TURTLE);

    Driver driver = GraphDatabase.driver("bolt://" + host, AuthTokens.basic(neoUser, neoPwd));

    Session session = driver.session();

    new NeoTransducer(iriField, modelGetter.getNss())
        .clearDb(session)
        .importNodes(modelGetter.getObjects(), session)
        .importRelations(modelGetter.getRelations(), session);

    session.close();
    driver.close();
  }
}
