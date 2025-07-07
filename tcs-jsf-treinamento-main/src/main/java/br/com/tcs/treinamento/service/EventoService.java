package br.com.tcs.treinamento.service;

import br.com.tcs.treinamento.entity.Evento;
import java.util.List;

public interface EventoService {
    void cadastrarEvento(Evento evento);
    void atualizarEvento(Evento evento);
    void excluirEvento(Evento evento);
    Evento buscarEventoPorId(Long id);
    List<Evento> listarEventos();
}
