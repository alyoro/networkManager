package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.Switch;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.SwitchNotFoundException;
import com.prototype.networkManager.neo4j.repository.SwitchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SwitchServiceImpl implements SwitchService {

    private final SwitchRepository switchRepository;

    private final PortService portService;

    public SwitchServiceImpl(SwitchRepository switchRepository, PortService portService) {
        this.switchRepository = switchRepository;
        this.portService = portService;
    }

    @Override
    public Switch getSwitch(Long id) throws SwitchNotFoundException {
        Optional<Switch> switchOptional = switchRepository.findById(id);
        if (switchOptional.isPresent()) {
            return switchOptional.get();
        } else {
            throw new SwitchNotFoundException("Switch with id: " + id + " not found.");
        }
    }

    @Override
    public Iterable<Switch> getSwitches() {
        return switchRepository.findAll(2);
    }

    @Override
    public void deleteSwitch(Long id) throws SwitchNotFoundException, PortNotFoundException {
        Optional<Switch> switchOptional = switchRepository.findById(id);
        if (switchOptional.isPresent()) {
            if (!switchOptional.get().getPorts().isEmpty()) {
                for (Port port : switchOptional.get().getPorts()) {
                    portService.deletePort(port.getId());
                }
            }
            switchRepository.deleteById(id);
        } else {
            throw new SwitchNotFoundException("Switch with id: " + id + " not found.");
        }
    }

    @Override
    public Switch createSwitch(Switch switchDevice) {
        switchDevice.setPorts(portService.createMultiplePorts(switchDevice.getNumberOfPorts(), true));
        return switchRepository.save(switchDevice);
    }

    @Override
    public Switch updateSwitch(Long id, Switch switchDevice) throws SwitchNotFoundException {
        Optional<Switch> switchOptional = switchRepository.findById(id);
        if (switchOptional.isPresent()) {
            switchOptional.get().setIdentifier(switchDevice.getIdentifier());
            switchOptional.get().setLocalization(switchDevice.getLocalization());
            switchOptional.get().setDateOfPurchase(switchDevice.getDateOfPurchase());
            switchOptional.get().setManagementIP(switchDevice.getManagementIP());

            return switchRepository.save(switchOptional.get());
        } else {
            throw new SwitchNotFoundException("Switch with id: " + id + " not found.");
        }
    }

    @Override
    public String createSwitchReport(Long id) throws SwitchNotFoundException {
        Switch switchDevice = this.getSwitch(id);

        StringBuilder text = new StringBuilder();

        text.append("Switch - " + switchDevice.getIdentifier() + " - Properties\n");
        text.append("Id: " + switchDevice.getId() + "\n");
        text.append("Identifier: " + switchDevice.getIdentifier() + "\n");
        text.append("Number of ports: " + switchDevice.getNumberOfPorts() + "\n");
        text.append("Localization: " + switchDevice.getLocalization() + "\n");
        text.append("Date Of Purchase: " + switchDevice.getDateOfPurchase() + "\n");
        text.append("Management Ip: " + switchDevice.getManagementIP() + "\n");

        return text.toString();
    }

    @Override
    public String createSwitchesReport() {
        Iterable<Switch> switches = this.getSwitches();

        StringBuilder text = new StringBuilder();

        text.append("Switch - All\n");
        text.append("Id" + getSepList() + "Identifier" + getSepList() + "No. Ports" + getSepList() + "Localization" + getSepList() +
                "Date of Purchase" + getSepList() + "Management Ip\n");

        for (Switch switchDevice : switches) {
            text.append(switchDevice.getId() + getSepList() + switchDevice.getIdentifier() + getSepList() +
                    switchDevice.getNumberOfPorts() + getSepList() + switchDevice.getLocalization() + getSepList() +
                    switchDevice.getDateOfPurchase() + getSepList() + switchDevice.getManagementIP() + "\n");
        }

        return text.toString();
    }

    @Override
    public String createSwitchesReportCSV() {
        Iterable<Switch> switches = this.getSwitches();

        StringBuilder text = new StringBuilder();

        text.append("Switch - All\n");
        text.append("Id" + getSepListCSV() + "Identifier" + getSepListCSV() + "No. Ports" + getSepListCSV() + "Localization" + getSepListCSV() +
                "Date of Purchase" + getSepListCSV() + "Management Ip\n");

        for (Switch switchDevice : switches) {
            text.append(switchDevice.getId() + getSepListCSV() + switchDevice.getIdentifier() + getSepListCSV() +
                    switchDevice.getNumberOfPorts() + getSepListCSV() + switchDevice.getLocalization() + getSepListCSV() +
                    switchDevice.getDateOfPurchase() + getSepListCSV() + switchDevice.getManagementIP() + "\n");
        }

        return text.toString();
    }

    private String getSepList() {
        return "\t\t";
    }

    private String getSepListCSV() {
        return ";";
    }
}
