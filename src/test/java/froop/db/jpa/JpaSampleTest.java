package froop.db.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

import froop.db.base.SampleTestBase;

public class JpaSampleTest extends SampleTestBase {
  private static final EntityManagerFactory FACTORY =
      Persistence.createEntityManagerFactory("jpa-sample");

  private EntityManager entityManager;
  private EntityTransaction transaction;

  @Before
  public void setUp() throws Exception {
    entityManager = FACTORY.createEntityManager();
    target = new JpaSample(entityManager);
  }

  @After
  public void tearDown() throws Exception {
    entityManager.close();
  }

  protected void beginTransaction() {
    transaction = entityManager.getTransaction();
    transaction.begin();
  }

  protected void commitTransaction() {
    transaction.commit();
  }
}
