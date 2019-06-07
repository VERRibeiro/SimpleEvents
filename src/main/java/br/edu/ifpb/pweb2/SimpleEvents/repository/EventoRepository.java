package br.edu.ifpb.pweb2.SimpleEvents.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.SimpleEvents.model.Evento;

public interface EventoRepository extends JpaRepository<Evento,Integer>{

}
