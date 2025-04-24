function toggleForm() {

    const form = document.getElementById('taskForm');

    if (form.style.display === "block") {
        form.style.display = 'none';
    } else {
        form.style.display="block";
    }
}