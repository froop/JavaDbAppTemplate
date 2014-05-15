package froop.db.jpa;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="sample")
@Data
public class Sample implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name="id")
  private long id;

  @Column(name="name")
  private String name;

  protected Sample() {}
}
