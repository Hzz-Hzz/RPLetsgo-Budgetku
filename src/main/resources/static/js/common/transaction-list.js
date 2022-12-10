
function onRemoveFinancialEvent(el){
    el = $(el);
    const workspaceId = el.attr("data-workspace-id");
    const financialEventId = el.attr("data-financial-event-id");
    const financialEventName = el.attr("data-financial-event-name");
    const financialEventType = el.attr("data-financial-event-type");

    var hideModalFunc;
    hideModalFunc = showModal({
        title: "Delete Financial Event",
        body: `Apakah Anda yakin ingin menghapus budget "${financialEventName}"?`,
        okBtnConfigurer: (btn) => {
            removeAllBootstrapColoringClass(btn);
            btn.addClass("btn-danger");
            btn.text("Delete");
        },
        cancelBtnConfigurer: (btn) => {
            removeAllBootstrapColoringClass(btn);
            btn.addClass("btn-secondary");
            btn.text("Cancel");
        },
        onOk: ()=>{
            deleteFinancialEvent(workspaceId, financialEventId, financialEventName, financialEventType);  // TODO @Pradipta.davi
            hideModalFunc();
        },
        onCancel: ()=>{
            console.log("cancel");
            hideModalFunc();
        },
    });
}

function deleteFinancialEvent(workspaceId, financialEventId, financialEventName, financialEventType) {
    console.log(workspaceId);
    console.log(financialEventId);
    console.log(financialEventType);
    let typePath = financialEventType.toLowerCase();
    console.log(typePath);

    $.post(`/${workspaceId}/${typePath}/delete/${financialEventId}`, {

    }).done(function () {
        addPendingToast({
            titleConfigurer: (el) => {el.text(`${financialEventType} Deleted`); el.addClass("text-primary")},
            body: `${financialEventType} "${financialEventName}" berhasil dihapus`
        });
        location.reload();
    }).fail(
        alsoHandleGeneralError(function (error){
            console.log(error.responseJSON);
            const {message} = error.responseJSON;
            showToast({
                titleConfigurer: (el) => {el.text("Delete Failed"); el.addClass("text-danger")},
                body: message
            });
        }, (msg) => {
            showFailedToast("Delete Failed", msg);
        })
    );
}

function onToggleEditFinancialEvent(el) {
    el = $(el);

    el.toggleClass("editing")
    const financialEventId = el.attr("data-financial-event-id");
    const isEditing = el.hasClass("editing");
    if (isEditing)
        onEditFinancialEvent(financialEventId);
    else
        onSaveFinancialEvent(financialEventId);
}


function onEditFinancialEvent(financialEventId){

}

function onSaveFinancialEvent(financialEventId){

}