package froop.db.jpa.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Sample.class)
public class Sample_ {
  public static volatile SingularAttribute<Sample, Long> id;
  public static volatile SingularAttribute<Sample, String> name;
}
