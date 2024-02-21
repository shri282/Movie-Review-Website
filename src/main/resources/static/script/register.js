
    $(document).ready(function() {
        $("#registerform").submit(function(event) {
            event.preventDefault();
            
             var formData = {
                             username: $("#username").val(),
                             password: $("#password").val(),
                             email: $("#email").val(),
                             firstname: $("#firstname").val(),
                             lastname: $("#lastname").val(),
                             role: $("#role").val()
                   
                             };
            
            $.ajax({
                type: "POST",
                url: "/register-user",
                data: formData,
                success: function(response) { 
                        // Redirect to login page or perform any other action
                        console.log(response);
                        window.location.href = "/login";
                 
                },
                error: function(response) {
                     if (Array.isArray(response.responseJSON)) {
                    // Iterate over the errors array
                        response.responseJSON.forEach(function(error) {
                        alert(error);
                     });
                     } else {
                     // Handle non-array response
                     alert(response.responseJSON);
                     }
                  
                }
            });
        });
    });

