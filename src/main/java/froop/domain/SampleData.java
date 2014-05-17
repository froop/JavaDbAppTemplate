package froop.domain;

import java.util.List;
import java.util.Optional;

public interface SampleData {

  List<String> queryAll();

  Optional<String> queryNameById(long id);

  void update(long id, String name);
}
