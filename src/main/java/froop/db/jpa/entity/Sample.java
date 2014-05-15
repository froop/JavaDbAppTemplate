package froop.db.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Sample implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private long id;

  private String name;

  protected Sample() {}
}
