document.getElementById("registerForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent form submission
  
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var fullName = document.getElementById("fullName").value;
    var gender = document.getElementById("gender").value;
    var birthdate = document.getElementById("birthdate").value;

    var street = document.getElementById("street").value;
    var houseNumber = document.getElementById("houseNumber").value;
    var neighborhood = document.getElementById("neighborhood").value;
    var city = document.getElementById("city").value;
    var state = document.getElementById("state").value;

    var countryCode = document.getElementById("countryCode").value;
    var areaCode = document.getElementById("areaCode").value;
    var number = document.getElementById("number").value;

  
    // Make a request to the external API for authentication
    fetch('patients/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        email: email,
        password: password,
        fullName: fullName,
        gender: gender,
        birthdate: birthdate,
        address: {
            street: street,
            houseNumber: houseNumber,
            neighborhood: neighborhood,
            city: city,
            state: state,
            country: "Brazil"
        },
        phoneNumber: {
            countryCode: countryCode,
            areaCode: areaCode,
            number: number
        }
      })
    })
    .then(function(response) {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('Registration failed.');
      }
    })
    .then(function(data) {
      // Authentication successful, perform necessary actions
      // For example, store the token in local storage and redirect to another page

      window.location.href = 'login.html';
    })
    .catch(function(error) {
      // Authentication failed, display an error message
      document.getElementById("message").innerHTML = error.message;
    });
  });
  