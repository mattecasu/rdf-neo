package convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.io.InputStream;
import lombok.Getter;
import lombok.Setter;

public class PropsGetter {

  private static final ObjectMapper mapper =
      new ObjectMapper(new YAMLFactory()).findAndRegisterModules();

  public static NeoConfig getProperties(String fileName) throws IOException {
    InputStream inputStream = PropsGetter.class.getClassLoader().getResourceAsStream(fileName);
    return mapper.readValue(inputStream, NeoConfig.class);
  }

  @Getter
  @Setter
  public static class NeoConfig {
    private NeoProps neo;
    private String iriField;
    private String filesFolder;
  }

  @Getter
  @Setter
  public static class NeoProps {
    private String username;
    private String password;
    private String host;
  }
}
