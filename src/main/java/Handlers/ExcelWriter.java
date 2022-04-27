package Handlers;


import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;


public class ExcelWriter {

    public void writeToExcel(int currentColumn, int currentRow, String value) throws BiffException {

        try {
            File fileToEdit = new File("Orders.xls");

            Workbook existingWorkbook = Workbook.getWorkbook(fileToEdit);
            WritableWorkbook workbookCopy = Workbook.createWorkbook(fileToEdit, existingWorkbook);
            WritableSheet sheetToEdit = workbookCopy.getSheet("Sheet1");

            WritableCell cell;

            Label l = new Label(currentColumn, currentRow, value);

            cell = (WritableCell) l;

            sheetToEdit.addCell(cell);

            workbookCopy.write();
            workbookCopy.close();

            existingWorkbook.close();

        } catch (WriteException | IOException e) {

            JOptionPane.showMessageDialog(null, "File Orders.xls is open. Close the file to write the data", "Error",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(0);

        }

    }

}
