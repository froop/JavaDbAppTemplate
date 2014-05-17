package froop.domain;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class SampleValue {

  @NonNull
  private final int id;

  @NonNull
  private final String name;
}
