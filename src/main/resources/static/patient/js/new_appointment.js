
function checkLogged() {
    const token = localStorage.getItem('token');
    if(!token) window.location.href = 'login.html'
}

document.getElementById("searchForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent form submission

    const service =  document.getElementById("offeredServices").value;
    // var date =  document.getElementById("desiredDate").value;
    const token = localStorage.getItem('token');

    fetch(`http://localhost:8080/professionals/filter/by-service/${service}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    })
    .then(function(response) {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('Filter request failed.');
      }
    })
    .then(function(data) {
      // Authentication successful, perform necessary actions
      // For example, store the token in local storage and redirect to another page
      if(data.length == 0){
        document.getElementById("message").innerHTML = 'No professionals were found';
        document.getElementById('professional-list').innerHTML = '';
      }
      else
        insertInList(data);
    })
    .catch(function(error) {
      // Authentication failed, display an error message
      document.getElementById("message").innerHTML = error.message;
    });
})

function insertInList(data){
    const list = document.getElementById('professional-list');
  
    list.innerHTML = '';
    document.getElementById("message").innerHTML = '';
  
    // Iterate over the data and create table rows
    data.forEach(item => {
      const dataToInsert = `
        <a href="#" onclick="createNewAppointment(${item.id})">
            <div class="professional-item">
                <p><strong>Name:</strong> ${item.fullName}</p>
                <p><strong>Price:</strong> R$ ${item.consultationPrice}</p>
                <p><strong>Email:</strong> ${item.email}</p>
            </div>
        </a>
      `
  
      list.insertAdjacentHTML(`beforeend`,dataToInsert);
    });
}

function createNewAppointment(professional_id) {
    const confirmation = confirm('Are you sure you want schedule this appointment?');

    const service =  document.getElementById("offeredServices").value;
    const date =  document.getElementById("desiredDate").value;
    const token = localStorage.getItem('token');

    if(confirmation){
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Authorization", `Bearer ${token}`);

        var raw = JSON.stringify({
            "professionalId": professional_id,
            "dateTime": new Date(date).toISOString(),
            "service": service
        });

        var requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch("http://localhost:8080/appointments", requestOptions)
        .then(function(response) {
            if (response.ok) {
              return response.json();
            } else {
              throw new Error('New appointment request failed. ');
            }
          })
          .then(function(data) {
            window.location.href = `dashboard.html`;
          })
          .catch(function(error) {
            // Authentication failed, display an error message
            document.getElementById("message").innerHTML = error.message;
          });
    }
}