package froop.db.jpa;

import froop.db.jpa.entity.Sample;
import froop.db.jpa.entity.Sample_;
import froop.domain.SampleData;

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
  public List<String> queryAll() {
//      List<Sample> entities = manager.createQuery(
//          "FROM Sample s ORDER BY s.name", Sample.class).getResultList();
    return executor.queryMulti(builder -> {
      CriteriaQuery<Sample> query = builder.createQuery(Sample.class);
      Root<Sample> root = query.from(Sample.class);
      return query.orderBy(builder.asc(root.get(Sample_.name)));
    }, Sample::getName);
  }

  @Override
  public Optional<String> queryNameById(long id) {
    return executor.querySingle(manager -> {
      Sample entity = manager.find(Sample.class, id);
      return entity.getName();
    });
  }

  @Override
  public void update(long id, String name) {
    executor.update(manager -> {
      Sample entity = manager.find(Sample.class, id);
      entity.setName(name);
      manager.persist(entity);
    });
  }
}
