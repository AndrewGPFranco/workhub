package com.agpf.workhub.services.demands;

import com.agpf.workhub.BaseTest;
import com.agpf.workhub.dtos.demands.EditDemandDTO;
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

import java.util.Optional;

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
                null, StatusDemandType.ONGOING, PriorityDemandType.URGENT, null);
    }

    @Test
    void testCreateDemand() {
        var demandDto = factoryDemandDTO();
        var user = getUser();

        Mockito.when(demandRepository.save(any(Demand.class))).thenReturn(getDemand());

        var demand = demandService.createDemand(demandDto, user);

        var demandCaptor = ArgumentCaptor.forClass(Demand.class);
        Mockito.verify(demandRepository).save(demandCaptor.capture());

        var savedDemand = demandCaptor.getValue();
        assertEquals("Demanda importante", savedDemand.getTitle());
        assertEquals("Programar utilizando TDD", savedDemand.getDescription());
        assertEquals(StatusDemandType.ONGOING, savedDemand.getStatus());
        assertEquals(PriorityDemandType.URGENT, savedDemand.getPriority());
        assertEquals(user, savedDemand.getUser());
        assertEquals("Demanda: 'Demanda importante' foi registrada com sucesso!", demand);
    }

    @Test
    void testEditDemand() {
        var demand = getDemand();
        var dto = new EditDemandDTO("Demanda atualizada", "Descrição atualizada", null,
                StatusDemandType.DONE, "Entregue no prazo.", PriorityDemandType.HIGH, null);

        Mockito.when(demandRepository.findById(demand.getId())).thenReturn(Optional.of(demand));

        demandService.editDemand(demand.getId(), dto, demand.getUser());

        assertEquals("Demanda atualizada", demand.getTitle());
        assertEquals("Descrição atualizada", demand.getDescription());
        assertEquals(StatusDemandType.DONE, demand.getStatus());
        assertEquals("Entregue no prazo.", demand.getObservationsToReview());
        assertEquals(PriorityDemandType.HIGH, demand.getPriority());
        Mockito.verify(demandRepository).save(demand);
    }

    @Test
    void testDeleteDemand() {
        var demand = getDemand();

        Mockito.when(demandRepository.findById(demand.getId())).thenReturn(Optional.of(demand));

        demandService.deleteDemand(demand.getId(), demand.getUser());

        Mockito.verify(demandRepository).deleteById(demand.getId());
    }

}
