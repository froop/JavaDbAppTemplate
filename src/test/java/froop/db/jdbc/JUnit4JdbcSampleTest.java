package froop.db.jdbc;

import froop.db.testutil.DerbyDBUnit;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.InputStream;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JUnit4JdbcSampleTest {

  @Rule
  public final DerbyDBUnit dbUnit;

  private JdbcSample target;

  public JUnit4JdbcSampleTest() throws Exception {
    this.dbUnit = DerbyDBUnit.xmlDataSetOf("jdbc:derby:data/derby/sample", getStream("Sample.xml"));
  }

  @Before
  public void setUp() throws Exception {
    target = new JdbcSample();
  }

  @Test
  public void testSelectNameById() throws SQLException {
    assertThat(target.selectNameById(1).get(), is("name1"));
  }

  @Test
  public void testUpdate() throws Exception {
    target.update(1, "name1b");

    dbUnit.assertEqualsTable(getStream("SampleUpdate.xml"), "sample");
  }

  private InputStream getStream(String name) {
    return getClass().getResourceAsStream(name);
  }
}
