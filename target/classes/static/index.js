// Fetch data using Axios when the page is loaded
window.addEventListener('load', function () {
  axios.get('/api/task')
    .then(function (response) {
      // Check if the response is successful and contains data
      if (response.status === 200 && response.data) {
        var data = response.data; // Assuming your data is an array of objects
        populateTable(data);
      } else {
        console.error('Failed to fetch data');
      }
    })
    .catch(function (error) {
      console.error('An error occurred while fetching data:', error);
    });
});

function populateTable(data) {
  // Get the template row
  var templateRow = document.querySelector('template').content.querySelector('tr');

  // Get the table body where you want to append rows
  var tableBody = document.querySelector('tbody');

  let dataArray = "";

  // Loop through the data and populate the table
  data.forEach(function (item) {
    // Clone the template row
    var newRow = templateRow.cloneNode(true);

    // Populate the cloned row with data
    newRow.querySelector('.task-title-link').textContent = item.title;
    newRow.querySelector('.task-description').textContent = item.description;

    newRow.querySelector('.task-status-dropdown').textContent = item.status;

    newRow.querySelector('.task-due-date').textContent = new Date(item.dueDateTime).toISOString().slice(0, 16);;

    newRow.querySelector('.view-link').setAttribute("href", `viewTask.html?id=${item.id}`);
    newRow.querySelector('.edit-link').setAttribute("href", `editTask.html?id=${item.id}`);
    newRow.querySelector('.delete-link').setAttribute("data-id", `${item.id}`);

    dataArray += newRow.outerHTML;
  });

  // Populate the table body with the rows
  tableBody.innerHTML = dataArray;

  // Attach delete event listeners
  document.querySelectorAll('.delete-link').forEach(function (link) {
    link.addEventListener('click', function () {
      var taskId = this.getAttribute('data-id');
      handleDeleteClick(taskId);
    });
  });

  // Handle status dropdown change
  document.querySelectorAll('.task-status-dropdown').forEach(function (dropdown) {
    dropdown.addEventListener('change', function () {
      var taskId = this.getAttribute('data-task-id');
      var newStatus = this.value;

      // Update status via REST API
      fetch(`https://api.example.com/tasks/${taskId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ status: newStatus })
      })
        .then(response => {
          if (!response.ok) {
            console.error('Failed to update status');
          }
        })
        .catch(error => console.error('Error updating status:', error));
    });
  });
}

// Function to handle delete link click
function handleDeleteClick(id) {
  Swal.fire({
    title: 'Are you sure?',
    text: "You won't be able to revert this!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Yes, delete it!'
  }).then((result) => {
    if (result.isConfirmed) {
      axios.delete(`/api/task/${id}`)
        .then(function (response) {
          if (response.status === 200) {
            window.location.reload()
          } else {
            console.error('Failed to delete product');
          }
        })
        .catch(function (error) {
          console.error('An error occurred while deleting product:', error);
        });
    }
  })
}

document.getElementById('statusDropdown').addEventListener('change', function () {
  const selectedValue = this.value;
  console.log('Selected status:', selectedValue);
  axios.get(`/api/task/filter-by-status?status=${selectedValue}`)
    .then(function (response) {
      var templateRow = document.querySelector('template').content.querySelector('tr');
      document.querySelector('tbody').innerHTML="";
      document.createElement('tbody').innerHTML=templateRow;
      // Check if the response is successful and contains data
      if (response.status === 200 && response.data) { 
        var data = response.data; // Assuming your data is an array of objects
        populateTable(data);
      } else {
        console.error('Failed to fetch data');
      }
    })
    .catch(function (error) {
      console.error('An error occurred while fetching data:', error);
    });
});
