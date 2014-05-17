package froop.domain;

public class SampleValue {
  private final int id;
  private final String name;

  public static SampleValue of(int id, String name) {
    return new SampleValue(id, name);
  }

  private SampleValue(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
