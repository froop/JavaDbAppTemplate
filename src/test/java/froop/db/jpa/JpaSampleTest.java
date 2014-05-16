package froop.db.jpa;

import froop.db.testutil.DerbyDBUnit;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.InputStream;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JpaSampleTest {

  @Rule
  public final DerbyDBUnit dbUnit;

  private JpaSample target;

  public JpaSampleTest() {
    this.dbUnit = DerbyDBUnit.xmlDataSetOf("jdbc:derby:data/derby/sample", toStream("Sample.xml"));
  }

  @Before
  public void setUp() throws Exception {
    target = new JpaSample();
  }

  @Test
  public void testSelectNameById() throws SQLException {
    assertThat(target.queryNameById(1).get(), is("name1"));
  }

  @Test
  public void testUpdate() throws Exception {
    target.update(1, "name1b");

    dbUnit.assertEqualsTable(toStream("SampleUpdate.xml"), "sample");
  }

  private InputStream toStream(String fileName) {
    return getClass().getResourceAsStream(fileName);
  }
}
