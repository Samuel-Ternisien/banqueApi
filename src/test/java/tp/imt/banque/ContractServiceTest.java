package tp.imt.banque.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tp.imt.banque.model.*;
import tp.imt.banque.repository.ContractRepository;
import tp.imt.banque.repository.UserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContractServiceTest {

    @InjectMocks
    private ContractService contractService;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventPublisherService eventPublisherService;

    private Client client;
    private CompteBancaire compteBancaire;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup a client
        client = new Client();
        client.setId("client123");
        client.setContractIds(new ArrayList<>());

        // Setup a contract
        compteBancaire = new CompteBancaire();
        compteBancaire.setId("contract123");
        compteBancaire.setClientId(client.getId());
        compteBancaire.setAmount(1000.0);
        compteBancaire.setDecouvertAutorise(500.0);
    }

    @Test
    void testGetAllContracts() {
        // Arrange
        List<Contract> contracts = List.of(compteBancaire);
        when(contractRepository.findAll()).thenReturn(contracts);

        // Act
        List<Contract> result = contractService.getAllContracts();

        // Assert
        assertEquals(1, result.size());
        verify(contractRepository, times(1)).findAll();
    }

    @Test
    void testGetContractById() {
        // Arrange
        when(contractRepository.findById("contract123")).thenReturn(Optional.of(compteBancaire));

        // Act
        Contract result = contractService.getContractById("contract123");

        // Assert
        assertNotNull(result);
        assertEquals("contract123", result.getId());
        verify(contractRepository, times(1)).findById("contract123");
    }

    @Test
    void testCreateContract() {
        // Arrange
        when(userRepository.findById("client123")).thenReturn(Optional.of(client));
        when(contractRepository.findByClientId("client123")).thenReturn(new ArrayList<>());
        when(contractRepository.save(compteBancaire)).thenReturn(compteBancaire);
        when(userRepository.save(client)).thenReturn(client);

        // Act
        Contract result = contractService.createContract(compteBancaire);

        // Assert
        assertNotNull(result);
        assertEquals("contract123", result.getId());
        verify(contractRepository, times(1)).save(compteBancaire);
        verify(userRepository, times(1)).save(client);
    }

    @Test
    void testDeleteContract() {
        // Arrange
        when(contractRepository.existsById("contract123")).thenReturn(true);

        // Act
        contractService.deleteContract("contract123");

        // Assert
        verify(contractRepository, times(1)).deleteById("contract123");
    }

    @Test
    void testUpdateContract() {
        // Arrange
        when(contractRepository.findById("contract123")).thenReturn(Optional.of(compteBancaire));
        when(contractRepository.save(compteBancaire)).thenReturn(compteBancaire);

        CompteBancaire updatedContract = new CompteBancaire();
        updatedContract.setAmount(800.0);
        updatedContract.setDecouvertAutorise(400.0);

        // Act
        Contract result = contractService.updateContract("contract123", updatedContract);

        // Assert
        assertNotNull(result);
        assertEquals(800.0, ((CompteBancaire) result).getAmount());
        verify(contractRepository, times(1)).save(compteBancaire);
    }

    @Test
    void testOverdraftExceededEvent() {
        // Arrange
        compteBancaire.setAmount(-600.0); // Exceeds overdraft

        when(contractRepository.findById("contract123")).thenReturn(Optional.of(compteBancaire));
        when(userRepository.findById("client123")).thenReturn(Optional.of(client));

        CompteBancaire updatedContract = new CompteBancaire();
        updatedContract.setAmount(-600.0);
        updatedContract.setDecouvertAutorise(500.0);

        // Act
        contractService.updateContract("contract123", updatedContract);

        // Assert
        verify(eventPublisherService, times(1)).publishOverdraftExceededEvent("contract123", null);
    }
}
