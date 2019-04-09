package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.Printer;
import com.prototype.networkManager.neo4j.exceptions.PrinterNotFoundException;
import com.prototype.networkManager.neo4j.repository.PortRepository;
import com.prototype.networkManager.neo4j.repository.PrinterRepository;

import java.util.Optional;

public class PrinterServiceImpl implements PrinterService {

    private final PrinterRepository printerRepository;

    private final PortRepository portRepository;

    public PrinterServiceImpl(PrinterRepository printerRepository, PortRepository portRepository) {
        this.printerRepository = printerRepository;
        this.portRepository = portRepository;
    }

    @Override
    public Printer getPrinter(Long id) throws PrinterNotFoundException {
        Optional<Printer> printerOptional = printerRepository.findById(id);
        if(printerOptional.isPresent()){
            return printerOptional.get();
        } else {
            throw new PrinterNotFoundException("Printer with id: "+id+" not found.");
        }
    }

    @Override
    public Iterable<Printer> getPrinters() {
        return printerRepository.findAll(2);
    }

    @Override
    public void deletePrinter(Long id) throws PrinterNotFoundException {
        Optional<Printer> printerOptional = printerRepository.findById(id);
        if(printerOptional.isPresent()){
            if(!printerOptional.get().getPorts().isEmpty()) {
                for(Port port: printerOptional.get().getPorts()) {
                    portRepository.delete(port);
                }
            }
            printerRepository.deleteById(id);
        } else {
            throw new PrinterNotFoundException("Printer with id: "+id+" not found.");
        }
    }

    @Override
    public Printer createPrinter(Printer printer) {
        return printerRepository.save(printer);
    }
}
