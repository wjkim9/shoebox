document.addEventListener('DOMContentLoaded', function() {
  let editingLi = null;

  document.querySelectorAll('.edit-btn').forEach(function(btn) {
    btn.addEventListener('click', function() {
      const li = this.closest('li');
      const brandName = li.querySelector('.brand-name').textContent;
      const brandImg = li.querySelector('img').src;
      openEditModal(brandName, '', brandImg, li);
    });
  });

  document.querySelectorAll('.delete-btn').forEach(function(btn) {
    btn.addEventListener('click', function() {
      this.closest('li').remove();
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
    editingLi = null;
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
  }

  // 이미지 미리보기
  document.getElementById('brandImage').addEventListener('change', function(e) {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function(ev) {
        document.getElementById('brandImgPreview').src = ev.target.result;
        document.getElementById('brandImgPreview').style.display = 'block';
      }
      reader.readAsDataURL(file);
    }
  });

  // 폼 제출(저장)
  document.getElementById('editBrandForm').onsubmit = function(e) {
    e.preventDefault();
    const newName = document.getElementById('brandName').value;
    const newCategory = document.getElementById('brandCategory').value;
    const newImgSrc = document.getElementById('brandImgPreview').src;

    if (editingLi) {
      // 수정
      editingLi.querySelector('.brand-name').textContent = newName;
      if (newImgSrc && newImgSrc.startsWith('data:')) {
        editingLi.querySelector('img').src = newImgSrc;
      }
    } else {
      // 추가
      const ul = document.querySelector('.brand-category-list');
      const li = document.createElement('li');
      const imgTag = `<img src="${newImgSrc && newImgSrc.startsWith('data:') ? newImgSrc : 'https://via.placeholder.com/100x50?text=Logo'}" alt="${newName}">`;
      li.innerHTML = `
        ${imgTag}
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <span class="brand-name">${newName}</span>
        <button type="button" class="edit-btn">수정</button>
        <button type="button" class="delete-btn">삭제</button>
      `;
      ul.appendChild(li);

      // 신규 버튼 이벤트 바인딩
      li.querySelector('.edit-btn').addEventListener('click', function() {
        openEditModal(newName, newCategory, li.querySelector('img').src, li);
      });
      li.querySelector('.delete-btn').addEventListener('click', function() {
        li.remove();
      });
    }

    alert('저장 되었습니다.');
    closeEditModal();
  };
});
