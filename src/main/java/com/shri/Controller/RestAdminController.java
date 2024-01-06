package com.shri.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.shri.Services.Service;
import com.shri.models.AuthorizedAdmins;
import com.shri.models.DeletedMovies;
import com.shri.models.Movies;

@RestController
@RequestMapping("/admin")
public class RestAdminController {
	
	@Autowired
	private Service service;

	@RequestMapping("/") 
	public ModelAndView getAdminPage() {
		ModelAndView mv = new ModelAndView();
		Map<String,Object> map = service.getadminpageobj();
		mv.setViewName("adminpage");
		mv.addAllObjects(map);
		return mv;
	}
	
	@GetMapping("/getmovies")
	public ResponseEntity<List<Movies>> getmovies() {
		List<Movies> movie = service.getlistofmovies();
		return new ResponseEntity<>(movie,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete-movie")
	public ResponseEntity<String> deletemovie(@RequestBody Map<String, Object> requestBody){
		
		int movieId = Integer.parseInt(requestBody.get("movieId").toString());
		boolean response = service.deleteMovieMyAdmin(movieId);
		
		if(response) {
			return new ResponseEntity<>("movie deleted",HttpStatus.OK);
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	
	@PostMapping("/add-movie")
	public ResponseEntity<List<String>> addmovie(@RequestBody Map<String, Object> movieDetails) {
		
		String movieName = movieDetails.get("movieName").toString();
        String description = movieDetails.get("description").toString();
        String director = movieDetails.get("director").toString();
        String year = movieDetails.get("year").toString();
        String rating = movieDetails.get("rating").toString();
        String path = movieDetails.get("path").toString();
        String genre = movieDetails.get("genre").toString();
        String trailerLink = movieDetails.get("trailerLink").toString();
        String slidePath = movieDetails.get("slidePath").toString();
        
        System.out.println("in restcon");
        System.out.println(movieName);
        List<String> message = service.addNewMovie(movieName,description,director,year,rating,path,genre,trailerLink,slidePath);
        System.out.println(message);
        if(message.size() == 0) {
        	return new ResponseEntity<>(message,HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        }
		
	}
	
	
	@PutMapping("/update-movie")
	public ResponseEntity<List<String>> updatemovie(@RequestBody Map<String, Object> updateDetials) {
		
		String movieid = updateDetials.get("updatemovieid").toString();
		String column = updateDetials.get("selectedoption").toString();
		String updatevalue = updateDetials.get("updatevalue").toString();
		
		List<String> message = service.updatemoviebyid(movieid,column,updatevalue);
		if(message.size() > 0) {
			return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	
	
	@GetMapping("/show-deletedmovies")
	public ResponseEntity<List<DeletedMovies>> getdeletedmovies() {
		List<DeletedMovies> list = service.getDeletedMovieList();
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	
	@PostMapping("/retrive-movie")
	public ResponseEntity<String> retrivedeletedmovie(@RequestBody Map<String, Object> requestBody) {
		
		String movieid = requestBody.get("deletedmovieId").toString();
		boolean status = service.retrivemovie(Integer.parseInt(movieid));
		if(status) {
			return new ResponseEntity<>("movie retrived",HttpStatus.OK);
		}else {
			return new ResponseEntity<>("movie not found",HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/show-admins")
	public ResponseEntity<List<AuthorizedAdmins>> showadmins() {
		List<AuthorizedAdmins> admins = service.getlistofadmins();
		return new ResponseEntity<>(admins,HttpStatus.OK);
	}
	
	@PostMapping("/add-admin")
	public ResponseEntity<List<String>> addadmins(@RequestBody Map<String,Object> body) {
		List<String> list = service.getaddadmin(body.get("username").toString(),body.get("email").toString());
		
		if(list.size() > 0) {
			return new ResponseEntity<>(list,HttpStatus.BAD_REQUEST);
		}
		list.add("admin added");
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete-admin")
	public ResponseEntity<List<String>> deleteadmin(@RequestBody Map<String,Object> body) {
		List<String> message = service.getdeleteadmin(Integer.parseInt(body.get("movieid").toString()));
		
		if(message.size() > 0) {
			return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		}
		
		message.add("admin deleted");
		
		return new ResponseEntity<>(message,HttpStatus.OK);
		
	}
	
		
	
	
}
