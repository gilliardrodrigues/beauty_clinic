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
      const actionsCell = document.createElement('td');
  
      dateCell.textContent =  new Date(item.dateTime).toLocaleString();
      serviceCell.textContent = item.service;
      statusCell.textContent = item.status;
      professionalCell.textContent = item.professionalFullName;
      actionsCell.innerHTML = `
        <button class="button is-info" onclick="downloadAppointment(${item.id})">Download Details</button>
        `
      fetch(`http://localhost:8080/appointments/${item.id}/download`)
        .then(response => response.blob())
        .then(blob => {
            // Step 2: Handle the API response

            // Step 3: Create a URL for the Blob
            const url = URL.createObjectURL(blob);

            

            // Clean up the URL object
            URL.revokeObjectURL(url);
      });

    
  
      row.appendChild(dateCell);
      row.appendChild(serviceCell);
      row.appendChild(professionalCell);
      row.appendChild(statusCell);
      row.appendChild(actionsCell);
  
      tableBody.appendChild(row);
    });
  }

  function downloadAppointment(id) {
    const token = localStorage.getItem('token');
    fetch(`http://localhost:8080/appointments/${id}/download`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => response.arrayBuffer())
    .then(data => {
        // Step 2: Handle the API response

        // Step 3: Create a Blob from the array buffer data
        const blob = new Blob([data], { type: 'application/pdf' });

        // Step 4: Create a URL for the Blob
        const url = URL.createObjectURL(blob);

        // Step 5: Create a link element
        const link = document.createElement('a');

        // Step 6: Set the link's attributes
        link.href = url;
        link.download = 'detalhes.pdf';

        // Step 7: Append the link to the HTML document
        document.body.appendChild(link);

        // Step 8: Trigger the download
        link.click();

        // Step 9: Clean up the URL object
        URL.revokeObjectURL(url);
    });
  }