document.addEventListener('DOMContentLoaded', function() {
    let editingLi = null;
    let editingId = null; // 수정할 카테고리 id

    // 수정 버튼 이벤트
    document.querySelectorAll('.edit-btn').forEach(function(btn) {
        btn.addEventListener('click', function() {
            const li = this.closest('li');
            editingLi = li;
            editingId = this.getAttribute('data-id');
            const brandName = li.querySelector('.brand-name').textContent;
            const brandImg = li.querySelector('img').src;
            openEditModal(brandName, '', brandImg, li);
        });
    });

    // 삭제 버튼 이벤트
    document.querySelectorAll('.delete-btn').forEach(function(btn) {
        btn.addEventListener('click', function() {
            const id = this.getAttribute('data-id');
            if (!confirm('정말 삭제하시겠습니까?')) return;
            fetch('/admin/category/delete/' + id, {
                method: 'DELETE'
            })
            .then(res => res.text())
            .then(result => {
                if (result === 'success') {
                    this.closest('li').remove();
                    alert('카테고리가 삭제되었습니다.');
                } else {
                    alert('삭제 실패');
                }
            });
        });
    });

    // 추가 버튼
    document.querySelector('.category_addbtn').addEventListener('click', function() {
        openAddModal();
    });

    function openAddModal() {
        document.getElementById('brandName').value = '';
        document.getElementById('brandCategory').value = '';
        document.getElementById('brandImgPreview').src = '';
        document.getElementById('brandImgPreview').style.display = 'none';
        document.getElementById('brandImage').value = '';
        document.getElementById('editModalBg').classList.add('active');
        if (document.getElementById('picName')) {
            document.getElementById('picName').value = '';
        }
        editingLi = null;
        editingId = null; // 추가 모드로 전환
    }

    function openEditModal(name, category, imgFile, li) {
        document.getElementById('brandName').value = name;
        document.getElementById('brandCategory').value = category || '';
        if (imgFile) {
            document.getElementById('brandImgPreview').src = imgFile;
            document.getElementById('brandImgPreview').style.display = 'block';
        } else {
            document.getElementById('brandImgPreview').style.display = 'none';
        }
        document.getElementById('brandImage').value = '';
        document.getElementById('editModalBg').classList.add('active');
        editingLi = li;
    }

    window.closeEditModal = function() {
        document.getElementById('editModalBg').classList.remove('active');
        editingLi = null;
        editingId = null;
    }

    // 파일 업로드 및 미리보기
    document.getElementById('brandImage').addEventListener('change', function(e) {
        const file = e.target.files[0];
        if (file) {
            // 파일 미리보기
            const reader = new FileReader();
            reader.onload = function(ev) {
                document.getElementById('brandImgPreview').src = ev.target.result;
                document.getElementById('brandImgPreview').style.display = 'block';
            }
            reader.readAsDataURL(file);

            // 파일 업로드
            const formData = new FormData();
            formData.append('file', file);
            if (editingId) {
                formData.append('categoriesId', editingId); // 수정 시 id 전달
            }

            fetch('/admin/category/upload', {
                method: 'POST',
                body: formData
            })
            .then(res => res.text())
            .then(fileName => {
                document.getElementById('picName').value = fileName; // 파일명만 저장!
            });
        }
    });

    // 폼 제출(저장/수정)
    document.getElementById('editBrandForm').onsubmit = function(e) {
        e.preventDefault();

        const newName = document.getElementById('brandName').value;
        const picName = document.getElementById('picName') ? document.getElementById('picName').value : '';

        console.log('picName:', picName);

        if (editingId) {
            // 수정
            fetch('/admin/category/update', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: `id=${encodeURIComponent(editingId)}&categoriesName=${encodeURIComponent(newName)}&picName=${encodeURIComponent(picName)}`
            })
            .then(res => res.text())
            .then(result => {
                if (result === 'success') {
                    window.location.reload();
                } else {
                    alert('수정 실패');
                }
            });
        } else {
            // 추가
            fetch('/admin/category/save', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: `categoriesName=${encodeURIComponent(newName)}&picName=${encodeURIComponent(picName)}`
            })
            .then(res => {
                if (res.redirected) {
                    window.location.href = res.url; // 저장 후 목록 새로고침
                } else {
                    return res.text();
                }
            })
            .catch(err => {
                alert('저장 실패: ' + err);
            });
        }
    };
});
