package froop.db.jdbc;

import froop.db.test_common.DerbyDBUnit;
import froop.domain.SampleData;
import froop.domain.SampleValue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JdbcSampleTest {

  @Rule
  public final DerbyDBUnit dbUnit;

  protected SampleData target;

  public JdbcSampleTest() {
    this.dbUnit = DerbyDBUnit.xmlDataSetOf("jdbc:derby:data/derby/sample", toStream("Sample.xml"));
  }

  @Before
  public void setUp() throws Exception {
    target = new JdbcSample();
  }

  @Test
  public void testQueryAll() throws SQLException {
    assertThat(target.queryAll(), is(Arrays.asList(
        SampleValue.of(1, "name1"), SampleValue.of(2, "name2"))));
  }

  @Test
  public void testQueryById() throws SQLException {
    assertThat(target.queryById(1).get(), is(SampleValue.of(1, "name1")));
  }

  @Test
  public void testUpdate() throws Exception {
    target.update(SampleValue.of(1, "name1b"));

    dbUnit.assertEqualsTable(toStream("SampleUpdate.xml"), "sample");
  }

  private InputStream toStream(String fileName) {
    return getClass().getResourceAsStream(fileName);
  }
}
