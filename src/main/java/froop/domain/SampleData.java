package froop.domain;

import java.util.Optional;

public interface SampleData {

  Optional<String> selectNameById(long id);

  void update(long id, String name);
}
