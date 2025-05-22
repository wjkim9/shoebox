document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form.panel");
    const mainContent = document.querySelector("main[layout\\:fragment='content']");

    if (!form || !mainContent) return;

    form.addEventListener("submit", function (e) {
        e.preventDefault(); // 기존 submit 막기

        const formData = new FormData(form);
        const queryString = new URLSearchParams(formData).toString();

        fetch(`/admin/member/member-list?${queryString}`, {
            headers: {
                "X-Requested-With": "XMLHttpRequest" // 프래그먼트 요청임을 명시
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`서버 오류: ${response.status}`);
            }
            return response.text();
        })
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, "text/html");
            const newContent = doc.querySelector("main[layout\\:fragment='content'], main");

            if (newContent) {
                mainContent.replaceWith(newContent);
            } else {
                console.warn("조각(fragment) HTML을 찾지 못했습니다.");
                alert("오류: 페이지 일부를 불러오지 못했습니다.");
            }
        })
        .catch(err => {
            console.error("콘텐츠 로드 오류:", err);
            alert("서버에서 데이터를 불러오는 중 오류가 발생했습니다.");
        });
    });
});
