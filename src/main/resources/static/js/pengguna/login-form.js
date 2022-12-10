function submitForm() {

    $("#submit_button").prop('disabled', true);

    var usernameData = $('#username_input').val()
    var passwordData = $('#password_input').val()
    var dataPost = {username: `${usernameData}`, password: `${passwordData}`};

    $.post("/login", dataPost).done(async function (response) {
    // console.log("success", response);
    // // $('#password_input').prop('hidden', false)
    // console.log($("#show_result"))
    // console.log($("#show_result").classList);

    try{
    $("#show_result").removeClass("alert-danger").addClass('alert-success')
} catch (err) {
}
    $('#show_result').text('Login Success');
    $('#show_result').show()
    await new Promise(resolve => setTimeout(resolve, 1000));
    window.location.href = '../';

}).fail(alsoHandleGeneralError(
    (err) => handleError(err),
    (err) => showError(err)
    ))
}

    function handleError(response) {
    const usernameNotFound = response["responseJSON"]['exceptionName'] === 'UsernameNotFoundException';
    const invalidCredential = response["responseJSON"]['exceptionName'] === `InvalidCredentialException`;
    if (usernameNotFound || invalidCredential) {
    showError(response["responseJSON"]['message']);
} else {
    showError("Unidentified error, try again later");
}
}

    function showError(msg){
    try{
    $("#show_result").removeClass("alert-success").addClass('alert-danger')
} catch (err) {
}

    $('#show_result').text(msg);
    $('#show_result').show()
    $("#submit_button").prop('disabled', false);
}