
// 정렬값 보내기
/* function sort() {
	
	// selected sort 값 가져오기
	var sort = $("#sort").val();
	
	
	}
	
	$.ajax( {
		url: "all",
		data: {"sort" : sort},
		type: "GET",
		success : function(data) {
			alert("성공")
		},
		error : function() {
			alert("에러")
		}
	}) */



// cno -> cafe/cno (카페개별페이지) 보내기
function sendToInfo(variable) {
	location.href = `/cafe/${variable}`;
}

// lino -> cafe/list/{lino} (리스트 개별페이지) 보내기
function sendToListInfo(variable) {
	location.href = `/cafe/list/${variable}`;
}


// 북마크 여부 확인
 $(document).ready(function () {
        let bmkVal = document.getElementById('bmk_check').value
        const cno = $("#cno").val();
        const username = $("#username").val();
        console.log(cno);
        console.log(username);
        const bmkImg = document.getElementById("bmkImg")
        console.log(bmkVal);
		
        if (bmkVal > 0) {
            bmkImg.src = "/img/bmk.png";
        } else {
             bmkImg.src = "/img/bmk-empty.png";
        }
        
// 북마크 버튼 클릭시 실행되는 코드
/*        $("#bmkImg").on("click", function () {
            $.ajax({
                url: 'cafe/{cno}',
                type: 'GET',
                data: {'cno': cno, 'username': username},
                success: function (data) {
                    if (data == 1) {
                        $("#bmkImg").attr("src", "/img/bmk.png");
                        location.href="cafe/"+cno;
                    } else {
                        $("#bmkImg").attr("src", "/img/bmk-empty.png");
                        location.href="cafe/"+cno;
                    }
                }, error: function () {
                    $("#bmkImg").attr("src", "/img/bmk.png");
                    console.log('오타 찾으세요')
                }

            });

        });
    });

*/



