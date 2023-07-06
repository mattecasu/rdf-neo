package convert;

import convert.access.FileModelGetter;
import convert.access.NeoTransducer;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

public class RdfToNeoImporter {

  public static void main(String... args) throws IOException {

    var props = PropsGetter.getProperties("application.yaml");

    var neoProps = props.getNeo();

    DirectoryStream<Path> rdfFiles =
        Files.newDirectoryStream(Paths.get(props.getFilesFolder()), "*.{ttl}");

    FileModelGetter modelGetter = new FileModelGetter(rdfFiles, RDFFormat.TURTLE);

    Driver driver =
        GraphDatabase.driver(
            "bolt://" + neoProps.getHost(),
            AuthTokens.basic(neoProps.getUsername(), neoProps.getPassword()));

    Session session = driver.session();

    new NeoTransducer(props.getIriField(), modelGetter.getNss())
        .clearDb(session)
        .importNodes(modelGetter.getObjects(), session)
        .importRelations(modelGetter.getRelations(), session);

    session.close();
    driver.close();
  }
}
