package Makers;

import DataBase.DbHandler;
import Repository.Item;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class CheckInBase {

    public void launchCheck(String[][] baseArray, int baseRows) throws SQLException {

        ProgressBarMaker pbm = new ProgressBarMaker(new JFrame("Updating database"));

        pbm.prepareBar(baseRows);

        for (int i = 1; i < baseRows; i++) {

            List<Item> objectFromBase = DbHandler.getTableObject("SELECT * FROM Orders WHERE OCN = '"
                    + baseArray[i][0] +
                    "' AND itemNumber = '" + baseArray[i][1] + "';");

            boolean isLineIn = false;

            for (Item it : objectFromBase) {

                if ((it.getOrderCustomerNumber().equals(baseArray[i][0]))
                        && (it.getItemNumber().equals(baseArray[i][1]))
                        && (it.getItemQuantity() == Integer.parseInt(baseArray[i][2]))
                        && (it.getItemAvl() == Integer.parseInt(baseArray[i][3]))
                        && (it.getItemPicked() == Integer.parseInt(baseArray[i][4]))
                        && (it.getItemBckOrd() == Integer.parseInt(baseArray[i][5]))
                        && (it.getOrderShipped() == Integer.parseInt(baseArray[i][6]))) {
                    isLineIn = true;
                } else {
                    DbHandler.itemUpdate(baseArray, i);
                    isLineIn = true;
                }
            }

            if (!isLineIn) DbHandler.addSingleItem(baseArray, i);

            pbm.launchBar(i);
        }

        pbm.closeBar();

    }

}
