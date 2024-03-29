package br.edu.ifpb.pweb2.SimpleEvents.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.SimpleEvents.model.VagaEvento;

public interface VagaEventoRepository  extends JpaRepository<VagaEvento,Integer>{
	
	List<VagaEvento> findByEventoId(Integer evento_id);
}