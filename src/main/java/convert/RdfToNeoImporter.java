package convert;

import convert.access.FileModelGetter;
import convert.access.NeoTransducer;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static com.google.common.collect.Lists.newArrayList;

@Slf4j
public class RdfToNeoImporter {

    public static void main(String... args) throws IOException {

        InputStream inputStream = RdfToNeoImporter.class.getClassLoader().getResourceAsStream("application.properties");
        Properties props = new Properties();
        props.load(inputStream);

        String neoUser = props.getProperty("neo.username");
        String neoPwd = props.getProperty("neo.password");
        String host = props.getProperty("neo.host");
        String iriField = props.getProperty("irifield");
        List<String> rdfFiles = newArrayList(props.getProperty("files"));

        inputStream.close();

        FileModelGetter modelGetter = new FileModelGetter(rdfFiles, RDFFormat.TURTLE);

        Driver driver = GraphDatabase.driver("bolt://" + host, AuthTokens.basic(neoUser, neoPwd));
        Session session = driver.session();

        new NeoTransducer(iriField, modelGetter.getNss())
                .clearDb(session)
                .importObjects(modelGetter.getObjects(), session)
                .importRelations(modelGetter.getRelations(), session);

        session.close();
        driver.close();
    }


}
