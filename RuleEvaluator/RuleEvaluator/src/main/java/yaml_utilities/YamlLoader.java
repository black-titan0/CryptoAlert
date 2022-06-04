package yaml_utilities;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YamlLoader {
    public Map<String , Object> getYamlData(String fileName) {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream( "rules/" + fileName + "-rules.yaml");
        return yaml.load(inputStream);
    }
}
