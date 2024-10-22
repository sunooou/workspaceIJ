$(document).ready(function(){
	$('#mid').on('change',function(){
		var mid = $(this).val();
		console.log('js/checkID mid = ['+mid+']');
		
		if(mid) {
			$.ajax({
				url : 'checkMID.do',
				type : 'POST',
				data : {mid : mid},
				success : function(data){
					console.log('checkID.js에 비동기 데이터 도착 = ['+data+']');
					console.log('데이터 타입 = ['+typeof data+']');

					if(data == 'true') {
						$('#result').text('DB에 존재하는 아이디입니다.').css('color','red')
					}
					else {
						$('#result').text('DB에 없는 아이디입니다.').css('color','green')
					}
				}
			});
		}
		else{
			$('#result').text('');
		}
	});

});