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
    const spendingAllowanceId = nameField.attr("data-allowance-id");
    const newAllowanceName = removeUnnecessaryWhitespaces(nameField.text());
    nameField.text(newAllowanceName);

    $.post(`/${workspaceId}/spending-allowance/update`, {
        spendingAllowanceId: spendingAllowanceId,
        nama: newAllowanceName,
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

    console.log("post");
    $.post(`/${workspaceId}/spending-allowance/delete`, {
        spendingAllowanceId: spendingAllowanceId,
    });
}


