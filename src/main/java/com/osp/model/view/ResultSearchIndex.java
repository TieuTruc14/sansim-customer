package com.osp.model.view;

import com.osp.common.PagingResult;

/**
 * Created by Admin on 1/20/2018.
 */
public class ResultSearchIndex {
    private PagingResult page;
    private boolean errorCaptcha;

    public PagingResult getPage() {
        return page;
    }

    public void setPage(PagingResult page) {
        this.page = page;
    }

    public boolean isErrorCaptcha() {
        return errorCaptcha;
    }

    public void setErrorCaptcha(boolean errorCaptcha) {
        this.errorCaptcha = errorCaptcha;
    }
}
