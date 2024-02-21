package com.shri.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shri.ratings;
import com.shri.Services.Service;

@RestController
@RequestMapping("/reviews")
public class RestReviewController {

	
	
	
	@Autowired
	private Service service;
	
	
	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<String> deletereview(@PathVariable("id") String reviewid) {
		if(reviewid.length() == 0) {
			return ResponseEntity.notFound().build();
		}
		
		boolean value = service.deletereview(Integer.parseInt(reviewid));
		
		if(!value) {
			return ResponseEntity.notFound().build();
		}
		
		
		return ResponseEntity.ok("review deleted successfully");
		
	}
	
	
	@PostMapping(path = "/post/submit-review")
    public ResponseEntity<List<String>> submitReview(@RequestBody ratings reviewData) {
    
		List<String> value = service.submitreview(reviewData);
		
	    if(value.size() == 0 || value.get(0).equals("warning don't add abusive commends")) {
	    	value.add("review added");
	        System.out.println("Received review data: " + reviewData);
			return new ResponseEntity<>(value,HttpStatus.OK);
		}
		
        return new ResponseEntity<>(value,HttpStatus.BAD_REQUEST);
        
    }
	
}
