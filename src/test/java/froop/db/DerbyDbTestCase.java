package froop.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.Assertion;
import org.dbunit.DatabaseTestCase;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import froop.db.common.DerbyConnectionFactory;

public abstract class DerbyDbTestCase extends DatabaseTestCase {

  protected Connection getConnection(String connectUrl) throws SQLException {
    return new DerbyConnectionFactory(connectUrl).getConnection();
  }

  protected IDataSet createDataSet(InputStream stream) throws Exception {
    ReplacementDataSet dataSet = new ReplacementDataSet(
        new FlatXmlDataSetBuilder().build(stream));
    dataSet.addReplacementObject("[NULL]", null);
    return dataSet;
  }

  protected void assertEqualsTable(IDataSet expectedDataSet, String tableName)
      throws Exception {
    assertEqualsTable(expectedDataSet, tableName, null);
  }

  protected void assertEqualsTable(IDataSet expectedDataSet, String tableName,
      String[] excludedColumns) throws Exception {
    ITable expectedTable = expectedDataSet.getTable(tableName);
    IDataSet databaseDataSet = getConnection().createDataSet();
    ITable actualTable = databaseDataSet.getTable(tableName);
    Assertion.assertEquals(
        excludedColumnsTable(expectedTable, excludedColumns),
        excludedColumnsTable(actualTable, excludedColumns));
  }

  private ITable excludedColumnsTable(ITable table, String[] excludedColumns)
      throws DataSetException {
    if (excludedColumns != null) {
      return DefaultColumnFilter.excludedColumnsTable(table, excludedColumns);
    }
    return table;
  }
}
