package convert.access;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eclipse.rdf4j.model.impl.SimpleIRI;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileModelGetterTest {

  private static FileModelGetter modelGetter;

  @BeforeAll
  public static void setup() throws IOException {
    DirectoryStream<Path> rdfFiles =
        Files.newDirectoryStream(Paths.get("src/test/resources/testRdf"), "*.{ttl}");
    modelGetter = new FileModelGetter(rdfFiles, RDFFormat.TURTLE);
  }

  @Test
  public void testGetObjects() {
    var obs = modelGetter.getObjects();
    assertTrue(obs.size() > 100);
    var pizza = obs.get("http://www.co-ode.org/ontologies/pizza/pizza.owl#Pizza");
    var pizzaLabel = pizza.get(RDFS.LABEL.stringValue()).stream().findFirst().get();
    var pizzaClass = pizza.get(RDF.TYPE.stringValue()).stream().findFirst().get();
    assertEquals("Pizza", pizzaLabel);
    assertEquals(pizzaClass, OWL.CLASS.stringValue());
  }

  @Test
  void getRelations() {
    var rels = modelGetter.getRelations();
    assertTrue(rels.size() > 100);
    var pizzaRels =
        rels.stream()
            .filter(
                rel ->
                    ((SimpleIRI) rel.getLeft())
                        .stringValue()
                        .equals("http://www.co-ode.org/ontologies/pizza/pizza.owl#Pizza"))
            .toList();
    assertTrue(pizzaRels.size() > 2);
    var pizzaSubClassRel =
        pizzaRels.stream()
            .filter(rel -> rel.getMiddle().toString().equals(RDFS.SUBCLASSOF.stringValue()))
            .findFirst()
            .get();
    var pizzaSuperClass = pizzaSubClassRel.getRight().toString();
    assertEquals(pizzaSuperClass, "http://www.co-ode.org/ontologies/pizza/pizza.owl#Food");
  }

  @Test
  void getNss() {
    var nss = modelGetter.getNss();
    assertTrue(nss.size() > 3);
    var rdfs = nss.stream().filter(ns -> ns.getName().equals(RDFS.NAMESPACE)).findFirst();
    assertTrue(rdfs.isPresent());
  }

  @Test
  void getModel() {
    assertTrue(modelGetter.getModel().subjects().size() > 100);
  }
}
