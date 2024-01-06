package com.shri.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shri.Services.Service;

@RestController
@RequestMapping("/watchlater")
public class RestWlController {

	@Autowired
	private Service service;
	
	@PostMapping(path = "/addwl")
	public ResponseEntity<String> addwatchlater(@RequestHeader("moviename") String moviename) {
		boolean value = service.addtowatchlater(moviename);
		
		if(!value) {
			System.out.println("at bad");
			return ResponseEntity.badRequest().build();
		}
		
        System.out.println("Received wl data: " + moviename);
        return ResponseEntity.ok("wl submitted successfully");
		
	}
	
	
	@DeleteMapping(path = "/remove-wl")
	public ResponseEntity<String> deletewatchlater(@RequestHeader("wlId") String wlId) {
		boolean value = service.deletewatchlater(Integer.parseInt(wlId));
		
		if(!value) {
			return ResponseEntity.badRequest().build();
		}
		
        System.out.println("deleted wl data: " + wlId);
        return ResponseEntity.ok("wl deleted successfully");
		
	}
	
}
