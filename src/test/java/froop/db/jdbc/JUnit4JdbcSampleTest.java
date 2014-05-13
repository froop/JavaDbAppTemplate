package froop.db.jdbc;

import froop.db.testutil.DBUnitForJUnit4;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.InputStream;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JUnit4JdbcSampleTest {

  @Rule
  public final DBUnitForJUnit4 dbUnit;

  private JdbcSample target;

  public JUnit4JdbcSampleTest() throws ClassNotFoundException, DataSetException {
    this.dbUnit = new DBUnitForJUnit4(
        new JdbcDatabaseTester("org.apache.derby.jdbc.EmbeddedDriver", "jdbc:derby:data/derby/sample", "", ""),
        DatabaseOperation.CLEAN_INSERT, null, createDataSet(this.getClass().getResourceAsStream("Sample.xml")), null);
  }

  @Before
  public void setUp() throws Exception {
    target = new JdbcSample();
  }

  @Test
  public void testSelectNameById() throws SQLException {
    assertThat(target.selectNameById(1).get(), is("name1"));
  }

//  @Test
//  public void testUpdate() throws Exception {
//    target.update(1, "name1b");
//
//    IDataSet expected = createDataSet(this.getClass().getResourceAsStream("SampleUpdate.xml"));
//    assertEqualsTable(expected, "sample");
//  }

  protected IDataSet createDataSet(InputStream stream) throws DataSetException {
    ReplacementDataSet dataSet = new ReplacementDataSet(
        new FlatXmlDataSetBuilder().build(stream));
    dataSet.addReplacementObject("[NULL]", null);
    return dataSet;
  }
}
