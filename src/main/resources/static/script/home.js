
let slideIndex = 1; // Start with the first slide
    
        function showSlide(n) {
            const slides = document.querySelectorAll(".movie-slide");
    
            if (n > slides.length) {
                slideIndex = 1;
            }
            if (n < 1) {
                slideIndex = slides.length;
            }
    
            // Hide all slides
            slides.forEach((slide) => {
                slide.style.display = "none";
            });
    
            // Show the current slide
            slides[slideIndex - 1].style.display = "block";
        }
    
        function nextSlide() {
            showSlide((slideIndex += 1));
        }
    
        function prevSlide() {
            showSlide((slideIndex -= 1));
        }
    
        // Show the first slide initially
        showSlide(slideIndex);
        
// JavaScript code for sending AJAX request when Watch Later button is clicked

function addtowatchlater(movieName) {
    // Create a new XMLHttpRequest object
    var xhr = new XMLHttpRequest();

    // Set the request method and URL
    xhr.open("POST", "/watchlater/addwl", true);

    // Set the request headers, including the movie name as an attribute
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("moviename", movieName);

    // Set up the callback function to handle the response
    xhr.onload = function () {
        if (xhr.status === 200) {
			if(xhr.responseText === "wl submitted successfully") {
				alert("watchlater added");
			}else {
				console.log("in redirect");
				console.log(xhr.responseText);
				window.location.assign("/login");
			}
        } else {
            alert("failure");
        }
    };

    // Handle potential network errors
    xhr.onerror = function () {
        alert("Network error occurred");
    };

    // Send the AJAX request
    xhr.send();
}

// Attach a click event listener to the "Watch Later" button
var watchLaterButtons = document.querySelectorAll('.watch-later');
watchLaterButtons.forEach(function(button) {
    button.addEventListener('click', function() {
        // Get the movie name from the adjacent h3 element
        var movieName = this.parentElement.querySelector('.movie-title').innerText;
        
        // Call the addtowatchlater function with the movie name
        addtowatchlater(movieName);                    
    });
});


        
       