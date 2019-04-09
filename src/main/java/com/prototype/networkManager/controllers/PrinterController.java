package com.prototype.networkManager.controllers;

import com.prototype.networkManager.neo4j.domain.None;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.Printer;
import com.prototype.networkManager.neo4j.exceptions.PrinterNotFoundException;
import com.prototype.networkManager.neo4j.services.PortService;
import com.prototype.networkManager.neo4j.services.PrinterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

public class PrinterController implements PortController {

    private final PrinterService printerService;

    private final PortService portService;

    public PrinterController(PrinterService printerService, PortService portService) {
        this.printerService = printerService;
        this.portService = portService;
    }

    @GetMapping("/api/printers")
    @ResponseStatus(HttpStatus.OK)
    Iterable<Printer> getPrinters(){
        return printerService.getPrinters();
    }

    @GetMapping("/api/printers/{id}")
    @ResponseStatus(HttpStatus.OK)
    Printer getPrinter(@PathVariable Long id){
        try{
            return printerService.getPrinter(id);
        } catch (PrinterNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/api/printers")
    @ResponseStatus(HttpStatus.CREATED)
    Printer createPrinter(@RequestBody Printer printer){
        return printerService.createPrinter(printer);
    }

    @DeleteMapping("/api/printers/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deletePrinter(@PathVariable Long id){
        try{
            printerService.deletePrinter(id);
        }catch(PrinterNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //-------------------- PortController --------------------

    @Override
    public PortService getPortService() {
        return portService;
    }

    @Override
    @GetMapping("/api/printers/{id}/ports")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Port> getPorts(@PathVariable Long id) {
        return PortController.super.getPorts(id);
    }

    @Override
    @GetMapping("/api/printers/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public Port getPort(@PathVariable Long portId) {
        return PortController.super.getPort(portId);
    }

    @Override
    @DeleteMapping("/api/printers/{id}/ports/{portId}")
    @ResponseStatus(HttpStatus.OK)
    public None deletePort(@PathVariable Long portId) {
        return
                PortController.super.deletePort(portId);
    }

    @Override
    @PostMapping("/api/printers/{id}/ports")
    @ResponseStatus(HttpStatus.CREATED)
    public Port createPort(@PathVariable Long id, @RequestBody Port port) {
        return PortController.super.createPort(id, port);
    }

}
