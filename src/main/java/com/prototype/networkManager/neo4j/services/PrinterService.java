package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Printer;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PrinterNotFoundException;

public interface PrinterService {
    Printer getPrinter(Long id) throws PrinterNotFoundException;

    Iterable<Printer> getPrinters();

    void deletePrinter(Long id) throws PrinterNotFoundException, PortNotFoundException;

    Printer createPrinter(Printer printer);

    Printer updatePrinter(Long id, Printer printer) throws PrinterNotFoundException;

    String createPrinterReport(Long id) throws PrinterNotFoundException;

    String createPrintersReport();

    String createPrintersReportCSV();
}
