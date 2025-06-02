/**
 * 리뷰 관리 페이지 JavaScript
 */
document.addEventListener('DOMContentLoaded', function() {
    // 검색 폼 초기화 버튼
    const resetBtn = document.getElementById('resetBtn');
    if (resetBtn) {
        resetBtn.addEventListener('click', function() {
            const form = document.getElementById('searchForm');
            const inputs = form.querySelectorAll('input:not([type="submit"])');
            const selects = form.querySelectorAll('select');

            inputs.forEach(input => {
                input.value = '';
            });

            selects.forEach(select => {
                select.selectedIndex = 0;
            });

            // 날짜 필드는 오늘 날짜 기준으로 30일 이전부터 오늘까지로 설정
            const today = new Date();
            const thirtyDaysAgo = new Date();
            thirtyDaysAgo.setDate(today.getDate() - 30);

            document.getElementById('reviewDateStart').value = formatDate(thirtyDaysAgo);
            document.getElementById('reviewDateEnd').value = formatDate(today);
        });
    }

    // 날짜 포맷 함수 (YYYY-MM-DD)
    function formatDate(date) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    // 전체 선택 체크박스
    const selectAll = document.getElementById('selectAll');
    if (selectAll) {
        selectAll.addEventListener('change', function() {
            const checkboxes = document.querySelectorAll('.order-checkbox');
            checkboxes.forEach(checkbox => {
                checkbox.checked = selectAll.checked;
            });
            updateBatchButtons();
        });
    }

    // 개별 체크박스 이벤트
    const reviewCheckboxes = document.querySelectorAll('.order-checkbox');
    reviewCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            updateSelectAllCheckbox();
            updateBatchButtons();
        });
    });

    // 선택된 체크박스 기반으로 '전체 선택' 체크박스 업데이트
    function updateSelectAllCheckbox() {
        const checkboxes = document.querySelectorAll('.order-checkbox');
        const checkedBoxes = document.querySelectorAll('.order-checkbox:checked');

        if (selectAll) {
            selectAll.checked = checkboxes.length > 0 && checkboxes.length === checkedBoxes.length;
        }
    }

    // 일괄 처리 버튼 활성화/비활성화 (필요시 추가)
    function updateBatchButtons() {
        // 현재 페이지에는 일괄 처리 버튼이 없지만, 향후 확장성을 위해 함수 구조는 유지
        const checkedBoxes = document.querySelectorAll('.order-checkbox:checked');

        // 향후 일괄 삭제 버튼 등이 추가될 경우 활용
        // const batchDeleteBtn = document.getElementById('batchDeleteBtn');
        // if (batchDeleteBtn) {
        //     batchDeleteBtn.disabled = checkedBoxes.length === 0;
        // }
    }

    // 삭제 버튼 이벤트
    const deleteBtns = document.querySelectorAll('.delete-btn');
    deleteBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            const reviewId = this.getAttribute('data-id');

            if (confirm('해당 리뷰를 삭제하시겠습니까? 삭제된 리뷰는 복구할 수 없습니다.')) {
                deleteReview(reviewId);
            }
        });
    });

    // 리뷰 삭제 함수
    function deleteReview(reviewId) {
        fetch('/admin/api/reviews/' + reviewId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': getCsrfToken()
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('서버 응답 오류');
                }
                return response.json();
            })
            .then(data => {
                alert('리뷰가 삭제되었습니다.');
                // 페이지 새로고침
                window.location.reload();
            })
            .catch(error => {
                console.error('Error:', error);
                alert('리뷰 삭제에 실패했습니다. 다시 시도해주세요.');
            });
    }

    // CSRF 토큰 가져오기
    function getCsrfToken() {
        const metaTag = document.querySelector('meta[name="_csrf"]');
        return metaTag ? metaTag.getAttribute('content') : '';
    }

    // 페이지 로드 시 초기화
    updateBatchButtons();
});