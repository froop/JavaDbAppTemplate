package froop.domain;

import java.util.Optional;

public interface SampleData {

  Optional<String> queryNameById(long id);

  void update(long id, String name);
}
