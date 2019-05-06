package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.Printer;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PrinterNotFoundException;
import com.prototype.networkManager.neo4j.repository.PrinterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PrinterServiceImpl implements PrinterService {

    private final PrinterRepository printerRepository;

    private final PortService portService;

    public PrinterServiceImpl(PrinterRepository printerRepository, PortService portService) {
        this.printerRepository = printerRepository;
        this.portService = portService;
    }

    @Override
    public Printer getPrinter(Long id) throws PrinterNotFoundException {
        Optional<Printer> printerOptional = printerRepository.findById(id);
        if (printerOptional.isPresent()) {
            return printerOptional.get();
        } else {
            throw new PrinterNotFoundException("Printer with id: " + id + " not found.");
        }
    }

    @Override
    public Iterable<Printer> getPrinters() {
        return printerRepository.findAll(2);
    }

    @Override
    public void deletePrinter(Long id) throws PrinterNotFoundException, PortNotFoundException {
        Optional<Printer> printerOptional = printerRepository.findById(id);
        if (printerOptional.isPresent()) {
            if (!printerOptional.get().getPorts().isEmpty()) {
                for (Port port : printerOptional.get().getPorts()) {
                    portService.deletePort(port.getId());
                }
            }
            printerRepository.deleteById(id);
        } else {
            throw new PrinterNotFoundException("Printer with id: " + id + " not found.");
        }
    }

    @Override
    public Printer createPrinter(Printer printer) {
        printer.setPorts(portService.createMultiplePorts(printer.getNumberOfPorts()));
        return printerRepository.save(printer);
    }

    @Override
    public Printer updatePrinter(Long id, Printer printer) throws PrinterNotFoundException {
        Optional<Printer> printerOptional = printerRepository.findById(id);
        if (printerOptional.isPresent()) {
            printerOptional.get().setLocalization(printer.getLocalization());
            printerOptional.get().setIp(printer.getIp());

            return printerRepository.save(printerOptional.get());
        } else {
            throw new PrinterNotFoundException("Printer with id: " + id + " not found.");
        }
    }
}
