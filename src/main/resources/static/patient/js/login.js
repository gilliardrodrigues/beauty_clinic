document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault(); 
  
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
  
    fetch('http://localhost:8080/patients/authenticate', {
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
      localStorage.setItem('token', data.jwtToken);
      window.location.href = 'dashboard.html';
    })
    .catch(function(error) {
      document.getElementById("message").innerHTML = error.message;
    });
  });
  