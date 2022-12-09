$(document).ready(() => {
    console.log("ready");


    const shadowBox = $(".shadowed-box");
    const nameBox = shadowBox.find(".name-box");
    const nameField = nameBox.find(".name");
    const saveBtn = shadowBox.find(".save-icon")
    console.log(nameField);

    function onNameBoxClick(_e) {
        console.log("click editBtn");
        if (!nameBox.hasClass("editing")){
            nameBox.addClass("editing");
            onShadowBoxEdit(shadowBox, nameField);

            nameBox.unbind("click");
        }
    }
    function onSaveBtnClick(_e) {
        console.log("click saveBtn");
        if (nameBox.hasClass("editing")){
            nameBox.removeClass("editing");
            onShadowBoxSave(shadowBox, nameField);

            setTimeout(() => {
                nameBox.click(onNameBoxClick);
            }, 100);
        }
    }
    nameBox.click(onNameBoxClick);
    nameBox.keydown(function (e){
        if (e.which === 13) {  // enter key
            console.log("enter");
            onSaveBtnClick(null);
            e.preventDefault()
        }
    });
    saveBtn.click(onSaveBtnClick);
});

function onShadowBoxEdit(shadowBox, nameField){
    console.log(nameField);
    nameField.attr("contenteditable", 'true');
    nameField.focus();
    nameField.selectText();
}

function onShadowBoxSave(shadowBox, nameField){
    console.log(nameField);
    nameField.attr("contenteditable", 'false');
}



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