document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent form submission
  
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
  
    // Make a request to the external API for authentication
    // Replace 'external-api-url' with the actual URL of the API
    fetch('external-api-url/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        email: email,
        password: password
      })
    })
    .then(function(response) {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('Authentication failed.');
      }
    })
    .then(function(data) {
      // Authentication successful, perform necessary actions
      // For example, store the token in local storage and redirect to another page
      localStorage.setItem('token', data.token);
      window.location.href = 'dashboard.html';
    })
    .catch(function(error) {
      // Authentication failed, display an error message
      document.getElementById("message").innerHTML = error.message;
    });
  });
  