package com.agpf.workhub.services.demands;

import com.agpf.workhub.BaseTest;
import com.agpf.workhub.dtos.demands.RegisterDemandDTO;
import com.agpf.workhub.enums.demands.PriorityDemandType;
import com.agpf.workhub.enums.demands.StatusDemandType;
import com.agpf.workhub.models.demands.Demand;
import com.agpf.workhub.repositories.demands.DemandRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class DemandServiceTest extends BaseTest {

    @InjectMocks
    private DemandService demandService;

    @Mock
    private DemandRepository demandRepository;

    private RegisterDemandDTO factoryDemandDTO() {
        return new RegisterDemandDTO("Demanda importante", "Programar utilizando TDD",
                null, StatusDemandType.ONGOING, PriorityDemandType.URGENT);
    }

    @Test
    void testCreateDemand() {
        var demandDto = factoryDemandDTO();

        Mockito.when(demandRepository.save(any(Demand.class))).thenReturn(getDemand());

        var demand = demandService.createDemand(demandDto, getUser());

        var demandCaptor = ArgumentCaptor.forClass(Demand.class);
        Mockito.verify(demandRepository).save(demandCaptor.capture());

        var savedDemand = demandCaptor.getValue();
        assertEquals("Demanda importante", savedDemand.getTitle());
        assertEquals("Programar utilizando TDD", savedDemand.getDescription());
        assertEquals(StatusDemandType.ONGOING, savedDemand.getStatus());
        assertEquals(PriorityDemandType.URGENT, savedDemand.getPriority());
        assertEquals("Demanda: Demanda importante foi registrada com sucesso!", demand);
    }

}
