document.getElementById('cancel-button').addEventListener('click', function() {
    if (confirm('주문을 취소하시겠습니까?')) {
        document.getElementById('cancel-form').submit();
    }
});


    // $('#cancel-button').click(function () {
    //     $.post("/order/cancel", $("form").serialize(), function (data) {
    //         var orderListId = $('input[name="orderListId"]').val();
    //         window.location.href = "/order/view/detail/" + orderListId;
    //     });
    // });

// $(document).ready(function () {
//     $('#buy-button').click(function () {
//         // 주문 생성 버튼 클릭 시 '/order/create'으로 GET 방식으로 요청을 보내고, 요청에 필요한 데이터를 파라미터로 보냄
//         var productId = $('input[name="productId"]').val();
//         var salesCount = $('input[name="salesCount"]').val();
//         window.location.href = "/order/create/" + productId + "/" + salesCount;
//     });
// });



