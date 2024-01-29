package com.opendevup.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.opendevup.dao.EtudiantRepository;
import com.opendevup.entities.Etudiant;

@Controller
@RequestMapping(value="/etudiant")
public class EtudiantController {

	
	@Autowired
	private EtudiantRepository etudiantRepository;
	
	@Value("${dir.images}")
	private String imageDir;
	
	@RequestMapping(value="/index")
	public String Index(Model model, @RequestParam(name="page",defaultValue="0") int page, 
			@RequestParam(name="motCle",defaultValue="") String motCle) {  
		Page<Etudiant> etds = etudiantRepository.findByMc("%"+motCle+"%", new PageRequest(page, 5));
		
		int NbPage = etds.getTotalPages();
		int[] pages = new int[NbPage];
		for (int i = 0; i < NbPage; i++) {
			pages[i] = i;
		}
		model.addAttribute("pages",pages);
		model.addAttribute("pageEtudiants",etds);
		model.addAttribute("pageCourent",page);
		model.addAttribute("motCle", motCle);
		return "etudiants";
	}
	
	@RequestMapping(value="/form",method=RequestMethod.GET)
	public String formEtudiant(Model model) {
		model.addAttribute("etudiant", new Etudiant());
		return "formEtudiant";
	}
	
	@RequestMapping(value="/SaveEtudiant",method=RequestMethod.POST)
	public String save(@Valid Etudiant et, BindingResult bindingResult, @RequestParam(name="picture")MultipartFile file) throws Exception {
		if (bindingResult.hasErrors()) {
			System.out.println("denndcndjk");
			return "formEtudiant";
		}
		if (!(file.isEmpty())) { et.setPhoto(file.getOriginalFilename());}
		etudiantRepository.save(et);
		
		if (!(file.isEmpty())) {
			et.setPhoto(file.getOriginalFilename());
			file.transferTo(new File(imageDir + et.getId()));
		}
		
		
		
		
		return "redirect:index";
	}
	
	@RequestMapping(value="/getPhoto", produces=MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto(Long id) throws Exception {
		File f = new File(imageDir + id);
		return IOUtils.toByteArray(new FileInputStream(f));
		
	}
	
	@RequestMapping(value="/supp")
	public String supp(Long id) {
		etudiantRepository.delete(id);
		return "redirect:index";
	}
	
	@RequestMapping(value="/editer")
	public String editer(Long id, Model model) {
		Etudiant etudiant = etudiantRepository.getOne(id);
		model.addAttribute("etudiant", etudiant);
		return "editEtudiant";
	}
	
	
	@RequestMapping(value="/UpdateEtudiant",method=RequestMethod.POST)
	public String update(@Valid Etudiant et, BindingResult bindingResult, @RequestParam(name="picture")MultipartFile file) throws Exception {
		if (bindingResult.hasErrors()) {
			return "editEtudiant";
		}
		if (!(file.isEmpty())) { et.setPhoto(file.getOriginalFilename());}
		etudiantRepository.save(et);
		
		if (!(file.isEmpty())) {
			et.setPhoto(file.getOriginalFilename());
			file.transferTo(new File(imageDir + et.getId()));
		}
			
		return "redirect:index";
	}
	
	
}
