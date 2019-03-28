function ajax(data) {
    var oReq = new XMLHttpRequest();
    oReq.addEventListener("load", function () {
        console.log(this.responseText);
    });
    oReq.open("GET", "http://www.example.org/getData?data=data");//parameter를 붙여서 보낼수있음.
    oReq.send();
}

function $(selector) {
    return document.querySelector(selector);
}

document.addEventListener("DOMContentLoaded", () => {
    initEvents();
})

function initEvents() {
    const answerBtn = document.getElementById("answerButton");
    if (answerBtn === null) return;
    console.log("1번");
    answerBtn.addEventListener("click", registerAnswerHandler);
}

function fetchManager({url, method, body, headers, callback}) {
    fetch(url, {method, body, headers, credentials: "same-origin"})
        .then((response) => {
            return response.json()
        }).then((result) => {
        callback(result)
    })
}

function registerAnswerHandler(evt) {
    console.log("2번");
    evt.preventDefault();
    const content = document.getElementById("content").value;
    console.log(content);
    $(".form-control").value = "";
    const id = location.pathname;
    fetchManager({
        url: `/api${id}/answers`,
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({content}),
        callback: appendAnswer
    })
}

function appendAnswer({id, question, writer, content}) {
    const html = `
        <article class="article">
            <div class="article-header">
                <div class="article-header-thumb">
                    <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                </div>
                <div class="article-header-text">
                    <a href="#" class="article-author-name">${writer.name}</a>
                    <div class="article-header-time">"2016-01-12 14:06"</div>
                </div>
            </div>
            <div class="article-doc comment-doc">
                ${content}
            </div>
            <div class="article-util">
            <ul class="article-util-list">
                <li>
                    <a class="link-modify-article" href="/api/questions/${question.id}/answers/${id}">수정</a>
                </li>
                <li>
                    <form class="delete-answer-form" action="/api/questions/${question.id}/answers/${id}" method="POST">
                        <input type="hidden" name="_method" value="DELETE">
                        <button type="submit" class="delete-answer-button">삭제</button>
                    </form>
                </li>
            </ul>
            </div>
        </article> `

    var element = document.getElementById("articles");
    element.insertAdjacentHTML("afterbegin",html);
    return;
    //$(".qna-comment-slipp-articles").insertAdjacentHTML("afterbegin", html);
}
