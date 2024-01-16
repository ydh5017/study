$(function(){
    // 로그인 유효성 검사
    $("#loginBtn").click(function(){

        var  id = document.getElementById("id");
        var  password = document.getElementById("password");

        if(id.value==""){
            alert("아이디를 입력해주세요.")
            id.focus();
            return false;
        }
        if(password.value==""){
            alert("비밀번호를 입력해주세요.")
            password.focus();
            return false;
        }
    })

    // 임시 비밀번호 발급 유효성 검사
    $("#findPassword").click(function(){

        var  id = document.getElementById("id");

        if(id.value==""){
            alert("아이디를 입력해주세요.")
            id.focus();
            return false;
        }
    })

    // 회원가입 유효성 검사
    $("#registerBtn").click(function(){

        var  name = document.getElementById("name");
        var  id = document.getElementById("id");
        var  password = document.getElementById("password");
        var  repeatPassword = document.getElementById("repeatPassword");

        if(name.value==""){
            alert("이름을 입력해주세요.")
            name.focus();
            return false;
        }
        if(id.value==""){
            alert("아이디를 입력해주세요.")
            id.focus();
            return false;
        }
        if(password.value==""){
            alert("비밀번호를 입력해주세요.")
            password.focus();
            return false;
        }
        if(repeatPassword.value==""){
            alert("비밀번호 확인을 입력해주세요.")
            repeatPassword.focus();
            return false;
        }
        if(repeatPassword.value!=password.value){
            alert("비밀번호와 비밀번호 확인을 똑같이 입력해주세요.")
            password.focus();
            return false;
        }
    })
})