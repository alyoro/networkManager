package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.None;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.Printer;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PrinterNotFoundException;
import com.prototype.networkManager.neo4j.services.PortService;
import com.prototype.networkManager.neo4j.services.PrinterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Printer Controller
 * Controller which handles requests to Printers Devices and it's ports.
 * API Url: {@code /api/printers/}
 */
@RestController
public class PrinterController implements PortController {

    private final PrinterService printerService;

    private final PortService portService;

    public PrinterController(PrinterService printerService, PortService portService) {
        this.printerService = printerService;
        this.portService = portService;
    }

    /**
     * GET ALL
     *
     * @return Returns all Printers in application
     */
    @GetMapping("/api/printers")
    @ResponseStatus(HttpStatus.OK)
    Iterable<Printer> getPrinters() {
        return printerService.getPrinters();
    }

    /**
     * GET SPECIFIC DEVICE
     *
     * @param id ID of device to return
     * @return Return Printer with specific ID
     */
    @GetMapping("/api/printers/{id}")
    @ResponseStatus(HttpStatus.OK)
    Printer getPrinter(@PathVariable Long id) {
        try {
            return printerService.getPrinter(id);
        } catch (PrinterNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * CREATE DEVICE
     *
     * @param printer Request Body to save in Database
     * @return Newly added Printer device
     */
    @PostMapping("/api/printers")
    @ResponseStatus(HttpStatus.CREATED)
    Printer createPrinter(@RequestBody Printer printer) {
        return printerService.createPrinter(printer);
    }

    /**
     * DELETE DEVICE
     *
     * @param id ID of device to delete
     * @return Empty entity when successful deletion
     */
    @DeleteMapping("/api/printers/{id}")
    @ResponseStatus(HttpStatus.OK)
    None deletePrinter(@PathVariable Long id) {
        try {
            printerService.deletePrinter(id);
            return new None();
        } catch (PrinterNotFoundException | PortNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * UPDATE DEVICE
     *
     * @param id      ID of device to update
     * @param printer Request body of updated device
     * @return Updated Printer device
     */
    @PutMapping("/api/printers/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    Printer updatePrinter(@PathVariable Long id, @RequestBody Printer printer) {
        try {
            return printerService.updatePrinter(id, printer);
        } catch (PrinterNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //TODO report
    @GetMapping("/api/printers/{id}/report")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getAccessPointReport(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Content-Disposition", "attachment; filename=report.txt")
                    .body(printerService.createPrinterReport(id));
        } catch (PrinterNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //TODO report
    @GetMapping("/api/printers/report")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getPrintersReport() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Disposition", "attachment; filename=report.txt")
                .body(printerService.createPrintersReport());
    }

    //TODO report
    @GetMapping(value = "/api/printers/report/csv", produces = "text/csv")
    @ResponseStatus(HttpStatus.OK)
    public void getPrintersReportCSV(HttpServletResponse response) {
        try {
            response.setContentType("text/plain; charset=utf-8");
            response.addHeader("Content-Disposition", "attachment; filename=report.csv");
            response.getWriter().print(printerService.createPrintersReportCSV());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //-------------------- PortController --------------------

    @Override
    public PortService getPortService() {
        return portService;
    }

    /**
     * RETURN DEVICE PORTS
     * Returns all ports that are part of specific device
     *
     * @param id ID of device
     * @return Ports of specific device
     */
    @Override
    @GetMapping("/api/printers/{id}/ports")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Port> getPorts(@PathVariable Long id) {
        return PortController.super.getPorts(id);
    }

    /**
     * GET ONE PORT
     * Return Specific port(portId) of specific Device(id)
     *
     * @param portId ID of port to GET
     * @return Port with given port Id
     */
    @Override
    @GetMapping("/api/printers/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port getPort(@PathVariable Long portId) {
        return PortController.super.getPort(portId);
    }

    /**
     * DELETE PORT
     *
     * @param portId ID of port to delete
     * @return Empty Entity when successful delete
     */
    @Override
    @DeleteMapping("/api/printers/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public None deletePort(@PathVariable Long portId) {
        return
                PortController.super.deletePort(portId);
    }

    /**
     * CREATE PORT FOR DEVICE
     *
     * @param id   ID of device to add new port
     * @param port Request Body with Port to add
     * @return Newly added Port Entity
     */
    @Override
    @PostMapping("/api/printers/{id}/ports")
    @ResponseStatus(HttpStatus.CREATED)
    public Port createPort(@PathVariable Long id, @RequestBody Port port) {
        return PortController.super.createPort(id, port);
    }

    /**
     * UPDATE PORT
     *
     * @param portId ID of port to be updated
     * @param port   Request Body of port to update
     * @return Updated Port
     */
    @Override
    @PutMapping("/api/printers/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port updatePort(@PathVariable Long portId, @RequestBody Port port) {
        return PortController.super.updatePort(portId, port);
    }

    /**
     * CHANGE PORT STATUS
     *
     * @param portId ID of port to change status
     * @return Updated Port with changed status
     */
    @Override
    @PatchMapping("/api/printers/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port changeStatusPort(@PathVariable Long portId) {
        return PortController.super.changeStatusPort(portId);
    }
}
