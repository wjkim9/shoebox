// homepage-notice-create.js
document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".notice-form");

    form.addEventListener("submit", (e) => {
        const title = document.querySelector("#title").value.trim();
        const content = document.querySelector("#content").value.trim();

        if (title.length === 0 || content.length === 0) {
            alert("제목과 내용을 모두 입력해주세요.");
            e.preventDefault();
        }
    });
});
