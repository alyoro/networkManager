package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.AccessPoint;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.AccessPointNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.repository.AccessPointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AccessPointServiceImpl implements AccessPointService {

    private final AccessPointRepository accessPointRepository;

    private final PortService portService;

    public AccessPointServiceImpl(AccessPointRepository accessPointRepository, PortService portService) {
        this.accessPointRepository = accessPointRepository;
        this.portService = portService;
    }

    @Override
    public AccessPoint getAccessPoint(Long id) throws AccessPointNotFoundException {
        Optional<AccessPoint> accessPointOptional = accessPointRepository.findById(id);
        if (accessPointOptional.isPresent()) {
            return accessPointOptional.get();
        } else {
            throw new AccessPointNotFoundException("AccessPoint with id: " + id + " not found.");
        }
    }

    @Override
    public Iterable<AccessPoint> getAccessPoints() {
        return accessPointRepository.findAll(2);
    }

    @Override
    public void deleteAccessServer(Long id) throws AccessPointNotFoundException, PortNotFoundException {
        Optional<AccessPoint> accessPointOptional = accessPointRepository.findById(id);
        if (accessPointOptional.isPresent()) {
            if (!accessPointOptional.get().getPorts().isEmpty()) {
                for (Port port : accessPointOptional.get().getPorts()) {
                    portService.deletePort(port.getId());
                }
            }
            accessPointRepository.deleteById(id);
        } else {
            throw new AccessPointNotFoundException("AccessPoint wit id: " + id + " not found.");
        }
    }

    @Override
    public AccessPoint createAccessPoint(AccessPoint accessPoint) {
        accessPoint.setPorts(portService.createMultiplePorts(accessPoint.getNumberOfPorts(), false));
        return accessPointRepository.save(accessPoint);
    }

    @Override
    public AccessPoint updateAccessPoint(Long id, AccessPoint accessPoint) throws AccessPointNotFoundException {
        Optional<AccessPoint> accessPointOptional = accessPointRepository.findById(id);
        if (accessPointOptional.isPresent()) {
            accessPointOptional.get().setLocalization(accessPoint.getLocalization());
            accessPointOptional.get().setIp(accessPoint.getIp());

            return accessPointRepository.save(accessPointOptional.get());
        } else {
            throw new AccessPointNotFoundException("AccessPoint with id: " + id + " not found.");
        }
    }

    @Override
    public String createAccessPointReport(Long id) throws AccessPointNotFoundException {
        AccessPoint accessPoint = this.getAccessPoint(id);

        StringBuilder text = new StringBuilder();

        text.append("Access Point - {" + accessPoint.getIdentifier() + "} + Properties\n");
        text.append("Id: " + accessPoint.getId() + "\n");
        text.append("Identifier: " + accessPoint.getIdentifier() + "\n");
        text.append("Number of ports: " + accessPoint.getNumberOfPorts() + "\n");
        text.append("Localization: " + accessPoint.getLocalization() + "\n");
        text.append("Ip: " + accessPoint.getIp() + "\n");

        return text.toString();
    }

    @Override
    public String createAccessPointsReport() {
        Iterable<AccessPoint> accessPoints = this.getAccessPoints();

        StringBuilder text = new StringBuilder();

        text.append("Access Point - All\n");
        text.append("Id" + getSepList() + "Identifier" + getSepList() + "No. Ports" + getSepList() + "Localization" + getSepList() + "Ip\n");

        for (AccessPoint accessPoint : accessPoints) {
            text.append(accessPoint.getId() + getSepList() + accessPoint.getIdentifier() + getSepList() +
                    accessPoint.getNumberOfPorts() + getSepList() + accessPoint.getLocalization() + getSepList() +
                    accessPoint.getIp() + "\n");
        }

        return text.toString();
    }

    @Override
    public String createAccessPointsReportCSV() {
        Iterable<AccessPoint> accessPoints = this.getAccessPoints();

        StringBuilder text = new StringBuilder();

        text.append("Access Point - All\n");
        text.append("Id" + getSepListCSV() + "Identifier" + getSepListCSV() + "No. Ports" + getSepListCSV() + "Localization" + getSepListCSV() + "Ip\n");

        for (AccessPoint accessPoint : accessPoints) {
            text.append(accessPoint.getId() + getSepListCSV() + accessPoint.getIdentifier() + getSepListCSV() +
                    accessPoint.getNumberOfPorts() + getSepListCSV() + accessPoint.getLocalization() + getSepListCSV() +
                    accessPoint.getIp() + "\n");
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
