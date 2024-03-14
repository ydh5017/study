// 대댓글 창 닫기
function movePostDetail(postSeq, commentSeq){
    location.href='/post/detail?no='+postSeq+'#'+commentSeq;
};
function drag(){
    const start = document.getElementById('start');
    const and = document.getElementById('and');
    window.getSelection().setBaseAndExtent(start, 1, and, 0);
};