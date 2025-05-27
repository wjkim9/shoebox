/**
 * 공지사항 관리 페이지 JavaScript
 * Q&A 관리 페이지 스크립트를 참고하여 구조 통일 (homepage-qna.js 기반)
 */
document.addEventListener('DOMContentLoaded', function() {
    // 검색 폼 초기화 버튼 - 필요시 활성화
    const resetBtn = document.getElementById('resetBtn');
    if (resetBtn) {
        resetBtn.addEventListener('click', function() {
            const form = document.getElementById('searchForm');
            const inputs = form.querySelectorAll('input:not([type="submit"])');
            const selects = form.querySelectorAll('select');

            inputs.forEach(input => input.value = '');
            selects.forEach(select => select.selectedIndex = 0);

            // 날짜 필드는 오늘 날짜 기준으로 30일 이전부터 오늘까지로 설정
            const today = new Date();
            const thirtyDaysAgo = new Date();
            thirtyDaysAgo.setDate(today.getDate() - 30);

            document.getElementById('noticeDateStart').value = formatDate(thirtyDaysAgo);
            document.getElementById('noticeDateEnd').value = formatDate(today);
        });
    }

    // 날짜 포맷 함수 (YYYY-MM-DD)
    function formatDate(date) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    // 전체 선택 체크박스 - 필요시 활성화
    const selectAll = document.getElementById('selectAll');
    if (selectAll) {
        selectAll.addEventListener('change', function() {
            const checkboxes = document.querySelectorAll('.notice-checkbox');
            checkboxes.forEach(cb => cb.checked = selectAll.checked);
            updateBatchButtons();
        });
    }

    // 개별 체크박스 이벤트 - 필요시 활성화
    const noticeCheckboxes = document.querySelectorAll('.notice-checkbox');
    noticeCheckboxes.forEach(cb => {
        cb.addEventListener('change', function() {
            updateSelectAllCheckbox();
            updateBatchButtons();
        });
    });

    // 전체 선택 체크박스 상태 동기화
    function updateSelectAllCheckbox() {
        if (!selectAll) return;
        const all = document.querySelectorAll('.notice-checkbox');
        const checked = document.querySelectorAll('.notice-checkbox:checked');
        selectAll.checked = all.length > 0 && all.length === checked.length;
    }

    // 일괄 처리 버튼 활성/비활성 (현재 없음, 확장 대비)
    function updateBatchButtons() {
        // 일괄 삭제 버튼 등 활성화 여부 설정
        const batchDeleteBtn = document.getElementById('batchDeleteBtn');
        if (batchDeleteBtn) {
            const checkedCount = document.querySelectorAll('.notice-checkbox:checked').length;
            batchDeleteBtn.disabled = checkedCount === 0;
        }
    }

    // 삭제 버튼 이벤트
    const deleteBtns = document.querySelectorAll('.delete-btn');
    deleteBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            const noticeId = this.getAttribute('data-id');
            if (confirm('해당 공지사항을 삭제하시겠습니까? 삭제된 공지사항은 복구할 수 없습니다.')) {
                deleteNotice(noticeId);
            }
        });
    });

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
                window.location.reload();
            })
            .catch(err => {
                console.error(err);
                alert('공지사항 삭제에 실패했습니다. 다시 시도해주세요.');
            });
    }

    // 일괄 삭제 기능 - 필요시 활성화
    const batchDeleteBtn = document.getElementById('batchDeleteBtn');
    if (batchDeleteBtn) {
        batchDeleteBtn.addEventListener('click', function() {
            const selectedIds = Array.from(document.querySelectorAll('.notice-checkbox:checked'))
                .map(cb => cb.value);

            if (selectedIds.length === 0) {
                alert('삭제할 공지사항을 선택해주세요.');
                return;
            }

            if (confirm(`선택한 ${selectedIds.length}개의 공지사항을 삭제하시겠습니까? 삭제된 공지사항은 복구할 수 없습니다.`)) {
                batchDeleteNotices(selectedIds);
            }
        });
    }

    // 일괄 삭제 API 호출 함수
    function batchDeleteNotices(noticeIds) {
        fetch('/admin/api/notice/batch-delete', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': getCsrfToken()
            },
            body: JSON.stringify({ ids: noticeIds })
        })
            .then(res => {
                if (!res.ok) throw new Error('서버 응답 오류');
                return res.json();
            })
            .then(data => {
                alert(`${noticeIds.length}개의 공지사항이 삭제되었습니다.`);
                window.location.reload();
            })
            .catch(err => {
                console.error(err);
                alert('공지사항 삭제에 실패했습니다. 다시 시도해주세요.');
            });
    }

    // CSRF 토큰 가져오기
    function getCsrfToken() {
        const meta = document.querySelector('meta[name="_csrf"]');
        return meta ? meta.getAttribute('content') : '';
    }

    // 초기화
    updateBatchButtons();
});