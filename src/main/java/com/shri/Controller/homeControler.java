package com.shri.Controller;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shri.Services.Service;
import com.shri.models.Movies;


@Controller
public class homeControler {
	
	
	
	@Autowired
	private Service service;
	
	
	
	@RequestMapping("/")
    public ModelAndView home() {
		
    	Map<String,Object> map = service.gethomeobj(); 
    	
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("home");
    	mv.addAllObjects(map);
    	return mv;
    	
    }
	
	
	@GetMapping(path = "/menu/{filter}")
	public ModelAndView getmenufilter(@PathVariable("filter") String filter) {
		
		Map<String,Object> map = service.getMenuObj(filter);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("menufilters");
		mv.addAllObjects(map);
		
		return mv;
	}
	
	
	@GetMapping("/movie/{moviename}")
	public ModelAndView getmovie(@PathVariable("moviename") String moviename) {
		
		Map<String,Object> map = service.getMoviesobj(moviename);	
		ModelAndView mv = new ModelAndView();
		mv.setViewName("reviewpage");
		mv.addAllObjects(map);
		return mv;
		
	}
	
	
	
	@GetMapping(path = "/login")
	public ModelAndView getlogin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");	
		return mv;
	}
	
	
	@GetMapping(path = "/register")
	public ModelAndView getregister() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("register");
		return mv;
	}
	
	
	
	@PostMapping(path = "/register-user")
	public ResponseEntity<List<String>> postregister(@RequestParam String username,@RequestParam String firstname, 
			                                 @RequestParam String lastname,@RequestParam String email,@RequestParam String role,@RequestParam String password) {
		List<String> message = service.registeruser(username,password,role,email,firstname,lastname);
		System.out.println(message);
		if(message.size() > 0) {
			return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(message,HttpStatus.OK);
			
	}
	
	
	
	
	@GetMapping(path = "/search")
	public ModelAndView search(@RequestParam("query") String query,@RequestParam("search-category") String category) {
		
		List<Movies> movies = service.getsearchcontent(query,category);
		System.out.println(movies);
		ModelAndView mv = new ModelAndView();
		mv.addObject("searchResult",movies);
		mv.setViewName("searchresult");
		
		return mv;
	}
	
	
	@GetMapping(path = "/watchlater")
	public ModelAndView getwatchlaterpage() {
		
		Map<String,Object> map = service.getwatchlaterobj();
		ModelAndView mv = new ModelAndView();
		mv.addAllObjects(map);
		mv.setViewName("watchlater");
		
		return mv;	
	}
	
	
	@PostMapping("/submitReply")
	public ResponseEntity<String> submitreply(@RequestParam("reviewId") String reviewid,@RequestParam("moviename") String moviename,@RequestParam("replyText") String reply) {
        boolean value = service.submitreply(reviewid,moviename,reply);
		
		if(!value) {
			return ResponseEntity.badRequest().build();
		}
		
        System.out.println("Received reply data");
        return ResponseEntity.ok("reply submitted successfully");
	}
	
	
	
	
	
	

}
