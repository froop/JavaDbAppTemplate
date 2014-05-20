package froop.db.jpa;

import froop.db.test_common.SampleTestBase;
import org.junit.After;
import org.junit.Before;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaSampleTest extends SampleTestBase {
  private static final EntityManagerFactory FACTORY =
      Persistence.createEntityManagerFactory("jpa-sample");

  private EntityManager entityManager;

  @Before
  public void setUp() throws Exception {
    entityManager = FACTORY.createEntityManager();
    target = new JpaSample(entityManager);
  }

  @After
  public void tearDown() throws Exception {
    entityManager.close();
  }

  @Override
  public void doUpdate() {
    EntityTransaction tran = entityManager.getTransaction();
    tran.begin();
    super.doUpdate();
    tran.commit();
  }
}
