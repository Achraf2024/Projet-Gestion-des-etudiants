package com.opendevup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.opendevup.dao.EtudiantRepository;
import com.opendevup.entities.Etudiant;

@SpringBootApplication
public class GestionEtudiantsApplication {

	public static void main(String[] args) throws ParseException {
		ApplicationContext ctx = SpringApplication.run(GestionEtudiantsApplication.class, args);
		EtudiantRepository etudiantRepository = ctx.getBean(EtudiantRepository.class);
		DateFormat date = new SimpleDateFormat("yyy-mm-dd");
		etudiantRepository.save(new Etudiant("James", date.parse("1989-03-14"), "james@gmail.com","james.jpg"));
		etudiantRepository.save(new Etudiant("Rachid", date.parse("1987-06-10"), "rachid@gmail.com","rachid.jpg"));
		etudiantRepository.save(new Etudiant("Johne", date.parse("1985-02-13"), "john@gmail.com","john.jpg"));
        
		Page<Etudiant> etudiants = etudiantRepository.findAll(new PageRequest(1, 5));
		
		for (Etudiant etudiant : etudiants) {
			System.out.println(etudiant.getNom());
		}
		
	}
}
