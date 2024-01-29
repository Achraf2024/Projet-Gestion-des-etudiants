package com.opendevup.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.opendevup.entities.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long>{
	public List<Etudiant> findByNom(String name);
	public Page<Etudiant> findByNom(String name, Pageable pageable);
	
	@Query("select e from Etudiant e where e.nom like :x")
	public Page<Etudiant> findByMc(@Param("x")String mc, Pageable pageable);
	
	@Query("select e from Etudiant e where e.dateNaissance > :x and e.dateNaissance < :y")
	public List<Etudiant> findByMc(@Param("x")Date date1, @Param("y")Date date2);
}
