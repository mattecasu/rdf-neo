package convert;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class PropsGetterTest {

  @Test
  void getProperties() throws IOException {
    var props = PropsGetter.getProperties("testProps.yaml");
    var neoProps = props.getNeo();
    assertEquals(neoProps.getUsername(), "neo4j");
    assertEquals(neoProps.getHost(), "localhost:7687");
    assertEquals(props.getIriField(), "iri");
  }
}
