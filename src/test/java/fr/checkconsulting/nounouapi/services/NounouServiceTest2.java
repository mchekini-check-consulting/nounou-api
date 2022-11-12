package fr.checkconsulting.nounouapi.services;

import fr.checkconsulting.nounouapi.dto.NounouDTO;
import fr.checkconsulting.nounouapi.entity.Nounou;
import fr.checkconsulting.nounouapi.repository.NounouRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class NounouServiceTest2 {

    @Mock
    NounouRepository nounouRepository;
    @Mock
    ModelMapper modelMapper;

    NounouService nounouService;

    @Test
    void shouldReturnAllNounous() {
        this.nounouService = new NounouService(modelMapper, nounouRepository);
        List<Nounou> nounouList = Arrays.asList(
                new Nounou("salah.abderraouf@gmail.com", "Salah", "Abderraouf", "Alger", "0555555556", "abderraouf.salah"),
                new Nounou("zakaria.ztm@gmail.com", "TOUAHRIA MILIANI", "Zakaria", "Alger", "0555555557", "zakaria")
        );
        Mockito.when(nounouRepository.findAll()).thenReturn(nounouList);
        List<NounouDTO> nounouDTOList =  nounouService.getAllNounous();
        Assertions.assertEquals(2, nounouDTOList.size());
    }
}
