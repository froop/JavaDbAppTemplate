package froop.db.jpa;

import froop.db.jpa.entity.Sample;
import froop.db.jpa.entity.Sample_;
import froop.domain.SampleData;
import org.eclipse.persistence.config.PersistenceUnitProperties;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JpaSample implements SampleData {
  private static final String DB_URL = "jdbc:derby:data/derby/sample";
  private static final EntityManagerFactory FACTORY =
      Persistence.createEntityManagerFactory("jpa-sample", createDbSetting());

  private static Map<String, String> createDbSetting() {
    HashMap<String, String> settings = new HashMap<>();
    settings.put(PersistenceUnitProperties.JDBC_URL, DB_URL);
    return settings;
  }

  @Override
  public List<String> queryAll() {
    return execute(manager -> {
//      TypedQuery<Sample> query = manager.createQuery(
//          "FROM Sample s ORDER BY s.name", Sample.class);
      TypedQuery<Sample> query = manager.createQuery(buildCriteriaQuery(manager.getCriteriaBuilder()));
      List<Sample> result = query.getResultList();
      return result.stream().map(Sample::getName).collect(Collectors.toList());
    });
  }

  private CriteriaQuery<Sample> buildCriteriaQuery(CriteriaBuilder builder) {
    CriteriaQuery<Sample> query = builder.createQuery(Sample.class);
    Root<Sample> root = query.from(Sample.class);
    return query.orderBy(builder.asc(root.get(Sample_.name)));
  }

  @Override
  public Optional<String> queryNameById(long id) {
    return querySingle(manager -> {
      Sample entity = manager.find(Sample.class, id);
      return entity.getName();
    });
  }

  @Override
  public void update(long id, String name) {
    update(manager -> {
      Sample entity = manager.find(Sample.class, id);
      entity.setName(name);
      manager.persist(entity);
    });
  }

  private <R> Optional<R> querySingle(Function<EntityManager, R> function) {
    R res = execute(function);
    return Optional.ofNullable(res);
  }

  private void update(Consumer<EntityManager> consumer) {
    execute(manager -> {
      consumer.accept(manager);
      return null;
    });
  }

  private <R> R execute(Function<EntityManager, R> function) {
    EntityManager manager = FACTORY.createEntityManager();
    try {
      EntityTransaction tran = manager.getTransaction();
      tran.begin();
      R res = function.apply(manager);
      tran.commit();
      return res;
    } finally {
      manager.close();
    }
  }
}
