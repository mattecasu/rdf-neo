package convert;

import java.util.Set;
import org.eclipse.rdf4j.model.Namespace;

public class NamespacesUtils {

  public static String getCurie(Set<Namespace> nss, String iri) {
    return nss.stream()
        .filter(n -> iri.startsWith(n.getName()))
        .findFirst()
        .map(n -> n.getPrefix() + "__" + iri.replace(n.getName(), ""))
        .orElse(iri);
  }
}
