package froop.db;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.junit.Before;
import org.junit.Test;

public class JdbcSampleTest extends DerbyDbTestCase {
  private JdbcSample target;

  @Override
  protected IDatabaseConnection getConnection() throws Exception {
    Connection conn = getConnection("jdbc:derby:data/derby/sample");
    return new DatabaseConnection(conn);
  }

  @Override
  protected IDataSet getDataSet() throws Exception {
    return createDataSet(this.getClass().getResourceAsStream("Sample.xml"));
  }

  @Before
  public void setUp() throws Exception {
    super.setUp();
    target = new JdbcSample();
  }

  @Test
  public void testSelectNameById() throws SQLException {
    assertThat(target.selectNameById(1).get(), is("name1"));
  }

  @Test
  public void testUpdate() throws Exception {
    target.update(1, "name1b");

    IDataSet expected = createDataSet(this.getClass().getResourceAsStream("SampleUpdate.xml"));
    assertEqualsTable(expected, "sample");
  }
}
