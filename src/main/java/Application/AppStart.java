package Application;

import DataBase.DbHandler;
import Handlers.DropBaseCheck;
import Handlers.ExcelHandler;
import Handlers.TableHandler;
import Makers.CheckInBase;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class AppStart {


    public static void main(String[] args) throws IOException, BiffException, WriteException {

        ExcelHandler excelHandler = new ExcelHandler();
        String[][] item;
        String[][] data;


        // Read data from Macros file
        item = excelHandler.readData("Macros.xls");

        // Create a database stream
        try {

            int option = new DropBaseCheck().showMessage();

            DbHandler.connect();

            if (option == 0) {

                DbHandler.dropTable();
                DbHandler.createTable();
                DbHandler.addItems(item, excelHandler.rowsAfterHandle);

            } else if (option == 1) {

                CheckInBase check = new CheckInBase();
                check.launchCheck(item, excelHandler.rowsAfterHandle);

            } else {
                System.exit(0);
            }

            DbHandler.close();

        } catch (SQLException | ClassNotFoundException e) {

            JOptionPane.showMessageDialog(null, "Database error", "Error",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(0);

        }

        // Read data from Orders file
        data = excelHandler.readData("Orders.xls");

        TableHandler tableHandler = new TableHandler();

        tableHandler.handleTable(data, excelHandler.rows);


        // Show message if success
        JOptionPane.showMessageDialog(null,
                "Data has been successfully processed", "Successfully",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
