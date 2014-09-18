package froop.db.mybatis;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import froop.domain.SampleData;
import froop.domain.SampleValue;

/**
 * MyBatis ライブラリを使ってDBにアクセスするサンプル.
 */
public class MyBatisSample implements SampleData {
  private static SqlMapClient sqlMap;

  static {
    try {
      String resource = "froop/db/mybatis/SqlMapConfig.xml";
      Reader reader = Resources.getResourceAsReader(resource);
      sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<SampleValue> queryAll() {
    try {
      return toValues(sqlMap.queryForList("selectAll"));
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public Optional<SampleValue> queryById(int id) {
    try {
      SampleRaw raw = (SampleRaw) sqlMap.queryForObject("selectOne", id);
      return Optional.ofNullable(toValue(raw));
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void update(SampleValue value) {
    try {
      sqlMap.update("update", toRaw(value));
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private List<SampleValue> toValues(List rawList) {
    List<SampleValue> res = new ArrayList<SampleValue>();
    for (SampleRaw raw : (List<SampleRaw>) rawList) {
      res.add(toValue(raw));
    }
    return res;
  }

  private SampleValue toValue(SampleRaw raw) {
    if (raw == null) {
      return null;
    }
    return SampleValue.of(raw.getId(), raw.getName());
  }

  private SampleRaw toRaw(SampleValue value) {
    return new SampleRaw(value.getId(), value.getName());
  }
}
