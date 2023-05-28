function fetchData() {

    const token = localStorage.getItem('token');

    if(token){
        fetch('http://localhost:8080/appointments', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => response.json())
        .then(data => {
            // Call a function to insert the data into the table
            insertDataIntoTable(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
    else
        window.location.href = 'login.html'

}

function insertDataIntoTable(data) {
    const tableBody = document.getElementById('tableBody');
  
    // Clear existing table rows if any
    tableBody.innerHTML = '';
  
    // Iterate over the data and create table rows
    data.forEach(item => {
      const row = document.createElement('tr');
      const dateCell = document.createElement('td');
      const serviceCell = document.createElement('td');
      const statusCell = document.createElement('td');
      const patientCell = document.createElement('td');
      const actionsCell = document.createElement('td');
  
      dateCell.textContent = new Date(item.dateTime).toLocaleString();
      serviceCell.textContent = item.service;
      statusCell.textContent = item.status;
      patientCell.textContent = item.patientFullName;
      console.log(item.status)
      if(item.status == "Requested"){
        
          actionsCell.innerHTML = `
          <button class="action-btn accept-appointment-btn" onclick="acceptAppointment(${item.id})">Accept</button>
          <button class="action-btn logout-btn" onclick="rejectAppointment(${item.id})">Reject</button>
          `
      } else {
        actionsCell.textContent = '-'
      } 
  
      row.appendChild(dateCell);
      row.appendChild(serviceCell);
      row.appendChild(patientCell);
      row.appendChild(statusCell);
      row.appendChild(actionsCell);
  
      tableBody.appendChild(row);
    });
  }


function acceptAppointment(appointment_id) {
    
    const confirmation = confirm("Are you sure you want to accept this appointment?")

    if(!confirmation) return;

    const token = localStorage.getItem('token');

    var myHeaders = new Headers();
    myHeaders.append("Authorization", `Bearer ${token}`);

    var requestOptions = {
        method: 'PUT',
        headers: myHeaders,
        redirect: 'follow'
    };

    fetch(`http://localhost:8080/appointments/${appointment_id}/accept`, requestOptions)
    .then(response => response.text())
    .then(result => 
            window.location.reload()
        )
    .catch(error => console.log('error', error));
}

function rejectAppointment(appointment_id) {
    
    const confirmation = confirm("Are you sure you want to accept this appointment?")

    if(!confirmation) return;

    const token = localStorage.getItem('token');

    var myHeaders = new Headers();
    myHeaders.append("Authorization", `Bearer ${token}`);

    var requestOptions = {
        method: 'PUT',
        headers: myHeaders,
        redirect: 'follow'
    };

    fetch(`http://localhost:8080/appointments/${appointment_id}/refuse`, requestOptions)
    .then(response => response.text())
    .then(result => 
            window.location.reload()
        )
    .catch(error => console.log('error', error));
}