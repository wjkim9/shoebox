/**
 * 주문 관리 페이지 JavaScript
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

            document.getElementById('orderDateStart').value = formatDate(thirtyDaysAgo);
            document.getElementById('orderDateEnd').value = formatDate(today);
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
    const selectAllOrders = document.getElementById('selectAllOrders');
    if (selectAllOrders) {
        selectAllOrders.addEventListener('change', function() {
            const checkboxes = document.querySelectorAll('.order-checkbox');
            checkboxes.forEach(checkbox => {
                checkbox.checked = selectAllOrders.checked;
            });
            updateBatchButtons();
        });
    }

    // 개별 체크박스 이벤트
    const orderCheckboxes = document.querySelectorAll('.order-checkbox');
    orderCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            updateSelectAllCheckbox();
            updateBatchButtons();
        });
    });

    // 선택된 체크박스 기반으로 '전체 선택' 체크박스 업데이트
    function updateSelectAllCheckbox() {
        const checkboxes = document.querySelectorAll('.order-checkbox');
        const checkedBoxes = document.querySelectorAll('.order-checkbox:checked');

        if (selectAllOrders) {
            selectAllOrders.checked = checkboxes.length > 0 && checkboxes.length === checkedBoxes.length;
        }
    }

    // 일괄 처리 버튼 활성화/비활성화
    function updateBatchButtons() {
        const checkedBoxes = document.querySelectorAll('.order-checkbox:checked');
        const batchStatusBtn = document.getElementById('batchStatusBtn');
        const batchPrintBtn = document.getElementById('batchPrintBtn');

        if (batchStatusBtn) {
            batchStatusBtn.disabled = checkedBoxes.length === 0;
        }

        if (batchPrintBtn) {
            batchPrintBtn.disabled = checkedBoxes.length === 0;
        }
    }

    // 송장 등록 모달
    const trackingBtns = document.querySelectorAll('.tracking-btn');
    const trackingModal = document.getElementById('trackingModal');
    const trackingForm = document.getElementById('trackingForm');
    const orderIdInput = document.getElementById('orderId');

    trackingBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            const orderId = this.getAttribute('data-id');
            if (orderIdInput) {
                orderIdInput.value = orderId;
            }
            if (trackingModal) {
                trackingModal.style.display = 'block';
            }
        });
    });

    // 일괄 상태 변경 모달
    const batchStatusBtn = document.getElementById('batchStatusBtn');
    const batchStatusModal = document.getElementById('batchStatusModal');
    const selectedOrderCountSpan = document.getElementById('selectedOrderCount');

    if (batchStatusBtn) {
        batchStatusBtn.addEventListener('click', function() {
            const selectedOrders = document.querySelectorAll('.order-checkbox:checked');
            if (selectedOrderCountSpan) {
                selectedOrderCountSpan.textContent = selectedOrders.length;
            }
            if (batchStatusModal) {
                batchStatusModal.style.display = 'block';
            }
        });
    }

    // 모달 닫기 버튼들
    const closeButtons = document.querySelectorAll('.close-modal, .close-modal-btn');
    closeButtons.forEach(button => {
        button.addEventListener('click', function() {
            const modals = document.querySelectorAll('.modal');
            modals.forEach(modal => {
                modal.style.display = 'none';
            });
        });
    });

    // 외부 클릭시 모달 닫기
    window.addEventListener('click', function(event) {
        const modals = document.querySelectorAll('.modal');
        modals.forEach(modal => {
            if (event.target === modal) {
                modal.style.display = 'none';
            }
        });
    });

    // 송장 정보 등록 폼 제출
    if (trackingForm) {
        trackingForm.addEventListener('submit', function(event) {
            event.preventDefault();

            const orderId = document.getElementById('orderId').value;
            const trackingCompany = document.getElementById('trackingCompany').value;
            const trackingNumber = document.getElementById('trackingNumber').value;

            if (!orderId || !trackingCompany || !trackingNumber) {
                alert('모든 필드를 입력해주세요.');
                return;
            }

            // AJAX 요청 보내기
            fetch('/admin/api/orders/' + orderId + '/tracking', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': getCsrfToken()
                },
                body: JSON.stringify({
                    trackingCompany: trackingCompany,
                    trackingNumber: trackingNumber
                })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('서버 응답 오류');
                    }
                    return response.json();
                })
                .then(data => {
                    alert('송장 정보가 등록되었습니다.');
                    trackingModal.style.display = 'none';
                    // 페이지 새로고침
                    window.location.reload();
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('송장 정보 등록에 실패했습니다. 다시 시도해주세요.');
                });
        });
    }

    // 일괄 상태 변경 폼 제출
    const batchStatusForm = document.getElementById('batchStatusForm');
    if (batchStatusForm) {
        batchStatusForm.addEventListener('submit', function(event) {
            event.preventDefault();

            const status = document.getElementById('batchOrderStatus').value;
            const selectedOrders = document.querySelectorAll('.order-checkbox:checked');
            const orderIds = Array.from(selectedOrders).map(checkbox => checkbox.value);

            if (!status || orderIds.length === 0) {
                alert('상태를 선택하고 주문을 선택해주세요.');
                return;
            }

            // AJAX 요청 보내기
            fetch('/admin/api/orders/batch-status', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': getCsrfToken()
                },
                body: JSON.stringify({
                    orderIds: orderIds,
                    status: status
                })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('서버 응답 오류');
                    }
                    return response.json();
                })
                .then(data => {
                    alert('주문 상태가 일괄 변경되었습니다.');
                    batchStatusModal.style.display = 'none';
                    // 페이지 새로고침
                    window.location.reload();
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('주문 상태 변경에 실패했습니다. 다시 시도해주세요.');
                });
        });
    }

    // 상품 준비중 상태로 변경 버튼
    const preparingBtns = document.querySelectorAll('.status-update-btn[data-action="preparing"]');
    preparingBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            const orderId = this.getAttribute('data-id');

            if (confirm('해당 주문을 "상품 준비중" 상태로 변경하시겠습니까?')) {
                updateOrderStatus(orderId, 'PREPARING');
            }
        });
    });

    // 주문 상태 업데이트 함수
    function updateOrderStatus(orderId, status) {
        fetch('/admin/api/orders/' + orderId + '/status', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': getCsrfToken()
            },
            body: JSON.stringify({ status: status })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('서버 응답 오류');
                }
                return response.json();
            })
            .then(data => {
                alert('주문 상태가 변경되었습니다.');
                // 페이지 새로고침
                window.location.reload();
            })
            .catch(error => {
                console.error('Error:', error);
                alert('주문 상태 변경에 실패했습니다. 다시 시도해주세요.');
            });
    }

    // CSRF 토큰 가져오기
    function getCsrfToken() {
        const metaTag = document.querySelector('meta[name="_csrf"]');
        return metaTag ? metaTag.getAttribute('content') : '';
    }

    // 엑셀 내보내기 버튼
    const exportOrdersBtn = document.getElementById('exportOrdersBtn');
    if (exportOrdersBtn) {
        exportOrdersBtn.addEventListener('click', function() {
            // 현재 검색 조건 가져오기
            const orderDateStart = document.getElementById('orderDateStart').value;
            const orderDateEnd = document.getElementById('orderDateEnd').value;
            const orderStatus = document.getElementById('orderStatus').value;
            const searchType = document.getElementById('searchType').value;
            const searchKeyword = document.getElementById('searchKeyword').value;

            // 쿼리 파라미터 구성
            const params = new URLSearchParams();
            if (orderDateStart) params.append('orderDateStart', orderDateStart);
            if (orderDateEnd) params.append('orderDateEnd', orderDateEnd);
            if (orderStatus) params.append('orderStatus', orderStatus);
            if (searchType) params.append('searchType', searchType);
            if (searchKeyword) params.append('searchKeyword', searchKeyword);

            // 엑셀 다운로드 URL 열기
            window.location.href = '/admin/orders/export?' + params.toString();
        });
    }

    // 송장 일괄 출력 버튼
    const batchPrintBtn = document.getElementById('batchPrintBtn');
    if (batchPrintBtn) {
        batchPrintBtn.addEventListener('click', function() {
            const selectedOrders = document.querySelectorAll('.order-checkbox:checked');
            const orderIds = Array.from(selectedOrders).map(checkbox => checkbox.value);

            if (orderIds.length === 0) {
                alert('출력할 주문을 선택해주세요.');
                return;
            }

            // 쿼리 파라미터 구성
            const params = new URLSearchParams();
            orderIds.forEach(id => params.append('orderIds', id));

            // 새 창에서 송장 출력 페이지 열기
            window.open('/admin/orders/print-invoice?' + params.toString(), '_blank');
        });
    }

    // 페이지 로드 시 초기화
    updateBatchButtons();
});