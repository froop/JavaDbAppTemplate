package froop.db.jdbc;

import froop.db.test_common.SampleTestBase;
import org.junit.Before;

public class JdbcSampleTest extends SampleTestBase {

  @Before
  public void setUp() throws Exception {
    target = new JdbcSample();
  }
}
