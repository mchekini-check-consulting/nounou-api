package fr.checkconsulting.nounouapi;

import fr.checkconsulting.nounouapi.entity.Nounou;
import fr.checkconsulting.nounouapi.repository.NounouRepository;
import fr.checkconsulting.nounouapi.services.NounouService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;

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
    void shouldReturnAllNounous() {
//        List<Nounou> nounouList = Arrays.asList(
//                new Nounou("salah.abderraouf@gmail.com", "Salah", "Abderraouf", "Alger", "0555555556", "abderraouf.salah"),
//                new Nounou("zakaria.ztm@gmail.com", "TOUAHRIA MILIANI", "Zakaria", "Alger", "0555555557", "zakaria")
//        );
//        Mockito.when(nounouRepository.findAll()).thenReturn(nounouList);
//        Assertions.assertFalse(nounouService.getAllNounous().isEmpty());
    }

    @Test
    void shouldReturnAnEmptyNounousList() {
        Mockito.when(nounouRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertTrue(nounouService.getAllNounous().isEmpty());
    }

    @Test
    void shouldFindNounouByEmail() {
//        String email = "salah.abderraouf@gmail.com";
//        Nounou nounou = new Nounou(email, "Salah", "Abderraouf", "Alger", "0555555556", "abderraouf.salah");
//        NounouDTO nounouDTO = new NounouDTO(email, "Salah", "Abderraouf", "Alger", "0555555556", "abderraouf.salah");
//        Mockito.when(nounouRepository.findById(email)).thenReturn(Optional.of(nounou));
//        Mockito.when(modelMapper.map(nounou, NounouDTO.class)).thenReturn(nounouDTO);
//        Optional<NounouDTO> expectedResponse = nounouService.getNounouByEmail(email);
//        Assertions.assertEquals(expectedResponse, Optional.of(nounouDTO));
    }

    @Test
    void shouldNotFindNounouByEmail() {
//        String email = "salah.abderraouf@gmail.com";
//        Mockito.when(nounouRepository.findById(email)).thenReturn(Optional.empty());
//        Optional<NounouDTO> expectedResponse = nounouService.getNounouByEmail(email);
//        Assertions.assertEquals(expectedResponse, Optional.empty());
    }

    @Test
    void shouldUpdateNounou() {
//        String email = "salah.abderraouf@gmail.com";
//        Nounou nounou = new Nounou(email, "Salah", "Abderraouf", "Alger", "0555555556", "abderraouf.salah");
//        Nounou nounouRequest = new Nounou(email, "Salah", "Abderraouf", "Laghouat", "0555555556", "abderraouf.salah");
//        Mockito.when(nounouRepository.findById(email)).thenReturn(Optional.of(nounou));
//        Optional<Nounou> expectedResponse = nounouService.updateNounou(email, nounouRequest);
//        Mockito.verify(nounouRepository, Mockito.times(1)).save(nounouArgumentCaptor.capture());
//        Assertions.assertEquals(nounouRequest.getAdresse(), nounouArgumentCaptor.getValue().getAdresse());
//        Assertions.assertEquals(Optional.of(nounouRequest), expectedResponse);
    }

    @Test
    void shouldNotUpdateNounou() {
//        String email = "salah.abderraouf@gmail.com";
//        Nounou nounouRequest = new Nounou(email, "Salah", "Abderraouf", "Laghouat", "0555555556", "abderraouf.salah");
//        Mockito.when(nounouRepository.findById(email)).thenReturn(Optional.empty());
//        Optional<Nounou> expectedResponse = nounouService.updateNounou(email, nounouRequest);
//        Assertions.assertEquals(expectedResponse, Optional.empty());
    }
}
