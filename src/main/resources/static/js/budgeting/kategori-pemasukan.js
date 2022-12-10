$(document).ready(() => initializeShadowBoxHandler(true));

function onShadowBoxEdit(shadowBox, nameField){
    shadowBox.find(".all-alokasi").slideDown(300);
}


function onShadowBoxSave(shadowBox, nameField){
    shadowBox.find(".all-alokasi").slideUp(300);
}

function showFailedToast(title, message){
    showToast({
        titleConfigurer: (el) => {el.text(title); el.addClass("text-danger")},
        body: message
    });
}

function editKategori(el){
    el = $(el);
    const kategoriId = el.attr("data-kategori-id");

    window.location.href = "./create-edit?kategoriId=" + kategoriId;
}



function onShadowBoxCreateNew(){
    window.location.href = "./create-edit";
}

function onShadowBoxDeleteWithConfirmation(shadowBox, nameField){
    const nama = removeUnnecessaryWhitespaces(nameField.text());

    var hideModalFunc;
    hideModalFunc = showModal({
        title: "Delete Kategori Pemasukan",
        body: `Apakah Anda yakin ingin menghapus kategori "${nama}"?`,
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
    const kategoriPemasukanId = nameField.attr("data-kategori-id");
    const nama = removeUnnecessaryWhitespaces(nameField.text());

    console.log("post");
    $.post(`/${workspaceId}/kategori-pemasukan/delete`, {
        kategoriPemasukanId: kategoriPemasukanId,
    }).done(function () {
        addPendingToast({
            titleConfigurer: (el) => {el.text("Kategori Deleted"); el.addClass("text-primary")},
            body: `Kategori "${nama}" berhasil dihapus`
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




/*
function onShadowBoxCreateNew(){
    const creationForm = $("#creation-form-template");

    const innerHtml = creationForm.html();

    var hideModalFunc;
    hideModalFunc = showModal({
        title: "Create New Spending Allowance",
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

    const workspaceId = nameField.attr("data-workspace-id");
    const spendingAllowanceId = nameField.attr("data-allowance-id");
    const newAllowanceName = removeUnnecessaryWhitespaces(nameField.text());
    nameField.text(newAllowanceName);

    $.post(`/${workspaceId}/spending-allowance/update`, {
        spendingAllowanceId: spendingAllowanceId,
        nama: newAllowanceName,
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




*/
