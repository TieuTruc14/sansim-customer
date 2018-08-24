/**
 * Created by Admin on 12/21/2017.
 */
app.controller('sansimCtrl', ['$scope', '$http', '$q', function ($scope, $http, $q) {
    $(".hiden1").css({"display": "block"});
    $scope.search = {telco: "", dauso: "", so: "", confirmStatus: false, birthday: "", from: "", to: "",sortPrice:0};
    $scope.page = page;
    //for loadding and no data,captcha
    $scope.loadding = false;
    $scope.noData = "";
    /*for captcha*/
    $scope.errorCaptcha = false;
    $scope.errorCaptchaMessage = "";
    $scope.imageUrl = preUrl + "/captcha";
    $scope.captcha = "";
    $scope.count = 1;
    $scope.itemDetail = null;
    /*for status load type*/
    $scope.loadType = 0;//0-load theo binh thuong khi tim kiem,1-load theo loai gia,2-load theo loai sim,3-load theo nam sinh
    /*----------------------------------------------------------------------------------*/
    $('#birthday').datepicker().on('changeDate', function (ev) {
        $scope.search.birthday = this.value;
    });
    /*xem co la link tim kiem theo*/

    if($(window).width() < 500){
        $('html, body').animate({
            scrollTop: $("#home-logo").offset().top
        }, 1000)
    }
    $scope.countFind = function () {
        var dataCount = $q.defer();
        $http.get(preUrl + "/captcha-count")
            .then(function (response) {
                $scope.count = response.data;
            });
        dataCount.resolve($scope.count);
        return dataCount.promise;
    };
    $scope.countFind();

    $scope.changeCaptcha = function () {
        var data = $q.defer();
        $http.get(preUrl + "/captcha")
            .then(function (response) {
                var random = (new Date()).getTime();
                $scope.imageUrl = preUrl + "/captcha?time=" + random;
                $scope.captcha = "";
            });
        data.resolve($scope.imageUrl);
        return data.promise;
    };
    /*for captcha*/

    $scope.find = function () {
        $scope.loadType = 0;
        if ($scope.count > 50) {
            if ($scope.captcha == 'undefined' || $scope.captcha.length == 0) {
                $scope.errorCaptchaMessage = "Bạn cần nhập mã xác nhận!";
                return;
            }
        }
        $scope.countFind();
        $scope.uncheckedAll();
        $scope.errorDate = "";
        if (($scope.search.birthday.length >= 0 && formatDate($scope.search.birthday) != null) || $scope.search.birthday == "" ) {
            $scope.preGetData();
            // $scope.page.pageNumber=1;
            var search = JSON.stringify($scope.search);
            $http.get(preUrl + "/search", {params: {search: search, captcha: $scope.captcha}})
                .then(function (response) {
                        $scope.changeCaptcha();
                        if (response != null && response != 'undefined' && response.status == 200 && response.data != 'undefined') {
                            if (response.data.page != null && response.data.page != 'undefined') {
                                $scope.page = response.data.page;
                                if($scope.page.items.length==0){
                                    $scope.errorGetData();
                                }
                                scrollbox();
                            } else {
                                $scope.page = page;
                            }
                            $scope.page.pageCount = getPageCount($scope.page);
                            $scope.page.pageList = getPageList($scope.page);
                            $scope.errorCaptcha = response.data.errorCaptcha;
                            if ($scope.errorCaptcha) {
                                $scope.errorCaptchaMessage = "Mã xác nhận nhập không chính xác!";
                            }
                            if ($scope.page.items == 'undefined' || $scope.page.items.length == 0) {
                                $scope.errorGetData();
                            } else {
                                $scope.endGetData()
                            }
                        } else {
                            $scope.errorGetData();
                        }
                    },
                    function (response) {
                        $scope.errorGetData();
                    }).finally(function() {
                        setTimeout(function() {
                            $scope.formatMark();
                        }, 1000);
                    });
        } else {
            $scope.errorDate = "Nhập ngày sinh đúng định dạng dd/MM/yyyy";
        }

    };

    $scope.loadPage = function (pageNumber) {
        if ($scope.count > 50) {
            if ($scope.captcha == 'undefined' || $scope.captcha.length == 0) {
                $scope.errorCaptchaMessage = "Bạn cần nhập mã xác nhận!";
                return;
            }
        }
        $scope.countFind();
        $scope.errorDate = "";
        if ($scope.search.birthday.length == 0 || formatDate($scope.search.birthday) != null) {
            if (pageNumber >= 1) {
                $scope.preGetData();
                var search = JSON.stringify($scope.search);
                $http.get(preUrl + "/search", {params: {search: search, p: pageNumber, captcha: $scope.captcha}})
                    .then(function (response) {
                            if (response != null && response != 'undefined' && response.status == 200) {
                                if (response.data.page != null && response.data.page != 'undefined') {
                                    $scope.page = response.data.page;
                                    if($scope.page.items.length==0){
                                        $scope.errorGetData();
                                    }
                                    scrollbox();
                                } else {
                                    $scope.page = page;
                                }
                                $scope.page.pageCount = getPageCount($scope.page);
                                $scope.page.pageList = getPageList($scope.page);
                                if ($scope.errorCaptcha) {
                                    $scope.errorCaptchaMessage = "Mã xác nhận nhập không chính xác!";
                                }
                                if ($scope.page.items == 'undefined' || $scope.page.items.length == 0) {
                                    $scope.errorGetData();
                                } else {
                                    $scope.endGetData()
                                }
                            } else {
                                $scope.errorGetData();
                            }
                        },
                        function (response) {
                            $scope.errorGetData();
                        }).finally(function() {
                            setTimeout(function() {
                                $scope.formatMark();
                            }, 1000);
                        });
            }
        } else {
            $scope.errorDate = "Nhập ngày sinh đúng định dạng dd/MM/yyyy";
        }
    };
    /*for sap xep*/
    $scope.sortPrice=function () {
        switch ($scope.search.sortPrice){
            case 0:
                $scope.search.sortPrice=1;
                break;
            case 1:
                $scope.search.sortPrice=2;
                break;
            case 2:
                $scope.search.sortPrice=0;
                break;
            default: break;
        }
        $scope.beforeLoadPage($scope.page.pageNumber);
    };
    /*end sap xep*/

    $scope.beforeLoadPage = function (pageNumber) {
        if (pageNumber > 0) {
            switch ($scope.loadType) {
                case 0:
                    $scope.loadPage(pageNumber);
                    break;
                case 1:
                    $scope.loadPageGroup(pageNumber);
                    break;
                default:
                    break;
            }
        }
    };
    /*LOAD MENU LOAI SIM*/
    $scope.listGroupMsisdn = "";
    $scope.listGroupPrice = "";
    $scope.listGroupYear = "";
    $scope.listGroupTelco=[{id:1,name:"Viettel"},{id:2,name:"Vinaphone"},{id:3,name:"Mobifone"},{id:4,name:"Gmobile"},{id:5,name:"VietNamMobile"},{id:0,name:"Khác"}];
    $scope.groupPriceId = gprice;
    $scope.groupMsisdnId = gmsisdn;
    $scope.groupYearId = gyear;
    $scope.groupTelcoId=gtelco;
    $scope.label={price:'',msisdn:'',year:'',telco:''};
    $http.get(preUrl + "/group/group-msisdn/list")
        .then(function (response) {
            $scope.listGroupMsisdn = response.data;
        });
    $http.get(preUrl + "/group/group-price/list")
        .then(function (response) {
            $scope.listGroupPrice = response.data;
        });
    $http.get(preUrl + "/group/group-year/list")
        .then(function (response) {
            $scope.listGroupYear = response.data;
        });

    //search by group price
    $scope.searchGroupPrice = function (groupPriceId) {
        if (groupPriceId > 0) {
            $scope.groupPriceId = (groupPriceId == $scope.groupPriceId) ? 0 : groupPriceId;
            $scope.changeUrlPage();
            $scope.loadType = 1;
            $scope.preGetData();
            $scope.getSearchByGroup(1);

            if($scope.groupPriceId==0){
                $(":checkbox[id^='price_']").prop('checked',false);
            }
            $scope.label.price=$scope.fillterPriceLabel(groupPriceId);
        }
    };

    //search by group msisdn
    $scope.searchGroupMsisdn = function (groupMsisdnId) {
        if (groupMsisdnId > 0) {
            $scope.groupMsisdnId = (groupMsisdnId == $scope.groupMsisdnId) ? 0 : groupMsisdnId;
            $scope.changeUrlPage();
            $scope.loadType = 1;
            $scope.preGetData();
            $scope.getSearchByGroup(1);

            if($scope.groupMsisdnId==0){
                $(":checkbox[id^='type_']").prop('checked',false);
            }
            $scope.label.msisdn=$scope.fillterMsisdnLabel(groupMsisdnId);
        }
    };

    //search by group msisdn
    $scope.searchGroupYear = function (groupYearId) {
        if (groupYearId > 0) {
            $scope.groupYearId = (groupYearId == $scope.groupYearId) ? 0 : groupYearId;
            $scope.changeUrlPage();
            $scope.loadType = 1;
            $scope.preGetData();
            $scope.getSearchByGroup(1);

            if($scope.groupYearId==0){
                $(":checkbox[id^='birth_']").prop('checked',false);
            }else{
                $scope.label.year=$scope.fillterYearLabel(groupYearId);
            }

        }
    };

    $scope.searchGroupTelco = function (groupTelcoId) {
        if (groupTelcoId >= 0 && groupTelcoId!=9) {
            $scope.groupTelcoId = (groupTelcoId == $scope.groupTelcoId) ? 9 : groupTelcoId;
            $scope.changeUrlPage();
            $scope.loadType = 1;
            $scope.preGetData();
            $scope.getSearchByGroup(1);

            if($scope.groupTelcoId==9){
                $(":checkbox[id^='telco_']").prop('checked',false);
            }
            $scope.label.telco=$scope.fillterTelcoLabel(groupTelcoId);
        }
    };
    $scope.changeUrlPage=function () {
        history.pushState({}, null, "/tim-theo-loai/pr="+$scope.groupPriceId+"-gr="+$scope.groupMsisdnId+"-y="+$scope.groupYearId+"-tel="+$scope.groupTelcoId);
    };

    $scope.uncheckedAll=function () {
        // $ ("input:checkbox[id^='type_']:not(:checked)");
        $scope.groupPriceId=0;
        $scope.groupMsisdnId=0;
        $scope.groupYearId=0;
        $scope.groupTelcoId=9;
        $(":checkbox[id^='price_']").prop('checked',false);
        $(":checkbox[id^='type_']").prop('checked',false);
        $(":checkbox[id^='birth_']").prop('checked',false);
        $(":checkbox[id^='telco_']").prop('checked',false);
        history.pushState({}, null, "/");
    }

    /*for label on table search*/
    $scope.fillterPriceLabel=function (id) {
        for(var i=0;i<$scope.listGroupPrice.length;i++){
            if(id==$scope.listGroupPrice[i].id){
                return $scope.listGroupPrice[i].name;
            }
        }
    };
    $scope.unLabelPrice=function () {
        $scope.searchGroupPrice($scope.groupPriceId);
    };

    $scope.fillterMsisdnLabel=function (id) {
        for(var i=0;i<$scope.listGroupMsisdn.length;i++){
            if(id==$scope.listGroupMsisdn[i].id){
                return $scope.listGroupMsisdn[i].groupName;
            }
        }
    };
    $scope.unLabelMsisdn=function () {
        $scope.searchGroupMsisdn($scope.groupMsisdnId);
    };

    $scope.fillterYearLabel=function (id) {
        for(var i=0;i<$scope.listGroupYear.length;i++){
            if(id==$scope.listGroupYear[i].id){
                return $scope.listGroupYear[i].name;
            }
        }
    };
    $scope.unLabelYear=function () {
        $scope.searchGroupYear($scope.groupYearId);
    };

    $scope.fillterTelcoLabel=function (id) {
        for(var i=0;i<$scope.listGroupTelco.length;i++){
            if(id==$scope.listGroupTelco[i].id){
                return $scope.listGroupTelco[i].name;
            }
        }
    };
    $scope.unLabelTelco=function () {
        $scope.searchGroupTelco($scope.groupTelcoId);
    };
    /*load search by group*/

    $scope.loadPageGroup = function (pageNumber) {
        $scope.preGetData();
        $scope.getSearchByGroup(pageNumber);
    };

    $scope.getSearchByGroup = function (pageNumber) {
        $http.get(preUrl + "/group/search-by-group", {params: {telco:$scope.groupTelcoId,sortPrice:$scope.search.sortPrice,gpriceId: $scope.groupPriceId, gmsisdnId: $scope.groupMsisdnId, gyearId: $scope.groupYearId, p: pageNumber}})
            .then(function (response) {
                    if (response != null && response != 'undefined' && response.status == 200) {
                        if (response.data != null && response.data != 'undefined') {
                            $scope.page = response.data;
                            // history.pushState({}, null, "lichvv");
                            if($scope.page.items.length==0){
                                $scope.errorGetData();
                            }
                            scrollbox();
                        } else {
                            $scope.page = page;
                        }
                        $scope.page.pageCount = getPageCount($scope.page);
                        $scope.page.pageList = getPageList($scope.page);
                        if ($scope.page.items == 'undefined' || $scope.page.items.length == 0) {
                            $scope.errorGetData();
                        } else {
                            $scope.endGetData()
                        }
                    } else {
                        $scope.errorGetData();
                    }
                },
                function (response) {
                    $scope.errorGetData();
                });
    };

    /*END LOAD MENU*/



    $scope.searchDetailMsisdn = function (msisdnId) {
        $http.get(preUrl + "/detailsim", {params: {msisdnId: msisdnId}})
            .then(function (response) {
                    if (response != null && response != 'undefined' && response.status == 200) {
                        if (response.data != null && response.data != 'undefined') {
                            $scope.itemDetail = response.data;

                            $("#exampleModal").addClass("modal-sim");
                            $("#exampleModal").modal();
                            scrollbox();
                        } else {
                            $scope.itemDetail = null;
                        }
                    } else {
                        $scope.errorGetData();
                    }
                },
                function (response) {
                    $scope.errorGetData();
                });


    };


    $scope.preGetData = function () {
        $scope.page = page;
        $scope.loadding = true;
    };
    $scope.endGetData = function () {
        $scope.loadding = false;
        $scope.changeCaptcha();
    };
    $scope.errorGetData = function () {
        $scope.noData = "Không có kết quả nào phù hợp với tìm kiếm của bạn.";
        $scope.page = page;
        $scope.loadding = false;
        $scope.changeCaptcha();
    };
    function scrollbox() {
        $('html, body').animate({
            scrollTop: $("#list-sim").offset().top
        }, 1000)
    }


    /*check tim kiem load trang mac dinh*/
    $scope.getSearchByGroup12 = function (pageNumber) {
        $http.get(preUrl + "/group/search-by-group", {params: {telco:$scope.groupTelcoId,sortPrice:$scope.search.sortPrice,gpriceId: $scope.groupPriceId, gmsisdnId: $scope.groupMsisdnId, gyearId: $scope.groupYearId, p: pageNumber}})
            .then(function (response) {
                    if (response != null && response != 'undefined' && response.status == 200) {
                        if (response.data != null && response.data != 'undefined') {
                            $scope.page = response.data;
                            // history.pushState({}, null, "lichvv");
                            if($scope.page.items.length==0){
                                $scope.errorGetData();
                            }
                            scrollbox();
                        } else {
                            $scope.page = page;
                        }
                        $scope.page.pageCount = getPageCount($scope.page);
                        $scope.page.pageList = getPageList($scope.page);
                        $scope.loadCheckBoxGroupPrice();
                        $scope.loadCheckBoxGroupMsisdn();
                        $scope.loadCheckBoxGroupYear();
                        $scope.loadCheckBoxGroupTelco();
                        $scope.label.price=$scope.fillterPriceLabel($scope.groupPriceId);
                        $scope.label.msisdn=$scope.fillterMsisdnLabel($scope.groupMsisdnId);
                        $scope.label.year=$scope.fillterYearLabel($scope.groupYearId);
                        $scope.label.telco=$scope.fillterTelcoLabel($scope.groupTelcoId);

                        if ($scope.page.items == 'undefined' || $scope.page.items.length == 0) {
                            $scope.errorGetData();
                        } else {
                            $scope.endGetData()
                        }
                    } else {
                        $scope.errorGetData();
                    }
                },
                function (response) {
                    $scope.errorGetData();
                });
    };
    $scope.loadCheckBoxGroupPrice=function () {
        if($scope.groupPriceId>0){
            for(var i=0;i<$scope.listGroupPrice.length;i++){
                if($scope.groupPriceId==$scope.listGroupPrice[i].id){
                    var j=i+1;
                    var checkboxes = $('#price_'+j);
                    checkboxes.prop( 'checked', true );
                    break;
                }
            }
        }
    };
    $scope.loadCheckBoxGroupMsisdn=function () {
        if($scope.groupMsisdnId>0){
            for(var i=0;i<$scope.listGroupMsisdn.length;i++){
                if($scope.groupMsisdnId==$scope.listGroupMsisdn[i].id){
                    var j=i+1;
                    var checkboxes = $('#type_'+j);
                    checkboxes.prop( 'checked', true );
                    break;
                }
            }
        }
    };
    $scope.loadCheckBoxGroupYear=function () {
        if($scope.groupYearId>0){
            for(var i=0;i<$scope.listGroupYear.length;i++){
                if($scope.groupYearId==$scope.listGroupYear[i].id){
                    var j=i+1;
                    var checkboxes = $('#birth_'+j);
                    checkboxes.prop( 'checked', true );
                    break;
                }
            }
        }
    };
    $scope.loadCheckBoxGroupTelco=function () {
        if($scope.groupTelcoId>=0){
            for(var i=0;i<$scope.listGroupTelco.length;i++){
                if($scope.groupTelcoId==$scope.listGroupTelco[i].id){
                    var j=i+1;
                    var checkboxes = $('#telco_'+j);
                    checkboxes.prop( 'checked', true );
                    break;
                }
            }
        }
    };
    $scope.loadDefault=function () {
        $scope.loadType = 1;
        $scope.getSearchByGroup12(1);
    };
    $scope.loadDefault();


    /*mark mssidn*/
    $scope.listCham=[];
    $scope.listViTriChuoi=[];

    $scope.layViTriDauCham=function (msisdn) {
        $scope.listCham=[];
        var vitri=-10;
        var start=0;
        do{
            vitri= msisdn.indexOf(".",start);
            if(vitri>=0){
                $scope.listCham.push(vitri);
                start=vitri+1;
            }
        }while(vitri>=0);

    };

    $scope.replaceAll=function (msisdn,search,replacement) {
        return msisdn.split(search).join(replacement);
    };

    $scope.timViTriChuoiSo=function (msisdn) {
        $scope.listViTriChuoi=[];
        var vitri=-10;
        var start=0;
        do{
            vitri=msisdn.indexOf($scope.search.so,start);
            if(vitri>=0){
                var object={min:vitri,max:vitri+$scope.search.so.length-1};
                $scope.listViTriChuoi.push(object);
                start=vitri+$scope.search.so.length;
            }
        }while (vitri>=0);
    };

    $scope.boSungDauChamTroLai=function (msisdn) {
        for(var i=0;i<$scope.listCham.length;i++){
            var vitri=$scope.listCham[i];
            msisdn=msisdn.substring(0,vitri)+"."+msisdn.substring(vitri);
            //thay doi lai gia tri vi tri chuoi
            for(var j=0;j<$scope.listViTriChuoi.length;j++){
                var min=$scope.listViTriChuoi[j].min;
                var max=$scope.listViTriChuoi[j].max;
                if(vitri<=min){
                    $scope.listViTriChuoi[j].min=min+1;
                    $scope.listViTriChuoi[j].max=max+1;
                }else if(vitri<=max){
                    $scope.listViTriChuoi[j].max=max+1;
                }
            }
        }
        return msisdn;
    };

    $scope.getLastResult=function (msisdn) {
        for(var i=$scope.listViTriChuoi.length-1;i>=0;i--){
            msisdn=msisdn.substring(0,$scope.listViTriChuoi[i].min)+preMark+msisdn.substring($scope.listViTriChuoi[i].min,$scope.listViTriChuoi[i].max+1)+suffMark +msisdn.substring($scope.listViTriChuoi[i].max+1,msisdn.length);
        }
        return msisdn;
    };

    $scope.formatMark = function() {
        $scope.getListStringSearch();
        var keyword = $scope.listStringSearch;
        var options = {};
        $(".highlight-content").unmark({
            done: function() {
                $(".highlight-content").mark(keyword, options);
            }
        });
    };

    $scope.listStringSearch=[];
    $scope.arraysOriginal=[];
    $scope.getListStringSearch=function () {
        $scope.listStringSearch=[];
        $scope.arraysOriginal=$scope.search.so.split("*");
        //loc bo gia tri ko can thiet
        for(var m=$scope.arraysOriginal.length-1;m>=0;m--){
            if($scope.arraysOriginal[m]==null || $scope.arraysOriginal[m]=='undefined' || $scope.arraysOriginal[m]==""){
                $scope.arraysOriginal.splice(m,1);
            }
        }

        //tim cac gia tri se danh dau'
        for(var i=0;i<$scope.page.items.length;i++){
            $scope.layViTriDauCham($scope.page.items[i][8]);
            $scope.page.items[i][8]=$scope.replaceAll($scope.page.items[i][8],".","");
            $scope.timViTriChuoiSoV2($scope.page.items[i][8]);
            if($scope.listCham.length>0){
                $scope.page.items[i][8]=$scope.boSungDauChamTroLai($scope.page.items[i][8]);
            }
            if($scope.listViTriChuoi.length>0){
                for(var k=0;k<$scope.listViTriChuoi.length;k++){
                    var min=$scope.listViTriChuoi[k].min;
                    var max=$scope.listViTriChuoi[k].max;
                    var stringSearch=$scope.page.items[i][8].substring(min,max+1);
                    $scope.listStringSearch.push(stringSearch);
                }
            }
        }
        //remove duplicate
        $scope.listStringSearch = $scope.listStringSearch.filter(function (a) {
            return !this[a] && (this[a] = true);
        }, Object.create(null));

    };

    $scope.timViTriChuoiSoV2=function (msisdn) {
        $scope.listViTriChuoi=[];
        var arrays=$scope.arraysOriginal;
        for(var i=0;i<arrays.length;i++){
            var vitri=-10;
            var start=0;
            do{
                vitri=msisdn.indexOf(arrays[i],start);
                if(vitri>=0){
                    var object={min:vitri,max:vitri+arrays[i].length-1};
                    $scope.listViTriChuoi.push(object);
                    start=vitri+arrays[i].length;
                }
            }while (vitri>=0);
        }
    };

}]);

