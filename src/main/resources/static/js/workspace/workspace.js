function onShadowBoxCreateNew(){
    const creationForm = $("#creation-form-template");

    const innerHtml = creationForm.html();

    var hideModalFunc;
    hideModalFunc = showModal({
        title: "Create New Workspace",
        verticallyCentered: false,
        body: (el) => {
            el.html(innerHtml);
        },
        modalConfigurer: (modal) => {
            modal.find(".modal-dialog").addClass("modal-xl");
        },
        okBtnConfigurer: (btn) => {
            removeAllBootstrapColoringClass(btn);
            btn.addClass("btn-primary");
            btn.text("Submit");
        },
        cancelBtnConfigurer: (btn) => {
            removeAllBootstrapColoringClass(btn);
            btn.addClass("btn-secondary");
            btn.text("Cancel");
        },
        onOk: ()=>{
            $("#creation-form").submit();
        },
        onCancel: ()=>{
            hideModalFunc();
        },
    });
}


function onShadowBoxEdit(shadowBox, nameField){
    nameField.attr("contenteditable", 'true');
    nameField.focus();
    nameField.selectText();
}

function onShadowBoxSave(shadowBox, nameField){
    nameField.attr("contenteditable", 'false');

    const currentWorkspaceId = nameField.attr("data-workspace-id");
    const newWorkspaceName = removeUnnecessaryWhitespaces(nameField.text());
    nameField.text(newWorkspaceName);

    $.post(`/${currentWorkspaceId}/workspace/update`, {
        workspaceId: currentWorkspaceId,
        nama: newWorkspaceName,
    }).done(function () {
        showToast({
            titleConfigurer: (el) => {el.text("Renamed Successfully"); el.addClass("text-primary")},
            body: "Perubahan berhasil disimpan"
        });
    }).fail(
        alsoHandleGeneralError(function (error) {
            const {message} = error.responseJSON;
            showFailedToast("Rename Failed", message);
        }, (msg) => {
            showFailedToast("Rename Failed", msg);
        })
    );
}

function showFailedToast(title, message){
    showToast({
        titleConfigurer: (el) => {el.text(title); el.addClass("text-danger")},
        body: message
    });
}


function onShadowBoxDeleteWithConfirmation(shadowBox, nameField){
    const nama = removeUnnecessaryWhitespaces(nameField.text());

    var hideModalFunc;
    hideModalFunc = showModal({
        title: "Delete Workspace",
        body: `Apakah Anda yakin ingin menghapus workspace "${nama}"?`,
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
            onShadowBoxDelete(shadowBox, nameField);
            hideModalFunc();
        },
        onCancel: ()=>{
            console.log("cancel");
            hideModalFunc();
        },
    });
}

function onShadowBoxDelete(shadowBox, nameField){
    const currentWorkspaceId = nameField.attr("data-workspace-id");
    const nama = removeUnnecessaryWhitespaces(nameField.text());

    console.log("post");
    $.post(`/${currentWorkspaceId}/workspace/delete`, {
        workspaceId: currentWorkspaceId,
    }).done(function () {
        addPendingToast({
            titleConfigurer: (el) => {el.text("Budget Deleted"); el.addClass("text-primary")},
            body: `Workspace "${nama}" berhasil dihapus`
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



