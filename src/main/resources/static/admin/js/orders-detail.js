/**
 * 주문 상세 정보 페이지 JavaScript
 */
document.addEventListener('DOMContentLoaded', function() {
    // CSRF 토큰 가져오기
    function getCsrfToken() {
        const metaTag = document.querySelector('meta[name="_csrf"]');
        return metaTag ? metaTag.getAttribute('content') : '';
    }

    // 페이지에 숨겨진 orderId 입력 요소들
    const shippingOrderIdInput = document.getElementById('shippingOrderId');
    const cancelOrderIdInput   = document.getElementById('cancelOrderId');
    const trackingOrderIdInput = document.getElementById('orderId');

    // 전역 주문 ID 결정
    let orderId = '';
    if (shippingOrderIdInput) orderId = shippingOrderIdInput.value;
    else if (cancelOrderIdInput) orderId = cancelOrderIdInput.value;
    else if (trackingOrderIdInput) orderId = trackingOrderIdInput.value;

    // 1. 송장 출력
    const printInvoiceBtn = document.getElementById('printInvoiceBtn');
    if (printInvoiceBtn) {
        printInvoiceBtn.addEventListener('click', function() {
            if (!orderId) {
                alert('주문 ID를 찾을 수 없습니다.');
                return;
            }
            window.open('/admin/orders/' + orderId + '/print-invoice', '_blank');
        });
    }

    // 2. 배송정보 수정 모달 열기
    const updateShippingBtn = document.getElementById('updateShippingBtn');
    const shippingInfoModal = document.getElementById('shippingInfoModal');
    if (updateShippingBtn) {
        updateShippingBtn.addEventListener('click', function() {
            if (shippingInfoModal) shippingInfoModal.style.display = 'block';
        });
    }

    // 3. 송장 등록/수정 모달 열기 (두 위치 모두 처리)
    const addTrackingBtn      = document.getElementById('addTrackingBtn');
    const registerTrackingBtn = document.getElementById('registerTrackingBtn');
    const trackingModal       = document.getElementById('trackingModal');
    [addTrackingBtn, registerTrackingBtn].forEach(btn => {
        if (btn) {
            btn.addEventListener('click', function() {
                const id = this.getAttribute('data-id') || orderId;
                if (trackingOrderIdInput) trackingOrderIdInput.value = id;
                if (trackingModal) trackingModal.style.display = 'block';
            });
        }
    });

    // 4. 주문 준비 처리
    const prepareOrderBtn = document.getElementById('prepareOrderBtn');
    if (prepareOrderBtn) {
        prepareOrderBtn.addEventListener('click', function() {
            const id = this.getAttribute('data-id') || orderId;
            if (confirm('해당 주문을 "상품 준비 처리"하시겠습니까?')) {
                updateOrderStatus(id, 'PREPARING');
            }
        });
    }

    // 5. 배송완료 처리 (두 버튼 모두)
    const markDeliveredBtn    = document.getElementById('markDeliveredBtn');
    const completeDeliveryBtn = document.getElementById('completeDeliveryBtn');
    [markDeliveredBtn, completeDeliveryBtn].forEach(btn => {
        if (btn) {
            btn.addEventListener('click', function() {
                const id = this.getAttribute('data-id') || orderId;
                if (confirm('해당 주문을 "배송 완료" 처리하시겠습니까?')) {
                    updateOrderStatus(id, 'DELIVERED');
                }
            });
        }
    });

    // 6. 주문 취소 모달 열기
    const cancelOrderBtn   = document.getElementById('cancelOrderBtn');
    const cancelOrderModal = document.getElementById('cancelOrderModal');
    if (cancelOrderBtn) {
        cancelOrderBtn.addEventListener('click', function() {
            const id = this.getAttribute('data-id') || orderId;
            if (cancelOrderIdInput) cancelOrderIdInput.value = id;
            if (cancelOrderModal) cancelOrderModal.style.display = 'block';
        });
    }

    // 7. 송장 정보 폼 제출 (trackingForm)
    const trackingForm = document.getElementById('trackingForm');
    if (trackingForm) {
        trackingForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const id = trackingOrderIdInput.value;
            const company = document.getElementById('trackingCompany').value;
            const number  = document.getElementById('trackingNumber').value;
            if (!company || !number) {
                alert('택배사와 송장번호를 모두 입력해주세요.');
                return;
            }
            fetch('/admin/api/orders/' + id + '/tracking', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': getCsrfToken()
                },
                body: JSON.stringify({ trackingCompany: company, trackingNumber: number })
            })
                .then(res => { if (!res.ok) throw new Error('서버 오류'); return res.json(); })
                .then(() => {
                    alert('송장 정보가 등록되었습니다.');
                    trackingModal.style.display = 'none';
                    window.location.reload();
                })
                .catch(err => {
                    console.error(err);
                    alert('송장 정보 등록에 실패했습니다.');
                });
        });
    }

    // 8. 배송정보 수정 폼 제출 (shippingInfoForm)
    const shippingForm = document.getElementById('shippingInfoForm');
    if (shippingForm) {
        shippingForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const id = shippingOrderIdInput.value;
            const payload = {
                recipientName: document.getElementById('recipientName').value,
                recipientPhone: document.getElementById('recipientPhone').value,
                zipCode: document.getElementById('zipCode').value,
                address: document.getElementById('address').value,
                addressDetail: document.getElementById('addressDetail').value,
                deliveryRequest: document.getElementById('deliveryRequest').value
            };
            fetch('/admin/api/orders/' + id + '/shipping-info', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': getCsrfToken()
                },
                body: JSON.stringify(payload)
            })
                .then(res => { if (!res.ok) throw new Error('서버 오류'); return res.json(); })
                .then(() => {
                    alert('배송 정보가 수정되었습니다.');
                    shippingInfoModal.style.display = 'none';
                    window.location.reload();
                })
                .catch(err => {
                    console.error(err);
                    alert('배송 정보 수정에 실패했습니다.');
                });
        });
    }

    // 9. 주문 취소 폼 제출 (cancelOrderForm)
    const cancelForm = document.getElementById('cancelOrderForm');
    if (cancelForm) {
        cancelForm.addEventListener('submit', function(event) {
            event.preventDefault();

            const id = cancelOrderIdInput.value;
            const reason = document.getElementById('cancelReason').value;
            const detail = document.getElementById('cancelDetail').value;

            if (!reason) {
                alert('취소 사유를 선택해주세요.');
                return;
            }

            fetch('/admin/orders/' + id + '/cancel', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': getCsrfToken()
                },
                body: JSON.stringify({ cancelReason: reason, cancelDetail: detail })
            })
                .then(res => {
                    if (!res.ok) throw new Error('서버 오류');
                    return;
                })
                .then(() => {
                    alert('주문이 취소되었습니다.');
                    cancelOrderModal.style.display = 'none';
                    window.location.reload();
                })
                .catch(err => {
                    console.error(err);
                    alert('주문 취소에 실패했습니다.');
                });
        });
    }


    // 10. 주문 상태 업데이트 함수
    function updateOrderStatus(id, status) {
        fetch('/admin/api/orders/' + id + '/status', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': getCsrfToken()
            },
            body: JSON.stringify({ status: status })
        })
            .then(res => { if (!res.ok) throw new Error('서버 오류'); return res.json(); })
            .then(() => {
                alert('주문 상태가 변경되었습니다.');
                window.location.reload();
            })
            .catch(err => {
                console.error(err);
                alert('상태 변경에 실패했습니다.');
            });
    }

    // 11. 모달 닫기 처리 (닫기 버튼 및 외부 클릭)
    const closeButtons = document.querySelectorAll('.close-modal, .close-modal-btn');
    closeButtons.forEach(btn => {
        btn.addEventListener('click', function() {
            document.querySelectorAll('.modal').forEach(m => m.style.display = 'none');
        });
    });
    window.addEventListener('click', function(event) {
        document.querySelectorAll('.modal').forEach(m => {
            if (event.target === m) m.style.display = 'none';
        });
    });
});
