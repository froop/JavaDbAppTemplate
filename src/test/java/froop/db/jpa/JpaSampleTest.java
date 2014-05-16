package froop.db.jpa;

import froop.db.test_common.SampleTestBase;
import org.junit.Before;

public class JpaSampleTest extends SampleTestBase {

  @Before
  public void setUp() throws Exception {
    target = new JpaSample();
  }
}
