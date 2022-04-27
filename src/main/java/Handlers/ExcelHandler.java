package Handlers;

import Makers.ProgressBarMaker;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class ExcelHandler {
    public int rows;
    public int columns;
    public int rowsAfterHandle;
    public String pbmMsg;


    public String[][] readData(String fileName) throws IOException {

        String[][] cells = null;

        try {
            DataFormatter formatter = new DataFormatter();

            FileInputStream inputStream = new FileInputStream(fileName);

            // Get the workbook instance for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheetAt(0);

            // Calculating rows and columns
            rows = sheet.getPhysicalNumberOfRows();
            columns = sheet.getRow(0).getPhysicalNumberOfCells();
            rowsAfterHandle = rows;

            cells = new String[rows][columns];

            Iterator<Row> rowIterator = sheet.iterator();
            int currentRow = 0;
            int currentColumn = 0;

            if (fileName.equals("Macros.xls")) pbmMsg = "Loading Macros data";
            if (fileName.equals("Orders.xls")) pbmMsg = "Loading Orders data";

            ProgressBarMaker pbm = new ProgressBarMaker(new JFrame(pbmMsg));

            pbm.prepareBar(rows);

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    String currentValue = "";

                    switch (cell.getCellTypeEnum()) {

                        case NUMERIC:
                            currentValue = formatter.formatCellValue(cell);
                            break;

                        case FORMULA:
                            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                            currentValue = String.valueOf(evaluator.evaluate(cell).getNumberValue());
                            break;

                        case STRING:
                            currentValue = cell.toString();
                            break;

                        case _NONE:
                            currentValue = "0";
                            break;

                        case BLANK:
                            currentValue = "0";
                            break;

                        case ERROR:
                            currentValue = "0";
                    }

                    cells[currentRow][currentColumn] = currentValue;
                    currentColumn++;

                }

                // Join lines and their quantities if there are same lines
                if (fileName.equals("Macros.xls")) {

                    if (currentRow != 0) {

                        if (cells[currentRow][0].equals(cells[currentRow - 1][0])) {

                            if (cells[currentRow][1].equals(cells[currentRow - 1][1])) {

                                FigureHandler fH = new FigureHandler();

                                cells[currentRow - 1][0] = cells[currentRow][0];
                                cells[currentRow - 1][1] = cells[currentRow][1];
                                cells[currentRow - 1][2] = fH.convertToIntString(cells[currentRow - 1][2], cells[currentRow][2]);
                                cells[currentRow - 1][3] = fH.convertToIntString(cells[currentRow - 1][3], cells[currentRow][3]);
                                cells[currentRow - 1][4] = fH.convertToIntString(cells[currentRow - 1][4], cells[currentRow][4]);
                                cells[currentRow - 1][5] = fH.convertToIntString(cells[currentRow - 1][5], cells[currentRow][5]);

                                currentRow = currentRow - 1;
                                rowsAfterHandle--;
                            }
                        }
                    }
                }

                currentRow++;

                currentColumn = 0;

                pbm.launchBar(currentRow);
            }

            pbm.closeBar();

        } catch (ArrayIndexOutOfBoundsException e) {

            JOptionPane.showMessageDialog(null, "Invalid quantity of columns or rows. Delete redundant ones", "Error",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(0);

        } catch (NullPointerException e) {

            JOptionPane.showMessageDialog(null, "File being processed does not contain any data", "Error",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(0);
        } catch (FileNotFoundException e) {

            JOptionPane.showMessageDialog(null, "File " + fileName + " is not found", "Error",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(0);
        }


        return cells;

    }

}

