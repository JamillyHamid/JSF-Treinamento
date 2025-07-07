package br.com.tcs.treinamento.dao;

import br.com.tcs.treinamento.entity.Evento;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class EventoDAO {
    private EntityManager em;
    private static EntityManagerFactory emf;

    public EventoDAO(){
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("myPU");
        }
        em = emf.createEntityManager();
    }

    public void cadastrarEvento(Evento evento) {
        try{
            em.getTransaction().begin();
            em.persist(evento);
            em.getTransaction().commit();
        } catch(Exception e){
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public Evento buscarEventoPorId(Long id) {
        return em.find(Evento.class, id);
    }

    public List<Evento> listarEventos() {
        return em.createQuery("select e from Evento e", Evento.class).getResultList();
    }

    public Evento atualizarEvento(Evento evento) {
        try{
            em.getTransaction().begin();
            Evento e = em.merge(evento);
            em.flush();
            em.getTransaction().commit();
            return e;
        } catch(Exception e){
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public void removerEvento(Evento evento) {
        try{
            em.getTransaction().begin();
            em.remove(em.contains(evento) ? evento : em.merge(evento));
            em.getTransaction().commit();
        } catch (Exception e){
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
}
