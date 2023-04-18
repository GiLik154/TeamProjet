$(document).ready(function () {
    $('#user-login-button').click(function () {
        // 유저 로그인 버튼 클릭 시 '/user/login'으로 POST 방식으로 폼 데이터 전송
        $.post("/user/login", $("form").serialize(), function (data) {
            window.location.href = "/main";
        });
    });

    $('#seller-login-button').click(function () {
        // 셀러 로그인 버튼 클릭 시 '/seller/login-form'으로 POST 방식으로 폼 데이터 전송 후 페이지 이동
        $.post("/seller/login-form", $("form").serialize(), function (data) {
            window.location.href = "/main";
        });
    });

    $('#find-id-button').click(function () {
        // 아이디 찾기 버튼 클릭 시 아이디 찾기 페이지로 이동
    });

    $('#find-password-button').click(function () {
        // 비밀번호 찾기 버튼 클릭 시 비밀번호 찾기 페이지로 이동
    });
});