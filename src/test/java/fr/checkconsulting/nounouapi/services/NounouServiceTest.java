package fr.checkconsulting.nounouapi.services;

import fr.checkconsulting.nounouapi.dto.NounouDTO;
import fr.checkconsulting.nounouapi.entity.Nounou;
import fr.checkconsulting.nounouapi.repository.NounouRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class NounouServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private NounouRepository nounouRepository;

    @Captor
    private ArgumentCaptor<Nounou> nounouArgumentCaptor;

    private NounouService nounouService;


    @BeforeEach
    void setUp() {
        nounouService = new NounouService(modelMapper, nounouRepository);
    }

    @Test
    void itShouldReturnAllNounous() {
        List<Nounou> nounouList = Arrays.asList(
                new Nounou("salah.abderraouf@gmail.com", "Salah", "Abderraouf", "Alger", "0555555556", "abderraouf.salah"),
                new Nounou("zakaria.ztm@gmail.com", "TOUAHRIA MILIANI", "Zakaria", "Alger", "0555555557", "zakaria")
        );
        Mockito.when(nounouRepository.findAll()).thenReturn(nounouList);
        Assertions.assertFalse(nounouService.getAllNounous().isEmpty());
    }

    @Test
    void itShouldReturnAnEmptyNounousList() {
        Mockito.when(nounouRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertTrue(nounouService.getAllNounous().isEmpty());
    }

    @Test
    void shouldFindNounouByEmail() {
        String email = "salah.abderraouf@gmail.com";
        Nounou nounou = new Nounou(email, "Salah", "Abderraouf", "Alger", "0555555556", "abderraouf.salah");
        NounouDTO nounouDTO = new NounouDTO(email, "Salah", "Abderraouf", "Alger", "0555555556", "abderraouf.salah");
        Mockito.when(nounouRepository.findById(email)).thenReturn(Optional.of(nounou));
        Mockito.when(modelMapper.map(nounou, NounouDTO.class)).thenReturn(nounouDTO);
        Optional<NounouDTO> expectedResponse = nounouService.getNounouByEmail(email);
        Assertions.assertEquals(expectedResponse, Optional.of(nounouDTO));
    }

    @Test
    void shouldNotFindNounouByEmail() {
        String email = "salah.abderraouf@gmail.com";
        Mockito.when(nounouRepository.findById(email)).thenReturn(Optional.empty());
        Optional<NounouDTO> expectedResponse = nounouService.getNounouByEmail(email);
        Assertions.assertEquals(expectedResponse, Optional.empty());
    }

    @Test
    void shouldUpdateNounou() {
        String email = "salah.abderraouf@gmail.com";
        Nounou nounou = new Nounou(email, "Salah", "Abderraouf", "Alger", "0555555556", "abderraouf.salah");
        Nounou nounouRequest = new Nounou(email, "Salah", "Abderraouf", "Laghouat", "0555555556", "abderraouf.salah");
        Mockito.when(nounouRepository.findById(email)).thenReturn(Optional.of(nounou));
        Optional<Nounou> expectedResponse = nounouService.updateNounou(email, nounouRequest);
        Mockito.verify(nounouRepository, Mockito.times(1)).save(nounouArgumentCaptor.capture());
        Assertions.assertEquals(nounouRequest.getAdresse(), nounouArgumentCaptor.getValue().getAdresse());
        Assertions.assertEquals(Optional.of(nounouRequest), expectedResponse);
    }

    @Test
    void shouldNotUpdateNounou() {
        String email = "salah.abderraouf@gmail.com";
        Nounou nounouRequest = new Nounou(email, "Salah", "Abderraouf", "Laghouat", "0555555556", "abderraouf.salah");
        Mockito.when(nounouRepository.findById(email)).thenReturn(Optional.empty());
        Optional<Nounou> expectedResponse = nounouService.updateNounou(email, nounouRequest);
        Assertions.assertEquals(expectedResponse, Optional.empty());
    }
}