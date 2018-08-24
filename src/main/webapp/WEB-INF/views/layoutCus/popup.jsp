<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="alert-warning modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel"><i class="fa fa-warning"></i> Cảnh báo</h4>
            </div>
            <div class="modal-body">Bạn có chắc chắn muốn xóa không ?</div>
            <div class="modal-footer">
                <a class="btn btn-danger btn-ok"><i class="fa fa-check"></i> Đồng ý</a>
                <button type="button" class="btn btn-default" data-dismiss="modal">Hủy bỏ</button>
            </div>
        </div>
    </div>
</div>
<script>
    $('#confirm-delete').on('show.bs.modal', function (e) {
        if ($("#hdfIds").val() != ";")
            $(this).find('.btn-ok').attr('onclick', 'CommonFunction.deleteItems();');
        else {
            CommonFunction.showPopUpMsg(CommonMessenger.ErrorTitle, CommonMessenger.MustChoiceBeforeDelete, CommonMessenger.errorType);
            e.preventDefault();
        }
    });
</script>
<div class="modal fade" id="confirm-cancel" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="alert-warning modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel"><i class="fa fa-warning"></i> Cảnh báo</h4>
            </div>
            <div class="modal-body">Bạn có chắc chắn muốn hủy đăng danh sách số đã chọn không?</div>
            <div class="modal-footer">
                <a class="btn btn-primary btn-ok"><i class="fa fa-check"></i> Đồng ý</a>
                <button type="button" class="btn btn-default" data-dismiss="modal">Hủy bỏ</button>
            </div>
        </div>
    </div>
</div>
<script>
    $('#confirm-cancel').on('show.bs.modal', function (e) {
        if ($("#hdfIds").val() != ";")
            $(this).find('.btn-ok').attr('onclick', '$("#frmRemoveMsisdn").submit();');
        else {
            CommonFunction.showPopUpMsg(CommonMessenger.ErrorTitle,"Vui lòng chọn số cần hủy đăng số!", CommonMessenger.errorType);
            e.preventDefault();
        }
    });
    
</script>
<div class="modal fade" id="popup-message" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel"></h4>
            </div>
            <div class="modal-body"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="btnClose" data-dismiss="modal">Đóng</button>
                <!-- <a class="btn btn-danger btn-ok">Oke</a> -->
            </div>
        </div>
    </div>
</div>