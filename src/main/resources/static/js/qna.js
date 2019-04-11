function $(selector) {
    return document.querySelector(selector);
}

document.addEventListener("DOMContentLoaded", () => {
    initEvents();
})

function initEvents() {
    console.log("initEvents실행");
    const answerBtn = document.querySelector(".submit-write .btn");
    const answerDeleteBtn = document.querySelectorAll(".delete-answer-button");
    console.log(answerBtn)
    console.log(answerDeleteBtn)
    if (answerBtn === null) return;
    answerBtn.addEventListener("click", registerAnswerHandler);
    answerDeleteBtn.forEach(function (delBtn) {
        delBtn.addEventListener('click', deleteAnswerHandler)
    })
}

function fetchManager({url, method, body, headers, callback}) {
    fetch(url, {method, body, headers, credentials: "same-origin"})
        .then((response) => {
            console.log(response.body)
            return response.json()
        }).then((result) => {
        callback(result)
    })
}

function deleteAnswerHandler(evt) {
    console.log(evt)
    evt.preventDefault();
    const id = document.querySelector(".article-answer").getAttribute('data-id');
    console.log(id);

    fetchManager({
        url: '/api' + location.pathname + '/answers/' + id,
        method: 'DELETE',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({'id': id}),
        callback: deleteAnswer
    })

}

function deleteAnswer({answerId}) {
    window.location.reload();
    const target = document.querySelector(`.article-answer[data-id='${answerId}']`);
    console.log(target)

    try {
        target.parentNode.removeChild(target);
    } catch (e) {
        alert("delete is failed")
    }
}

function registerAnswerHandler(evt) {
    console.log("핸들러 실행");
    console.log(evt)
    evt.preventDefault();
    const contents = document.querySelector(".submit-write textarea").value;
    document.querySelectorAll(".form-control")[1].value = "";
    console.log(contents);
    fetchManager({
        url: '/api' + location.pathname + '/answers',
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({'contents': contents}),
        callback: appendAnswer
    })
}

function appendAnswer({id, contents, writer, time, url}) {
    window.location.reload();
    console.log(id)
    console.log(contents)
    console.log(writer)
    console.log(time)
    const html = `
        <article class="article-answer" data-id="${id}">
            <div class="article-header">
                <div class="article-header-thumb">
                    <img src="https://graph.facebookcom/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                </div>
                <div class="article-header-text">
                    <a href="#" class="article-author-name">${writer.name}</a>
                    <div class="article-header-time"> ${time}</div>
                </div>
            </div>
            <div class="article-doc comment-doc">
                ${contents}
            </div>
            <div class="article-util">
            <ul class="article-util-list">
                <li>
                    <a class="link-modify-article" href="${url}/${id}">수정</a>
                </li>
                <li>
                    <form class="delete-answer-form" action="${url}/${id}" method="POST">
                        <input type="hidden" name="_method" value="DELETE">
                        <button type="submit" class="delete-answer-button">삭제</input>
                    </form>
                </li>
            </ul>
            </div>
        </article> `

    document.querySelector(".qna-comment-slipp-articles").insertAdjacentHTML("afterbegin", html);
}