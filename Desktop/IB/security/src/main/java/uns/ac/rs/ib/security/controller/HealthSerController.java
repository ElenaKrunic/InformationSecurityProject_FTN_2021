package uns.ac.rs.ib.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uns.ac.rs.ib.security.dto.HealthSerDTO;
import uns.ac.rs.ib.security.model.HealthSer;
import uns.ac.rs.ib.security.repository.HealthSerRepository;
import uns.ac.rs.ib.security.service.HealthSerService;

@RestController
@RequestMapping(value = "/api/healthServices")
public class HealthSerController {

	@Autowired
	HealthSerService healthSerService; 
	
	@Autowired 
	HealthSerRepository healthSerRepository;
	
	@GetMapping(value="/all")
	public ResponseEntity<List<HealthSerDTO>> getHealthServices() {
		List<HealthSer> healthServices = healthSerService.findAll(); 
		List<HealthSerDTO> healthServicesDTO = new ArrayList<>();
		for(HealthSer h : healthServices) {
			healthServicesDTO.add(new HealthSerDTO(h));
		}
		
		return new ResponseEntity<List<HealthSerDTO>>(healthServicesDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<HealthSerDTO> getHealthService(@PathVariable("id") Long id) {
		HealthSer healthService = healthSerService.findOne(id); 
		
		if (healthService == null) {
			return new ResponseEntity<HealthSerDTO>(HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<HealthSerDTO>(new HealthSerDTO(healthService), HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<HealthSerDTO> saveHealthService(@RequestBody HealthSerDTO healthSerDTO) {
		HealthSer healthSer = new HealthSer();
		healthSer.setName(healthSerDTO.getName());
		healthSer.setPrice(healthSerDTO.getPrice());
		
		healthSer = healthSerService.save(healthSer); 
		
		return new ResponseEntity<HealthSerDTO>(new HealthSerDTO(healthSer), HttpStatus.CREATED);
	}
	
	@PutMapping(value="id", consumes = "application/json")
	public ResponseEntity<HealthSerDTO> updateHealthService(@RequestBody HealthSerDTO healthSerDTO, @PathVariable("id") Long id) {
		HealthSer healthSer = healthSerService.findOne(id);
		
		healthSer.setName(healthSerDTO.getName());
		healthSer.setPrice(healthSerDTO.getPrice());
		
		healthSer = healthSerService.save(healthSer); 
		
		return new ResponseEntity<HealthSerDTO>(new HealthSerDTO(healthSer), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteHealthService(@PathVariable("id") Long id) {
		HealthSer healthSer = healthSerService.findOne(id); 
		
		if(healthSer != null) {
			healthSerService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	
}
