package froop.db.testutil;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.IOperationListener;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import java.io.InputStream;

public class DerbyDBUnit extends DBUnitForJUnit4 {

  public static DerbyDBUnit xmlDataSetOf(String dbUrl, InputStream dataXml) {
    try {
      return new DerbyDBUnit(new JdbcDatabaseTester("org.apache.derby.jdbc.EmbeddedDriver", dbUrl),
          DatabaseOperation.CLEAN_INSERT, null, createDataSet(dataXml), null);
    } catch (ClassNotFoundException | DataSetException e) {
      throw new IllegalStateException(e);
    }
  }

  private DerbyDBUnit(IDatabaseTester databaseTester, DatabaseOperation setupOperation, DatabaseOperation tearDownOperation,
                     IDataSet dataSet, IOperationListener operationListener) {
    super(databaseTester, setupOperation, tearDownOperation, dataSet, operationListener);
  }

  public void assertEqualsTable(InputStream expectedXml, String tableName)
      throws Exception {
    assertEqualsTable(expectedXml, tableName, null);
  }

  public void assertEqualsTable(InputStream expectedXml, String tableName,
                                   String[] excludedColumns) throws Exception {
    ITable expectedTable = createDataSet(expectedXml).getTable(tableName);
    IDataSet actualDataSet = getDatabaseDataSet();
    ITable actualTable = actualDataSet.getTable(tableName);
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

  private static IDataSet createDataSet(InputStream stream) throws DataSetException {
    ReplacementDataSet dataSet = new ReplacementDataSet(
        new FlatXmlDataSetBuilder().build(stream));
    dataSet.addReplacementObject("[NULL]", null);
    return dataSet;
  }
}
