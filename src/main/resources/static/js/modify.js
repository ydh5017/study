$(function(){
    // 비밀번호 변경
    $("#passwordChg").click(function(){

        var  currentPassword = document.getElementById("currentPassword");
        var  newPassword = document.getElementById("newPassword");
        var  passwordConfirm = document.getElementById("passwordConfirm");


        if(currentPassword.value==""){
            alert("현재 비밀번호를 입력해 주세요.")
            currentPassword.focus();
            return false;
        }
        if(newPassword.value==""){
            alert("새 비밀번호를 입력해 주세요.")
            newPassword.focus();
            return false;
        }
        if(passwordConfirm.value==""){
            alert("새 비밀번호 확인을 입력해 주세요.")
            passwordConfirm.focus();
            return false;
        }
        if(passwordConfirm.value!=newPassword.value){
            alert("새 비밀번호와 새 비밀번호 확인을 똑같이 입력해 주세요.")
            passwordConfirm.focus();
            return false;
        }
        if(currentPassword.value==newPassword.value){
            alert("현재 비밀번호와 새 비밀번호를 다르게 입력해주세요.")
            passwordConfirm.focus();
            return false;
        }
    })

    // 회원정보 수정
    $("#userInfoChg").click(function(){

        var  userName = document.getElementById("userName");

        if(userName.value==""){
            alert("name을 입력해주세요.")
            userName.focus();
            return false;
        }
    })
})