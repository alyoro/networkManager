package com.prototype.networkManager.neo4j.services;

import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.domain.Server;
import com.prototype.networkManager.neo4j.exceptions.PortNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.ServerNotFoundException;
import com.prototype.networkManager.neo4j.repository.ServerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;

    private final PortService portService;

    public ServerServiceImpl(ServerRepository serverRepository, PortService portService) {
        this.serverRepository = serverRepository;
        this.portService = portService;
    }

    @Override
    public Server getServer(Long id) throws ServerNotFoundException {
        Optional<Server> serverOptional = serverRepository.findById(id);
        if (serverOptional.isPresent()) {
            return serverOptional.get();
        } else {
            throw new ServerNotFoundException("Server with id: " + id + " not found");
        }
    }

    @Override
    public Iterable<Server> getServers() {
        return serverRepository.findAll(2);
    }

    @Override
    public void deleteServer(Long id) throws ServerNotFoundException, PortNotFoundException {
        Optional<Server> serverOptional = serverRepository.findById(id);
        if (serverOptional.isPresent()) {
            if (!serverOptional.get().getPorts().isEmpty()) {
                for (Port port : serverOptional.get().getPorts()) {
                    portService.deletePort(port.getId());
                }
            }
            serverRepository.deleteById(id);
        } else {
            throw new ServerNotFoundException("Server with id: " + id + " not found");
        }
    }

    @Override
    public Server createServer(Server server) {
        server.setPorts(portService.createMultiplePorts(server.getNumberOfPorts(), true));
        return serverRepository.save(server);
    }

    @Override
    public Server updateServer(Long id, Server server) throws ServerNotFoundException {
        Optional<Server> serverOptional = serverRepository.findById(id);
        if (serverOptional.isPresent()) {
            serverOptional.get().setLocalization(server.getLocalization());
            serverOptional.get().setIp(server.getIp());

            return serverRepository.save(serverOptional.get());
        } else {
            throw new ServerNotFoundException("Server with id: " + id + " not found.");
        }

    }

    @Override
    public String createServerReport(Long id) throws ServerNotFoundException {
        Server server = this.getServer(id);

        StringBuilder text = new StringBuilder();

        text.append("Server - {" + server.getIdentifier() + "} + Properties\n");
        text.append("Id: " + server.getId() + "\n");
        text.append("Identifier: " + server.getIdentifier() + "\n");
        text.append("Number of ports: " + server.getNumberOfPorts() + "\n");
        text.append("Localization: " + server.getLocalization() + "\n");
        text.append("Ip: " + server.getIp() + "\n");

        return text.toString();
    }

    @Override
    public String createServersReport() {
        Iterable<Server> servers = this.getServers();

        StringBuilder text = new StringBuilder();

        text.append("Server - All\n");
        text.append("Id" + getSepList() + "Identifier" + getSepList() + "No. Ports" + getSepList() + "Localization" + getSepList() + "Ip\n");

        for (Server server : servers) {
            text.append(server.getId() + getSepList() + server.getIdentifier() + getSepList() +
                    server.getNumberOfPorts() + getSepList() + server.getLocalization() + getSepList() +
                    server.getIp() + "\n");
        }

        return text.toString();
    }

    @Override
    public String createServersReportCSV() {
        Iterable<Server> servers = this.getServers();

        StringBuilder text = new StringBuilder();

        text.append("Server - All\n");
        text.append("Id" + getSepListCSV() + "Identifier" + getSepListCSV() + "No. Ports" + getSepListCSV() + "Localization" + getSepListCSV() + "Ip\n");

        for (Server server : servers) {
            text.append(server.getId() + getSepListCSV() + server.getIdentifier() + getSepListCSV() +
                    server.getNumberOfPorts() + getSepListCSV() + server.getLocalization() + getSepListCSV() +
                    server.getIp() + "\n");
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
