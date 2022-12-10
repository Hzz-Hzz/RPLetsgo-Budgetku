function onShadowBoxCreateNew(){
    window.location.href = "create";
}


function onShadowBoxEdit(shadowBox, nameField){
    nameField.attr("contenteditable", 'true');
    nameField.focus();
    nameField.selectText();
}

function onShadowBoxSave(shadowBox, nameField){
    nameField.attr("contenteditable", 'false');

    const workspaceId = nameField.attr("data-workspace-id");
    const spendingAllowanceId = nameField.attr("data-allowance-id");
    const newAllowanceName = removeUnnecessaryWhitespaces(nameField.text());
    nameField.text(newAllowanceName);

    $.post(`/${workspaceId}/spending-allowance/update`, {
        spendingAllowanceId: spendingAllowanceId,
        nama: newAllowanceName,
    }).done(function () {
        showToast({
            titleConfigurer: (el) => {el.text("Renamed Successfully"); el.addClass("text-info")},
            body: "Perubahan berhasil disimpan"
        });
    }).fail(alsoHandleGeneralError("Rename Failed", function (error) {
        const {message} = error.responseJSON;
        showFailedToast("Rename Failed", message);
    }));
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
        title: "Delete Spending Allowance",
        body: `Apakah Anda yakin ingin menghapus budget "${nama}"?`,
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
    const spendingAllowanceId = nameField.attr("data-allowance-id");
    const nama = removeUnnecessaryWhitespaces(nameField.text());

    console.log("post");
    $.post(`/${workspaceId}/spending-allowance/delete`, {
        spendingAllowanceId: spendingAllowanceId,
    }).done(function () {
        addPendingToast({
            titleConfigurer: (el) => {el.text("Budget Deleted"); el.addClass("text-info")},
            body: `Budget "${nama}" berhasil dihapus`
        });
        location.reload();
    }).fail(alsoHandleGeneralError("Deletion Failed", function (error){
        console.log(error.responseJSON);
        const {message} = error.responseJSON;
        showToast({
            titleConfigurer: (el) => {el.text("Deletion Failed"); el.addClass("text-danger")},
            body: message
        })
    }));
}



