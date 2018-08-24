/**
 * Created by Admin on 12/21/2017.
 */
app.controller('detailsimCtrl', ['$scope', '$http', '$q', function ($scope, $http, $q) {
        $(".hiden1").css({"display": "block"});
        $scope.page = page;
        //for loadding and no data,captcha
        $scope.loadding = false;
        $scope.noData = "";
        /*for status load type*/
        $scope.loadType = 0;//0-load theo binh thuong khi tim kiem,1-load theo loai gia,2-load theo loai sim,3-load theo nam sinh
        $scope.cusId = $("#cusId").val();
        
        $(document).ready(function () {
           $scope.loadPageGroup(1);
        });   

        $scope.beforeLoadPage = function (pageNumber) {
            if (pageNumber > 0) {
                $scope.loadPageGroup(pageNumber);
            }
        };
        /*LOAD MENU LOAI SIM*/
        $scope.listGroupMsisdn = "";
        $scope.listGroupPrice = "";
        $scope.listGroupYear = "";
        $scope.groupPriceId = 0;
        $scope.groupMsisdnId = 0;
        $scope.groupYearId = 0;
        
        
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
                $scope.loadType = 1;
                $scope.preGetData();
                $scope.getSearchByGroup(1);
            }
        };
        //search by group msisdn
        $scope.searchGroupMsisdn = function (groupMsisdnId) {
            if (groupMsisdnId > 0) {
                $scope.groupMsisdnId = (groupMsisdnId == $scope.groupMsisdnId) ? 0 : groupMsisdnId;
                $scope.loadType = 1;
                $scope.preGetData();
                $scope.getSearchByGroup(1);
            }
        };

        //search by group msisdn
        $scope.searchGroupYear = function (groupYearId) {
            if (groupYearId > 0) {
                $scope.groupYearId = (groupYearId == $scope.groupYearId) ? 0 : groupYearId;
                $scope.loadType = 1;
                $scope.preGetData();
                $scope.getSearchByGroup(1);
            }
        };
        /*load sort Price*/
        $scope.sortPrice=0;
        $scope.changeSortPrice=function () {
            switch ($scope.sortPrice){
                case 0:
                    $scope.sortPrice=1;
                    break;
                case 1:
                    $scope.sortPrice=2;
                    break;
                case 2:
                    $scope.sortPrice=1;
                    break;
                default: break;
            }
            $scope.loadPageGroup($scope.page.pageNumber);
        };

        /*load search by group*/

        $scope.loadPageGroup = function (pageNumber) {
            $scope.preGetData();
            $scope.getSearchByGroup(pageNumber);
        };

        $scope.getSearchByGroup = function (pageNumber) {
            $http.get(preUrl + "/group/search-by-group", {params: {sortPrice:$scope.sortPrice,gpriceId: $scope.groupPriceId, gmsisdnId: $scope.groupMsisdnId, gyearId: $scope.groupYearId, p: pageNumber, cusId: $scope.cusId}})
                    .then(function (response) {
                        if (response != null && response != 'undefined' && response.status == 200) {
                            if (response.data != null && response.data != 'undefined') {
                                $scope.page = response.data;
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


        $scope.preGetData = function () {
            $scope.page = page;
            $scope.loadding = true;
        };
        $scope.endGetData = function () {
            $scope.loadding = false;
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
        
    }]);

