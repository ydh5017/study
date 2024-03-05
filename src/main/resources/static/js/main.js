// 대댓글 창 닫기
function setPostType(postType){
    const postTypeUl = document.getElementById('postTypeUl');
    const week = document.getElementById('week');
    const day = document.getElementById('day');
    const href = document.getElementById('href');
    const table = document.getElementById('table');
    const tbody = document.getElementById('tbody');

    const pTypeList = postTypeUl.getElementsByTagName('li')
    for (let i = 0; i < pTypeList.length; i++) {
        pTypeList.item(i).classList.remove('active');
    }
    if (postType == "week"){
        week.className += ' active';
        href.href = "/post?postType=week&cateCode=100&pno=1"
    }else if (postType == "day") {
        day.className += ' active';
        href.href = "/post?postType=day&cateCode=100&pno=1"
    }

    tbody.remove();

    $.ajax({
        url:"/api/PopularPost",
        type:"get",
        dataType: 'json',
        data:{"postType":postType},
        success:function(postList){
            const html = document.createElement("tbody");
            html.id = "tbody";

            if (postList.length > 0) {
                for (let i = 0; i < postList.length; i++){
                    html.innerHTML += '              <tr>\n' +
                        '                                <input type="hidden" id="postSeq" name="postSeq" value="' + postList[i].postSeq + '">\n' +
                        '                                <td style="color: #2a96a5">' + postList[i].cateName + '</td>\n' +
                        '                                <td onclick="location.href=\'post/detail?no=' + postList[i].postSeq + '\'">' + postList[i].title + '</td>\n' +
                        '                                <td><i class="fas fa-eye"></i> ' + postList[i].views + '</td>\n' +
                        '                                <td><i class="fas fa-thumbs-up"></i> ' + postList[i].views + '</td>\n' +
                        '                                <td>' + postList[i].writeId + '</td>\n' +
                        '                                <td>' + postList[i].writeDt + '</td>\n' +
                        '                            </tr>';
                }
            }else {
                html.innerHTML += '<tr>\n' +
                    '                                <td colspan="4">조회된 결과가 없습니다.</td>\n' +
                    '                            </tr>';
            }

            table.append(html);
        },
        error: function (request, status, error) {
            alert("code: " + request.status + "\n" + "error: " + error);
        }
    })
};