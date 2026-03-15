const apiUrl = "http://localhost:8080/api/jobs";

// DOM elements
const companyInput = document.getElementById("company");
const positionInput = document.getElementById("position");
const statusInput = document.getElementById("status");
const addJobBtn = document.getElementById("addJobBtn");
const jobsTableBody = document.querySelector("#jobsTable tbody");

// Load jobs on page load
window.onload = fetchJobs;

// Add job
addJobBtn.addEventListener("click", () => {
    const job = {
        company: companyInput.value,
        position: positionInput.value,
        status: statusInput.value
    };

    fetch(apiUrl, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(job)
    })
    .then(res => res.json())
    .then(() => {
        companyInput.value = "";
        positionInput.value = "";
        statusInput.value = "";
        fetchJobs();
    });
});

// Fetch all jobs
function fetchJobs() {
    fetch(apiUrl)
        .then(res => res.json())
        .then(data => {
            jobsTableBody.innerHTML = "";
            data.forEach(job => {
                const row = document.createElement("tr");

                const statusClass = `status-${job.status.toLowerCase()}`;

                row.innerHTML = `
                    <td>${job.id}</td>
                    <td>${job.company}</td>
                    <td>${job.position}</td>
                    <td class="${statusClass}">${job.status}</td>
                    <td>
                        <button onclick="editJob(${job.id})">Edit</button>
                        <button class="delete-btn" onclick="deleteJob(${job.id})">Delete</button>
                    </td>
                `;

                jobsTableBody.appendChild(row);
            });
        });
}

// Delete job
function deleteJob(id) {
    fetch(`${apiUrl}/${id}`, { method: "DELETE" })
        .then(() => fetchJobs());
}

// Update job (simple prompt-based)
function updateJob(id) {
    const company = prompt("Enter new company:");
    const position = prompt("Enter new position:");
    const status = prompt("Enter new status:");

    const updatedJob = { company, position, status };

    fetch(`${apiUrl}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updatedJob)
    })
    .then(() => fetchJobs());
}

// Edit job
function editJob(id) {

    const company = prompt("Update company:");
    const position = prompt("Update position:");
    const status = prompt("Update status (Applied / Interview / Rejected / Offer):");

    const updatedJob = {
        company: company,
        position: position,
        status: status
    };

    fetch(`${apiUrl}/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(updatedJob)
    })
    .then(() => fetchJobs());
}