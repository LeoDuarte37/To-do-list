const url = "http://localhost:8080/todolist/task"

function verifyResponse(ok) {
    if (ok) {
        window.location.href = url;
    } else {
        window.alert("Ops! Algo deu errado. Por favor, reinicie a página e tente novamente.");
        window.location.href = url;
    }
}

async function toggleDone(id, done) {

    const response = await fetch(
        url.concat("?id=", id, "&done=", !done),
        {
            method: "PUT"
        }
    );

    verifyResponse(response.ok);
}

async function deleteTask(id) {

    const response = await fetch(
        url.concat("?id=", id),
        {
            method: "DELETE"
        }
    );

    verifyResponse(response.ok);
}

