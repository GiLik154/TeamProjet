$(function() {
    // 각 목록 링크를 클릭하면 이벤트를 처리합니다.
    $('.sidebar a').on('click', function(e) {
        e.preventDefault(); // 링크의 기본 동작을 취소합니다.
        var linkHref = $(this).attr('href'); // 클릭한 링크의 href 속성 값을 가져옵니다.
        if (linkHref === '/order_list/view') {
            // 구매내역 링크일 경우, ajax를 사용하여 해당 내용을 불러옵니다.
            $.ajax({
                url: '/order_list/view',
                success: function(data) {
                    // 성공적으로 내용을 불러오면, main-content 영역에 삽입합니다.
                    $('.main-content').html(data);
                }
            });
        } else {
            // 다른 링크일 경우, iframe 요소를 숨기고, 링크의 href 속성 값을 클릭한 링크의 href 속성 값으로 변경합니다.
            $('iframe').hide();
            $('.main-content').load(linkHref);
        }
    });
});