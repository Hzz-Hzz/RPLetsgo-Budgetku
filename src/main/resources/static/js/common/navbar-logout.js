function onLogOut(){

    console.log("masukkkkkkk");
    var hideModalFunc;
    hideModalFunc = showModal({
        title: "Log Out",
        body: `Apakah Anda yakin ingin log out?`,
        okBtnConfigurer: (btn) => {
            removeAllBootstrapColoringClass(btn);
            btn.addClass("btn-danger");
            btn.text("Log out");
        },
        cancelBtnConfigurer: (btn) => {
            removeAllBootstrapColoringClass(btn);
            btn.addClass("btn-secondary");
            btn.text("Cancel");
        },
        onOk: ()=>{
            $.get(`/logout`).done(async function () {
                window.location = '/login';

            }).fail(alsoHandleGeneralError(
                (err) => handleError(err),
                (err) => showError(err)
            ))
            // hideModalFunc();
        },
        onCancel: ()=>{
            console.log("cancel");
            hideModalFunc();
        },
    });
}