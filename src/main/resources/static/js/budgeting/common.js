/**
 * @param allowNameBoxAsSaveBtn bila false, maka user harus click tombol save (ataupun tekan enter) untuk save.
 *                              Bila true, maka user boleh click teks yang sedang diedit untuk men-save.
 */
function initializeShadowBoxHandler(allowNameBoxAsSaveBtn = false){
    const shadowBoxes = $(".shadowed-box");
    for (let shadowBox of shadowBoxes) {
        shadowBox = $(shadowBox);
        assignShadowBoxHandler(shadowBox, allowNameBoxAsSaveBtn);
    }
}

function assignShadowBoxHandler(shadowBox, allowNameBoxAsSaveBtn=false){
    const nameBox = shadowBox.find(".name-box");
    const nameField = nameBox.find(".name");
    const saveBtn = shadowBox.find(".save-icon");
    const deleteBtn = shadowBox.find(".delete-icon");

    function onNameBoxClick(_e, nameBox) {
        if (!nameBox.hasClass("editing")){
            nameBox.addClass("editing");
            onShadowBoxEdit(shadowBox, nameField);

            nameBox.unbind("click");
            if (allowNameBoxAsSaveBtn)
                nameBox.click((e) => onSaveBtnClick(e, nameBox));
        }
    }
    function onSaveBtnClick(_e, nameBox=null) {
        if (nameBox == null)
            nameBox = $(this);

        if (nameBox.hasClass("editing")){
            nameBox.removeClass("editing");
            onShadowBoxSave(shadowBox, nameField);

            setTimeout(() => {
                nameBox.unbind("click");
                nameBox.click((e) => onNameBoxClick(e, nameBox));
            }, 50);
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
    if (!allowNameBoxAsSaveBtn)
        saveBtn.click((e) => onSaveBtnClick(e, nameBox));
}


$(document).ready(function () {
    const flexCreateButton = $("#flex-create-new");
    const fixedCreateButton = $("#fixed-create-new");

    flexCreateButton.click(function () {
        onShadowBoxCreateNew();
    });
    // fixedCreateButton.click(function () {
    //     onShadowBoxCreateNew();
    // });


    if (flexCreateButton.length === 0 || fixedCreateButton.length === 0) {
        console.log("no create button");
        return;
    }

    function onScroll(animationTime=300){
        const isInScreen = isWholeElementInScreen(flexCreateButton, 0);
        if (isInScreen){
            fixedCreateButton.slideUp(animationTime);
        }else{
            // fixedCreateButton.slideDown(animationTime);
        }
    }

    onScroll(0);
    fixedCreateButton.removeClass("hidden");
    $(window).scroll(onScroll);
})