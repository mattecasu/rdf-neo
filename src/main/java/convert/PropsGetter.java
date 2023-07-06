package convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class PropsGetter {

  private static final ObjectMapper mapper =
      new ObjectMapper(new YAMLFactory()).findAndRegisterModules();

  public static Map<String, Object> getProperties(String fileName) throws IOException {
    InputStream inputStream = PropsGetter.class.getClassLoader().getResourceAsStream(fileName);
    return mapper.readValue(inputStream, Map.class);
  }
}
