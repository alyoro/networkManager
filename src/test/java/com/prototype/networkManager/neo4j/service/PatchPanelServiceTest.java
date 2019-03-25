package com.prototype.networkManager.neo4j.service;

import com.prototype.networkManager.neo4j.domain.DeviceType;
import com.prototype.networkManager.neo4j.domain.PatchPanel;
import com.prototype.networkManager.neo4j.domain.Port;
import com.prototype.networkManager.neo4j.exceptions.PatchPanelNotFoundException;
import com.prototype.networkManager.neo4j.exceptions.PortNumberAlreadyInListException;
import com.prototype.networkManager.neo4j.repository.PatchPanelRepository;
import com.prototype.networkManager.neo4j.services.HelperFunctions;
import com.prototype.networkManager.neo4j.services.PatchPanelService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;


@SpringBootTest
@RunWith(SpringRunner.class)
public class PatchPanelServiceTest {

    @MockBean(name = "patchPanelRepository")
    PatchPanelRepository patchPanelRepository;

    @Autowired
    PatchPanelService patchPanelService;

    @Autowired
    HelperFunctions helperFunctions;


    @Test
    public void getPatchPanelNotEmptyTest() throws PatchPanelNotFoundException{
        Long id = 1L;
        PatchPanel patchPanel = new PatchPanel();
        given(this.patchPanelRepository.findById(id)).willReturn(Optional.of(patchPanel));

        assertEquals(patchPanelService.getPatchPanel(id), patchPanel);
    }

    @Test(expected = PatchPanelNotFoundException.class)
    public void getPatchPanelEmptyTest() throws PatchPanelNotFoundException{
        Long idEmpty = 3L;
        given(this.patchPanelRepository.findById(idEmpty)).willReturn(Optional.empty());

        assertEquals(patchPanelService.getPatchPanel(idEmpty),null);
    }

    @Test
    public void addPortUniquePortTest() throws PortNumberAlreadyInListException,PatchPanelNotFoundException {
        Long id = 1L;
        PatchPanel patchPanel = new PatchPanel("D-10", "101","identifier",
                "close to door", "desc", 20);
        List<Port> ports = new ArrayList<>();
        ports.add(new Port(1, DeviceType.None, "", null));
        ports.add(new Port(2, DeviceType.None, "", null));
        patchPanel.setPorts(ports);

        given(this.patchPanelRepository.findById(id)).willReturn(Optional.of(patchPanel));

        Port portUnique = new Port(3, DeviceType.None, "", null);
        patchPanelService.addPort(id,portUnique);
        assertEquals(patchPanel.getPorts().size(), 3);
    }

    @Test(expected = PatchPanelNotFoundException.class)
    public void addPortPatchPanelNotFoundTest() throws PortNumberAlreadyInListException,PatchPanelNotFoundException {
        Long id = 2L;
        PatchPanel patchPanel = new PatchPanel("D-10", "101","identifier",
                "close to door", "desc", 20);
        List<Port> ports = new ArrayList<>();
        ports.add(new Port(1, DeviceType.None, "", null));
        ports.add(new Port(2, DeviceType.None, "", null));
        patchPanel.setPorts(ports);

        given(this.patchPanelRepository.findById(id)).willReturn(Optional.empty());

        Port portUnique = new Port(3, DeviceType.None, "", null);
        patchPanelService.addPort(id,portUnique);
    }

    @Test(expected = PortNumberAlreadyInListException.class)
    public void addPortNumberAlreadyInListTest() throws PortNumberAlreadyInListException,PatchPanelNotFoundException {
        Long id = 3L;
        PatchPanel patchPanel = new PatchPanel("D-10", "101","identifier",
                "close to door", "desc", 20);
        List<Port> ports = new ArrayList<>();
        ports.add(new Port(1, DeviceType.None, "", null));
        ports.add(new Port(2, DeviceType.None, "", null));
        patchPanel.setPorts(ports);

        given(this.patchPanelRepository.findById(id)).willReturn(Optional.of(patchPanel));

        Port portNotUnique = new Port(2, DeviceType.None, "", null);
        patchPanelService.addPort(id,portNotUnique);
    }
}
