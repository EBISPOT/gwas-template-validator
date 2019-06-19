package uk.ac.ebi.spot.gwas.template.validator.util;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class SubmissionTemplateReader {

    private FileInputStream excelFile;

    private Workbook importedWorkbook;

    private boolean valid;

    public SubmissionTemplateReader(String input) {
        valid = true;
        try {
            readFile(input);
        } catch (IOException e) {
            System.err.println("Unable to read submission document: " + e.getMessage());
            valid = false;
        }
    }

    private void readFile(String input) throws IOException {
        excelFile = new FileInputStream(input);
        importedWorkbook = new XSSFWorkbook(excelFile);
    }

    public Iterator<Sheet> sheets() {
        return importedWorkbook.sheetIterator();
    }

    public void close() {
        try {
            importedWorkbook.close();
            excelFile.close();
        } catch (IOException e) {
            System.err.println("Unable to close submission document: " + e.getMessage());
        }
    }

    public boolean isValid() {
        return valid;
    }
}
