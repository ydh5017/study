//대댓글 출력하기
function onReplyComment(commentSeq){
    const replyDiv = document.getElementById('replyDiv-'+commentSeq);
    const onReplyComment = document.getElementById('onReplyComment-'+commentSeq);
    const offReplyComment = document.getElementById('offReplyComment-'+commentSeq);

    replyDiv.style.display = ""
    onReplyComment.style.display = "none"
    offReplyComment.style.display = ""

    $.ajax({
        url:"/api/replyList",
        type:"get",
        dataType: 'json',
        data:{"commentSeq":commentSeq},
        success:function(ReplyCommentList){
            const html = document.createElement("div");
            html.id = "replyList-" + commentSeq;

            if (ReplyCommentList.length > 0) {
                for(let i = 0; i < ReplyCommentList.length; i++) {
                    if (ReplyCommentList[i].writer > 0) {
                        if (ReplyCommentList[i].liker > 0) {
                            html.innerHTML+= '<div class="col-xl-12" style="padding-left: 3rem">' +
                                '<div class="card border-left h-10 py-2" id="childComment-' + ReplyCommentList[i].commentSeq + '">' +
                                '   <div class="card-body">' +
                                '       <div class="row no-gutters align-items-center">' +
                                '           <div class="col mr-2">' +
                                '               <div class="border-bottom mb-1">' +
                                '                   <div class="text-xl-left font-weight-bold text-primary text-uppercase mb-1" style="float: left">'+
                                ReplyCommentList[i].commentWriteId +
                                '                   </div>' +
                                '                   <div class="text-xs text-right font-weight-bold text-uppercase mb-1">'+
                                ReplyCommentList[i].commentWriteDt +
                                '                   </div>' +
                                '               </div>' +
                                '               <div class="h5 mb-0 font-weight-bold text-gray-800 mt-2" style="float: left">'+
                                ReplyCommentList[i].content +
                                '               </div>' +
                                '               <div class="h5 mb-0 font-weight-bold text-gray-800 text-xl-left" style="float: right">' +
                                '                   <nav class="navbar mb-0 text-right" style="padding: 0rem 0.5rem;">' +
                                '                       <div class="navbar text-xs text-right font-weight-bold text-uppercase mb-0" data-toggle="dropdown">' +
                                '                           <i class="fas fa-solid fa-ellipsis-h -align-center mt-2"></i>' +
                                '                       </div>' +
                                '                       <div class="dropdown-menu dropdown-menu-right animated--fade-in" aria-labelledby="navbarDropdown">' +
                                '                           <a class="dropdown-item" onclick="return modifyReply(' + ReplyCommentList[i].postSeq + ', ' + ReplyCommentList[i].commentSeq + ', ' + "'" + ReplyCommentList[i].content +"'" + ')">수정</a>' +
                                '                           <form class="mr-1" method="post" action="/comment">' +
                                '                               <input type="hidden" name="_method" value="patch">' +
                                '                               <input type="hidden" name="commentSeq" value="' + ReplyCommentList[i].commentSeq + '">' +
                                '                               <input type="hidden" name="postSeq" value="' + ReplyCommentList[i].postSeq + '">' +
                                '                               <button class="dropdown-item" type="submit">삭제</button>' +
                                '                           </form>' +
                                '                       </div>' +
                                '                   </nav>' +
                                '               </div>' +
                                '           </div>' +
                                '       </div>' +
                                '   </div>' +
                                '   <!-- 댓글 좋아요 -->' +
                                '   <div class="col mr-2">' +
                                '       <a class="btn btn-block btn-circle btn-sm" id="commentLikeOnBtn-' + ReplyCommentList[i].commentSeq + '" style="border-color: #858796; display: none" onclick="return commentLike(' + ReplyCommentList[i].commentSeq + ')">' +
                                '          <i class="fas fa-thumbs-up"></i>' +
                                '       </a>' +
                                '       <a class="btn btn-info btn-circle btn-sm" id="commentLikeOffBtn-' + ReplyCommentList[i].commentSeq + '" onclick="return commentLike(' + ReplyCommentList[i].commentSeq + ')">' +
                                '          <i class="fas fa-thumbs-up"></i>' +
                                '       </a>' +
                                '       <span class="text-xs" id="commentLikeCount-' + ReplyCommentList[i].commentSeq + '">'+ ReplyCommentList[i].likes +'</span>' +
                                '   </div>' +
                                '</div>' +
                                '<div class="col-xl-3" id="ReplyModDiv-' + ReplyCommentList[i].commentSeq + '" style="display: none"></div>' +
                                '</div>';
                        }else {
                            html.innerHTML+= '<div class="col-xl-4" style="padding-left: 3rem">' +
                                '<div class="card border-left h-10 py-2" id="childComment-' + ReplyCommentList[i].commentSeq + '">' +
                                '   <div class="card-body">' +
                                '       <div class="row no-gutters align-items-center">' +
                                '           <div class="col mr-2">' +
                                '               <div class="border-bottom mb-1">' +
                                '                   <div class="text-xl-left font-weight-bold text-primary text-uppercase mb-1" style="float: left">'+
                                ReplyCommentList[i].commentWriteId +
                                '                   </div>' +
                                '                   <div class="text-xs text-right font-weight-bold text-uppercase mb-1">'+
                                ReplyCommentList[i].commentWriteDt +
                                '                   </div>' +
                                '               </div>' +
                                '               <div class="h5 mb-0 font-weight-bold text-gray-800 mt-2" style="float: left">'+
                                ReplyCommentList[i].content +
                                '               </div>' +
                                '               <div class="h5 mb-0 font-weight-bold text-gray-800 text-xl-left" style="float: right">' +
                                '                   <nav class="navbar mb-0 text-right" style="padding: 0rem 0.5rem;">' +
                                '                       <div class="navbar text-xs text-right font-weight-bold text-uppercase mb-0" data-toggle="dropdown">' +
                                '                           <i class="fas fa-solid fa-ellipsis-h -align-center mt-2"></i>' +
                                '                       </div>' +
                                '                       <div class="dropdown-menu dropdown-menu-right animated--fade-in" aria-labelledby="navbarDropdown">' +
                                '                           <a class="dropdown-item" onclick="return modifyReply(' + ReplyCommentList[i].postSeq + ', ' + ReplyCommentList[i].commentSeq + ', ' + "'" + ReplyCommentList[i].content +"'" + ')">수정</a>' +
                                '                           <form class="mr-1" method="post" action="/comment">' +
                                '                               <input type="hidden" name="_method" value="patch">' +
                                '                               <input type="hidden" name="commentSeq" value="' + ReplyCommentList[i].commentSeq + '">' +
                                '                               <input type="hidden" name="postSeq" value="' + ReplyCommentList[i].postSeq + '">' +
                                '                               <button class="dropdown-item" type="submit">삭제</button>' +
                                '                           </form>' +
                                '                       </div>' +
                                '                   </nav>' +
                                '               </div>' +
                                '           </div>' +
                                '       </div>' +
                                '   </div>' +
                                '   <!-- 댓글 좋아요 -->' +
                                '   <div class="col mr-2">' +
                                '       <a class="btn btn-block btn-circle btn-sm" id="commentLikeOnBtn-' + ReplyCommentList[i].commentSeq + '" style="border-color: #858796;" onclick="return commentLike(' + ReplyCommentList[i].commentSeq + ')">' +
                                '          <i class="fas fa-thumbs-up"></i>' +
                                '       </a>' +
                                '       <a class="btn btn-info btn-circle btn-sm" id="commentLikeOffBtn-' + ReplyCommentList[i].commentSeq + '" style="display: none" onclick="return commentLike(' + ReplyCommentList[i].commentSeq + ')">' +
                                '          <i class="fas fa-thumbs-up"></i>' +
                                '       </a>' +
                                '       <span class="text-xs" id="commentLikeCount-' + ReplyCommentList[i].commentSeq + '">'+ ReplyCommentList[i].likes +'</span>' +
                                '   </div>' +
                                '</div>' +
                                '<div class="col-xl-3" id="ReplyModDiv-' + ReplyCommentList[i].commentSeq + '" style="display: none"></div>' +
                                '</div>';
                        }
                    }else {
                        if (ReplyCommentList[i].liker > 0) {
                            html.innerHTML+= '<div class="col-xl-4" style="padding-left: 3rem">' +
                                '<div class="card border-left h-10 py-2">' +
                                '   <div class="card-body">' +
                                '       <div class="row no-gutters align-items-center">' +
                                '           <div class="col mr-2">' +
                                '               <div class="border-bottom mb-1">' +
                                '                   <div class="text-xl-left font-weight-bold text-primary text-uppercase mb-1" style="float: left">'+
                                ReplyCommentList[i].commentWriteId +
                                '                   </div>' +
                                '                   <div class="text-xs text-right font-weight-bold text-uppercase mb-1">'+
                                ReplyCommentList[i].commentWriteDt +
                                '                   </div>' +
                                '               </div>' +
                                '               <div class="h5 mb-0 font-weight-bold text-gray-800 mt-2">'+
                                ReplyCommentList[i].content +
                                '               </div>' +
                                '           </div>' +
                                '       </div>' +
                                '   </div>' +
                                '   <!-- 댓글 좋아요 -->' +
                                '   <div class="col mr-2">' +
                                '       <a class="btn btn-block btn-circle btn-sm" id="commentLikeOnBtn-' + ReplyCommentList[i].commentSeq + '" style="border-color: #858796; display: none" onclick="return commentLike(' + ReplyCommentList[i].commentSeq + ')">' +
                                '          <i class="fas fa-thumbs-up"></i>' +
                                '       </a>' +
                                '       <a class="btn btn-info btn-circle btn-sm" id="commentLikeOffBtn-' + ReplyCommentList[i].commentSeq + '" onclick="return commentLike(' + ReplyCommentList[i].commentSeq + ')">' +
                                '          <i class="fas fa-thumbs-up"></i>' +
                                '       </a>' +
                                '       <span class="text-xs" id="commentLikeCount-' + ReplyCommentList[i].commentSeq + '">'+ ReplyCommentList[i].likes +'</span>' +
                                '   </div>' +
                                '</div>' +
                                '</div>';
                        }else {
                            html.innerHTML+= '<div class="col-xl-4" style="padding-left: 3rem">' +
                                '<div class="card border-left h-10 py-2">' +
                                '   <div class="card-body">' +
                                '       <div class="row no-gutters align-items-center">' +
                                '           <div class="col mr-2">' +
                                '               <div class="border-bottom mb-1">' +
                                '                   <div class="text-xl-left font-weight-bold text-primary text-uppercase mb-1" style="float: left">'+
                                ReplyCommentList[i].commentWriteId +
                                '                   </div>' +
                                '                   <div class="text-xs text-right font-weight-bold text-uppercase mb-1">'+
                                ReplyCommentList[i].commentWriteDt +
                                '                   </div>' +
                                '               </div>' +
                                '               <div class="h5 mb-0 font-weight-bold text-gray-800 mt-2">'+
                                ReplyCommentList[i].content +
                                '               </div>' +
                                '           </div>' +
                                '       </div>' +
                                '   </div>' +
                                '   <!-- 댓글 좋아요 -->' +
                                '   <div class="col mr-2">' +
                                '       <a class="btn btn-block btn-circle btn-sm" id="commentLikeOnBtn-' + ReplyCommentList[i].commentSeq + '" style="border-color: #858796;" onclick="return commentLike(' + ReplyCommentList[i].commentSeq + ')">' +
                                '          <i class="fas fa-thumbs-up"></i>' +
                                '       </a>' +
                                '       <a class="btn btn-info btn-circle btn-sm" id="commentLikeOffBtn-' + ReplyCommentList[i].commentSeq + '" style="display: none" onclick="return commentLike(' + ReplyCommentList[i].commentSeq + ')">' +
                                '          <i class="fas fa-thumbs-up"></i>' +
                                '       </a>' +
                                '       <span class="text-xs" id="commentLikeCount-' + ReplyCommentList[i].commentSeq + '">'+ ReplyCommentList[i].likes +'</span>' +
                                '   </div>' +
                                '</div>' +
                                '</div>';
                        }
                    }
                }
            }else {
                html.innerHTML ='<div class="col-xl-4"  style="padding-left: 3rem">' +
                    '<div class="card border-left h-10 py-2">' +
                    '<div class="card-body">' +
                    '<div class="row no-gutters align-items-center">' +
                    '<div class="col mr-2">' +
                    '<div class="h5 mb-0 font-weight-bold text-gray-800">등록된 답글이 없습니다.</div>' +
                    '</div></div></div></div></div>';
            }
            replyDiv.append(html);
        },
        error: function (request, status, error) {
            alert("code: " + request.status + "\n" + "error: " + error);
        }
    })
};
// 대댓글 창 닫기
function offReplyComment(commentSeq){
    const replyDiv = document.getElementById('replyList-'+commentSeq);
    const onReplyComment = document.getElementById('onReplyComment-'+commentSeq);
    const offReplyComment = document.getElementById('offReplyComment-'+commentSeq);

    replyDiv.remove();
    onReplyComment.style.display = ""
    offReplyComment.style.display = "none"
};
// 답글 input창 열기, 닫기
function reply(i){
    const replyCommentPost = document.getElementById('replyCommentPost-'+i);
    if(replyCommentPost.style.display == "none"){
        replyCommentPost.style.display = ""
    }else if (replyCommentPost.style.display != "none"){
        replyCommentPost.style.display = "none"
    }
};
// 댓글 수정 창 열기
function modifyComment(commentSeq, postSeq, content){
    const parentComment = document.getElementById('parentComment-'+commentSeq);
    const modifyDiv = document.getElementById('modifyDiv-'+commentSeq);

    parentComment.style.display = "none"
    modifyDiv.style.display = ""

    const html = document.createElement("div");
    html.id = "parentCommentMod-" + commentSeq;
    html.innerHTML+= '<div class="card border-left h-10 py-2">' +
        '<div class="card-body">' +
        '<div class="row no-gutters align-items-center">' +
        '<div class="col mr-2">' +
        '<div class="h5 mb-0 font-weight-bold text-gray-800">' +
        '<form method="post" action="/comment">' +
        '<input type="hidden" name="_method" value="put">' +
        '<input type="hidden" name="commentDepth" value=0>' +
        '<input type="hidden" name="postSeq" value="' + postSeq + '">' +
        '<input type="hidden" name="commentSeq" value="' + commentSeq + '">' +
        '<input type="text" id="content" name="content" value="' + content + '">' +
        '<input type="submit" id="submit" value="수정" class="btn">' +
        '<a onclick="return commentModCancel('+ commentSeq +')">취소</a>' +
        '</form>' +
        '</div></div></div></div></div>';
    modifyDiv.append(html);
};
// 대댓글 수정 창 열기
function modifyReply(postSeq, commentSeq, content){
    const childComment = document.getElementById('childComment-'+commentSeq);
    const ReplyModDiv = document.getElementById('ReplyModDiv-'+commentSeq);

    childComment.style.display = "none"
    ReplyModDiv.style.display = ""

    const html = document.createElement("div");
    html.id = "childCommentMod-" + commentSeq;
    html.innerHTML+= '<div class="card border-left h-10 py-2">' +
        '<div class="card-body">' +
        '<div class="row no-gutters align-items-center">' +
        '<div class="col mr-2">' +
        '<div class="h5 mb-0 font-weight-bold text-gray-800">' +
        '<form method="post" action="/comment">' +
        '<input type="hidden" name="_method" value="put">' +
        '<input type="hidden" name="commentDepth" value=0>' +
        '<input type="hidden" name="postSeq" value="' + postSeq + '">' +
        '<input type="hidden" name="commentSeq" value="' + commentSeq + '">' +
        '<input type="text" id="content" name="content" value="' + content + '">' +
        '<input type="submit" id="submit" value="수정" class="btn">' +
        '<a onclick="return replyModCancel('+ commentSeq +')">취소</a>' +
        '</form>' +
        '</div></div></div></div></div>';
    ReplyModDiv.append(html);
};
// 댓글 수정 창 닫기
function commentModCancel(commentSeq){
    const parentComment = document.getElementById('parentComment-'+commentSeq);
    const parentCommentMod = document.getElementById('parentCommentMod-'+commentSeq);

    parentCommentMod.remove();
    parentComment.style.display = ""
};
// 대댓글 수정 창 닫기
function replyModCancel(commentSeq){
    const childComment = document.getElementById('childComment-'+commentSeq);
    const childCommentMod = document.getElementById('childCommentMod-'+commentSeq);

    childCommentMod.remove();
    childComment.style.display = ""
};