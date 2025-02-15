package dev.idz.test.service.impl;

import dev.idz.test.service.api.POIService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A service to work with XSSF(Excel) Workbooks
 * Currently provides means to open a workbook directly from string filepath with file check.
 * Also provides a way to query all physical cells via their type.
 */
@Service
public class ExcelService implements POIService {

    /**
     * Open document with file existence check
     *
     * @param pathToFile A path to a local XSSF Workbook
     * @return An AutoCloseable resource of the opened XSSF Workbook
     * @throws IllegalArgumentException If the file doesn't exist
     * @throws InvalidFormatException   If the file is not a proper XSSF Workbook
     * @throws IOException              If any unpredictable IOException happened
     */
    public XSSFWorkbook openDocument(String pathToFile) throws IllegalArgumentException, InvalidFormatException, IOException {
        File file = new File(pathToFile);
        if (!file.exists()) {
            throw new IllegalArgumentException(
                    "The requested file does not exist."
            );
        }

        OPCPackage fs = OPCPackage.open(file);
        return new XSSFWorkbook(fs);
    }

    /**
     * Get all physical cells of a type from the sheet
     *
     * @param sheet    A sheet to query cells from
     * @param cellType A type of cells to query
     * @return A list of physical cells
     */
    public List<Cell> getPhysicalCellsFromSheet(XSSFSheet sheet, CellType cellType) {
        List<Cell> cells = new ArrayList<>();

        Row row;
        for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext(); ) {
            row = rowIterator.next();

            Cell cell;
            for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext(); ) {
                cell = cellIterator.next();

                if (!cell.getCellType().equals(cellType)) {
                    continue;
                }

                cells.add(cell);
            }
        }

        return cells;
    }

}
