$(function() {
	// 点击确定按钮，提交数据到后台，并把返回的数据填写到页面上。
	$("#confirm").click(function() {
		confirmDispachter();
	});

	// 点击重置按钮，将页面数据清空。
	$("#reset").click(function() {
		$("#curMonthSalary").val("");
		$("#yearCash").val("");
		$(".d").text("");
	});
	// 初始化时显示的项。
	var method = $("#method").val();
	$("." + method).show();

	// 选择计算方式时，显示相应的项。
	$("#method").change(function() {
		// 先将所有项隐藏
		$(".a, .b, .c").hide();
		// 再显示相应的项
		$("." + $(this).val()).show();
		$("#reset").trigger("click");
	});

	// 点击确定按钮，根据不同的计算类型，选择相应的计算方法。
	function confirmDispachter() {
		var method = $("#method").val();
		switch (method) {
		case "a":
			calculateMonthTax();
			break;
		case "b":
			calculateYearTax();
			break;
		case "c":
			calculateOptSolution();
			break;
		}
	}
	// 计算个人所得税
	function calculateMonthTax() {
		var curMonthSalary = $("#curMonthSalary").val();
		$.ajax({
			url : context + "/service/tax/monthtax",
			data : {
				"curMonthSalary" : curMonthSalary
			},
			dataType : "json",
			success : function(data) {
				// 获取返回的数据，并写到页面上。
				$(".monthTax").text(data.monthTax);
				$(".realWage").text(data.realWage);
			}
		})
	}

	// 计算年终奖所得税
	function calculateYearTax() {
		// 月工资
		var curMonthSalary = $("#curMonthSalary").val();
		// 年终奖
		var yearCash = $("#yearCash").val();
		$.ajax({
			url : context + "/service/tax/yearTax",
			data : {
				"curMonthSalary" : curMonthSalary,
				"yearCash" : yearCash
			},
			dataType : "json",
			success : function(data) {
				// 获取返回的数据，并写到页面上。
				$(".yearTax").text(data.yearTax);
				$(".yearCashAfterTax").text(data.yearCashAfterTax);
			}
		})
	}
	// 计算最优年终奖方案
	function calculateOptSolution() {
		// 月工资
		var curMonthSalary = $("#curMonthSalary").val();
		// 年终奖
		var yearCash = $("#yearCash").val();
		$.ajax({
			url : context + "/service/tax/minYearTax",
			data : {
				"curMonthSalary" : curMonthSalary,
				"yearCash" : yearCash
			},
			dataType : "json",
			success : function(data) {
				// 获取返回的数据，并写到页面上。
				$(".optYearBonus").text(data.optYearBonus);
				$(".optMonth1Bonus").text(data.optMonth1Bonus);
				$(".optMonth2Bonus").text(data.optMonth2Bonus);
				$(".realTotalBonus").text(data.realTotalBonus);
			}
		})
	}

});
