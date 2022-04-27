package DataBase;

import Makers.ProgressBarMaker;
import Repository.Item;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbHandler {

    public static Connection conn;
    public static Statement statement;
    public static ResultSet resSet;

    private static final String DB_FILE_NAME = "CurrentOrdersList.db";


    // --------Connection to a Database--------
    public static void connect() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:"+DB_FILE_NAME);

    }


    // --------Creating a table--------
    public static void createTable() throws ClassNotFoundException, SQLException {
        statement = conn.createStatement();
        statement.execute("CREATE TABLE if not exists 'Orders' (" +
                "'OCN'         VARCHAR (50) NOT NULL," +
                "'itemNumber'  VARCHAR (50) NOT NULL," +
                "'itemQty'     VARCHAR (10)," +
                "'itemAvl'     VARCHAR (10)," +
                "'itemPicked'  VARCHAR (10)," +
                "'itemBckOrd'  VARCHAR (10)," +
                "'itemShipped' VARCHAR (10));");

    }


    // --------Getting all items to be shipped from the table--------
    public static List<Item> getAllShippedItems() throws SQLException {

        List<Item> resultList = new ArrayList<>();

        statement = conn.createStatement();

        resSet = statement.executeQuery("SELECT * FROM Orders WHERE itemPicked <> 0;");

        while (resSet.next()) {

            resultList.add(new Item(
                    resSet.getString("OCN"),
                    resSet.getString("itemNumber"),
                    resSet.getInt("itemQty"),
                    resSet.getInt("itemAvl"),
                    resSet.getInt("itemPicked"),
                    resSet.getInt("itemBckOrd"),
                    resSet.getInt("itemShipped")));

        }

        return resultList;

    }

    // --------Adding all items to the table--------
    public static void addItems(String[][] baseArray, int baseRows) throws SQLException {

        ProgressBarMaker pbm = new ProgressBarMaker(new JFrame("Loading into database"));

        pbm.prepareBar(baseRows);

        PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO Orders (OCN, itemNumber, itemQty, itemAvl, itemPicked, " +
                        "itemBckOrd, itemShipped) VALUES (?, ?, ?, ?, ?, ?, ?)");

        for (int i = 1; i < baseRows; i++) {

            statement.setString(1, baseArray[i][0]);
            statement.setString(2, baseArray[i][1]);
            statement.setString(3, baseArray[i][2]);
            statement.setString(4, baseArray[i][3]);
            statement.setString(5, baseArray[i][4]);
            statement.setString(6, baseArray[i][5]);
            statement.setString(7, baseArray[i][6]);

            statement.addBatch();

            pbm.launchBar(i);

        }
        statement.executeBatch();
        pbm.closeBar();

    }


    // --------Getting an item from the table------
    public static List<Item> getTableObject(String sqlLine) throws SQLException {

        List<Item> resultList = new ArrayList<>();

        statement = conn.createStatement();

        resSet = statement.executeQuery(sqlLine);

        while (resSet.next()) {

            resultList.add(new Item(
                    resSet.getString("OCN"),
                    resSet.getString("itemNumber"),
                    resSet.getInt("itemQty"),
                    resSet.getInt("itemAvl"),
                    resSet.getInt("itemPicked"),
                    resSet.getInt("itemBckOrd"),
                    resSet.getInt("itemShipped")));

        }

        return resultList;

    }


    // --------Adding an item to the table--------
    public static void addSingleItem(String[][] baseArray, int baseElement) throws SQLException {

        PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO Orders (OCN, itemNumber, itemQty, itemAvl, itemPicked, " +
                        "itemBckOrd, itemShipped) VALUES (?, ?, ?, ?, ?, ?, ?)");

        statement.setString(1, baseArray[baseElement][0]);
        statement.setString(2, baseArray[baseElement][1]);
        statement.setString(3, baseArray[baseElement][2]);
        statement.setString(4, baseArray[baseElement][3]);
        statement.setString(5, baseArray[baseElement][4]);
        statement.setString(6, baseArray[baseElement][5]);
        statement.setString(7, baseArray[baseElement][6]);

        statement.addBatch();

        statement.executeBatch();

    }

    // --------Updating an item to the table--------
    public static void itemUpdate(String[][] baseArray, int baseElement) throws SQLException {

        statement = conn.createStatement();

        statement.executeUpdate("UPDATE Orders SET " +
                "itemQty = '" + baseArray[baseElement][2] + "', " +
                "itemAvl = '" + baseArray[baseElement][3] + "', " +
                "itemPicked = '" + baseArray[baseElement][4] + "', " +
                "itemBckOrd = '" + baseArray[baseElement][5] + "', " +
                "itemShipped = '" + baseArray[baseElement][6] + "' " +
                "WHERE OCN = '" + baseArray[baseElement][0] + "' " +
                "AND itemNumber = '" + baseArray[baseElement][1] + "';");

    }


    // --------Dropping a table--------
    public static void dropTable() throws SQLException {

        statement = conn.createStatement();

        statement.executeUpdate("DROP TABLE IF EXISTS Orders;");

    }


    // --------Closing a connection--------
    public static void close() throws ClassNotFoundException, SQLException {

        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (statement != null) statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (resSet != null) resSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
