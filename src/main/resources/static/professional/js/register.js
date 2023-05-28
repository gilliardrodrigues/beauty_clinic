document.getElementById("registerForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent form submission
  
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var fullName = document.getElementById("fullName").value;

    var street = document.getElementById("street").value;
    var houseNumber = document.getElementById("houseNumber").value;
    var neighborhood = document.getElementById("neighborhood").value;
    var city = document.getElementById("city").value;
    var state = document.getElementById("state").value;

    var countryCode = document.getElementById("countryCode").value;
    var areaCode = document.getElementById("areaCode").value;
    var number = document.getElementById("number").value;

    var summary = document.getElementById("summary").value;
    var offeredServices = Array.from(document.getElementById("offeredServices").selectedOptions).map(option => option.value);
    var consultationPrice = document.getElementById("consultationPrice").value;

    fetch('http://localhost:8080/professionals/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: email,
        password: password,
        fullName: fullName,
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
        },
        consultationPrice: consultationPrice,
        summary: summary,
        offeredServices: offeredServices
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
      window.location.href = 'login.html';
    })
    .catch(function(error) {
      document.getElementById("message").innerHTML = error.message;
    });
  });
  

function clearFields() {

  document.getElementById('street').value=("");
  document.getElementById('neighborhood').value=("");
  document.getElementById('city').value=("");
  document.getElementById('state').value=("");

}

function my_callback(response) {
if (!("erro" in response)) {

    document.getElementById('street').value=(response.logradouro);
    document.getElementById('neighborhood').value=(response.bairro);
    document.getElementById('city').value=(response.localidade);
    document.getElementById('state').value=(response.uf);
} 
else {
    clearFields();
    alert("CEP not found.");
}
}

function searchAddressByCep(cep){

  var cep = cep.replace(/\D/g, '');

  if (cep != "") {

      var validacep = /^[0-9]{8}$/;

      if(validacep.test(cep)) {

          document.getElementById('street').value="...";
          document.getElementById('neighborhood').value="...";
          document.getElementById('city').value="...";
          document.getElementById('state').value="...";

          var script = document.createElement('script');

          script.src = 'https://viacep.com.br/ws/'+ cep + '/json/?callback=my_callback';

          document.body.appendChild(script);

      }
      else {
          clearFields();
          alert("Invalid format CEP.");
      }
  } 
  else {
      clearFields();
  }
};