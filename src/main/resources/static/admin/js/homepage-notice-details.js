/**
 * 공지사항 상세 페이지 JavaScript
 * homepage-qna-details.js 및 homepage-notice.js 스타일에 맞춘 구조
 */
document.addEventListener('DOMContentLoaded', function() {
    // 삭제 버튼 이벤트
    const deleteBtn = document.querySelector('.delete-btn');
    if (deleteBtn) {
        deleteBtn.addEventListener('click', function() {
            const noticeId = this.getAttribute('data-id');
            if (confirm('해당 공지사항을 삭제하시겠습니까? 삭제된 공지사항은 복구할 수 없습니다.')) {
                deleteNotice(noticeId);
            }
        });
    }

    // 공지사항 삭제 함수
    function deleteNotice(noticeId) {
        fetch('/admin/api/notice/' + noticeId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': getCsrfToken()
            }
        })
            .then(res => {
                if (!res.ok) throw new Error('서버 응답 오류');
                return res.json();
            })
            .then(data => {
                alert('공지사항이 삭제되었습니다.');
                // 목록 페이지로 리다이렉트
                window.location.href = '/admin/homepage/notice';
            })
            .catch(err => {
                console.error(err);
                alert('공지사항 삭제에 실패했습니다. 다시 시도해주세요.');
            });
    }

    // 첨부 파일 다운로드 기능 (첨부 파일이 있는 경우를 대비)
    const downloadLinks = document.querySelectorAll('.file-download');
    if (downloadLinks.length > 0) {
        downloadLinks.forEach(link => {
            link.addEventListener('click', function(e) {
                const fileId = this.getAttribute('data-file-id');
                const fileName = this.getAttribute('data-file-name');
                downloadFile(fileId, fileName);
            });
        });
    }

    // 파일 다운로드 함수 (필요시 구현)
    function downloadFile(fileId, fileName) {
        window.location.href = `/admin/api/file/download/${fileId}`;
    }

    // CSRF 토큰 가져오기
    function getCsrfToken() {
        const meta = document.querySelector('meta[name="_csrf"]');
        return meta ? meta.getAttribute('content') : '';
    }
});