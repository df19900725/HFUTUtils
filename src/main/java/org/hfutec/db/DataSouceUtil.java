package org.hfutec.db;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

/**
 * Created by DuFei on 2017/2/22.
 */
public class DataSouceUtil {

  /*******
   * uri:jdbc:mysql://127.0.0.1:3306
   * **********/
  public static DataSource getDataSource(String connectURI){

    BasicDataSource ds = new BasicDataSource();

    ds.setDriverClassName("com.mysql.jdbc.Driver");
    ds.setUsername("root");
    ds.setPassword("112233");
    ds.setMaxWaitMillis(1000*60*20);
    ds.setUrl(connectURI);

    return ds;

  }

  public static DataSource getDataSource(String connectURI, String username, String passwd){

    BasicDataSource ds = new BasicDataSource();

    ds.setDriverClassName("com.mysql.jdbc.Driver");
    ds.setUsername(username);
    ds.setPassword(passwd);
    ds.setMaxWaitMillis(1000*60*20);
    ds.setUrl(connectURI);

    return ds;

  }

}
