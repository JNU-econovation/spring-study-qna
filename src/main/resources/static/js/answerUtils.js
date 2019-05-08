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
    answerBtn.addEventListener("click", registerAnswerHandler);
    var answerList = document.querySelector('.qna-comment-slipp-articles');
    answerList.addEventListener('click', deleteAnswerHandler);
}

function fetchManager({url, method, body, headers, callback}) {
    fetch(url, {
        //fetch("http://xxx.com/dd", {
        method,
        body,
        headers,
        credentials: "same-origin"
    }).then((response) => {
        const data = response.json();
        return data.then(result => {
            return {
                'result': result,
                'status': response.status
            }
        })
    }).then(({result, status}) => {
        if (status >= 400) {
            console.log('error 가 발생했네요 ', result.error);
        } else {
            callback(result)
        }
    }).catch(err => {
        console.log("oops..", err)
    })
}

function registerAnswerHandler(evt) {
    evt.preventDefault();
    const content = document.getElementById("content").value;
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

function deleteAnswerHandler(evt) {
    if (evt.target.className !== "answer-delete") return;
    evt.preventDefault();
    const url = evt.target.href;
    const id = url.replace(/.+\/(\d+)$/, "$1");

    fetchManager({
        url,
        method: 'DELETE',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({id}),
        callback: deleteAnswer
    })
}

function deleteAnswer({answer}) {
    var id = answer.id;
    const selector = `[data-id=${id}]`;
    const target = $(selector);
    target.remove();
}

function appendAnswer({id, question, date, writer, content}) {

    const html = `
        <article class="article" data-id=${id}>
            <div class="article-header">
                <div class="article-header-thumb">
                    <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                </div>
                <div class="article-header-text">
                    <a href="#" class="article-author-name">${writer.name}</a>
                    <div class="article-header-time">${date}</div>
                </div>
            </div>
            <div class="article-doc comment-doc">
                ${content}
            </div>
            <div class="article-util">
            <ul class="article-util-list">
                <li>
                    <a class="answer-delete" methods="DELETE" id ="deleteBtn" href="/api/question/${question.id}/answers/${id}">삭제</a>
                </li>
            </ul>
            </div>
        </article> `

    var element = document.getElementById("submit");
    element.insertAdjacentHTML("beforebegin", html);
}
