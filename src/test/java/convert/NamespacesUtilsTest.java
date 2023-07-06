package convert;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.junit.jupiter.api.Test;

class NamespacesUtilsTest {

  private static Set<Namespace> nss =
      newHashSet(
          new SimpleNamespace("owl", OWL.NAMESPACE), new SimpleNamespace("rdf", RDF.NAMESPACE));

  @Test
  void getCurie() {
    assertEquals(NamespacesUtils.getCurie(nss, RDF.TYPE.stringValue()), "rdf__type");
    assertEquals(NamespacesUtils.getCurie(nss, OWL.CLASS.stringValue()), "owl__Class");
  }
}
