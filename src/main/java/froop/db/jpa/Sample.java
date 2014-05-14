package froop.db.jpa;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="sample")
public class Sample implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name="id")
  private long id;

  @Column(name="name")
  private String name;

  protected Sample() {}

  public Sample(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
