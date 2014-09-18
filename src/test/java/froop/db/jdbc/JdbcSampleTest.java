package froop.db.jdbc;

import org.junit.Before;

import froop.db.base.SampleTestBase;

public class JdbcSampleTest extends SampleTestBase {

  @Before
  public void setUp() throws Exception {
    target = new JdbcSample();
  }

  @Override
  protected void beginTransaction() {
  }

  @Override
  protected void commitTransaction() {
  }
}
