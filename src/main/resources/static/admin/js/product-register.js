document.addEventListener('DOMContentLoaded', function() {
    // 사이즈 및 재고 추가/제거 기능
    const sizeStockContainer = document.getElementById('sizeStockContainer');

    // 이벤트 위임을 사용하여 동적 요소 이벤트 처리
    sizeStockContainer.addEventListener('click', function(e) {
        // 사이즈 추가 버튼 클릭
        if (e.target.closest('.add-size-btn')) {
            const currentRow = e.target.closest('.size-stock-row');
            const newRow = currentRow.cloneNode(true);

            // 새 행의 입력값 초기화
            newRow.querySelectorAll('input, select').forEach(input => {
                input.value = '';
            });

            // 새 행의 제거 버튼 활성화
            newRow.querySelector('.remove-size-btn').disabled = false;

            // 새 행 추가
            sizeStockContainer.appendChild(newRow);
        }

        // 사이즈 제거 버튼 클릭
        if (e.target.closest('.remove-size-btn') && !e.target.closest('.remove-size-btn').disabled) {
            const currentRow = e.target.closest('.size-stock-row');
            currentRow.remove();
        }
    });

    // 메인 이미지 미리보기
    const mainImage = document.getElementById('mainImage');
    const mainImagePreview = document.getElementById('mainImagePreview');
    const fileName = document.querySelector('.file-name');

    mainImage.addEventListener('change', function() {
        if (this.files && this.files[0]) {
            const reader = new FileReader();
            reader.onload = function(e) {
                mainImagePreview.src = e.target.result;
                mainImagePreview.style.display = 'block';
            }
            reader.readAsDataURL(this.files[0]);
            fileName.textContent = this.files[0].name;
        } else {
            fileName.textContent = '선택된 파일 없음';
            mainImagePreview.style.display = 'none';
        }
    });

    // 추가 이미지 미리보기
    const additionalImages = document.getElementById('additionalImages');
    const additionalImagesPreview = document.getElementById('additionalImagesPreview');
    const additionalFileName = document.querySelector('.additional-file-name');

    additionalImages.addEventListener('change', function() {
        additionalImagesPreview.innerHTML = '';

        if (this.files && this.files.length > 0) {
            let fileNames = '';
            for (let i = 0; i < Math.min(this.files.length, 5); i++) {
                const reader = new FileReader();
                const imgContainer = document.createElement('div');
                imgContainer.className = 'position-relative';

                reader.onload = function(e) {
                    imgContainer.innerHTML = `
              <img src="${e.target.result}" alt="추가 이미지 ${i+1}">
              <div class="image-remove" data-index="${i}">
                <i class="fas fa-times"></i>
              </div>
            `;
                    additionalImagesPreview.appendChild(imgContainer);
                }

                reader.readAsDataURL(this.files[i]);
                fileNames += (i > 0 ? ', ' : '') + this.files[i].name;
            }
            additionalFileName.textContent = this.files.length > 1 ?
                `${this.files.length}개 파일 선택됨` : fileNames;
        } else {
            additionalFileName.textContent = '선택된 파일 없음';
        }
    });

    // 사이즈 중복 체크
    function checkDuplicateSizes() {
        const sizeSelects = document.querySelectorAll('.size-select');
        const selectedSizes = [];
        let hasDuplicates = false;

        sizeSelects.forEach(select => {
            if (select.value && selectedSizes.includes(select.value)) {
                select.classList.add('is-invalid');
                hasDuplicates = true;
            } else {
                select.classList.remove('is-invalid');
                if (select.value) {
                    selectedSizes.push(select.value);
                }
            }
        });

        return !hasDuplicates;
    }

    // 폼 제출 전 유효성 검사
    const productForm = document.getElementById('productForm');

    productForm.addEventListener('submit', function(e) {
        // 모든 필수 필드 검사
        const requiredFields = productForm.querySelectorAll('[required]');
        let isValid = true;

        requiredFields.forEach(field => {
            if (!field.value.trim()) {
                isValid = false;
                field.classList.add('is-invalid');
            } else {
                field.classList.remove('is-invalid');
            }
        });

        // 사이즈 중복 체크
        if (!checkDuplicateSizes()) {
            isValid = false;
            alert('사이즈가 중복되었습니다. 다른 사이즈를 선택해주세요.');
        }

        // 추가 이미지 개수 제한 체크
        const additionalImagesInput = document.getElementById('additionalImages');
        if (additionalImagesInput.files.length > 5) {
            isValid = false;
            alert('추가 이미지는 최대 5개까지만 등록 가능합니다.');
        }

        if (!isValid) {
            e.preventDefault();
            // 첫 번째 오류 필드로 스크롤
            const firstInvalidField = productForm.querySelector('.is-invalid');
            if (firstInvalidField) {
                firstInvalidField.scrollIntoView({ behavior: 'smooth', block: 'center' });
                firstInvalidField.focus();
            }

            if (!checkDuplicateSizes()) {
                alert('사이즈가 중복되었습니다. 다른 사이즈를 선택해주세요.');
            } else {
                alert('필수 입력 항목을 모두 입력해주세요.');
            }
        }
    });

    // 입력 필드 실시간 검증
    const inputs = productForm.querySelectorAll('input, select, textarea');
    inputs.forEach(input => {
        input.addEventListener('input', function() {
            if (this.hasAttribute('required') && !this.value.trim()) {
                this.classList.add('is-invalid');
            } else {
                this.classList.remove('is-invalid');
            }
        });
    });

    // 사이즈 선택 변경 시 중복 체크
    sizeStockContainer.addEventListener('change', function(e) {
        if (e.target.classList.contains('size-select')) {
            checkDuplicateSizes();
        }
    });

    // 할인율 변경 시 가격 계산 표시
    const priceInput = document.getElementById('price');
    const discountRateInput = document.getElementById('discountRate');

    function updateDiscountedPrice() {
        const price = parseFloat(priceInput.value) || 0;
        const discountRate = parseFloat(discountRateInput.value) || 0;

        if (price > 0 && discountRate > 0) {
            const discountedPrice = price * (1 - discountRate / 100);
            const discountInfo = document.getElementById('discountInfo');

            if (!discountInfo) {
                const infoElement = document.createElement('div');
                infoElement.id = 'discountInfo';
                infoElement.className = 'discount-info';
                infoElement.style.marginTop = '5px';
                infoElement.style.fontSize = '13px';
                infoElement.style.color = 'var(--primary-color)';
                infoElement.textContent = `할인 적용가: ${Math.round(discountedPrice).toLocaleString()}원`;
                discountRateInput.parentNode.appendChild(infoElement);
            } else {
                discountInfo.textContent = `할인 적용가: ${Math.round(discountedPrice).toLocaleString()}원`;
            }
        } else {
            const discountInfo = document.getElementById('discountInfo');
            if (discountInfo) {
                discountInfo.remove();
            }
        }
    }

    priceInput.addEventListener('input', updateDiscountedPrice);
    discountRateInput.addEventListener('input', updateDiscountedPrice);
});