$(document).ready(() => {
    const shadowBoxes = $(".shadowed-box");
    for (let shadowBox of shadowBoxes) {
        shadowBox = $(shadowBox);
        assignShadowBoxHandler(shadowBox);
    }
});

function assignShadowBoxHandler(shadowBox){
    const nameBox = shadowBox.find(".name-box");
    const nameField = nameBox.find(".name");
    const saveBtn = shadowBox.find(".save-icon");
    const deleteBtn = shadowBox.find(".delete-icon");

    function onNameBoxClick(_e, nameBox) {
        if (!nameBox.hasClass("editing")){
            nameBox.addClass("editing");
            onShadowBoxEdit(shadowBox, nameField);

            nameBox.unbind("click");
        }
    }
    function onSaveBtnClick(_e, nameBox=null) {
        if (nameBox == null)
            nameBox = $(this);

        if (nameBox.hasClass("editing")){
            nameBox.removeClass("editing");
            onShadowBoxSave(shadowBox, nameField);

            setTimeout(() => {
                nameBox.click((e) => onNameBoxClick(e, nameBox));
            }, 100);
        }
    }

    nameBox.click((e) => {
        onNameBoxClick(e, nameBox)
    });
    nameBox.keydown(function (e){
        if (e.which === 13) {  // enter key
            onSaveBtnClick(null, nameBox);
            e.preventDefault();
        }
    });
    deleteBtn.click((e) => onShadowBoxDeleteWithConfirmation(shadowBox, nameField));
    saveBtn.click((e) => onSaveBtnClick(e, nameBox));
}


function onShadowBoxEdit(shadowBox, nameField){
    nameField.attr("contenteditable", 'true');
    nameField.focus();
    nameField.selectText();
}

function onShadowBoxSave(shadowBox, nameField){
    nameField.attr("contenteditable", 'false');

    const workspaceId = nameField.attr("data-workspace-id");
    const newWorkspaceName = removeUnnecessaryWhitespaces(nameField.text());
    nameField.text(newWorkspaceName);

    $.post(`/workspace/update`, {
        nama: newWorkspaceName,
        workspaceId: workspaceId,
    }).done(function () {
        showToast({
            titleConfigurer: (el) => {el.text("Renamed Successfully"); el.addClass("text-info")},
            body: "Perubahan berhasil disimpan"
        });
    }).fail(function (error, cause) {
        if (error.responseJSON == null  && error.status===0){
            showFailedToast("Rename Failed", "You're offline");
            return;
        }

        const {message} = error.responseJSON;
        showFailedToast("Rename Failed", message);
    });
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
    const workspaceId = nameField.attr("data-workspace-id");
    const nama = removeUnnecessaryWhitespaces(nameField.text());

    console.log("post");
    $.post(`/workspace/delete`, {
        workspaceId: workspaceId,
    }).done(function () {
        addPendingToast({
            titleConfigurer: (el) => {el.text("Budget Deleted"); el.addClass("text-info")},
            body: `Workspace "${nama}" berhasil dihapus`
        });
        location.reload();
    }).fail(function (error){
        console.log(error.responseJSON);
        const {message} = error.responseJSON;
        showToast({
            titleConfigurer: (el) => {el.text("Deletion Failed"); el.addClass("text-danger")},
            body: message
        });
    });
}


