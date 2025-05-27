$(document).ready(function() {
    // 1) 메뉴 아코디언 기능 (기존)
    $('.menu-link').on('click', function(e) {
        if ($(this).next('.submenu').length > 0) {
            e.preventDefault();
            $(this).toggleClass('active');
            $(this).next('.submenu').toggleClass('open');
        }
    });

    // 2) 현재 활성 메뉴 셋팅 (기존)
    function setActiveMenuItem() {
        const currentPath = window.location.pathname;
        $('.submenu-link').each(function() {
            const linkPath = $(this).attr('href');
            if (currentPath.includes(linkPath)) {
                $(this).addClass('active');
                $(this).closest('.submenu').addClass('open');
                $(this).closest('.submenu').prev('.menu-link').addClass('active');
            }
        });
    }
    setActiveMenuItem();

    // 3) 서브메뉴 클릭 시 AJAX 로딩 & URL 갱신
    $(document).on('click', '.submenu-link', function(e) {
        e.preventDefault();
        const url = $(this).attr('href');

        // 메뉴 활성화 토글
        $('.submenu-link').removeClass('active');
        $(this).addClass('active');
        $('.menu-link').removeClass('active');
        $(this).closest('.submenu').prev('.menu-link').addClass('active');

        // AJAX 요청 (fragment만 반환하도록 컨트롤러 수정 필요)
        $.ajax({
            url: url,
            type: 'GET',
            headers: { 'X-Requested-With': 'XMLHttpRequest' },
            success: function(htmlFragment) {
                // admin_layout.html 의 <main class="content-wrapper"> 영역 교체
                $('.content-wrapper').html(htmlFragment);
                // 주소 표시줄만 변경
                history.pushState(null, null, url);
            },
            error: function(err) {
                console.error('콘텐츠 로드 오류:', err);
            }
        });
    });

    // 4) 뒤로/앞으로 버튼 처리
    window.addEventListener('popstate', function() {
        $.ajax({
            url: location.pathname,
            type: 'GET',
            headers: { 'X-Requested-With': 'XMLHttpRequest' },
            success: function(htmlFragment) {
                $('.content-wrapper').html(htmlFragment);
                setActiveMenuItem();
            }
        });
    });
});
