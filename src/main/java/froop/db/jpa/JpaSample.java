package froop.db.jpa;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

public class JpaSample {
  private static EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("jpa-sample", createDbSetting());

  public Optional<String> selectNameById(long id) {
    return querySingle(manager -> {
      Sample entity = manager.find(Sample.class, id);
      return entity.getName();
    });
  }

  public void update(long id, String name) {
    EntityManager manager = FACTORY.createEntityManager();
    try {
      EntityTransaction tran = manager.getTransaction();
      tran.begin();
      Sample entity = manager.find(Sample.class, id);
      entity.setName(name);
      manager.persist(entity);
      tran.commit();
    } finally {
      manager.close();
    }
  }

  private static HashMap<String, String> createDbSetting() {
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
}
