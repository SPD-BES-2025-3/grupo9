package br.ufg.inf.grupo9.biblioteca.service;
import br.ufg.inf.grupo9.biblioteca.adapter.AutorAdapter;
import br.ufg.inf.grupo9.biblioteca.dto.autor.AutorRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.autor.AutorResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Autor;
import br.ufg.inf.grupo9.biblioteca.repository.AutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AutorAdapter autorAdapter;

    @InjectMocks
    private AutorService autorService;

    private Autor autorEntity;
    public  AutorRequestDTO requestDTO;
    private AutorResponseDTO responseDTO;

    @BeforeEach
    void setup() {
        // Criando entidade Autor (simula o que vem do banco)
        autorEntity = new Autor();
        autorEntity.setId("123");
        autorEntity.setNome("José");
        autorEntity.setNacionalidade("Brasileiro");

        requestDTO = new AutorRequestDTO();

        responseDTO = new AutorResponseDTO("123", "José","Brasileiro");

    }

    @Test
    void getAllAutor_deveRetornarListaDTO() {
        when(autorRepository.findAll()).thenReturn(List.of(autorEntity));
        when(autorAdapter.toAutorDTO(autorEntity)).thenReturn(responseDTO);

        List<AutorResponseDTO> result = autorService.getAllAutor();

        assertThat(result).hasSize(1).containsExactly(responseDTO);
        verify(autorRepository).findAll();
        verify(autorAdapter).toAutorDTO(autorEntity);
    }

    @Test
    void getAutorById_quandoEncontrado_deveRetornarDTO() {
        when(autorRepository.findById("123")).thenReturn(Optional.of(autorEntity));
        when(autorAdapter.toAutorDTO(autorEntity)).thenReturn(responseDTO);

        AutorResponseDTO result = autorService.getAutorById("123");

        assertThat(result).isEqualTo(responseDTO);
        verify(autorRepository).findById("123");
        verify(autorAdapter).toAutorDTO(autorEntity);
    }

    @Test
    void getAutorById_quandoNaoEncontrado_deveLancar404() {
        when(autorRepository.findById("999")).thenReturn(Optional.empty());

        ResponseStatusException ex = catchThrowableOfType(
                () -> autorService.getAutorById("999"),
                ResponseStatusException.class
        );

        assertThat(ex.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        verify(autorRepository).findById("999");
        verifyNoInteractions(autorAdapter);
    }

    @Test
    void getAutorByNacionalidade_deveFiltrarEConverter() {
        when(autorRepository.findAllByNacionalidade("Brasileiro"))
                .thenReturn(List.of(autorEntity));
        when(autorAdapter.toAutorDTO(autorEntity)).thenReturn(responseDTO);

        List<AutorResponseDTO> result = autorService.getAutorByNacionalidade("Brasileiro");

        assertThat(result).hasSize(1).containsExactly(responseDTO);
        verify(autorRepository).findAllByNacionalidade("Brasileiro");
    }

    @Test
    void getAutorByNome_deveFiltrarEConverter() {
        when(autorRepository.findByNome("José"))
                .thenReturn(List.of(autorEntity));
        when(autorAdapter.toAutorDTO(autorEntity)).thenReturn(responseDTO);

        List<AutorResponseDTO> result = autorService.getAutorByNome("José");

        assertThat(result).hasSize(1).containsExactly(responseDTO);
        verify(autorRepository).findByNome("José");
    }

    @Test
    void create_deveSalvarEConverter() {
        when(autorAdapter.toAutor(requestDTO)).thenReturn(autorEntity);
        when(autorRepository.save(autorEntity)).thenReturn(autorEntity);
        when(autorAdapter.toAutorDTO(autorEntity)).thenReturn(responseDTO);

        AutorResponseDTO result = autorService.create(requestDTO);

        assertThat(result).isEqualTo(responseDTO);
        verify(autorAdapter).toAutor(requestDTO);
        verify(autorRepository).save(autorEntity);
        verify(autorAdapter).toAutorDTO(autorEntity);
    }

    @Test
    void update_quandoExistir_deveAtualizarEConverter() {
        // dado que existe
        when(autorRepository.findById("123")).thenReturn(Optional.of(autorEntity));
        // adapter converte request em nova entidade sem id
        Autor entidadeAtualizada = new Autor();
        entidadeAtualizada.setNome("João");
        entidadeAtualizada.setNacionalidade("Brasileiro");
        when(autorAdapter.toAutor(requestDTO)).thenReturn(entidadeAtualizada);
        // repositorio salva e retorna entidade com id
        Autor salvo = new Autor();
        salvo.setId("123");
        salvo.setNome("João");
        salvo.setNacionalidade("Brasileiro");
        when(autorRepository.save(entidadeAtualizada)).thenReturn(salvo);
        when(autorAdapter.toAutorDTO(salvo)).thenReturn(responseDTO);

        AutorResponseDTO result = autorService.update(requestDTO, "123");

        assertThat(result).isEqualTo(responseDTO);
        // confirma que o id antigo foi preservado
        assertThat(entidadeAtualizada.getId()).isEqualTo("123");
        verify(autorRepository).findById("123");
        verify(autorRepository).save(entidadeAtualizada);
    }

    @Test
    void update_quandoNaoExistir_deveLancar404() {
        when(autorRepository.findById("999")).thenReturn(Optional.empty());

        ResponseStatusException ex = catchThrowableOfType(
                () -> autorService.update(requestDTO, "999"),
                ResponseStatusException.class
        );

        assertThat(ex.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        verify(autorRepository).findById("999");
        verify(autorRepository, never()).save(any());
    }

    @Test
    void delete_quandoExistir_deveChamarDelete() {
        when(autorRepository.findById("123")).thenReturn(Optional.of(autorEntity));

        autorService.delete("123");

        verify(autorRepository).findById("123");
        verify(autorRepository).delete(autorEntity);
    }

    @Test
    void delete_quandoNaoExistir_deveLancar404() {
        when(autorRepository.findById("999")).thenReturn(Optional.empty());

        ResponseStatusException ex = catchThrowableOfType(
                () -> autorService.delete("999"),
                ResponseStatusException.class
        );

        assertThat(ex.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        verify(autorRepository).findById("999");
        verify(autorRepository, never()).delete(any());
    }
}



