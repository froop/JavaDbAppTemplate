package froop.db.jdbc;

import froop.db.test_common.SampleTestBase;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JdbcSampleTest extends SampleTestBase {

  @Before
  public void setUp() throws Exception {
    target = new JdbcSample();
  }

  @Test
  public void testQueryAll() throws SQLException {
    assertThat(target.queryAll(), is(Arrays.asList("name1", "name2")));
  }
}
