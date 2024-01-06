package com.shri.Services;

import java.sql.Timestamp; 
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.shri.ratings;
import com.shri.Repo.AuthorizedAdminRepo;
import com.shri.Repo.DeletedMovieRepo;
import com.shri.Repo.MoviesRepo;
import com.shri.Repo.ReviewRepo;
import com.shri.Repo.UserRepo;
import com.shri.Repo.UserWarningRepo;
import com.shri.Repo.WatchLaterRepo;
import com.shri.Repo.reviewreplyRepo;
import com.shri.models.AuthorizedAdmins;
import com.shri.models.DeletedMovies;
import com.shri.models.Movies;
import com.shri.models.ReviewReplys;
import com.shri.models.Reviews;
import com.shri.models.Roles;
import com.shri.models.UserModel;
import com.shri.models.UserWarning;
import com.shri.models.WatchLater;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@org.springframework.stereotype.Service
public class Service {
	
	@Autowired
	private MoviesRepo moviesrepo;
	@Autowired
	private UserRepo userrepo;
	@Autowired
	private ReviewRepo reviewrepo;
	@Autowired
	private WatchLaterRepo watchlaterrepo;
	@Autowired
	private reviewreplyRepo reviewreplyrepo;
	@Autowired
	private DeletedMovieRepo deletedmovierepo;
	@Autowired
	private UserWarningRepo userwarningrepo;
	@Autowired
	private EmailService emailservice;
	@Autowired
	private HttpServletRequest httpservletrequest;
	@Autowired
	private AuthorizedAdminRepo authorizedadminrepo;
	
	
	
	public Authentication getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication;
	}
	
	
	
	
	
	// home page
	
	// 1. getting home page content   
	
	public Map<String, Object> gethomeobj() {
		
		List<Movies> mvs = moviesrepo.findAll(); 
		List<Movies> topmovies = moviesrepo.findTop5ByOrderByRatingDesc();
    	Authentication auth = getCurrentUser();
    	Map<String,Object> map = new HashMap<>();
    	if(!auth.getName().equals("anonymousUser")) {
    		UserModel user = userrepo.findByusername(auth.getName());
    		Roles role = user.getRole();
    		map.put("role", role.ordinal());
    		map.put("superadmin",user.isSuperadmin());
    	}
    	map.put("auth",auth);
    	map.put("mvs",mvs);
    	map.put("topmovies", topmovies);
    	map.put("pageTitle", "header");
    	return map;
    	
	}
	
	
	// 2. getting search content
	
	public List<Movies> getsearchcontent(String query,String category) {

		List<Movies> movies = new ArrayList<>();
		if(query.length() == 0) {
			return movies;
		}
		
		query = query.toLowerCase().replace(" ", "");
		
		List<Movies> upmovies = moviesrepo.findAll();
		
		for(Movies movie : upmovies) {	
			
			String str = "";
				
			if(category.equals("genre")) str = movie.getGenre();
			else if(category.equals("director")) str = movie.getDirector();
			else str = movie.getMoviename();
			
			str = str.toLowerCase().replace(" ", "");
			
			if(query.equals(str)) {
				movies.add(movie);
				if(category.equals("moviename")) break;
			}
			else if(isSubstring(str,query)) {
			   movies.add(movie);	
			}	
			
		}
		
		
		return movies;
		
	}


	private boolean isSubstring(String movie, String query) {
		// TODO Auto-generated method stub
		if(query.length() > movie.length()) {
			return false;
		}
		
		int PRIME = 101;
		double queryhash = calculatehash(query,PRIME);
		double moviehash = calculatehash(movie.substring(0,query.length()),PRIME);
		int queryLength = query.length();
		
		
		for(int i=0; i<=movie.length() - queryLength; i++) {
			if(queryhash == moviehash) {
				if(movie.substring(i,i+queryLength).equals(query)) {
					return true;
				}
			}
			
			if(i < movie.length() - queryLength) {
				moviehash = updatehash(moviehash,movie.charAt(i),movie.charAt(i + queryLength),queryLength,PRIME);
			}
		}
		
		return false;
	}

	
	

	private double calculatehash(String query,int PRIME) {
		// TODO Auto-generated method stub
		double hash = 0;
		for(int i=0; i < query.length(); i++) {
		   hash += query.charAt(i) * Math.pow(PRIME, i);
		}
		    
		return hash;
	}

	

	private double updatehash(double moviehash, char oldChar, char newChar, int queryLength,int PRIME) {
		// TODO Auto-generated method stub
		double newHash = (moviehash - oldChar) / PRIME;
		newHash = newHash + newChar * Math.pow(PRIME, queryLength - 1);
		return newHash;
	}

	

	// 3. Menu items

	public Map<String, Object> getMenuObj(String filter) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		
		if(filter.equals("allmovies")) {
			map.put("filter", "All Movies");
			map.put("movies", moviesrepo.findAll());
		}else if(filter.equals("topmovies")) {
			map.put("filter", "Top Movies");
			map.put("movies", moviesrepo.findTop5ByOrderByRatingDesc());
		}else if(filter.equals("fantasy")) {
			map.put("filter", "Fantasy Movies");
			map.put("movies", moviesrepo.findByGenre("Fantasy"));
		}else if(filter.equals("thriller")) {
			map.put("filter", "Thriller Movies");
			map.put("movies", moviesrepo.findByGenre("Thriller"));
		}else if(filter.equals("action")) {
			map.put("filter", "Action Movies");
			map.put("movies", moviesrepo.findByGenre("Action"));
		}else if(filter.equals("sifi")){
			map.put("filter", "Si-Fi Movies");
			map.put("movies", moviesrepo.findByGenre("Si-fi"));
		}else if(filter.equals("romcom")) {
			map.put("filter", "Romantic Comedy Movies");
			map.put("movies", moviesrepo.findByGenre("RomCom"));
		}else {
			map.put("filter", "Adult-Comedy Movies");
			map.put("movies", moviesrepo.findByGenre("Adult-comedy"));
		}
		
		return map;
	}
	
	
	// 4. Watch later  
	
	// adding movie to watch later
	public boolean addtowatchlater(String moviename) {
		// TODO Auto-generated method stub
		if(moviename == null || moviename.equals("")) {
			System.out.println("its the moviename" +moviename);
			return false;
		}
		
		System.out.println("in addwl");
		
		Movies movie = moviesrepo.findByMoviename(moviename).orElse(null);
		UserModel user = userrepo.findByusername(getCurrentUser().getName());
		WatchLater wl = new WatchLater();
		wl.setMovie(movie);
		wl.setUser(user);
		wl.setLink(null);
		LocalDateTime currentDateTime = LocalDateTime.now();
		wl.setTimestamp(Timestamp.valueOf(currentDateTime));
		
		watchlaterrepo.save(wl);
		
		return true;
	}

	// getting watch later contents
	public Map<String, Object> getwatchlaterobj() {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		
		UserModel user = userrepo.findByusername(getCurrentUser().getName());
		Authentication auth = getCurrentUser();
		if(!auth.getName().equals("anonymousUser")) {
    		Roles Role = userrepo.findByusername(auth.getName()).getRole();
    		map.put("role", Role.ordinal());
    	}
		List<WatchLater> wl = watchlaterrepo.findByUser(user);
		map.put("wls", wl);
		map.put("auth",auth);
		map.put("pageTitle", "header");
		
		return map;
	}

	// deleting a movie from a watch later content for a particular user
	public boolean deletewatchlater(int wlid) {
		
		Optional<WatchLater> maxObj = watchlaterrepo.findAll().stream().max(Comparator.comparing(WatchLater::getId));
		int maxId = maxObj.map(WatchLater::getId).orElse(0);
		
		if(wlid > maxId || wlid < 0) {
			return false;
		}
		// TODO Auto-generated method stub
		WatchLater wlobj = watchlaterrepo.getReferenceById(wlid);
		watchlaterrepo.delete(wlobj);
			
		return true;
	}

	
	
	
	
	
	
	

	// review page
	
	// 1. get movie's review page content for the particular movie
	
	public Map<String, Object> getMoviesobj(String moviename) {
		
		Movies movie = moviesrepo.findByMoviename(moviename).orElse(null);
		UserModel user = userrepo.findByusername(getCurrentUser().getName());
		List<Reviews> reviews = reviewrepo.findByMovie(movie);
		List<ReviewReplys> reviewreplys = reviewreplyrepo.findByMovie(movie);
		Map<Integer,List<ReviewReplys>> replymap = getMapMatchingReviews(reviews,reviewreplys);
		Authentication auth = getCurrentUser();
		List<ReviewReplys> emptyreplys = new ArrayList<>();
		Map<String,Object> map = new HashMap<>();
		if(!auth.getName().equals("anonymousUser")) {
    		Roles Role = userrepo.findByusername(auth.getName()).getRole();
    		map.put("role", Role.ordinal());
    	}
		System.out.println(replymap);
		 if (movie != null) {
			  map.put("moviename", movie.getMoviename()); 
			  map.put("director", movie.getDirector());
			  map.put("rating", movie.getRating());
			  map.put("year", movie.getYear());
			  map.put("movieshortline", movie.getDescription());
			  map.put("trailer", movie.getTrailerlink());
			  map.put("reviews", reviews);
			  map.put("currentuser", user);
			  map.put("replys", replymap);
			  map.put("emptyreplys", emptyreplys);
			  
	     }

		
		map.put("pageTitle", "header");
		map.put("auth", auth);
		
		
		return map;
		
	}
	
	
	private Map<Integer, List<ReviewReplys>> getMapMatchingReviews(List<Reviews> reviews,
			List<ReviewReplys> reviewreplys) {
		// TODO Auto-generated method stub
		Map<Integer, List<ReviewReplys>> replymap = new HashMap<>();
		
		for(Reviews review : reviews) {
			for(ReviewReplys reply : reviewreplys) {
				if(reply.getReview().equals(review)) {
					List<ReviewReplys> replybyid = replymap.getOrDefault(review.getId(), new ArrayList<>());
					replybyid.add(reply);
					replymap.put(review.getId(), replybyid);
				}
			}
		}
		
		return replymap;
	}

	
	
	// 2. submit review
	
	@Transactional
	public List<String> submitreview(ratings rating) {
		List<String> Returnlist = new ArrayList<>();
		if(rating.getComment().length() == 0 || rating.getRating() == null) {
			Returnlist.add("please don't leave empty");
			return Returnlist;
		}
		
		List<String> list = Arrays.asList("fuck","suck","bullshit","motherfucker","fuck you");
		
		String commend = rating.getComment();
		commend = commend.trim();
		
		if(!validateCommend(commend)) {
			Returnlist.add("invalid commend it should contain chars > 3 && chars <= 200,nums < 25");
			return Returnlist;
		}
		
		for(String str : list) {
			if(isSubstring(rating.getComment(),str)) {
				UserModel user = userrepo.findByusername(getCurrentUser().getName());
				UserWarning userwarning = userwarningrepo.findByUserid(user).orElse(null);
				if(userwarning == null) {
					userwarning = new UserWarning(user,1);
					userwarningrepo.save(userwarning);
				}else {
					if(userwarning.getWarning() == 3) {	
						deleteAllUserData(user);
						String usermail = user.getEmail();
						String username = user.getUsername();
						String message = "hii " +username +" due to continuous abusive commends you have removed from our movie review application";
						userwarningrepo.delete(userwarning);
						userrepo.delete(user);
						emailservice.sendEmail(usermail, "Account Removed", message);
						Returnlist.add("you are removed from our website");
						invalidatesession();
						return Returnlist;
					}else {
						int warning = userwarning.getWarning() + 1;
						userwarningrepo.updateUserWarning(warning, userwarning.getId());	
					}
				}
				
				commend = changeToAstrik(str,commend);
				Returnlist.add("warning don't add abusive commends");
				break;
			}
		}
		
		
		Movies movie = moviesrepo.findByMoviename(rating.getMoviename()).orElse(null);
		UserModel user = userrepo.findByusername(getCurrentUser().getName());
		LocalDateTime currentDateTime = LocalDateTime.now();
	    Timestamp timestamp = Timestamp.valueOf(currentDateTime);
	    
		Reviews review = new Reviews(commend, Integer.parseInt(rating.getRating()), user, movie, timestamp);    
	    reviewrepo.save(review);
	    
	    return Returnlist;
	    	
	}
	
	
	private void invalidatesession() {
		// TODO Auto-generated method stub
		HttpSession session = httpservletrequest.getSession(false);
		session.invalidate();
		
	}





	private boolean validateCommend(String commend) {
		// TODO Auto-generated method stub
		return true;
	}





	private void deleteAllUserData(UserModel user) {
		// TODO Auto-generated method stub
		List<Reviews> reviews = reviewrepo.findByUser(user);
		List<WatchLater> watchlaters = watchlaterrepo.findByUser(user);
		List<ReviewReplys> replys = reviewreplyrepo.findByUser(user);
		
		for (Reviews review : reviews) {
			deletereview(review.getId());
		}
		
		for(WatchLater watchlater : watchlaters) {
			deletewatchlater(watchlater.getId());
		}
		
		for(ReviewReplys reply : replys) {
			reviewreplyrepo.delete(reply);
		}
	}





	private String changeToAstrik(String str, String command) {
		// TODO Auto-generated method stub
		str = str.toLowerCase();
        String copy = command.toLowerCase();
        int start = 0;
        int end = -1;
        StringBuilder substring = new StringBuilder();

        for (int i = 0; i < copy.length(); i++) {
            if(substring.length() == str.length() && substring.toString().equals(str)) {  
                end = i;
                break;
            }else if(substring.length() == str.length()){
                start++;
                substring.deleteCharAt(0);
            }
            substring.append(copy.charAt(i));
        }

        if(end == -1 && substring.length() == str.length() && substring.toString().equals(str)) {  
            end = copy.length();
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < command.length(); i++) {
            if(i >= start && i < end) {
                sb.append("*");
            }else {
                sb.append(command.charAt(i));
            }
        }

        return sb.toString();
	}





	// 3. delete review of a particular movie
	
	public boolean deletereview(int id) {
		// TODO Auto-generated method stub
		
		Reviews review;
		List<ReviewReplys> replysToDelete;
		try {
			review = reviewrepo.getReferenceById(id);
			replysToDelete = reviewreplyrepo.findByReview(review);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return false;
		}
		
		for(ReviewReplys reviewreply : replysToDelete) {
			reviewreplyrepo.delete(reviewreply);
		}
		
		reviewrepo.delete(review);
		
		return true;
	}
	
	
	// 4. submit reply for a particular movie's review.
	
	public boolean submitreply(String reviewid, String moviename, String reply) {
		// TODO Auto-generated method stub
		if(reviewid.equals(null) || reviewid.equals("") || moviename.equals("") || moviename.equals(null) || reply.equals(null) || reply.equals("")) {
			return false;
		}
		
		Reviews review = reviewrepo.getReferenceById(Integer.parseInt(reviewid));
		UserModel user = userrepo.findByusername(getCurrentUser().getName());
		Movies movie = moviesrepo.findByMoviename(moviename).orElse(null);
		
		ReviewReplys reviewreply = new ReviewReplys();
		reviewreply.setMovie(movie);
		reviewreply.setReply(reply);
		reviewreply.setReview(review);
		reviewreply.setUser(user);
		LocalDateTime currentDateTime = LocalDateTime.now();
		reviewreply.setTimestamp(Timestamp.valueOf(currentDateTime));
		reviewreplyrepo.save(reviewreply);
		
		
		return true;
	}


	
	// register
	
	
	public List<String> registeruser(String username,String password,String role,String email,String firstname,String lastname) {
			 
		List<String> message = validateregisterdata(username,password,role,email);
		
		if(message.size() > 0) return message;
		
        if(role.equals("ADMIN")) {
			List<AuthorizedAdmins> list = authorizedadminrepo.findByEmail(email);
			if(list.size() == 0) {
				message.add("you can't register yourself as admin you're not an authorized admin");
				return message;
			}
			
		}
		
		boolean superadmin = false;
		
		
		UserModel user = new UserModel(username, new BCryptPasswordEncoder().encode(password), email, Roles.valueOf(role),superadmin, firstname, lastname);
		
		System.out.println(user);
		userrepo.save(user);
		
		return message;
			
	}
	
	private List<String> validateregisterdata(String name, String password, String role, String email) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		
		if(!validateEmail(email)) {
			list.add("Your email id is incorrect it should contain chars >=8 && chars <= 20,numbers >= 2 && numbers <= 7 and it should end with @gmail.com");
		}
        if(!validateUserName(name)) {	
        	list.add("invalid username it should contain chars >= 4 && chars <= 12 ,nums >= 2 && nums <= 10 and no whitespace");
		}
        if(!validatePassword(password)) {
        	list.add("invalid password it should contain chars >= 4 && chars <= 12, nums >= 2 && nums <= 7, unique chars >= 1 && unique chars <= 4 and no white space");
		}
        
		return list;
	}



	private boolean validatePassword(String password) {
		    password = password.toLowerCase();
	        int charcount = 0;
	        int numscount = 0;
	        int uniqechar = 0;
	        int space = 0;
	        int others = 0;
	        
	        for (int i = 0; i < password.length(); i++) {
	            if(password.charAt(i) >= 97 && password.charAt(i) <= 123) {
	                charcount++;
	            }else if(password.charAt(i) >= 48 && password.charAt(i) <= 57) {
	                numscount++;
	            }else if(password.charAt(i) == ' '){
	                space++;
	            }else if(password.charAt(i) >= 35 && password.charAt(i) <= 38 || password.charAt(i) == 42){
	                uniqechar++;
	            }else {
	                others++;
	            }
	        }

	        if(charcount < 4 || charcount > 12 || numscount < 2 || numscount > 7 || uniqechar < 1 || uniqechar > 4 || space > 0 || others > 0) {
	           return false;
	        }

			return true;

	}





	private boolean validateUserName(String username) {
		
		username = username.toLowerCase();
        int charcount = 0;
        int numscount = 0;
        int space = 0;
        int others = 0;
        for (int i = 0; i < username.length(); i++) {
            if(username.charAt(i) >= 97 && username.charAt(i) <= 123) {
                charcount++;
            }else if(username.charAt(i) >= 48 && username.charAt(i) <= 57) {
                numscount++;
            }else if(username.charAt(i) == ' '){
                space++;
            }else {
                others++;
            }
        }
        if(charcount < 4 || charcount > 12 || numscount < 2 || numscount > 10 || space > 0 || others > 0) {
           return false;
        }

		return true;
	}





	private boolean validateEmail(String email) {
		if(email.length() < 10 || !email.substring(email.length() - 10,email.length()).equals("@gmail.com")) {
            return false;
        }

        email = email.toLowerCase();
        int charcount = 0;
        int numscount = 0;
        int space = 0;
        int others = 0;
        System.out.println(email.substring(0,email.length() - 10));
        for (int i = 0; i < email.length() - 10; i++) {
            if(email.charAt(i) >= 97 && email.charAt(i) <= 123) {
                charcount++;
            }else if(email.charAt(i) >= 48 && email.charAt(i) <= 57) {
                numscount++;
            }else if(email.charAt(i) == ' '){
                space++;
            }else {
                others++;
            }
        }

        if(charcount < 8 || charcount > 20 || numscount < 2 || numscount > 7 || space > 0 || others > 0) {
           return false;
        }

		return true;
	}


	
	
	
	
	// Admin page
	
	// 1. get all movies
	public List<Movies> getlistofmovies() {
		// TODO Auto-generated method stub
		List<Movies> movies = moviesrepo.findAll();
		return movies;
	}


	// 2. deleting a movie

	public boolean deleteMovieMyAdmin(int movieid) {
		// TODO Auto-generated method stub
		Movies movie;
		System.out.println("in admin delete movie");
		
		try {
			movie = moviesrepo.getReferenceById(movieid);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return false;
		}
		
		List<Reviews> reviews = reviewrepo.findByMovie(movie);
		List<WatchLater> watchlaters = watchlaterrepo.findByMovie(movie);
		
		for (WatchLater watchLater : watchlaters) {
			watchlaterrepo.delete(watchLater);
		}
		
		for (Reviews review : reviews) {
			deletereview(review.getId());
		}
		
		DeletedMovies deletedmovie = new DeletedMovies(movie.getMoviename(), movie.getDescription(), movie.getDirector(), movie.getYear(), movie.getRating(), movie.getPath(), movie.getGenre(), movie.getTrailerlink(), movie.getSlidepath());
		deletedmovierepo.save(deletedmovie);
		System.out.println("in delete");
		moviesrepo.delete(movie);
		
		return true;
	}



	// 3. adding a new movie


	public List<String> addNewMovie(String movieName, String description, String director, String year, String rating,
			String path, String genre, String trailerLink, String slidePath) {
		// TODO Auto-generated method stub
		
		if(movieName.length() == 0 || description.length() == 0 || director.length() == 0 || year.length() == 0 || path.length() == 0 || genre.length() == 0 || trailerLink.length() == 0 || slidePath.length() == 0) {
			return Arrays.asList("please don't leave empty");
		}
		
		movieName = movieName.trim();
		description = description.trim();
		director = director.trim();
		year = year.trim();
		rating = rating.trim();
		path = path.trim();
		genre = genre.trim();
		trailerLink = trailerLink.trim();
		slidePath = slidePath.trim();
		
		List<String> message = validatemoviedata(movieName,description,director,year,rating,path,genre,trailerLink,slidePath);
		if(message.size() > 0) return message;
		
		Movies movie = new Movies(movieName, description, director, year, Float.parseFloat(rating), path, genre, trailerLink, slidePath);	
		moviesrepo.save(movie);
		System.out.println("movie added"); 
		
		return message;
	}





	private List<String> validatemoviedata(String movieName, String description, String director, String year, String rating,
			String path, String genre, String trailerLink, String slidePath) {
		// TODO Auto-generated method stub
        List<String> list = new ArrayList<>();
		
		if(!validateMovieName(movieName)) {
			list.add("invalid movie name it should contain chars >= 3 && chars <= 20, no special chars, space <= 3 , nums <= 2.");
		}
        if(!validateDescription(description)) {	
        	list.add("invalid description it should contain chars >= 35 && chars <= 300,nums <= 10,space <= 70,others <= 20.");
		}
        if(!validateYear(year)) {
        	list.add("invalid year it sholud only contain integer and should not contain Whitespaces.");
		}
        
        if(!validateRating(rating)) {
        	list.add("invalid rating it should be <= 10.");
		}
        
        if(!validateDirector(director)) {
        	list.add("invalid director name it should contain chars > 2 && chars <= 25, nums == 0,space < 3,no special chars");
        }
        
        if(!validatePath(path)) {
        	list.add("invalid path it should start with /images and end with .jpg or .jpeg or other image extentions.");
		}
        
        if(!validateGenre(genre)) {
        	list.add("invalid genre it should be either drama,si-fi,documentry,thriller,sitcom,fantacy,action,adult-comedy,romance.");
		}
        
        if(!validateTrailerLink(trailerLink)) {
        	list.add("invalid trailerlink it should be start and end with <iframe and </iframe> respectivly.");
		}
        
        if(!validatePath(slidePath)) {
        	list.add("invalid slidepath it should start with /images/ and end with .jpg or .jpeg or other image extentions.");
		}
        
		return list;
	}





	private boolean validateDirector(String director) {
		// TODO Auto-generated method stub
		director = director.toLowerCase();
        int charcount = 0;
        int numscount = 0;
        int space = 0;
        int others = 0;
        for (int i = 0; i < director.length(); i++) {
            if(director.charAt(i) >= 97 && director.charAt(i) <= 123) {
                charcount++;
            }else if(director.charAt(i) >= 48 && director.charAt(i) <= 57) {
                numscount++;
            }else if(director.charAt(i) == ' '){
                space++;
            }else {
                others++;
            }
        }

        if((charcount < 3 || charcount > 25) || numscount > 0 || space > 3 || others > 0) {
           return false;
        }
		return true;
	}





	private boolean validateDescription(String description) {
		
		description = description.toLowerCase();
        int charcount = 0;
        int numscount = 0;
        int space = 0;
        int others = 0;
        for (int i = 0; i < description.length(); i++) {
            if(description.charAt(i) >= 97 && description.charAt(i) <= 123) {
                charcount++;
            }else if(description.charAt(i) >= 48 && description.charAt(i) <= 57) {
                numscount++;
            }else if(description.charAt(i) == ' '){
                space++;
            }else {
                others++;
            }
        }

        if((charcount < 35 || charcount > 300) || numscount > 10 || space > 70 || others > 20) {
           return false;
        }

		return true;
	}





	private boolean validateYear(String year) {
		// TODO Auto-generated method stub
		int numYear = 0;
		
		try {
			numYear = Integer.parseInt(year);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return false;
		}
		
		System.out.println(numYear);
		
		return true;
	}





	private boolean validatePath(String path) {
		// TODO Auto-generated method stub
		if(path.length() <= 16) return false;
		if((path.substring(path.length() - 4,path.length()).equals(".jpg") || path.substring(path.length() - 5,path.length()).equals(".jpeg")) && path.substring(0,8).equals("/images/")) {
			return true;
		}
		return false;
	}





	private boolean validateTrailerLink(String trailerLink) {
		// TODO Auto-generated method stub
		if(trailerLink.length() < 20) return false;
		if(trailerLink.substring(trailerLink.length() - 9,trailerLink.length()).equals("</iframe>") && trailerLink.substring(0,7).equals("<iframe")) {
			return true;
		}
		return false;
	}





	private boolean validateGenre(String genre) {
		genre = genre.toLowerCase();
		if(genre.equals("fantasy") || genre.equals("action") || genre.equals("thriller") || genre.equals("drama") || genre.equals("adult-comedy") || genre.equals("romcom") || genre.equals("documentry") || genre.equals("si-fi") || genre.equals("romance") || genre.equals("sitcom")) {
			return true;
		}
		return false;
	}





	private boolean validateRating(String rating) {
		// TODO Auto-generated method stub
		try {
			Float.parseFloat(rating);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return false;
		}
		
		if(Float.parseFloat(rating) > 10) {
			return false;
		}
		return true;
	}





	private boolean validateMovieName(String movieName) {
		// TODO Auto-generated method stub
		movieName = movieName.toLowerCase();
        int charcount = 0;
        int numscount = 0;
        int space = 0;
        int others = 0;
        
        for (int i = 0; i < movieName.length(); i++) {
            if(movieName.charAt(i) >= 97 && movieName.charAt(i) <= 123) {
                charcount++;
            }else if(movieName.charAt(i) >= 48 && movieName.charAt(i) <= 57) {
                numscount++;
            }else if(movieName.charAt(i) == ' '){
                space++;
            }else {
                others++;
            }
        }

        if(charcount < 3 || charcount > 20 || numscount > 2 || space > 3 || others > 0) {
           return false;
        }

		return true;
	}
	
	
	@Transactional
	public List<String> updatemoviebyid(String movieid, String column, String updatevalue) {
		// TODO Auto-generated method stub
		List<String> list = validateupdatevalue(movieid,column,updatevalue);
		
		if(!column.equals("moviename") && !column.equals("description") && !column.equals("director") && !column.equals("year") 
				&& !column.equals("rating") && !column.equals("path") && !column.equals("genre") && !column.equals("trailerlink") && !column.equals("slidepath")) {
		   	list.add("add valid column to update");
		}
		
		System.out.println(list);
		
        if(list.size() > 0) return list;
        
        int id = Integer.parseInt(movieid);
        
        
        if(column.equals("moviename")) {
        	moviesrepo.updateMovienameById(id, updatevalue);
        }else if(column.equals("description")) {
        	moviesrepo.updateDescriptionById(id, updatevalue);
        }else if(column.equals("director")) {
        	moviesrepo.updateDirectorById(id, updatevalue);
        }else if(column.equals("year")) {
        	moviesrepo.updateyearById(id, updatevalue);
        }else if(column.equals("rating")) {
        	moviesrepo.updateRatingById(id, Float.parseFloat(updatevalue));
        }else if(column.equals("path")) {
        	moviesrepo.updatePathById(id, updatevalue);
        }else if(column.equals("genre")) {
        	moviesrepo.updateGenreById(id, updatevalue);
        }else if(column.equals("trailerlink")) {
        	moviesrepo.updateTrailerlinkById(id, updatevalue);
        }else if(column.equals("slidepath")) {
        	moviesrepo.updateSlidepathById(id, updatevalue);
        }
		
		return list;
	}


	

	private List<String> validateupdatevalue(String movieid,String column, String updatevalue) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		
		Movies movie = null;
		try {
			movie = moviesrepo.getReferenceById(Integer.parseInt(movieid));
			System.out.println(movie);
		}catch (Exception e) {
			// TODO: handle exception
			list.add(e.getMessage());
			System.out.println(list);
			return list;
		}
		
		
		
		if(column.equals("moviename") && !validateMovieName(updatevalue)) {
			list.add("invalid movie name it should contain chars >= 3 && chars <= 20, no special chars, space <= 3 , nums <= 2.");
		}
        if(column.equals("description") && !validateDescription(updatevalue)) {	
        	list.add("invalid description it should contain chars >= 35 && chars <= 300,nums <= 10,space <= 70,others <= 20.");
		}
        if(column.equals("year") && !validateYear(updatevalue)) {
        	list.add("invalid year it sholud only contain integer and should not contain Whitespaces.");
		}
        
        if(column.equals("director") && !validateDirector(updatevalue)) {
        	list.add("invalid director name it should contain chars > 2 && chars <= 25, nums == 0,space < 3,no special chars");
        }
        
        if(column.equals("rating") && !validateRating(updatevalue)) {
        	list.add("invalid rating it should be <= 10 and should be integer or float.");
		}
        
        if(column.equals("path") && !validatePath(updatevalue)) {
        	list.add("invalid path it should start with /images/ and end with .jpg or .jpeg or other image extentions.");
		}
        
        if(column.equals("genre") && !validateGenre(updatevalue)) {
        	list.add("invalid genre it should be either drama,si-fi,documentry,thriller,sitcom,fantacy,action,adult-comedy,romance.");
		}
        
        if(column.equals("trailerlink") && !validateTrailerLink(updatevalue)) {
        	list.add("invalid trailerlink it should start and end with <iframe and </iframe> respectivly.");
		}
        
        if(column.equals("slidepath") && !validatePath(updatevalue)) {
        	list.add("invalid slidepath it should start with /images/ and end with .jpg or .jpeg or other image extentions.");
		}
        
		return list;
	}





	// deleted movies


	public List<DeletedMovies> getDeletedMovieList() {
		// TODO Auto-generated method stub
		List<DeletedMovies> deletedmovies = deletedmovierepo.findAll();
		return deletedmovies;
	}





	public boolean retrivemovie(int movieId) {
		// TODO Auto-generated method stub
		DeletedMovies dmovie;
		try {
			dmovie = deletedmovierepo.getReferenceById(movieId);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return false;	
		}
		
		Movies movie = new Movies(dmovie.getMoviename(), dmovie.getDescription(), dmovie.getDirector(), dmovie.getYear(), dmovie.getRating(), dmovie.getPath(), dmovie.getGenre(), dmovie.getTrailerlink(), dmovie.getSlidepath());
		moviesrepo.save(movie);	
		deletedmovierepo.delete(dmovie);
		
		return true;
	}





	public Map<String, Object> getadminpageobj() {
		// TODO Auto-generated method stub
		Authentication auth = getCurrentUser();
    	Map<String,Object> map = new HashMap<>();
    	if(!auth.getName().equals("anonymousUser")) {
    		UserModel user = userrepo.findByusername(auth.getName());
    		Roles role = user.getRole();
    		map.put("role", role.ordinal());
    		map.put("superadmin",user.isSuperadmin());
    	}
		return map;
	}





	public List<AuthorizedAdmins> getlistofadmins() {
		// TODO Auto-generated method stub
		System.out.println(authorizedadminrepo.findAll());
		return authorizedadminrepo.findAll();
	}





	public List<String> getaddadmin(String username, String email) {
		// TODO Auto-generated method stub
		List<String> message = validateadmindata(username,email);
		System.out.println(username);
		System.out.println(email);
		System.out.println(message);
		
		if(message.size() > 0) return message;
		
		AuthorizedAdmins admin = new AuthorizedAdmins(username, email);
		
		try {
			authorizedadminrepo.save(admin);
		}catch (Exception e) {
			// TODO: handle exception
			message.add(e.getMessage());
			return message;
		}
		
		return message;
	}





	private List<String> validateadmindata(String username, String email) {
		// TODO Auto-generated method stub
		List<String> message = new ArrayList<>();
		if(username.equals("") || email.equals("")) {
			message.add("don't leave empty");
			return message;
		}
		
		if(!validateEmail(email)) {
			message.add("invalid email");
		}
		
		if(!validateUserName(username)) {
			message.add("invalid username");
		}
		
		
		return message;
	}





	public List<String> getdeleteadmin(int movieid) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		AuthorizedAdmins admin = null;
		try {
			admin = authorizedadminrepo.getReferenceById(movieid);
			System.out.println(admin);
		}catch (Exception e) {
			// TODO: handle exception
			list.add(e.getMessage());
			return list;
		}
		
		authorizedadminrepo.delete(admin);
		
		return list;
	}





	





	


	


	
	
   
}
