package froop.db.jpa;

import froop.db.jpa.entity.Sample;
import froop.domain.SampleData;
import org.eclipse.persistence.config.PersistenceUnitProperties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

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
