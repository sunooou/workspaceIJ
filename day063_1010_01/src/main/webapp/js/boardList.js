$(document).ready(function () {
    $('#searchKeyword').on( 'click',function () {
        var keyword = $('#keyword').val();
        console.log('js/boardList keyword = ['+keyword+']');
        var condition = $('#condition').val();
        console.log('js/boardList condition = ['+condition+']');

        $.ajax({
            url: 'boardList.do',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({'keyword':keyword,'condition':condition}),
            dataType: 'json',
            success: function (datas) {
                $('.board-list').empty();
                console.log('boardList.js에 비동기 데이터 도착 = [' + datas + ']');
                console.log('데이터 타입 = [' + typeof datas + ']');
                console.log('데이터 타입 = [' + JSON.stringify(datas) + ']');
                datas.forEach( data=> {
                    console.log('data = [' + JSON.stringify(data.content )  + ']');
                    console.log('data = [' + JSON.stringify(data.writer )  + ']');
                    var listItem = '<li class="board-item">' +
                        '<span class="board-title">' + data.content + '</span><br>' +
                        '<span class="board-writer">작성자: ' + data.writer + '</span>' +
                        '</li>';
                    $('#board-list').append(listItem);
                });
            },
            error: function (xhr, status, error) {
                console.error('AJAX 오류: ', error);
                console.error('응답 내용:', xhr.responseText);
            }
        });
    });
});