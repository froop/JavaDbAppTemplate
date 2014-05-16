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
  private static EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("jpa-sample", createDbSetting());

  @Override
  public Optional<String> selectNameById(long id) {
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

  private static Map<String, String> createDbSetting() {
    HashMap<String, String> settings = new HashMap<>();
    settings.put(PersistenceUnitProperties.JDBC_URL, "jdbc:derby:data/derby/sample");
    return settings;
  }

  private <R> Optional<R> querySingle(Function<EntityManager, R> function) {
    EntityManager manager = FACTORY.createEntityManager();
    try {
      R res = function.apply(manager);
      return Optional.ofNullable(res);
    } finally {
      manager.close();
    }
  }

  private void update(Consumer<EntityManager> consumer) {
    EntityManager manager = FACTORY.createEntityManager();
    try {
      EntityTransaction tran = manager.getTransaction();
      tran.begin();
      consumer.accept(manager);
      tran.commit();
    } finally {
      manager.close();
    }
  }
}
