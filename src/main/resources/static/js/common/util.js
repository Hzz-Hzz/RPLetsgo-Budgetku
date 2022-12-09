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



function reloadAndShowToast(
    {
        titleConfigurer = (el) => {},
        subtitleConfigurer = (el) => {},
        toastConfigurer = (el) => {},
        body,
        toastTemplateId = "default-toast-template",
    }
){
    const toast = createToastElement({
        titleConfigurer, subtitleConfigurer, toastConfigurer, body, toastTemplateId
    });
    const toastHtml = toast.html();
    localStorage.setItem("pending-toasts", JSON.stringify([
        toastHtml
    ]));
    location.reload();
}

$(document).ready(function () {
    let pendingToasts = localStorage.getItem("pending-toasts");
    if (pendingToasts == null)
        return;
    pendingToasts = JSON.parse(pendingToasts);

    const toastParentElement = $(".toast-container");
    for (const pendingToast of pendingToasts) {
        const toast = $(pendingToast);
        toastParentElement.append(toast);

        const onToastHide = (toast) => {
            toast.slideUp(600, function () {
                toast.remove();
            });
        }

        setTimeout(() => onToastHide(toast), 20000);
    }
    localStorage.removeItem("pending-toasts");
})


function showToast(
    {
        titleConfigurer = (el) => {},
        subtitleConfigurer = (el) => {},
        toastConfigurer = (el) => {},
        body,
        toastTemplateId = "default-toast-template",
        toastParentElement=null,
        onToastHide=null,
        delay=2000,
   }
){
    console.log(arguments);
    const toast = createToastElement({
        titleConfigurer, subtitleConfigurer, toastConfigurer, body, toastTemplateId
    });

    if (toastParentElement == null)
        toastParentElement = $(".toast-container");
    toastParentElement.append(toast);

    toast.toast("show");


    if (onToastHide == null) {
        onToastHide = (toast) => {
            toast.slideUp(600, function () {
                toast.remove();
            });
        }
    }

    if (delay > 0) {
        setTimeout(() => onToastHide(toast), delay);
    }
    return () => onToastHide(toast);
}

function createToastElement(
    {
        titleConfigurer = (el) => {},
        subtitleConfigurer = (el) => {},
        toastConfigurer = (el) => {},
        body,
        toastTemplateId = "default-toast-template",
    }
) {
    const template = $(`#${toastTemplateId}`);
    const toast = $(template.html().trim());

    const titleEl = toast.find(".toast-title");
    const subtitleEl = toast.find(".toast-subtitle");
    const bodyEl = toast.find(".toast-body");

    titleConfigurer(titleEl);
    subtitleConfigurer(subtitleEl);
    toastConfigurer(toast);
    bodyEl.text(body);

    return toast.toast({
        animation: true,
        autohide: false,
    });
}


function showModal({
   title, body,
   okBtnConfigurer = (btn) => {
       removeAllBootstrapColoringClass(btn);
       btn.addClass("primary-btn");
   },
   cancelBtnConfigurer = (btn) => {
       removeAllBootstrapColoringClass(btn);
       btn.addClass("secondary-btn");
   },
   onOk, onCancel,
   modalId = "myModal"
}){
    const modal = $(`#${modalId}`);
    console.log(modal);

    const titleEl = modal.find(".modal-title");
    const bodyEl = modal.find(".modal-body");
    const okBtn = modal.find(".ok-btn");
    const cancelBtn = modal.find(".cancel-btn");
    const closeBtn = modal.find(".close-btn");

    titleEl.text(title);
    bodyEl.text(body);
    okBtnConfigurer(okBtn);

    okBtnConfigurer(okBtn);
    cancelBtnConfigurer(cancelBtn);
    rebindOnClick(okBtn, cancelBtn, closeBtn, onOk, onCancel);

    modal.modal("show");
    return () => modal.modal("hide");
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