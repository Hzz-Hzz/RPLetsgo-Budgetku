
function addPendingToast(
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
    toast.addClass("show");
    const toastHtml = toast.prop("outerHTML");
    localStorage.setItem("pending-toasts", JSON.stringify([
        toastHtml
    ]));
}

function showPendingToasts(){
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

        setTimeout(() => onToastHide(toast), 3000);
    }
    localStorage.removeItem("pending-toasts");
}
$(document).ready(showPendingToasts);


function showToast(
    {
        titleConfigurer = (el) => {},
        subtitleConfigurer = (el) => {},
        toastConfigurer = (el) => {},
        body,
        toastTemplateId = "default-toast-template",
        toastParentElement=null,
        onToastHide=null,
        delay=2500,
    }
){
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