//Viết các hàm javascript sử dụng chung
CommonFunction = {
	// Đi đến một trang cụ thể trong khi xem danh sách
	// p: trang muốn đi đến, curPage: trang hiện tại,maxPage: trang tối đa có
	// thể đi đến
	goToPageNum : function(p, curPage, maxPage, e) {
		if($("#filter") != null && $("#filter").val() != null && $("#filter").val() != ""){
			var obj = JSON.parse($("#filter").val());
			jQuery.each(obj, function(i, val) {
				console.log(val);
				 $("#" + i).val(val);
                                 
			});
		}
		else
			$("#frmSearch")[0].reset();
		if (e != null) {
			if (e.keyCode == 13 && p > 0 && p != parseInt(curPage)
					&& p <= parseInt(maxPage)) {
				$("#page").val(p);
                                $("#typeAction").val("1");
				$("#frmSearch").submit();
			}
		} else {
			$("#page").val(p);
                        $("#typeAction").val("1");
			$("#frmSearch").submit();
		}
	},
	 alpha:function(e) {
	    var k;
	    document.all ? k = e.keyCode : k = e.which;
	    return (k!=34 && k!=39 && k!=47 && k!=92 && k!=124);
	},
	replaceSpecialChar:function(str){
		str = CommonFunction.replaceAllSC("\"","",str);
		str = CommonFunction.replaceAllSC("\'","",str);
		str = CommonFunction.replaceAllSC("\\","",str);
		str = CommonFunction.replaceAllSC("\/","",str);
		return str;
	},
	// Lưu danh sách các bản ghi được chọn
	// item: checkbox;
	
	replaceAllSC:function(find, replace, str){
		while( str.indexOf(find) > -1)
	      {
	        str = str.replace(find, replace);
	      }
	      return str;
	},
	
	selectedItems : function(item) {
		var value = item.value;
		if (value != "0") {
			var ids = $("#hdfIds").val();
			if (item.checked) {
				if (ids == ";") {
					$("#hdfIds").val(ids + value + ";");
				} else {
					if (ids.indexOf(";" + value + ";") < 0)
						$("#hdfIds").val(ids + value + ";");
				}
			} else {
				var re = new RegExp(';' + value + ';', 'g');
				$("#hdfIds").val(ids.replace(re, ";"));
			}
		} else {
			var ids = ";";
			if (item.checked) {
				$("table.table > tbody > tr").each(function() {
					var id = $('td:eq(0) input', this).val();
					if(id != null){
						if (ids == ";")
							ids = ids + id + ";";
						else if (ids.indexOf(";" + id + ";") < 0)
							ids = ids + id + ";";
					}
				});
				$("#hdfIds").val(ids);
			} else {
				$("#hdfIds").val(";");
			}
		}
	},
	// Hiển thị popup thông báo
	// title: tiêu đề thông báo, msg: nội dung thông báo
	// type: error, warning...
	showPopUpMsg : function(title, msg, type) {
		$('#confirm-delete').modal('hide');
		$("#popup-message #myModalLabel").empty();
		if (type.indexOf("error") > -1) {
			$("#popup-message #myModalLabel").append("<i class='fa fa-warning'></i>");
			$("#popup-message .modal-header").addClass("alert-danger");
		} else {
			$("#popup-message .modal-header").removeClass("alert-danger");
		}
		$("#popup-message #myModalLabel").append(title);
		$("#popup-message .modal-body").text(msg);
		$("#popup-message").modal('show');
	},
	// Xóa một hoặc nhiều bản ghi bằng ajax
	// id: id của bản ghi đó
	infoLog : function(id) {
		if ($("#hdfIdInfo").val() != ";")
			$("#frmInfo").submit();
		else
			CommonFunction.showPopUpMsg(CommonMessenger.ErrorTitle,
					CommonMessenger.MustChoiceBeforeDelete,
					CommonMessenger.errorType);
	},
	deleteItems : function(id) {
		if ($("#hdfIds").val() != ";")
			$("#frmDelete").submit();
		else
			CommonFunction.showPopUpMsg(CommonMessenger.ErrorTitle,
					CommonMessenger.MustChoiceBeforeDelete,
					CommonMessenger.errorType);
	},
	deleteFile : function(id, file) {
		var ids = $("#fileIdsDel").val();
		if (ids == ";") {
			$("#fileIdsDel").val(ids + id + ";");
		} else {
			if (ids.indexOf(";" + id + ";") < 0)
				$("#fileIdsDel").val(ids + id + ";");
		}
		localStorage.setItem("fileDel" + id,$(file).html());
		localStorage.setItem("fileDelImage" + id,$(file).css("background-image"));
		$(file).css("background-image","none");
		$(file).html("<p class=\"reject text-orange\" onclick=\"CommonFunction.rejectDeleteFile("+ id +", this.parentNode)\">Hoàn tác</p>");
	}, 
	rejectDeleteFile : function(id, file){
		if(localStorage.getItem("fileDel" + id))
			$(file).html(localStorage.getItem("fileDel" + id));
		var re = new RegExp(';' + id + ';', 'g');
		$("#fileIdsDel").val($("#fileIdsDel").val().replace(re, ";"));
		if(localStorage.getItem("fileDelImage" + id))
			$(file).css("background-image", localStorage.getItem("fileDelImage" + id));
	},
	downloadFile: function(e, id){
		if(e.target.tagName.indexOf("DIV") > -1)
		{
			var a = document.createElement('a');
			a.href='download.html?id=' + id;
			a.target = '_blank';
			document.body.appendChild(a);
			a.click();
		}
	},
	
	formatCurrency : function(ctrl, event) {
	    //Check if arrow keys are pressed - we want to allow navigation around textbox using arrow keys
	    if (event != null)
	    {
	    	if(event.keyCode == 37 || event.keyCode == 38 || event.keyCode == 39 || event.keyCode == 40)
	    		return false;
	    }
	    var val = ctrl.value.replace(/,/g, "");
	    val = '' + (val != '' ? parseInt(val) : '');
	    ctrl.value = "";
	    val += '';
	    x = val.split('.');
	    x1 = x[0];
	    x2 = x.length > 1 ? '.' + x[1] : '';

	    var rgx = /(\d+)(\d{3})/;

	    while (rgx.test(x1)) {
	        x1 = x1.replace(rgx, '$1' + ',' + '$2');
	    }

	    ctrl.value = x1 + x2;
	},
	
	formatCurrencyHtml : function(ctrl) {
	    var val = ctrl.innerHTML;
	    val = val.replace(/,/g, "");
	    val += '';
	    x = val.split('.');
	    x1 = x[0];
	    x2 = x.length > 1 ? '.' + x[1] : '';

	    var rgx = /(\d+)(\d{3})/;

	    while (rgx.test(x1)) {
	        x1 = x1.replace(rgx, '$1' + ',' + '$2');
	    }

	    ctrl.innerHTML = x1 + x2;
	},

	checkNumeric: function(event) {
		var x = event.which || event.keyCode;
	    return x == 8 || x == 46 || (x >= 48 && x <= 57);
	},
	
	/*checkRound: function(ctrl){
		 if(ctrl.value.length>=5){
		     ctrl.value=ctrl.value.substring(0, ctrl.value.length-3)+"000";
		     var checkzero = CommonFunction.replaceAll(",","",ctrl.value);
		     if(parseInt(checkzero)==0){
					ctrl.value="0";
				}
		 }else{
			 ctrl.value="0";
		 }
	},*/
	
	currencyToNum:function(ctrl){
		if(ctrl.value!=""){
			ctrl.value=CommonFunction.replaceAll(",","",ctrl.value);
		}
	},
	replaceAll:function(find, replace, str) {
	  return str.replace(new RegExp(find, 'g'), replace);
	},
	submitForm: function (){
		$(".currency").each(function(){
			CommonFunction.currencyToNum(this);
		});
		$('#btnSave').click();
		$(".currency").each(function(){
			CommonFunction.formatCurrency(this, null);
		});
	},
	
	//BIỂU ĐỒ
	initLineChart : function (canvas, datas,dates,datelength,maxY){
		var speed = 20;
		var ctx = canvas.getContext("2d");
		var paddingleft = 130;
		var paddingtop = 200;
		var marginleft = 80;
		var margintop = 50;
		var width = canvas.width-paddingleft;
		var height = canvas.height-paddingtop;
		var totalRow = 10;
		var unitPerRow = maxY/totalRow;
		//var dates = ["01/11/2015","02/11/2015","03/11/2015","04/11/2015","05/11/2015","06/11/2015","07/11/2015","08/11/2015","09/11/2015","10/11/2015"];
		//var datelength = 10;
		
		var colPerCol = datelength > 10 ? 5 : 1;
		var totalCol = datelength/colPerCol;
		var colPerColLastest = datelength%colPerCol;
		var spacingVertical = height/totalRow;
		var xyColor = "#416FBD";
		var cellBorderColor = "#DADCDE";
		
		var data = datas[0];
		var spacingHorizontal = width/(colPerCol == 1 ? totalCol-1 : totalCol);
		var spacingHorizontalChild = spacingHorizontal/colPerCol;
		
		ctx.font = "14px Arial";
		var middleCol = Math.round(colPerCol/2);
		if(colPerCol%2 > 0 && colPerCol%2 == middleCol)
			middleCol++;
		var colLoop = colPerCol == 1 ? totalCol : totalCol+1;
		
		//Draw Column
		for(var i = 0; i < colLoop; i++)
		{
			var hidden = false;
			ctx.beginPath();
			if(i==0)
			{
				ctx.lineWidth = 3;
				ctx.strokeStyle = xyColor;
			}
			else
			{
				ctx.lineWidth = 1;
				ctx.strokeStyle = cellBorderColor;
			}
			if(i==0)
			{
				ctx.moveTo(marginleft+i*spacingHorizontal,margintop-2);
				ctx.lineTo(marginleft+i*spacingHorizontal,height+margintop+6);
				//Index of col
				if(i < dates.length)
					ctx.fillText(dates[i],50+i*spacingHorizontal,height+70);
			}
			else if(i < datelength/colPerCol-1)
			{
				ctx.moveTo(marginleft+i*spacingHorizontal- (colPerCol == 1 ? 0 : spacingHorizontalChild),margintop);
				ctx.lineTo(marginleft+i*spacingHorizontal- (colPerCol == 1 ? 0 : spacingHorizontalChild),height+margintop);
				//Index of col
				if(i < dates.length)
					ctx.fillText(dates[i],50+i*spacingHorizontal- (colPerCol == 1 ? 0 : spacingHorizontalChild),height+70);
			}else
			{
				if(colPerColLastest > 0)
				{
					if(i < datelength/colPerCol)
					{
						if(colPerColLastest>=middleCol)
						{
							ctx.moveTo(marginleft+i*spacingHorizontal-spacingHorizontalChild,margintop);
							ctx.lineTo(marginleft+i*spacingHorizontal-spacingHorizontalChild,height+margintop);
							//Index of col
							if(i < dates.length)
								ctx.fillText(dates[i],50+i*spacingHorizontal-spacingHorizontalChild,height+70);
						}
						else
							hidden = true;
					}
					else
					{
						ctx.moveTo(marginleft+i*spacingHorizontal-spacingHorizontalChild*(colPerCol-colPerColLastest+1),margintop);
						ctx.lineTo(marginleft+i*spacingHorizontal-spacingHorizontalChild*(colPerCol-colPerColLastest+1),height+margintop);
						//Index of col
						if(i < dates.length)
							ctx.fillText(dates[i],50+i*spacingHorizontal-spacingHorizontalChild*(colPerCol-colPerColLastest+1),height+70);
					}
				}
				else
				{
					ctx.moveTo(marginleft+i*spacingHorizontal-(colPerCol == 1 ? 0 : spacingHorizontalChild),margintop);
					ctx.lineTo(marginleft+i*spacingHorizontal-(colPerCol == 1 ? 0 : spacingHorizontalChild),height+margintop);
					//Index of col
					if(i < dates.length)
						ctx.fillText(dates[i],50+i*spacingHorizontal-(colPerCol == 1 ? 0 : spacingHorizontalChild),height+70);
				}
			}
			ctx.stroke();
			ctx.closePath();
			if(i>0 && !hidden){
				ctx.beginPath();
				ctx.lineWidth = 3;
				ctx.strokeStyle = xyColor;
				if(i >= colLoop - 1 && colPerColLastest > 0)
				{
					ctx.moveTo(marginleft+i*spacingHorizontal-spacingHorizontalChild*(colPerCol-colPerColLastest+1),height+margintop-2);
					ctx.lineTo(marginleft+i*spacingHorizontal-spacingHorizontalChild*(colPerCol-colPerColLastest+1),height+margintop+6);
				}
				else {
					ctx.moveTo(marginleft+i*spacingHorizontal-(colPerCol == 1 ? 0 : spacingHorizontalChild),i == colLoop - 1 ? height+margintop-2 : height+margintop);
					ctx.lineTo(marginleft+i*spacingHorizontal-(colPerCol == 1 ? 0 : spacingHorizontalChild),height+margintop+6);
				}
				ctx.stroke();
				ctx.closePath();
				ctx.beginPath();
			}
		}
		
		//Draw row
		for(var i = 0; i < 11; i++)
		{
			ctx.beginPath();
			ctx.lineWidth = 3;
			ctx.strokeStyle = xyColor;
			ctx.moveTo(i%2==0 ? marginleft - 12 : marginleft - 6,i*spacingVertical+margintop);
				
			ctx.lineTo(marginleft,i*spacingVertical+margintop);
			ctx.stroke();
			ctx.closePath();
			ctx.beginPath();
			if(i==10)
			{
				ctx.lineWidth = 3;
				ctx.strokeStyle = xyColor;
			}
			else
			{
				ctx.lineWidth = 1;
				ctx.strokeStyle = cellBorderColor;
			}
			ctx.moveTo(marginleft+2,i*spacingVertical+margintop);
			ctx.lineTo(width+marginleft-(colPerCol == 1 ? 0 : spacingHorizontalChild),i*spacingVertical+margintop);
			//Index of row
			if(totalRow-i==0)
			{
				ctx.fillText("0",50,i*spacingVertical+53);
			}
			else
			if(i%2==0)
			{
				var str = maxY/totalRow*(totalRow-i) + '';
				var margleft = 50;
				if(str.length == 2)
					margleft = 42;
				if(str.length == 3)
					margleft = 34;
				else if(str.length == 4)
					margleft = 26;
				else if(str.length == 5)
					margleft = 19;
				else if(str.length == 6)
					margleft = 11;
				else if(str.length == 7)
					margleft = 5;
				ctx.fillText(maxY/totalRow*(totalRow-i),margleft,i*spacingVertical+53);
			}
			ctx.stroke();
			ctx.closePath();
		}
		
		//Draw Description
		ctx.strokeStyle= "green";
		ctx.fillStyle= "green";
		ctx.lineWidth = 2;
		ctx.beginPath();
		ctx.arc(170,height+100, 3, 0, 2 * Math.PI, false);
		ctx.fill();
		ctx.closePath();
		ctx.beginPath();
		ctx.moveTo(170,height+100);
		ctx.lineTo(270,height+100);
		ctx.stroke();
		ctx.closePath();
		ctx.beginPath();
		ctx.arc(270,height+100, 3, 0, 2 * Math.PI, false);
		ctx.fill();
		ctx.fillText("Số thường trả trước",160,height+120);
		ctx.closePath();
		
		ctx.strokeStyle= "blue";
		ctx.fillStyle= "blue";
		ctx.beginPath();
		ctx.arc(370,height+100, 3, 0, 2 * Math.PI, false);
		ctx.fill();
		ctx.closePath();
		ctx.beginPath();
		ctx.moveTo(370,height+100);
		ctx.lineTo(470,height+100);
		ctx.stroke();
		ctx.closePath();
		ctx.beginPath();
		ctx.arc(470,height+100, 3, 0, 2 * Math.PI, false);
		ctx.fill();
		ctx.fillText("Số thường trả sau",360,height+120);
		ctx.closePath();
		
		ctx.strokeStyle= "orange";
		ctx.fillStyle= "orange";
		ctx.beginPath();
		ctx.arc(570,height+100, 3, 0, 2 * Math.PI, false);
		ctx.fill();
		ctx.closePath();
		ctx.beginPath();
		ctx.moveTo(570,height+100);
		ctx.lineTo(670,height+100);
		ctx.stroke();
		ctx.closePath();
		ctx.beginPath();
		ctx.arc(670,height+100, 3, 0, 2 * Math.PI, false);
		ctx.fill();
		ctx.fillText("Cam kết (CK 10, CK 11)",550,height+120);
		ctx.closePath();
		
		//Draw Line
		var verticalCoefficient = height/maxY;//1000/10=100
		ctx.strokeStyle= "green";
		ctx.fillStyle= "green";
		ctx.lineWidth = 2;
		for(var currCount1 = 1; currCount1 < data.length; currCount1++){
			if(currCount1 == 1)
			{
				ctx.beginPath();
				ctx.arc(marginleft,height - data[0]*verticalCoefficient + margintop, 3, 0, 2 * Math.PI, false);
				ctx.fill();
				ctx.closePath();
				ctx.beginPath();
				ctx.moveTo(marginleft,height - data[0]*verticalCoefficient + margintop);
			}
			ctx.lineTo(marginleft + currCount1*spacingHorizontalChild,height - data[currCount1]*verticalCoefficient + margintop);
			ctx.stroke();
			ctx.closePath();
			ctx.beginPath();
			ctx.arc(marginleft + currCount1*spacingHorizontalChild,height - data[currCount1]*verticalCoefficient + margintop, 3, 0, 2 * Math.PI, false);
			ctx.fill();
			ctx.closePath();
			ctx.beginPath();
			ctx.moveTo(marginleft + currCount1*spacingHorizontalChild, height - data[currCount1]*verticalCoefficient + margintop);
			
			if(currCount1 == data.length-1)
			{
				ctx.lineTo(marginleft + currCount1*spacingHorizontalChild,height - data[currCount1]*verticalCoefficient + margintop);
				ctx.stroke();
				ctx.closePath();
			}
		}
		data = datas[1];
		ctx.strokeStyle= "blue";
		ctx.fillStyle= "blue";
		ctx.beginPath();
		ctx.arc(marginleft,height - data[0]*verticalCoefficient + margintop, 3, 0, 2 * Math.PI, false);
		ctx.fill();
		ctx.closePath();
		ctx.beginPath();
		ctx.moveTo(marginleft,height - data[0]*verticalCoefficient + margintop);
		for(var currCount2 = 1; currCount2 < data.length; currCount2++){
			ctx.lineTo(marginleft + currCount2*spacingHorizontalChild,height - data[currCount2]*verticalCoefficient + margintop);
			ctx.stroke();
			ctx.closePath();
			ctx.beginPath();
			ctx.arc(marginleft + currCount2*spacingHorizontalChild,height - data[currCount2]*verticalCoefficient + margintop, 3, 0, 2 * Math.PI, false);
			ctx.fill();
			ctx.closePath();
			//ctx.fillText(data[i],margintop + i*spacingHorizontal,height - data[i]/10*verticalCoefficient + margintop);
			ctx.beginPath();
			ctx.moveTo(marginleft + currCount2*spacingHorizontalChild, height - data[currCount2]*verticalCoefficient + margintop);
		}
		data = datas[2];
		ctx.strokeStyle= "orange";
		ctx.fillStyle= "orange";
		ctx.beginPath();
		ctx.arc(marginleft,height - data[0]*verticalCoefficient + margintop, 3, 0, 2 * Math.PI, false);
		ctx.fill();
		ctx.closePath();
		ctx.beginPath();
		ctx.moveTo(marginleft,height - data[0]*verticalCoefficient + margintop);
		var currCount3 = 1;
		for(var currCount3 = 1; currCount3 < data.length; currCount3++){
			ctx.lineTo(marginleft + currCount3*spacingHorizontalChild,height - data[currCount3]*verticalCoefficient + margintop);
			ctx.stroke();
			ctx.closePath();
			ctx.beginPath();
			ctx.arc(marginleft + currCount3*spacingHorizontalChild,height - data[currCount3]*verticalCoefficient + margintop, 3, 0, 2 * Math.PI, false);
			ctx.fill();
			ctx.closePath();
			//ctx.fillText(".",margintop + i*spacingHorizontalChild-5,height - data[i]/totalRow*verticalCoefficient + margintop-5);
			// position for next segment
			ctx.beginPath();
			ctx.moveTo(marginleft + currCount3*spacingHorizontalChild, height - data[currCount3]*verticalCoefficient + margintop);
		}
		/*var currCount1 = 1;
		inter1 = setInterval(function() {	
			if(currCount1 == 1)
			{
				ctx.beginPath();
				ctx.arc(marginleft,height - data[0]*verticalCoefficient + margintop, 3, 0, 2 * Math.PI, false);
				ctx.fill();
				ctx.closePath();
				ctx.beginPath();
				ctx.moveTo(marginleft,height - data[0]*verticalCoefficient + margintop);
			}
			ctx.lineTo(marginleft + currCount1*spacingHorizontalChild,height - data[currCount1]*verticalCoefficient + margintop);
			ctx.stroke();
			ctx.closePath();
			ctx.beginPath();
			ctx.arc(marginleft + currCount1*spacingHorizontalChild,height - data[currCount1]*verticalCoefficient + margintop, 3, 0, 2 * Math.PI, false);
			ctx.fill();
			ctx.closePath();
			ctx.beginPath();
			ctx.moveTo(marginleft + currCount1*spacingHorizontalChild, height - data[currCount1]*verticalCoefficient + margintop);
			currCount1++;
			if(currCount1 == data.length-1)
			{
				ctx.lineTo(marginleft + currCount1*spacingHorizontalChild,height - data[currCount1]*verticalCoefficient + margintop);
				ctx.stroke();
				ctx.closePath();
			}
			if (currCount1 > data.length-1) {
			   clearInterval(inter1);
			   data = datas[1];
				ctx.strokeStyle= "blue";
				ctx.fillStyle= "blue";
				ctx.beginPath();
				ctx.arc(marginleft,height - data[0]*verticalCoefficient + margintop, 3, 0, 2 * Math.PI, false);
				ctx.fill();
				ctx.closePath();
				ctx.beginPath();
				ctx.moveTo(marginleft,height - data[0]*verticalCoefficient + margintop);
				
				var currCount2 = 1;
				inter2 = setInterval(function() {	
					ctx.lineTo(marginleft + currCount2*spacingHorizontalChild,height - data[currCount2]*verticalCoefficient + margintop);
					ctx.stroke();
					ctx.closePath();
					ctx.beginPath();
					ctx.arc(marginleft + currCount2*spacingHorizontalChild,height - data[currCount2]*verticalCoefficient + margintop, 3, 0, 2 * Math.PI, false);
					ctx.fill();
					ctx.closePath();
					//ctx.fillText(data[i],margintop + i*spacingHorizontal,height - data[i]/10*verticalCoefficient + margintop);
					ctx.beginPath();
					ctx.moveTo(marginleft + currCount2*spacingHorizontalChild, height - data[currCount2]*verticalCoefficient + margintop);
					currCount2++;
					if (currCount2 > data.length-1) {
					   clearInterval(inter2);
					   data = datas[2];
						ctx.strokeStyle= "orange";
						ctx.fillStyle= "orange";
						ctx.beginPath();
						ctx.arc(marginleft,height - data[0]*verticalCoefficient + margintop, 3, 0, 2 * Math.PI, false);
						ctx.fill();
						ctx.closePath();
						ctx.beginPath();
						ctx.moveTo(marginleft,height - data[0]*verticalCoefficient + margintop);
						var currCount3 = 1;
						inter3 = setInterval(function() {
							ctx.lineTo(marginleft + currCount3*spacingHorizontalChild,height - data[currCount3]*verticalCoefficient + margintop);
							ctx.stroke();
							ctx.closePath();
							ctx.beginPath();
							ctx.arc(marginleft + currCount3*spacingHorizontalChild,height - data[currCount3]*verticalCoefficient + margintop, 3, 0, 2 * Math.PI, false);
							ctx.fill();
							ctx.closePath();
							//ctx.fillText(".",margintop + i*spacingHorizontalChild-5,height - data[i]/totalRow*verticalCoefficient + margintop-5);
							// position for next segment
							ctx.beginPath();
							ctx.moveTo(marginleft + currCount3*spacingHorizontalChild, height - data[currCount3]*verticalCoefficient + margintop);
							currCount3++;
							if (currCount3 > data.length-1) {
							   clearInterval(inter3);
							}
						},speed);
						ctx.stroke();
						ctx.closePath();
					}
				},speed);
				ctx.stroke();
				ctx.closePath();
			}
		},speed);*/
		
		
	}
}

var CommonMessenger = {
	errorType : 'error',
	ErrorTitle : ' Thông báo lỗi',
	Info : 'Thông báo',
	Warning : 'Cảnh báo',
	Success : 'Cập nhật thành công!',
	Delete : 'Bạn có chắc chắn xóa dữ liệu này không?',
	Error : 'Đã có lỗi trong quá trình xử lý!',
	SureToDelete : 'Bạn có chắc chắn muốn xóa dữ liệu này không?',
	NothingToDownload : 'Dữ liệu này không có tập tin đính kèm!',
	ConfirmWrongPassword : 'Nhắc lại mật khẩu mới chưa đúng!',
	OnlyChoiceToView : 'Bạn chỉ được chọn một giá trị để xem chi tiết!',
	OnlyChoiceToEdit : 'Bạn chỉ được chọn một giá trị để thực hiện thay đổi nội dung!',
	OnlyChoiceToAction : 'Bạn chỉ được chọn một giá trị để {0}!',
	MustFillAll : 'Bạn phải nhập đầy đủ thông tin!',
	MustChoiceBeforeAction : 'Bạn phải chọn dữ liệu cần {0} trước!',
	MustChoiceBeforeAction2 : 'Bạn phải chọn {0} cần {1} trước!',
	MustChoiceBeforeView : 'Bạn phải chọn dữ liệu cần xem trước!',
	MustChoiceBeforePhanCong : 'Bạn phải chọn hồ sơ để phân công rà soát!',
	MustChoiceBeforeEdit : 'Bạn phải chọn dữ liệu cần sửa trước!',
	MustChoiceBeforeTrinh : 'Bạn phải chọn hồ sơ trước khi trình lãnh đạo!',
	MustChoiceBeforeDelete : 'Bạn phải chọn dữ liệu cần xóa trước!',
	MustChoiceBeforePrint : 'Bạn phải chọn dữ liệu cần in trước!',
	MustChoiceBeforeSendMail : 'Bạn phải chọn dữ liệu cần phải gửi email trước!',
	MustChoiceBeforeDownload : 'Bạn phải chọn tài liệu đính kèm cần tải xuống trước!',
	MustChoiceDeXuatBeforeAttach : 'Bạn phải chọn đề xuất cần đính kèm tài liệu trước!',
	MustChoiceDeTaiDuAnBeforeAttach : 'Bạn phải chọn đề tài - dự án cần đính kèm tài liệu trước!',
	MustChoiceBeforeAddAttach : 'Bạn phải chọn {0} cần đính kèm tài liệu trước!',
	MustChoiceBeforeEditAttach : 'Bạn phải chọn tài liệu {0} cần sửa thông tin trước!',
	MustChoiceBeforeDeleteAttach : 'Bạn phải chọn tài liệu {0} cần xóa trước!',
	MustChoiceBeforeUpdate : 'Bạn phải chọn dữ liệu cần cập nhật thông tin trước!',
	MustChoiceBeforeSecurity : 'Bạn phải chọn ứng dụng cần phân quyền trước!',
	MustChoiceBeforeAddUserOrGroup : 'Bạn phải chọn nhóm quyền cần thêm nhóm/người dùng trước!',
	MustChoiceBeforeDanhGia : 'Bạn phải chọn tài liệu {0} cần đánh giá trước!',
	OnlyChoiceOneRecode : 'Bạn chỉ được chọn một bản ghi ',

	successNotice : function(title, msg) {
		window.Ext.net.Notification.show({
			// iconCls: 'icon-information',
			// icon: windown.Ext.Net.Icon.Information,
			icon : window.Ext.Msg.INFO,
			hideDelay : 3000,
			autoHide : true,
			closeVisible : true,
			html : msg,
			title : CommonMessenger.Info,
			alignToCfg : {
				offset : [ 0, 20 ],
				position : 'tr-tr'
			},
			showFx : {
				args : [ 't', {} ],
				fxName : 'slideIn'
			},
			hideFx : {
				args : [ 't', {} ],
				fxName : 'ghost'
			}
		});
	},
	successHandler : function(form, action) {
		if (action.result && action.result.msg) {
		}
		CommonFunction.CloseWindow();
		// successNotice('Thông báo', 'Lưu dữ liệu thành công');
	},

	failureHandler : function(form, action) {
		var msg = '';

		if (action.failureType == 'client'
				|| (action.result && action.result.errors && action.result.errors.length > 0)) {
			msg = "Không nhập đủ thông tin";
		} else if (action.result && action.result.msg) {
			msg = action.result.msg;
		} else if (action.response) {
			msg = action.response.responseText;
		}
		CommonMessenger.showErrorMessage('', msg);
	},

	showInfoMessage : function(title, msg) {
		window.Ext.Msg.show({
			title : CommonMessenger.Info,
			msg : msg,
			buttons : window.Ext.Msg.OK,
			icon : window.Ext.Msg.INFO
		});
	},

	showErrorMessage : function(title, msg) {
		window.Ext.Msg.show({
			title : CommonMessenger.ErrorTitle,
			msg : msg,
			buttons : window.Ext.Msg.OK,
			icon : window.Ext.Msg.ERROR
		});
	},

	showWarningMessage : function(title, msg) {
		window.Ext.Msg.show({
			title : CommonMessenger.Warning,
			msg : msg,
			buttons : window.Ext.Msg.OK,
			icon : window.Ext.Msg.WARNING
		});
	},

	showConfirmMessage : function(title, msg, func) {
		Ext.Msg.show({
			title : CommonMessenger.Info,
			msg : msg,
			buttons : Ext.MessageBox.YESNO,
			icon : Ext.MessageBox.QUESTION,
			fn : func,
			width : 350
		});
	},

	showWindowMessage : function(title, msg, func) {
		window.Ext.Msg.show({
			title : title,
			msg : msg,
			buttons : window.Ext.Msg.OK,
			icon : window.Ext.Msg.INFO
		});
	},
};

$(document).ready(function(){
	$(".currency").each(function(){
		CommonFunction.formatCurrency(this, null);
	});
	$(".currencyHtml").each(function(){
		CommonFunction.formatCurrencyHtml(this);
	});
	$('.currency')
    .keyup(function(event){
    	CommonFunction.formatCurrency(this, event);
    })
    .keypress(function(event){
    	return CommonFunction.checkNumeric(event);
    })
    /*.blur(function(){
    	CommonFunction.checkRound(this);
    })*/
    .change(function(event){
    	CommonFunction.formatCurrency(this, event);
    });
});
