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


$(document).ready(function () {
    const flexCreateButton = $("#flex-create-new");
    const fixedCreateButton = $("#fixed-create-new");

    if (flexCreateButton.length === 0 || fixedCreateButton.length === 0) {
        console.log("no create button");
        return;
    }

    function onScroll(animationTime=300){
        const isInScreen = isWholeElementInScreen(flexCreateButton, 0);
        console.log(isInScreen);
        if (isInScreen){
            fixedCreateButton.slideUp(animationTime);
        }else{
            fixedCreateButton.slideDown(animationTime);
        }
    }

    onScroll(0);
    fixedCreateButton.removeClass("hidden");
    $(window).scroll(onScroll);

    flexCreateButton.click(function () {
        onShadowBoxCreateNew();
    });
    fixedCreateButton.click(function () {
        onShadowBoxCreateNew();
    });
})