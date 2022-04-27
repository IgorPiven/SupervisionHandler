package Handlers;


import DataBase.DbHandler;
import Makers.DateMaker;
import Makers.ProgressBarMaker;
import Repository.Item;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TableHandler {

    private List<Item> partsList;

    private boolean isItemReady;
    private boolean isTotalOrderReady;
    private String thisFriday;
    private String nextFriday;
    private String currentDay;
    private String dateToPrint;

    public void handleTable(String[][] data, int rows) throws BiffException, WriteException, IOException {


        String currentDates = new DateMaker().getDates();
        String[] dates = currentDates.split("\\s");
        thisFriday = dates[0];
        nextFriday = dates[1];
        currentDay = dates[2];

        try {

            ProgressBarMaker pbm = new ProgressBarMaker(new JFrame("Processing Orders data"));
            pbm.prepareBar(rows);

            DbHandler.connect();

            for (int i = 0; i < rows; i++) {

                // ---------Checking PICKED-----------
                if (data[i][4].equalsIgnoreCase("picked")) {

                    partsList = DbHandler.getTableObject("SELECT * FROM Orders WHERE OCN = '" + data[i][2] + "';");

                    isTotalOrderReady = true;
                    isItemReady = false;

                    for (Item it : partsList) {

                        if ((it.getItemPicked() == (it.getItemQuantity() - it.getOrderShipped()))
                                && (it.getItemPicked() == it.getItemAvl())
                                && (it.getItemAvl() == (it.getItemQuantity() - it.getOrderShipped()))
                                && (!data[i][3].toLowerCase().contains("sh"))) {

                            isItemReady = true;
                            dateToPrint = thisFriday;

                        } else if ((it.getItemPicked() == (it.getItemQuantity() - it.getOrderShipped()))
                                && (it.getItemPicked() == it.getItemAvl())
                                && (it.getItemAvl() == (it.getItemQuantity() - it.getOrderShipped()))
                                && (data[i][3].toLowerCase().contains("sh"))) {

                            dateToPrint = handleSh(data[i][1], data[i][3]);
                            isItemReady = true;

                        } else {
                            isItemReady = false;
                            isTotalOrderReady = false;

                        }

                    }

                    if (isItemReady && isTotalOrderReady) new ExcelWriter().writeToExcel(5, i, dateToPrint);

                }


                //----------Checking Available-------------
                if (data[i][4].equalsIgnoreCase("avl")) {

                    partsList = DbHandler.getTableObject("SELECT * FROM Orders WHERE OCN = '" + data[i][2] + "';");

                    isTotalOrderReady = true;
                    isItemReady = false;

                    for (Item it : partsList) {

                        if ((it.getItemAvl() == (it.getItemQuantity() - it.getOrderShipped())
                                && ((currentDay.equalsIgnoreCase("mon")) ||
                                (currentDay.equalsIgnoreCase("tue")) ||
                                (currentDay.equalsIgnoreCase("wed")))
                                && (!data[i][3].toLowerCase().contains("sh")))) {

                            isItemReady = true;
                            dateToPrint = thisFriday;

                        } else if ((it.getItemAvl() == (it.getItemQuantity() - it.getOrderShipped())
                                && (currentDay.equalsIgnoreCase("thu"))
                                && ((data[i][3].toLowerCase().contains("st")) ||
                                (data[i][3].equalsIgnoreCase("fleet")))
                                && (!data[i][3].toLowerCase().contains("sh")))) {

                            isItemReady = true;
                            dateToPrint = nextFriday;

                        } else if ((it.getItemAvl() == (it.getItemQuantity() - it.getOrderShipped())
                                && (currentDay.equalsIgnoreCase("thu"))
                                && ((data[i][3].toLowerCase().contains("cd")) ||
                                (data[i][3].toLowerCase().contains("ec")))
                                && (!data[i][3].toLowerCase().contains("sh")))) {

                            isItemReady = true;
                            dateToPrint = thisFriday;

                        } else if ((it.getItemAvl() == (it.getItemQuantity() - it.getOrderShipped())
                                && (currentDay.equalsIgnoreCase("fri"))
                                && (!data[i][3].toLowerCase().contains("sh")))) {

                            isItemReady = true;
                            dateToPrint = nextFriday;

                        } else if ((it.getItemAvl() == (it.getItemQuantity() - it.getOrderShipped())
                                && (data[i][3].toLowerCase().contains("sh")))) {

                            dateToPrint = handleSh(data[i][1], data[i][3]);
                            isItemReady = true;

                        } else {
                            isItemReady = false;
                            isTotalOrderReady = false;
                        }

                    }
                    if (isItemReady && isTotalOrderReady) new ExcelWriter().writeToExcel(5, i, dateToPrint);

                }

                pbm.launchBar(i);
            }

            pbm.closeBar();

            DbHandler.close();

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "There might be empty cells in file Orders.xls", "Error",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(0);

        } catch (SQLException | ClassNotFoundException e) {

            JOptionPane.showMessageDialog(null, "Database error", "Error",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(0);

        }
    }

    private String handleSh(String orderInputDate, String orderShType) {

        int typeOfSh = 0;

        if (orderShType.equalsIgnoreCase("sh")) typeOfSh = 60;
        if (orderShType.equalsIgnoreCase("sh 45")) typeOfSh = 45;
        if (orderShType.equalsIgnoreCase("sh 60")) typeOfSh = 60;
        if (orderShType.equalsIgnoreCase("sh 90")) typeOfSh = 90;
        if (orderShType.equalsIgnoreCase("sh 120")) typeOfSh = 120;
        if (orderShType.equalsIgnoreCase("sh 180")) typeOfSh = 180;

        String outDate = new DateMaker().getShShippingDate(orderInputDate, typeOfSh);

        return outDate;

    }

}








