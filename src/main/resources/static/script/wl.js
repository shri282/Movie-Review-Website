var removeButtons = document.querySelectorAll('.removebutton');
    removeButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            // Get the watchlater ID from the button's data-wl-id attribute
            var wlId = this.getAttribute('data-wl-id');
            
            // Call the function to remove the watchlater object
            removeWatchLater(wlId);
        });
    });

    // Function to send an AJAX request to remove the watchlater object
    function removeWatchLater(wlId) {
        // Create a new XMLHttpRequest object
        var xhr = new XMLHttpRequest();

        // Set the request method and URL
        xhr.open("DELETE", "/watchlater/remove-wl", true);

        // Set the request headers, including the watchlater ID as an attribute
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.setRequestHeader("wlId", wlId);

        // Set up the callback function to handle the response
        xhr.onload = function () {
        if (xhr.status === 200) {
            alert("deleted");
            location.reload();
            console.log(xhr.responseText);
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