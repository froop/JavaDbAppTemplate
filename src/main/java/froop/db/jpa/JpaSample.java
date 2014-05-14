package froop.db.jpa;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Optional;

public class JpaSample {
  private static EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("jpa-sample", createDbSetting());

  public Optional<String> selectNameById(long id) {
    EntityManager manager = FACTORY.createEntityManager();
    try {
      Sample res = manager.find(Sample.class, id);
      return Optional.ofNullable(res.getName());
    } finally {
      manager.close();
    }
  }

  public void update(long id, String name) {
    // TODO
  }

  private static HashMap<String, String> createDbSetting() {
    HashMap<String, String> settings = new HashMap<>();
    settings.put(PersistenceUnitProperties.JDBC_URL, "jdbc:derby:data/derby/sample");
    return settings;
  }
}
