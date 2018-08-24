/**
 * Created by Admin on 12/21/2017.
 */
var app = angular.module('sansimso', ["ngSanitize"]);


//Bat ky tu
var digitsOnly = /[1234567890]/g;
var digitsAndSemicolon = /[1234567890;]/g;
var digitsAndAsterisk = /[1234567890\\*]/g;
var digitsAndAlias = /[1234567890\\.\\s]/g;
var digitsAndSlash =/[1234567890/]/g;

function restrictCharacters(myfield, e, restrictionType) {
    if (!e) var e = window.event
    if (e.keyCode) code = e.keyCode;
    else if (e.which) code = e.which;
    var character = String.fromCharCode(code);

    // if they pressed esc... remove focus from field...
    if (code==27) { this.blur(); return false; }

    // ignore if they are press other keys
    // strange because code: 39 is the down key AND ' key...
    // and DEL also equals .
    if (!e.ctrlKey && code!=9 && code!=8 && code!=36 && code!=37 && code!=38 && (code!=39 || (code==39 && character=="'")) && code!=40) {
        if (character.match(restrictionType)) {
            return true;
        } else {
            return false;
        }

    }
}

/*When click my-enter*/
app.directive('myEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.myEnter);
                });

                event.preventDefault();
            }
        });
    };
});

/*KHU VỰC PHÂN TRANG JAVASCRIPT*/
 var page={items:"",rowCount:0,numberPerPage:20,pageNumber:1,pageList:[],pageCount:0};
/*load tong trang va danh sach trang*/
function getPageCount(pageResult) {
    var pageCount=Math.ceil(pageResult.rowCount/pageResult.numberPerPage);
    return pageCount;
}

/*TRợ giúp tính toán số trang hiển thị khi hiện page*/
function getPageList(pagingResult) {
    var pages=[];
    var from = pagingResult.pageNumber  - 3;
    var to = pagingResult.pageNumber + 5;
    if (from < 0) {
        to -= from;
        from = 1;
    }

    if (from < 1) {
        from = 1;
    }

    if (to > pagingResult.pageCount) {
        to = pagingResult.pageCount;
    }

    for (var i=from; i<=to; i++ ) {
        pages.push(i);
    }
    return pages;
}


//convert date dd/mm/yyyy sang date cua he thong.
function formatDate(strDate) {
    if(strDate==null || strDate.length!=10) return null;
    var dateArray = strDate.split("/");
    var date = dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0];
     
    if(moment(date,"YYYY-MM-DD",true).isValid()){
        return new Date(date);
    }else{
        return null;
    }

}