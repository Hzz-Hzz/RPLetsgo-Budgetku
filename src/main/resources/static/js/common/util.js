function removeAllBootstrapColoringClass(btn){
    btn.removeClass("btn-primary");
    btn.removeClass("btn-secondary");
    btn.removeClass("btn-success");
    btn.removeClass("btn-danger");
    btn.removeClass("btn-warning");
    btn.removeClass("btn-info");
    btn.removeClass("btn-light");
    btn.removeClass("btn-dark");
    btn.removeClass("btn-link");
}


function removeUnnecessaryWhitespaces(string){
    string = string.replace(/\s\s+/, " ");
    string = string.replace("\n", "");
    string = string.trim()
    return string;
}



// untuk dipakai ketika $.post().fail(() => {})
// return true apabila terhandle. return false jika tidak terhandle
function alsoHandleGeneralError(additionalErrorHandler, generalErrorHandler){
    function errorHandler(error, ...args) {
        if (error.responseJSON != null) {
            additionalErrorHandler(error, ...args);
            return;
        }

        if (error.status === 0){
            generalErrorHandler("You're offline");
        }else {
            generalErrorHandler("Unidentified error, try again later");
            throw new Error("Unhandled error");
        }
    }
    return errorHandler;
}


function isWholeElementInScreen(elm, additionalMarginThreshold=0, evalType="visible" ) {
    var vpH = $(window).height(), // Viewport Height
        st = $(window).scrollTop(), // Scroll Top
        y = $(elm).offset().top,
        elementHeight = $(elm).height();

    if (evalType === "visible")
        return ((y + elementHeight + additionalMarginThreshold < (vpH + st)) && (y-additionalMarginThreshold > (st)));
    if (evalType === "above") return ((y+elementHeight + additionalMarginThreshold < (vpH + st)));
}


function showModal(
    {
        title,
        body,  // body may be a string or a function
        modalConfigurer = (modal) => {},
        okBtnConfigurer = (btn) => {
           removeAllBootstrapColoringClass(btn);
           btn.addClass("primary-btn");
        },
        cancelBtnConfigurer = (btn) => {
           removeAllBootstrapColoringClass(btn);
           btn.addClass("secondary-btn");
        },
        onOk, onCancel,
        verticallyCentered = false,
        templateElement = null
    }
){
    if (templateElement == null) {
        templateElement = $("#default-modal-template")
    }
    const modal = $(templateElement.html().trim());
    modalConfigurer(modal);

    const titleEl = modal.find(".modal-title");
    const bodyEl = modal.find(".modal-body");
    const okBtn = modal.find(".ok-btn");
    const cancelBtn = modal.find(".cancel-btn");
    const closeBtn = modal.find(".close-btn");

    titleEl.text(title);
    if (isString(body))
        bodyEl.text(body);
    else
        body(bodyEl);

    if (verticallyCentered)
        modal.find(".modal-dialog").addClass("modal-dialog-centered");

    okBtnConfigurer(okBtn);
    cancelBtnConfigurer(cancelBtn);
    rebindOnClick(okBtn, cancelBtn, closeBtn, onOk, onCancel);

    modal.modal("show");
    return () => {
        modal.modal("hide");
        modal.remove();
    };
}
function isString(variable){
    return typeof variable === 'string' || variable instanceof String;
}


function rebindOnClick(okBtn, cancelBtn, closeBtn, onOk, onCancel){
    okBtn.unbind("click");
    okBtn.click(function (_e){
        onOk();
    });
    cancelBtn.unbind("click");
    cancelBtn.click(function (_e){
        onCancel();
    });
    closeBtn.unbind("click");
    closeBtn.click(function (_e){
        onCancel();
    });
}



// souce: https://stackoverflow.com/a/9976413/7069108
jQuery.fn.selectText = function(){
    this.find('input').each(function() {
        if($(this).prev().length == 0 || !$(this).prev().hasClass('p_copy')) {
            $('<p class="p_copy" style="position: absolute; z-index: -1;"></p>').insertBefore($(this));
        }
        $(this).prev().html($(this).val());
    });
    var doc = document;
    var element = this[0];
    console.log(this, element);
    if (doc.body.createTextRange) {
        var range = document.body.createTextRange();
        range.moveToElementText(element);
        range.select();
    } else if (window.getSelection) {
        var selection = window.getSelection();
        var range = document.createRange();
        range.selectNodeContents(element);
        selection.removeAllRanges();
        selection.addRange(range);
    }
};