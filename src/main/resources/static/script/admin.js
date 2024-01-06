function fetchMovieDetails() {
        // TODO: Perform an AJAX request to get a list of movies from the server.
        // Example using Fetch API:
        fetch('/admin/getmovies')
        .then(response => response.json())
        .then(data => {
            // Handle the response from the server
            console.log(data);

            // Display movies in the movie list div
            const movieListDiv = document.getElementById('movieList');
            movieListDiv.innerHTML = ''; // Clear previous content

            data.forEach(movie => {
                const listItem = document.createElement('li');
                listItem.textContent = `${movie.id} - ${movie.moviename} - ${movie.director} - ${movie.rating} - ${movie.year}`;
                movieListDiv.appendChild(listItem);
            });

            // Show the movie details div
            document.getElementById('movieDetailsDiv').style.display = 'block';
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
 
 function deleteMovie() {
        var movieId = document.getElementById('deleteMovieId').value;
        // TODO: Perform an AJAX request to send the movieId to the server for deletion.
        // Example using Fetch API:
        fetch('/admin/delete-movie', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({movieId: movieId }),
        })
        .then(response => {
		console.log(response);	
        return response.text();
        })
        .then(data => {
            // Handle the response from the server
            alert(data);
            console.log(data);
        })
        .catch(error => {
			alert(error);
            console.error('Error:', error);
        });
    }

    async function addMovie() {
        // Collect movie details from the input fields
        var movieDetails = {
            movieName: document.getElementById('movieName').value,
            description: document.getElementById('description').value,
            director: document.getElementById('director').value,
            year: document.getElementById('year').value,
            rating: document.getElementById('rating').value,
            path: document.getElementById('path').value,
            genre: document.getElementById('genre').value,
            trailerLink: document.getElementById('trailerLink').value,
            slidePath: document.getElementById('slidePath').value,
        };
        
        const condiv = document.getElementById("constdiv");
        condiv.innerHTML = '';
        var count = 1;

        // TODO: Perform an AJAX request to send the movie details to the server for addition.
        // Example using Fetch API:
        
	   fetch('/admin/add-movie', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(movieDetails),
        }).then(response => {
    if (response.ok) {
        // Success: Movie added
        alert("Movie added successfully");
    } else {
        // Error: Display the error message
        return response.json();
    }
})
.then(data => {
    data.forEach(con => {
                const no = document.createElement('strong');
                const constrient = document.createElement('p');
                const newline = document.createElement('br');
                no.innerText = count + " : ";
                constrient.innerText = con;
                constrient.style.display = 'inline-block';
                constrient.style.color = 'red';
                constrient.style.margin = 2;
                condiv.appendChild(no);
                condiv.appendChild(constrient);
                condiv.appendChild(newline);
                count++;
            });
            condiv.style.display = 'block';
})
.catch(error => {
    // Handle network errors or other issues
    console.error('Error:', error);
});
        
}


function showDeletedMovies() {
	
	fetch('/admin/show-deletedmovies')
        .then(response => response.json())
        .then(data => {
            // Handle the response from the server
            console.log(data);

            // Display movies in the movie list div
            const movieListDiv = document.getElementById('deletedmovieList');
            movieListDiv.innerHTML = ''; // Clear previous content

            data.forEach(deletedmovie => {
                const listItem = document.createElement('li');
                listItem.textContent = `${deletedmovie.id} - ${deletedmovie.moviename} - ${deletedmovie.director} - ${deletedmovie.rating} - ${deletedmovie.year}`;
                movieListDiv.appendChild(listItem);
            });

            // Show the movie details div
            document.getElementById('deletedmovieDiv').style.display = 'block';
        })
        .catch(error => {
            console.error('Error:', error);
        });
	
}


function retriveMovie() {
	
	 var deletedmovieId = document.getElementById('deletedMovieId').value;
        // TODO: Perform an AJAX request to send the movieId to the server for deletion.
        // Example using Fetch API:
        fetch('/admin/retrive-movie', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({deletedmovieId: deletedmovieId }),
        })
        .then(response => response.text())
        .then(data => {
            // Handle the response from the server
            alert(data);
            console.log(data);
        })
        .catch(error => {
			alert(error);
            console.error('Error:', error);
        });
	
}


function updatemoviebyid() {
	
	var updatemovieid = document.getElementById('updatemovieid').value;
	var selectedoption = document.getElementById('optionselected').value;
	var updatevalue = document.getElementById('updatevalue').value;
	
	fetch('/admin/update-movie', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
				                  updatemovieid: updatemovieid,
				                  selectedoption : selectedoption,
				                  updatevalue : updatevalue 
				                  }),
        })
        .then(response => {
		        if(response.ok) {
				   alert("movie updated");
				}else {
				   return response.json();
				}
        })
        .then(data => {
            data.forEach(con => {
                alert(con);
            });
        })
        .catch(error => {
			alert(error);
            console.error('Error:', error);
        });
	
}


function showadmins() {
	
	const showadmindiv = document.getElementById('showadminvalues');
	showadmindiv.innerHTML = '';
	
	fetch('/admin/show-admins')
        .then(response => response.json())
        .then(data => {
            data.forEach(admin => {
                const listitem = document.createElement('li');
                listitem.textContent = `${admin.id} - ${admin.username} - ${admin.email}`;
                showadmindiv.appendChild(listitem);
            });
        })
        .catch(error => {
			alert(error);
            console.error('Error:', error);
        });
}


function addadmin() {
	var username = document.getElementById('adminusernamevalue').value;
	var email = document.getElementById('adminemailvalue').value;
	
	fetch('/admin/add-admin', {
		 method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
				                  username: username,
				                  email : email
				                  }),
	}).then(response => response.json())
	.then(data => {
		 data.forEach(message => {
                alert(message);
            });
	}).catch(error => {
			alert(error);
            console.error('Error:', error);
        });
        
        
}


function deleteadmin() {
	
	var movieid = document.getElementById('adminid').value;
	
	fetch('/admin/delete-admin', {
		 method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
				                  movieid : movieid
				                  }),
	}).then(response => response.json())
	.then(data => {
		 data.forEach(message => {
                alert(message);
            });
	}).catch(error => {
			alert(error);
            console.error('Error:', error);
        });
	
}



