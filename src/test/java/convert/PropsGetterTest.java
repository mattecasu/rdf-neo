package convert;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PropsGetterTest {

  @Test
  void getProperties() throws IOException {
    var props = PropsGetter.getProperties("testProps.yaml");
    Map<String, Object> neoProps = (Map<String, Object>) props.get("neo");
    assertEquals(neoProps.get("username"), "neo4j");
    assertEquals(neoProps.get("host"), "localhost:7687");
    assertEquals(props.get("irifield"), "iri");
  }
}
