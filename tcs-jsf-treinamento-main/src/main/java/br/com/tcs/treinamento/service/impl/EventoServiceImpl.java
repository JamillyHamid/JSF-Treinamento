package br.com.tcs.treinamento.service.impl;

import br.com.tcs.treinamento.dao.EventoDAO;
import br.com.tcs.treinamento.entity.Evento;
import br.com.tcs.treinamento.service.EventoService;

import java.util.List;

public class EventoServiceImpl implements EventoService {

    private EventoDAO eventoDAO = new EventoDAO();

    @Override
    public void cadastrarEvento(Evento evento) {
        eventoDAO.cadastrarEvento(evento);
    }

    @Override
    public void atualizarEvento(Evento evento) {
        eventoDAO.atualizarEvento(evento);
    }

    @Override
    public void excluirEvento(Evento evento) {
        eventoDAO.removerEvento(evento);
    }

    @Override
    public Evento buscarEventoPorId(Long id) {
        return eventoDAO.buscarEventoPorId(id);
    }

    @Override
    public List<Evento> listarEventos() {
        return eventoDAO.listarEventos();
    }
}
