
document.addEventListener('DOMContentLoaded', function() {
  const starRating = document.querySelector('.star-rating');
  const submitButton = document.querySelector('.comment-box button');
  const moviename = document.querySelector('.movie-name').innerText;

  starRating.addEventListener('change', function() {
    // Collect the rating data here
    const rating = document.querySelector('input[name="rating"]:checked');
    console.log('Selected Rating:', rating ? rating.value : 'No rating selected');
    submitButton.disabled = !rating;
  });

  submitButton.addEventListener('click', function() {
    const rating = document.querySelector('input[name="rating"]:checked');
    const comment = document.querySelector('textarea[name="comment"]').value;

    // Create an object with the data to be sent
    const data = {
	  moviename : moviename,
      rating: rating ? rating.value : null,
      comment: comment
    };

    // Make a POST request to the backend endpoint
    fetch('/reviews/post/submit-review', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    })
    .then(response => {
      return response.json();
    })
    .then(data => {
      // Handle the response from the server
      data.forEach(message => {
		  if(message === "you are removed from our website") {
			  alert(message);
			  window.location.assign("/");
			  return;
		  }
		  alert(message);
	  });
      location.reload(); 
      console.log('Success:', data);
    })
    .catch(error => {
      // Handle errors during the fetch
      alert(error);
      console.error('Error:', error);
    });
  });
});






function deleteReview(reviewId) {
    if (confirm("Are you sure you want to delete this review?")) {
        // Assuming you are using AJAX with jQuery
        $.ajax({
            type: 'DELETE',
            url: '/reviews/delete/' + reviewId, // Replace with your actual endpoint
            
            success: function (data) {
                // Assuming a successful response means the review was deleted
                // You may want to add additional checks based on your backend response
                alert(data);
                // Optionally, you can reload the page or update the UI without a page reload
                $('#review_' + reviewId).remove(); // Reloads the page
            },
            error: function (error) {
                // Handle errors, display an alert, or perform other actions
                alert(error);
                console.error("Error deleting review:", error);
            }
        });
    }
}




// Event delegation for .toggle-reply-form
document.body.addEventListener('click', function(event) {
    if (event.target.classList.contains('toggle-reply-form')) {
        // Find the associated reply form
        var replyForm = event.target.parentElement.querySelector('.reply-form');

        // Toggle the visibility of the reply form
        if (replyForm.style.display === 'none' || replyForm.style.display === '') {
            replyForm.style.display = 'block';
        } else {
            replyForm.style.display = 'none';
        }
    }
});


// Event delegation for .submitreply
document.body.addEventListener('click', function(event) {
    if (event.target.classList.contains('submitreply')) {
        // Find the associated reply form and input fields
        var replyForm = event.target.parentElement;
        var reviewId = replyForm.querySelector('input[name="reviewId"]').value;
        var moviename = replyForm.querySelector('input[name="moviename"]').value;
        var replyText = replyForm.querySelector('input[name="replyText"]').value;

        // Send an Ajax request to submit the reply
        // Adjust the URL and data as per your backend API
        $.ajax({
            type: 'POST',
            url: '/submitReply', // Adjust the URL
            data: {
                reviewId: reviewId,
                replyText: replyText,
                moviename: moviename
            },
            success: function(response) {
                // Handle success, e.g., update the UI with the new reply
                alert("reply added");
                location.reload();
                console.log(response);
            },
            error: function(error) {
                // Handle error
                alert("failed")
                console.error('Error submitting reply:', error);
            }
        });

        // Optionally, hide the reply form after submission
        replyForm.style.display = 'none';
    }
});
