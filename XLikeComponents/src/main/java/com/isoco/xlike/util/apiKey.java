package com.isoco.xlike.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class apiKey {
  public static void main(String[] args) throws Exception {
    System.out.println("Test");
	 Class.forName("org.sqlite.JDBC");
    Connection conn =
      DriverManager.getConnection("jdbc:sqlite:db_api_key.db");
    Statement stat = conn.createStatement();
    
    stat.executeUpdate("drop table if exists listkey;");
    stat.executeUpdate("create table listkey (name, key);");
    PreparedStatement prep = conn.prepareStatement(
      "insert into listkey values (?, ?);");

    prep.setString(1, "cruiz");
    prep.setString(2, "5107300523422220000000000");
    prep.addBatch();
    prep.setString(1, "rcharro");
    prep.setString(2, "6635927414238140000000000");
    prep.addBatch();

    conn.setAutoCommit(false);
    prep.executeBatch();
    conn.setAutoCommit(true);
    System.out.println("Test2");
    ResultSet rs = stat.executeQuery("select * from listkey;");
    while (rs.next()) {
    	System.out.println("Test3");
      System.out.println("name = " + rs.getString("name"));
      System.out.println("key = " + rs.getString("key"));
    }
    rs.close();
    conn.close();
  }
}
