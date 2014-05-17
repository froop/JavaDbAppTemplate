package froop.domain;

import java.util.List;
import java.util.Optional;

public interface SampleData {

  List<SampleValue> queryAll();

  Optional<SampleValue> queryById(int id);

  void update(SampleValue value);
}
