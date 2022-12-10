$(document).ready(function() {


})

function addSpendingPengeluaran() {
    $.get("./spending-allowance/").done(function (response) {
     //   console.log("bisa", response[0])
        var spendingSelect = $("#spendingPengeluaran");

      //  console.log(spendingSelect)
        for (var spending of response) {
          //  console.log(spending)
            var option = document.createElement("option");
            option.text = spending['nama']
            option.value = spending['id']
         //   console.log(option)
            spendingSelect.append(option)
        }
    //    console.log(spendingSelect)

    }).fail(function (response) {
        console.log("fail", response)
    })
}

function addKategoriPemasukan() {
    $.get("./kategori-pemasukan").done(function (response) {
     //   console.log("bisa", response[0])
        var kategoriSelect = $("#kategoriPemasukan");

        // console.log(spendingSelect)
        for (var kategori of response) {
            // console.log(spending)
            var option = document.createElement("option");
            option.text = kategori['nama']
            option.value = kategori['id']
            // console.log(option)
            kategoriSelect.append(option)
        }
        // console.log(spendingSelect)

    }).fail(function (response) {
        console.log("fail", response)
    })
}

function addTagihanPengeluaran() {
    $.get("./tagihan/getlist").done(function (response) {
     //   console.log("bisa", response[0])
        var tagihanSelect = $("#tagihanPengeluaran");

        // console.log(kategoriSelect)
        for (var tagihan of response) {
        //    console.log(tagihan)
            var option = document.createElement("option");
            option.text = tagihan['nama']
            option.value = tagihan['id']
            // console.log(option)
            tagihanSelect.append(option)
        }
        // console.log(kategoriSelect)

    }).fail(function (response) {
        console.log("fail", response)
    })
}

async function onShadowBoxCreateNew(){
    const creationForm = $("#modal-template");

    const innerHtml = creationForm.html();

    var hideModalFunc;
    hideModalFunc = showModal({
        title: "Create New Transaction",
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
            btn.text("Create");
            btn.hide();
        },
        cancelBtnConfigurer: (btn) => {
            removeAllBootstrapColoringClass(btn);
            btn.addClass("btn-secondary");
            btn.text("Cancel");
            btn.hide();
        },
        onOk: ()=>{
            // $("#creation-form").submit();
        },
        onCancel: ()=>{
            hideModalFunc();
        },
    });

    await new Promise(resolve => setTimeout(resolve, 500));
    addKategoriPemasukan()
    addSpendingPengeluaran()
    addTagihanPengeluaran()
}

function submitTagihan() {
    $("#tagihanSubmit").prop('disabled', true);

    var namaData = $('#namaTagihan').val()
    var keteranganData = $('#keteranganTagihan').val()
    var waktuData = $('#waktuTagihan').val()
    var nominalData = $('#nominalTagihan').val()
    var dataPost = {nama: `${namaData}`, keterangan: `${keteranganData}`, waktuStr: `${waktuData}`, nominal: `${nominalData}`};
    console.log(dataPost)

    $.post("./tagihan/create", dataPost).done(async function (response) {
        try{
            $("#tagihanAlert").removeClass("alert-danger").addClass('alert-success')
        } catch (err) {
        }
        $('#tagihanAlert').text('Tambah tagihan success');
        $('#tagihanAlert').show()
        await new Promise(resolve => setTimeout(resolve, 1000));
        location.reload();

    }).fail(alsoHandleGeneralError(
        (err) => handleError('#tagihanAlert', "#tagihanSubmit", err),
        (err) => showError('#tagihanAlert', "#tagihanSubmit", err)
    ))
}

function submitMasukan() {
    $("#pemasukanSubmit").prop('disabled', true);

    var namaData = $('#namaPemasukan').val()
    var keteranganData = $('#keteranganPemasukan').val()
    var waktuData = $('#waktuPemasukan').val()
    var nominalData = $('#nominalPemasukan').val()
    var kategoriData = $('#kategoriPemasukan').val()
    var dataPost = {nama: `${namaData}`, keterangan: `${keteranganData}`, waktu: `${waktuData}`, nominal: `${nominalData}`, kategoriPemasukanId: `${kategoriData}`};

    $.post("./pemasukan/create", dataPost).done(async function (response) {
        try{
            $("#pemasukanAlert").removeClass("alert-danger").addClass('alert-success')
        } catch (err) {
        }
        $('#pemasukanAlert').text('Tambah pemasukan success');
        $('#pemasukanAlert').show()
        await new Promise(resolve => setTimeout(resolve, 1000));
        location.reload();

    }).fail(alsoHandleGeneralError(
        (err) => handleError('#pemasukanAlert', "#pemasukanSubmit", err),
        (err) => showError('#pemasukanAlert', "#pemasukanSubmit", err)
    ))
}

function submitPengeluaran() {
    $("#pengeluaranSubmit").prop('disabled', true);

    var namaData = $('#namaPengeluaran').val()
    var keteranganData = $('#keteranganPengeluaran').val()
    var waktuData = $('#waktuPengeluaran').val()
    var nominalData = $('#nominalPengeluaran').val()
    var spendingData = $('#spendingPengeluaran').val()
    var tagihanData = $('#tagihanPengeluaran').val()
    var dataPost = {nama: `${namaData}`, keterangan: `${keteranganData}`, waktu: `${waktuData}`, nominal: `${nominalData}`, spendingAllowanceId: `${spendingData}`};
    if (tagihanData !== '') {
        dataPost['tagihanId'] = tagihanData
    }

    $.post("./pengeluaran/create", dataPost).done(async function (response) {
        try{
            $("#pengeluaranAlert").removeClass("alert-danger").addClass('alert-success')
        } catch (err) {
        }
        $('#pengeluaranAlert').text('Tambah pengeluaran sukses');
        $('#pengeluaranAlert').show()
        await new Promise(resolve => setTimeout(resolve, 1000));
        location.reload();

    }).fail(alsoHandleGeneralError(
        (err) => handleError('#pengeluaranAlert', "#pengeluaranSubmit", err),
        (err) => showError('#pengeluaranAlert', "#pengeluaranSubmit", err)
    ))
}

function submitMasukan() {
    $("#pemasukanSubmit").prop('disabled', true);

    var namaData = $('#namaPemasukan').val()
    var keteranganData = $('#keteranganPemasukan').val()
    var waktuData = $('#waktuPemasukan').val()
    var nominalData = $('#nominalPemasukan').val()
    var kategoriData = $('#kategoriPemasukan').val()
    var dataPost = {nama: `${namaData}`, keterangan: `${keteranganData}`, waktu: `${waktuData}`, nominal: `${nominalData}`, kategoriPemasukanId: `${kategoriData}`};

    $.post("./pemasukan/create", dataPost).done(async function (response) {
        try{
            $("#pemasukanAlert").removeClass("alert-danger").addClass('alert-success')
        } catch (err) {
        }
        $('#pemasukanAlert').text('Tambah pemasukan sukses');
        $('#pemasukanAlert').show()
        await new Promise(resolve => setTimeout(resolve, 1000));
        location.reload();

    }).fail(alsoHandleGeneralError(
        (err) => handleError('#pemasukanAlert', "#pemasukanSubmit", err),
        (err) => showError('#pemasukanAlert', "#pemasukanSubmit", err)
    ))
}

function submitMasukan() {
    $("#pemasukanSubmit").prop('disabled', true);

    var namaData = $('#namaPemasukan').val()
    var keteranganData = $('#keteranganPemasukan').val()
    var waktuData = $('#waktuPemasukan').val()
    var nominalData = $('#nominalPemasukan').val()
    var kategoriData = $('#kategoriPemasukan').val()
    var dataPost = {nama: `${namaData}`, keterangan: `${keteranganData}`, waktu: `${waktuData}`, nominal: `${nominalData}`, kategoriPemasukanId: `${kategoriData}`};

    $.post("./pemasukan/create", dataPost).done(async function (response) {
        try{
            $("#pemasukanAlert").removeClass("alert-danger").addClass('alert-success')
        } catch (err) {
        }
        $('#pemasukanAlert').text('Tambah pemasukan sukses');
        $('#pemasukanAlert').show()
        await new Promise(resolve => setTimeout(resolve, 1000));
        location.reload();

    }).fail(alsoHandleGeneralError(
        (err) => handleError('#pemasukanAlert', "#pemasukanSubmit", err),
        (err) => showError('#pemasukanAlert', "#pemasukanSubmit", err)
    ))
}



function handleError(dst, but, response) {
    const usernameNotFound = response["responseJSON"]['exceptionName'] === 'UsernameNotFoundException';
    const invalidCredential = response["responseJSON"]['exceptionName'] === `InvalidCredentialException`;
    console.log(response["responseJSON"])
    if (usernameNotFound || invalidCredential) {
        showError(dst, but, response["responseJSON"]['message']);
    } else if (response["responseJSON"]['error'] == 'Bad Request') {
        var kurang = response["responseJSON"]['message'].split("'")
        console.log(kurang[1])
        showError(dst, but, "Kolom "+ kurang[1] + " tidak sesuai");
    }
    else {
        showError(dst, but, "Unidentified error, try again later");
    }
}

function showError(dst, but, msg){
    try{
        $(`${dst}`).removeClass("alert-success").addClass('alert-danger')
    } catch (err) {
    }

    $(`${dst}`).text(msg);
    $(`${dst}`).show();
    $(`${but}`).prop('disabled', false);
}

