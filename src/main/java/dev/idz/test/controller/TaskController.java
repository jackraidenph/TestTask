package dev.idz.test.controller;

import dev.idz.test.service.impl.ExcelService;
import dev.idz.test.util.MathHelper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

@Controller
public class TaskController {

    private final ExcelService excelService;

    public TaskController(ExcelService excelService) {
        this.excelService = excelService;
    }

    /**
     * What to improve:
     * Upon every request the workbook resource is opened and physical cells are queried.
     * Caching returned values upon workbook update might help.
     *
     * @param filePath A path to the local XSSF workbook
     * @param n        Index of the largest values to find
     * @return Nth largest value in the requested file to find
     * <p>
     */
    @GetMapping(value = "/nthMax")
    @ApiResponse(responseCode = "500", description = """
            Opening the file failed due to an IOException,
            or the requested file is found, but the calculation has failed as the file has no sheets.
            """)
    @ApiResponse(responseCode = "400", description = """
            The requested file is not a valid XSSF workbook,
            or the index is out of bounds.
            """)
    @ApiResponse(responseCode = "404", description = "The requested file does not exist.")
    public @ResponseBody int getNthMaximumInLocalFile(
            @RequestParam("path") String filePath,
            @RequestParam("n") int n
    ) {
        List<Cell> cells = new ArrayList<>();
        try (XSSFWorkbook workbook = this.excelService.openDocument(filePath)) {

            if (workbook.getNumberOfSheets() < 1) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "The requested file has no sheets."
                );
            }

            XSSFSheet sheet = workbook.getSheetAt(0);

            cells.addAll(this.excelService.getPhysicalCellsFromSheet(
                    sheet,
                    CellType.NUMERIC
            ));

        } catch (IOException ioException) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Couldn't open the requested file due to an IOException"
            );
        } catch (InvalidFormatException invalidFormatException) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The requested file is not a workbook."
            );
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "The requested file does not exist."
            );
        }

        Set<Integer> values = new HashSet<>();
        for (Cell cell : cells) {
            values.add((int) cell.getNumericCellValue());
        }

        try {
            return MathHelper.findNthLargestInt(values, n);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The requested N is greater than the amount of values in the requested file, or less than 0."
            );
        }
    }

}
