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
      const professionalCell = document.createElement('td');
  
      dateCell.textContent =  new Date(item.dateTime).toLocaleString();
      serviceCell.textContent = item.service;
      statusCell.textContent = item.status;
      professionalCell.textContent = item.professionalFullName;
  
      row.appendChild(dateCell);
      row.appendChild(serviceCell);
      row.appendChild(professionalCell);
      row.appendChild(statusCell);
  
      tableBody.appendChild(row);
    });
  }