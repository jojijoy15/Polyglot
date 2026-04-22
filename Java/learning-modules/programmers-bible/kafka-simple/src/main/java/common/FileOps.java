package common;

import java.io.*;
import java.util.List;

public class FileOps {

  public static List<String> readFile(String path) {
    InputStream resourceAsStream = FileOps.class.getClassLoader()
        .getResourceAsStream(path);
    try (InputStreamReader isr = new InputStreamReader(resourceAsStream);
    BufferedReader br = new BufferedReader(isr)) {
      return br.lines().toList();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    } finally {
      try {
        resourceAsStream.close();
      } catch (IOException ex){
        throw new UncheckedIOException(ex);
      }
    }
  }
}
