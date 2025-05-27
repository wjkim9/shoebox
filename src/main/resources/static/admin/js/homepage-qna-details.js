/**
 * Q&A 상세 페이지 JavaScript
 * homepage-qna.js 스타일에 맞춘 구조
 */
document.addEventListener('DOMContentLoaded', function () {
    // 답변 등록 폼
    const answerForm = document.getElementById('answerForm');
    const answerContent = document.getElementById('answerContent');

    if (answerForm) {
        answerForm.addEventListener('submit', function (event) {
            // 내용이 비어 있으면 경고
            if (!answerContent.value.trim()) {
                event.preventDefault(); // 폼 제출 막기
                alert('답변 내용을 입력해주세요.');
                answerContent.focus();
            }
        });
    }

    // CSRF 토큰 가져오기 (향후 AJAX 전송 시 참고용)
    function getCsrfToken() {
        const meta = document.querySelector('meta[name="_csrf"]');
        return meta ? meta.getAttribute('content') : '';
    }
});
