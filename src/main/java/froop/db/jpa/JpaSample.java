package froop.db.jpa;

import froop.db.jpa.model.Sample;
import froop.db.jpa.model.Sample_;
import froop.domain.SampleData;
import froop.domain.SampleValue;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * JPA (Java Persistence API) 2.1 を使ってDBにアクセスするサンプル.
 */
public class JpaSample implements SampleData {
  private static final EntityManagerFactory FACTORY =
      Persistence.createEntityManagerFactory("jpa-sample");

  private final JpaSqlExecutor executor = new JpaSqlExecutor(FACTORY);

  @Override
  public List<SampleValue> queryAll() {
//      List<Sample> entities = manager.createQuery(
//          "FROM Sample s ORDER BY s.name", Sample.class).getResultList();
    return executor.queryMulti(builder -> {
      CriteriaQuery<Sample> query = builder.createQuery(Sample.class);
      Root<Sample> root = query.from(Sample.class);
      return query.orderBy(builder.asc(root.get(Sample_.name)));
    }, entity -> SampleValue.of(entity.getId(), entity.getName()));
  }

  @Override
  public Optional<String> queryNameById(int id) {
    return executor.querySingle(manager -> {
      Sample entity = manager.find(Sample.class, id);
      return entity.getName();
    });
  }

  @Override
  public void update(SampleValue value) {
    executor.update(manager -> {
      Sample entity = manager.find(Sample.class, value.getId());
      entity.setName(value.getName());
      manager.persist(entity);
    });
  }
}
