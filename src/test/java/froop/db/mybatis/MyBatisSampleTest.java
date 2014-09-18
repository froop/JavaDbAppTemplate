package froop.db.mybatis;

import org.junit.Before;

import froop.db.base.SampleTestBase;

public class MyBatisSampleTest extends SampleTestBase {

  @Before
  public void setUp() throws Exception {
    target = new MyBatisSample();
  }

  @Override
  protected void beginTransaction() {
  }

  @Override
  protected void commitTransaction() {
  }
}
