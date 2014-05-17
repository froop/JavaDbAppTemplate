package froop.domain;

import java.util.List;
import java.util.Optional;

public interface SampleData {

  List<SampleValue> queryAll();

  Optional<String> queryNameById(int id);

  void update(int id, String name);
}
