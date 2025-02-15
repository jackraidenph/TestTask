package dev.idz.test.service.api;

import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

/**
 * Abstraction for opening POIXML documents via a string filepath.
 * Redundant in current task.
 * Might be useful for theoretically extending the application later.
 */
public interface POIService {

    POIXMLDocument openDocument(String filePath) throws IOException, InvalidFormatException;

}
