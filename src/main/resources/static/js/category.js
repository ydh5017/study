// 카테고리 선택
function getCatePostList(cateCode){
    const cateList = document.getElementById('cateList').getElementsByTagName('li');
    const category = document.getElementById('category-'+cateCode);
    const postType = document.getElementById('postType').innerText;
    const postTbody = document.getElementById('postTbody');
    const postThead = document.getElementById('postThead');
    const postDiv = document.getElementById('postDiv');

    for (let i = 0; i < cateList.length; i++){
        cateList.item(i).classList.remove('active');
    }

    category.className += ' active';

    if (cateCode == "all") {
        cateCode = "100";
    }
    postTbody.remove();

    $.ajax({
        url:"/api/catePostList",
        type:"get",
        dataType: 'json',
        data:{"cateCode":cateCode, "postType":postType},
        success:function(postList){
            const html = document.createElement("tbody");
            html.id = "postTbody";

            if (postList.length > 0) {
                for(let i = 0; i < postList.length; i++) {
                    html.innerHTML+= '<tr>\n' +
                        '                  <input type="hidden" id="postSeq" name="postSeq" value="' + postList[i].postSeq + '">\n' +
                        '                  <td style="color: #2a96a5">' + postList[i].cateName + '</td>\n' +
                        '                  <td onclick="location.href=\'post/detail?no=' + postList[i].postSeq + '\'">' + postList[i].title + '</td>\n' +
                        '                  <td><i class="fas fa-eye"></i> ' + postList[i].views + '</td>\n' +
                        '                  <td><i class="fas fa-thumbs-up"></i> ' + postList[i].likes + '</td>\n' +
                        '                  <td>' + postList[i].writeId + '</td>\n' +
                        '                  <td>' + postList[i].writeDt + '</td>\n' +
                        '             </tr>';
                }
            }else {
                html.innerHTML+= '<tr>\n' +
                    '                    <td colSpan="4">조회된 결과가 없습니다.</td>\n' +
                    '                </tr>';
            }
            postThead.append(html);
        },
        error: function (request, status, error) {
            alert("code: " + request.status + "\n" + "error: " + error);
        }
    })
};