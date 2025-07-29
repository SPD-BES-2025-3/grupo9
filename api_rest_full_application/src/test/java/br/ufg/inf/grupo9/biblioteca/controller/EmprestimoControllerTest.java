package br.ufg.inf.grupo9.biblioteca.controller;

import br.ufg.inf.grupo9.biblioteca.dto.emprestimo.EmprestimoRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.emprestimo.EmprestimoResponseDTO;
import br.ufg.inf.grupo9.biblioteca.service.EmprestimoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmprestimoControllerTest {

    private EmprestimoService emprestimoService;
    private EmprestimoController emprestimoController;

    @BeforeEach
    void setUp() {
        emprestimoService = mock(EmprestimoService.class);
        emprestimoController = new EmprestimoController(emprestimoService);
    }

    @Test
    void testGetAllEmprestimo() {
        List<EmprestimoResponseDTO> mockList = List.of(new EmprestimoResponseDTO(), new EmprestimoResponseDTO());
        when(emprestimoService.getAllEmprestimo()).thenReturn(mockList);

        ResponseEntity<List<EmprestimoResponseDTO>> response = emprestimoController.getAllEmprestimo();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetEmprestimoById() {
        String id = "123";
        EmprestimoResponseDTO dto = new EmprestimoResponseDTO();
        when(emprestimoService.getEmprestimoById(id)).thenReturn(dto);

        ResponseEntity<EmprestimoResponseDTO> response = emprestimoController.getEmprestimoById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testGetEmprestimoByDataEmprestimo() {
        Date data = new Date();
        List<EmprestimoResponseDTO> mockList = List.of(new EmprestimoResponseDTO());
        when(emprestimoService.getEmprestimoByDataEmprestimo(data)).thenReturn(mockList);

        ResponseEntity<List<EmprestimoResponseDTO>> response = emprestimoController.getEmprestimoByDataEmprestimo(data);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetEmprestimosPorLivro() {
        String livroId = "livro123";
        List<EmprestimoResponseDTO> mockList = List.of(new EmprestimoResponseDTO());
        when(emprestimoService.getEmprestimosPorLivro(livroId)).thenReturn(mockList);

        ResponseEntity<List<EmprestimoResponseDTO>> response = emprestimoController.getEmprestimosPorLivro(livroId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testCreate() {
        EmprestimoRequestDTO requestDTO = new EmprestimoRequestDTO();
        EmprestimoResponseDTO responseDTO = new EmprestimoResponseDTO();
        when(emprestimoService.createEmprestimo(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<EmprestimoResponseDTO> response = emprestimoController.create(requestDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void testUpdate() {
        String id = "456";
        EmprestimoRequestDTO requestDTO = new EmprestimoRequestDTO();
        EmprestimoResponseDTO updatedDTO = new EmprestimoResponseDTO();
        when(emprestimoService.updateEmprestimo(id, requestDTO)).thenReturn(updatedDTO);

        ResponseEntity<EmprestimoResponseDTO> response = emprestimoController.update(id, requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedDTO, response.getBody());
    }

    @Test
    void testDelete() {
        String id = "789";

        doNothing().when(emprestimoService).deleteEmprestimo(id);

        ResponseEntity<String> response = emprestimoController.delete(id);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
