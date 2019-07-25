package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.DeviceNode;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.enums.PortStatus;
import com.prototype.networkManager.neo4j.exceptions.*;
import com.prototype.networkManager.neo4j.repository.PortRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
@Transactional
public class PortServiceImpl implements PortService {

    private final PortRepository portRepository;

    private final DeviceNodeService deviceNodeService;

    private final HelperFunctions helperFunctions;

    private final ConnectionService connectionService;

    @Override
    public Port getPort(Long id) throws PortNotFoundException {
        Optional<Port> port = portRepository.findById(id);
        if (port.isPresent()) {
            return port.get();
        } else {
            throw new PortNotFoundException("Port with id: " + id + " not found.");
        }
    }

    @Override
    public Iterable<Port> getPorts() {
        return portRepository.findAll();
    }

    @Override
    public Iterable<Port> getPorts(Long id) {
        return deviceNodeService.getDevicePorts(id);
    }

    @Override
    public void deletePort(Long id) throws PortNotFoundException {
        Optional<Port> portOptional = portRepository.findById(id);
        if (portOptional.isPresent()) {
            if (portOptional.get().getConnections() == null) {
                portRepository.delete(portOptional.get());
            } else {
                Optional<Port> otherPort;
                if (portOptional.get().getId() == portOptional.get().getConnections().get(0).getPortIdStart()) {
                    otherPort = portRepository.findById(portOptional.get().getConnections().get(0).getPortIdEnd());

                } else {
                    otherPort = portRepository.findById(portOptional.get().getConnections().get(0).getPortIdStart());
                }
                otherPort.get().setDevicePlugged("None");
                otherPort.get().setPortOnTheOtherElement("None");
                portRepository.save(otherPort.get());
                portRepository.delete(portOptional.get());
            }
        } else {
            throw new PortNotFoundException("Port with id: " + id + " not found.");
        }
    }

    @Override
    public Port createPort(Long id, Port port)
            throws DeviceNotFoundException, MaximumPortNumberReachedException, PortNumberAlreadyInListException {
        DeviceNode node = deviceNodeService.getDeviceNodeById(id,1);
        List<Port> ports = node.getPorts();
        port.setDevicePlugged("None");
        port.setPortOnTheOtherElement("None");
        if (ports != null) {
            if (node.getNumberOfPorts() == ports.size()) {
                node.setNumberOfPorts(node.getNumberOfPorts() + 1);
            }
            if (helperFunctions.arePortNumberListUnique(ports, port.getPortNumber())) {
                ports.add(port);
            } else {
                throw new PortNumberAlreadyInListException("Port with this number already added to device");
            }
        } else {
            if (node.getNumberOfPorts() > 0) {
                ports = new ArrayList<>();
                ports.add(port);
            } else {
                throw new MaximumPortNumberReachedException("Cant put more ports in this device");
            }
        }
        node.setPorts(ports);
        deviceNodeService.save(node);
        return port;

    }

    @Override
    public List<Port> createMultiplePorts(Integer numberOfPorts, boolean logical) {
        ArrayList<Port> ports = new ArrayList<>();
        for (int i = 0; i < numberOfPorts; i++) {
            ports.add(new Port(
                    i + 1,
                    "None",
                    "None",
                    "Ethernet1Gb",
                    PortStatus.DOWN,
                    logical,
                    logical ? new ArrayList<>() : null
            ));
        }
        return StreamSupport.stream(portRepository.saveAll(ports).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Port updatePort(Long id, Port port) throws PortNotFoundException {
        Optional<Port> portOptional = portRepository.findById(id);
        if (portOptional.isEmpty()) {
            throw new PortNotFoundException("Port with id: " + id + " not found.");
        } else {
            if (portOptional.get().getConnections() == null) {
                portOptional.get().setPortOnTheOtherElement("None");
                portOptional.get().setDevicePlugged("None");
                portOptional.get().setPortNumber(port.getPortNumber());
                portOptional.get().setPortSpeed(port.getPortSpeed());
                portOptional.get().setVlans(port.getVlans());

                return portRepository.save(portOptional.get());
            } else {
                Map<String, Port> portsOfConnection = connectionService.getStartAndEndNode(
                        portOptional.get().getConnections().get(0).getId());
                Port portMaster = portsOfConnection.get("portMaster");
                Port portSlave = portsOfConnection.get("portSlave");

                portOptional.get().setPortNumber(port.getPortNumber());
                portOptional.get().setPortSpeed(port.getPortSpeed());
                portOptional.get().setVlans(port.getVlans());

                if (port.getId() == portMaster.getId()) {
                    portSlave.setPortOnTheOtherElement(String.valueOf(port.getPortNumber()));
                    portRepository.save(portSlave);
                }
                return portRepository.save(portOptional.get());
            }
        }
    }

    @Override
    public Port changeStatusPort(Long id) throws PortNotFoundException, CantChangePortStatusException {
        Optional<Port> portOptional = portRepository.findById(id);
        if (portOptional.isEmpty()) {
            throw new PortNotFoundException("Port with id: " + id + " not found.");
        } else {
            if (portOptional.get().getConnections() != null) {
                Optional<Port> otherPort = Optional.empty();
                if (portOptional.get().getId() == portOptional.get().getConnections().get(0).getPortIdStart()) {
                    otherPort = portRepository.findById(portOptional.get().getConnections().get(0).getPortIdEnd());
                } else {
                    otherPort = portRepository.findById(portOptional.get().getConnections().get(0).getPortIdStart());
                }
                otherPort.get().setPortStatus(changePortStatus(portOptional.get().getPortStatus()));
                portRepository.save(otherPort.get());
            }
            portOptional.get().setPortStatus(changePortStatus(portOptional.get().getPortStatus()));
            return portRepository.save(portOptional.get());
        }
    }

    private PortStatus changePortStatus(PortStatus portStatus) {
        if (portStatus == PortStatus.UP) {
            return PortStatus.DOWN;
        } else {
            return PortStatus.UP;
        }
    }
}
