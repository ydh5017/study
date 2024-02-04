// 게시글 좋아요 on/off
function postLike(postSeq){
    const postLikeOnBtn = document.getElementById('postLikeOnBtn');
    const postLikeOffBtn = document.getElementById('postLikeOffBtn');
    const likeCount = document.getElementById('postLikeCount');

    if (postLikeOffBtn.style.display == "none"){
        $.ajax({
            url:"/api/postLikeInc",
            type:"post",
            data:{"postSeq":postSeq},
            success:function(){
                postLikeOffBtn.style.display = ""
                postLikeOnBtn.style.display = "none"
                likeCount.innerText = Number(likeCount.innerText) + 1;
            },
            error: function (request, status, error) {
                alert("로그인 후 이용해주세요.");
            }
        })
    } else {
        $.ajax({
            url:"/api/postLikeDec",
            type:"delete",
            data:{"postSeq":postSeq},
            success:function(){
                postLikeOffBtn.style.display = "none"
                postLikeOnBtn.style.display = ""
                likeCount.innerText = Number(likeCount.innerText) - 1;
            },
            error: function (request, status, error) {
                alert("로그인 후 이용해주세요.");
            }
        })
    }

};
// 댓글 좋아요 on/off
function commentLike(commentSeq){
    const commentLikeOnBtn = document.getElementById('commentLikeOnBtn-'+commentSeq);
    const commentLikeOffBtn = document.getElementById('commentLikeOffBtn-'+commentSeq);
    const likeCount = document.getElementById('commentLikeCount-'+commentSeq);

    if (commentLikeOffBtn.style.display == "none"){
        $.ajax({
            url:"/api/commentLikeInc",
            type:"post",
            data:{"commentSeq":commentSeq},
            success:function(){
                commentLikeOffBtn.style.display = ""
                commentLikeOnBtn.style.display = "none"
                likeCount.innerText = Number(likeCount.innerText) + 1;
            },
            error: function (request, status, error) {
                alert("로그인 후 이용해주세요.");
            }
        })
    } else {
        $.ajax({
            url:"/api/commentLikeDec",
            type:"delete",
            data:{"commentSeq":commentSeq},
            success:function(){
                commentLikeOffBtn.style.display = "none"
                commentLikeOnBtn.style.display = ""
                likeCount.innerText = Number(likeCount.innerText) - 1;
            },
            error: function (request, status, error) {
                alert("로그인 후 이용해주세요.");
            }
        })
    }

};