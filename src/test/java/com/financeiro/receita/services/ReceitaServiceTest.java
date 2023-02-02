package com.financeiro.receita.services;

import com.financeiro.receita.exceptions.ReceitaNotFoundException;
import com.financeiro.receita.http.models.ReceitaRequest;
import com.financeiro.receita.http.models.ReceitaResponse;
import com.financeiro.receita.repositories.ReceitaRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReceitaServiceTest {

    private ReceitaRequest request = requestBuilder();
    private final ReceitaRepository repository = mock(ReceitaRepository.class);
    private final ReceitaService service = new ReceitaService(repository);


    @Test
    public void shouldRegisterNewReceita() {
        service.cadastrar(request);

        verify(repository, times(1)).save(any());
    }

    @Test
    public void shouldBeThrowNullPointerExceptionWhenRegisteringANewNullReceita() {
        request = null;
        assertThrows(NullPointerException.class, () -> service.cadastrar(request));
    }

    @Test
    public void shouldGetListOfReceitas() {
        List<ReceitaResponse> listaReceita = service.listar();

        verify(repository, times(1)).findAll();
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenIdNotFound() {
        assertThrows(RuntimeException.class, () -> service.buscar(1L));
        verify(repository, times(1)).findById(any());
    }

    @Test
    public void shouldThrowReceitaNotFoundExceptionWhenDescricaoNotFound() {
        assertThrows(ReceitaNotFoundException.class, () -> service.buscarPorDescricao("Descrição teste"));
        verify(repository, times(1)).findByDescricao(any());
    }

    private ReceitaRequest requestBuilder() {
        return new ReceitaRequest("Descrição teste", new BigDecimal(50.0));
    }

}