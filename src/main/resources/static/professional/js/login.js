function checkLogged() {
  const token = localStorage.getItem('professionalToken');
  if(token) window.location.href = 'dashboard.html'
}

document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent form submission
  
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
  
    // Make a request to the external API for authentication
    fetch('http://localhost:8080/professionals/authenticate', {
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
      localStorage.setItem('professionalToken', data.jwtToken);
      window.location.href = 'dashboard.html';
    })
    .catch(function(error) {
      // Authentication failed, display an error message
      document.getElementById("message").innerHTML = error.message;
    });
  });
  