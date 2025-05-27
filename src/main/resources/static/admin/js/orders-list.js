/**
 * 주문 관리 페이지 JavaScript
 */
console.log("orders-list.js 로딩됨");

document.addEventListener('DOMContentLoaded', function() {
    // 검색 폼 초기화 버튼
    const resetBtn = document.getElementById('resetBtn');

    resetBtn.addEventListener('click', function () {
        const form = document.getElementById('searchForm');
        if (form) {
            form.querySelectorAll('input, select').forEach(el => el.value = '');
            form.submit();
        }
    });



    // 송장 등록 모달
    const trackingBtns = document.querySelectorAll('.tracking-btn');
    const trackingModal = document.getElementById('trackingModal');
    const trackingForm = document.getElementById('trackingForm');
    const orderIdInput = document.getElementById('orderId');

    trackingBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            const orderId = this.getAttribute('data-id');
            if (orderIdInput) orderIdInput.value = orderId;
            if (trackingModal) trackingModal.style.display = 'block';
        });
    });

    // 일괄 상태 변경 모달
    const batchStatusBtn = document.getElementById('batchStatusBtn');
    const batchStatusModal = document.getElementById('batchStatusModal');
    const selectedOrderCountSpan = document.getElementById('selectedOrderCount');

    if (batchStatusBtn) {
        batchStatusBtn.addEventListener('click', function() {
            const selectedOrders = document.querySelectorAll('.order-checkbox:checked');
            if (selectedOrderCountSpan) selectedOrderCountSpan.textContent = selectedOrders.length;
            if (batchStatusModal) batchStatusModal.style.display = 'block';
        });
    }

    // 모달 닫기
    const closeButtons = document.querySelectorAll('.close-modal, .close-modal-btn');
    closeButtons.forEach(button => {
        button.addEventListener('click', function() {
            document.querySelectorAll('.modal').forEach(modal => modal.style.display = 'none');
        });
    });

    window.addEventListener('click', function(event) {
        document.querySelectorAll('.modal').forEach(modal => {
            if (event.target === modal) modal.style.display = 'none';
        });
    });

    // 송장 등록
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

            fetch('/admin/api/orders/' + orderId + '/tracking', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': getCsrfToken()
                },
                body: JSON.stringify({ trackingCompany, trackingNumber })
            })
                .then(response => {
                    if (!response.ok) throw new Error('서버 오류');
                    return response.json();
                })
                .then(() => {
                    alert('송장 정보가 등록되었습니다.');
                    trackingModal.style.display = 'none';
                    window.location.reload();
                })
                .catch(() => alert('송장 등록 실패'));
        });
    }

    // 일괄 상태 변경
    const batchStatusForm = document.getElementById('batchStatusForm');
    if (batchStatusForm) {
        batchStatusForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const status = document.getElementById('batchOrderStatus').value;
            const orderIds = Array.from(document.querySelectorAll('.order-checkbox:checked')).map(cb => cb.value);
            if (!status || orderIds.length === 0) {
                alert('상태와 주문 선택 필수');
                return;
            }

            fetch('/admin/api/orders/batch-status', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': getCsrfToken()
                },
                body: JSON.stringify({ orderIds, status })
            })
                .then(response => {
                    if (!response.ok) throw new Error('서버 오류');
                    return response.json();
                })
                .then(() => {
                    alert('일괄 변경 완료');
                    batchStatusModal.style.display = 'none';
                    window.location.reload();
                })
                .catch(() => alert('변경 실패'));
        });
    }

    // 단건 상태 변경
    const preparingBtns = document.querySelectorAll('.status-update-btn[data-action="preparing"]');
    preparingBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            const orderId = this.getAttribute('data-id');
            if (confirm('"상품 준비중" 상태로 변경할까요?')) {
                updateOrderStatus(orderId, 'PREPARING');
            }
        });
    });

    function updateOrderStatus(orderId, status) {
        fetch('/admin/api/orders/' + orderId + '/status', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': getCsrfToken()
            },
            body: JSON.stringify({ status })
        })
            .then(response => {
                if (!response.ok) throw new Error('서버 오류');
                return response.json();
            })
            .then(() => {
                alert('상태 변경 완료');
                window.location.reload();
            })
            .catch(() => alert('변경 실패'));
    }

    function getCsrfToken() {
        const meta = document.querySelector('meta[name="_csrf"]');
        return meta ? meta.getAttribute('content') : '';
    }

    // 엑셀 내보내기
    const exportOrdersBtn = document.getElementById('exportOrdersBtn');
    if (exportOrdersBtn) {
        exportOrdersBtn.addEventListener('click', function() {
            const params = new URLSearchParams();
            ['orderDateStart', 'orderDateEnd', 'orderStatus', 'searchType', 'searchKeyword'].forEach(id => {
                const val = document.getElementById(id).value;
                if (val) params.append(id, val);
            });
            window.location.href = '/admin/orders/export?' + params.toString();
        });
    }

    // 송장 일괄 출력
    const batchPrintBtn = document.getElementById('batchPrintBtn');
    if (batchPrintBtn) {
        batchPrintBtn.addEventListener('click', function() {
            const orderIds = Array.from(document.querySelectorAll('.order-checkbox:checked')).map(cb => cb.value);
            if (orderIds.length === 0) {
                alert('출력할 주문을 선택해주세요.');
                return;
            }
            const params = new URLSearchParams();
            orderIds.forEach(id => params.append('orderIds', id));
            window.open('/admin/orders/print-invoice?' + params.toString(), '_blank');
        });
    }

    updateBatchButtons();
});


