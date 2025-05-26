document.addEventListener('DOMContentLoaded', function () {
    const sizeStockContainer = document.getElementById('sizeStockContainer');

    // 사이즈 행 템플릿 생성
    function createSizeStockRow(size = '', stock = '') {
        const row = document.createElement('div');
        row.classList.add('size-stock-row');
        row.innerHTML = `
      <select name="sizes[]" class="form-select size-select" required>
        <option value="">사이즈 선택</option>
        ${[220, 225, 230, 235, 240, 245, 250, 255, 260, 265, 270, 275, 280, 285, 290, 295, 300].map(s => `
          <option value="${s}" ${s == size ? 'selected' : ''}>${s}</option>
        `).join('')}
      </select>
      <input type="number" name="sizeStocks[]" class="form-input size-stock" placeholder="재고 수량" min="0" value="${stock}" required>
      <div class="size-btn-group">
        <button type="button" class="btn btn-icon add-size-btn"><i class="fas fa-plus"></i></button>
        <button type="button" class="btn btn-icon btn-delete remove-size-btn"><i class="fas fa-minus"></i></button>
      </div>
    `;
        return row;
    }

    // 초기 이벤트 바인딩
    function bindRowEvents(row) {
        const addBtn = row.querySelector('.add-size-btn');
        const removeBtn = row.querySelector('.remove-size-btn');

        addBtn.addEventListener('click', function () {
            const newRow = createSizeStockRow();
            bindRowEvents(newRow);
            sizeStockContainer.appendChild(newRow);
        });

        removeBtn.addEventListener('click', function () {
            if (sizeStockContainer.children.length > 1) {
                row.remove();
            }
        });
    }

    // 초기 모든 행에 이벤트 연결
    document.querySelectorAll('.size-stock-row').forEach(row => bindRowEvents(row));

    // 메인 이미지 미리보기
    const mainImageInput = document.querySelector('input[name="mainImage"]');
    const mainImagePreview = document.querySelector('#mainImagePreview');

    if (mainImageInput && mainImagePreview) {
        mainImageInput.addEventListener('change', function () {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = e => {
                    mainImagePreview.src = e.target.result;
                    mainImagePreview.style.display = 'block';
                };
                reader.readAsDataURL(file);
            } else {
                mainImagePreview.style.display = 'none';
            }
        });
    }

    // 추가 이미지 미리보기 (선택 사항)
    const additionalInput = document.querySelector('input[name="additionalImages"]');
    const additionalPreview = document.querySelector('.additional-images-preview');

    if (additionalInput && additionalPreview) {
        additionalInput.addEventListener('change', function () {
            additionalPreview.innerHTML = '';
            Array.from(this.files).forEach(file => {
                const reader = new FileReader();
                reader.onload = e => {
                    const img = document.createElement('img');
                    img.src = e.target.result;
                    img.style.maxHeight = '80px';
                    img.style.marginRight = '8px';
                    additionalPreview.appendChild(img);
                };
                reader.readAsDataURL(file);
            });
        });
    }
});
