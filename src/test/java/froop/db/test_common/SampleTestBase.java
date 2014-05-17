package froop.db.test_common;

import froop.domain.SampleData;
import org.junit.Rule;
import org.junit.Test;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public abstract class SampleTestBase {

  @Rule
  public final DerbyDBUnit dbUnit;

  protected SampleData target;

  public SampleTestBase() {
    this.dbUnit = DerbyDBUnit.xmlDataSetOf("jdbc:derby:data/derby/sample", toStream("Sample.xml"));
  }

  @Test
  public void testQueryAll() throws SQLException {
    assertThat(target.queryAll(), is(Arrays.asList("name1", "name2")));
  }

  @Test
  public void testQueryNameById() throws SQLException {
    assertThat(target.queryNameById(1).get(), is("name1"));
  }

  @Test
  public void testUpdate() throws Exception {
    target.update(1, "name1b");

    dbUnit.assertEqualsTable(toStream("SampleUpdate.xml"), "sample");
  }

  private InputStream toStream(String fileName) {
    return SampleTestBase.class.getResourceAsStream(fileName);
  }
}
