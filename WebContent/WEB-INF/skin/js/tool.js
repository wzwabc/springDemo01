$(function() {
	// 点击确定按钮，提交数据到后台，并把返回的数据填写到页面上。
	$( "#confirm" ).click(function() {
		var curMonthSalary = $( "#curMonthSalary" ).val();
		$.ajax({
			url: context + "/service/tax/monthtax",
			data: { "curMonthSalary": $( "#curMonthSalary" ).val() },
			dataType: "json",
			success: function( data ) {
				// 获取返回的数据，并写到页面上。
				$( ".monthTax" ).text( data.monthTax );
				$( ".realWage" ).text( data.realWage );
			}
		})
	});
	
	// 点击重置按钮，将页面数据清空。
	$( "#reset" ).click(function() {
		$( ".monthTax" ).text( "" );
		$( ".realWage" ).text( "" );
	});
})
