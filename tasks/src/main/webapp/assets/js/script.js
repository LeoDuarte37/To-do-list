async function toggleDone(id, done) {

    const url = "http://localhost:8080/todolist/task";

    const response = await fetch(
        url.concat("?id=", id, "&done=", !done),
        {
            method: "PUT"
        }
    );

    if (response.ok) {
        window.location.href = url;
    } else {
        window.alert("Ops! Algo deu errado. Por favor, reinicie a página e tente novamente.");
        window.location.href = url;
    }
}