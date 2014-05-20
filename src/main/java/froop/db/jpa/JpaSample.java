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

  private final EntityManager entityManager;

  public JpaSample(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<SampleValue> queryAll() {
//      List<Sample> entities = manager.createQuery(
//          "FROM Sample s ORDER BY s.name", Sample.class).getResultList();
    return new JpaQueryExecutor(entityManager).queryMulti(builder -> {
      CriteriaQuery<Sample> query = builder.createQuery(Sample.class);
      Root<Sample> root = query.from(Sample.class);
      return query.orderBy(builder.asc(root.get(Sample_.name)));
    }, this::toValue);
  }

  @Override
  public Optional<SampleValue> queryById(int id) {
    Sample entity = entityManager.find(Sample.class, id);
    return Optional.ofNullable(toValue(entity));
  }

  @Override
  public void update(SampleValue value) {
    Sample entity = entityManager.find(Sample.class, value.getId());
    entity.setName(value.getName());
    entityManager.persist(entity);
  }

  private SampleValue toValue(Sample entity) {
    return SampleValue.of(entity.getId(), entity.getName());
  }
}
