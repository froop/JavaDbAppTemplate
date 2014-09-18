package froop.db.mybatis;

import lombok.Data;

@Data
public class SampleRaw {

  private int id;

  private String name;

  public SampleRaw() {
  }

  public SampleRaw(int id, String name) {
    this.id = id;
    this.name = name;
  }
}
