
function getAlokasi(){
    const alokasiJson = $("#daftar-alokasi").html();
    const alokasi = JSON.parse(alokasiJson);
    return alokasi;
}
function getSpendingAllowance(){
    const json = $("#daftar-spending-allowance").html();
    return  JSON.parse(json);
}
function isCreate(){
    const kategoriId = $("#kategoriPemasukanId");
    const res = kategoriId.val();
    if (res == null)
        return true;
    if (res.length === 0)
        return true;
    return false;
}


function initializeForm(alokasis){
    for (const alokasi of alokasis) {
        const {spendingAllowance, besarAlokasi} = alokasi;
        const {id} = spendingAllowance;

        addNewPair(id, besarAlokasi);
    }

    if (isCreate()) {
        addNewPair(null, 0.0);
    }
}
$(document).ready(function () {
    initializeForm(getAlokasi());
})


function onAddNewSpendingAllowanceClick(){
    addNewPair();
}

function onSubmitClick(){
    let form = $("#form-element");
    let actionUrl = "update";
    if (isCreate()) {
        actionUrl = "create";
    }


    $.ajax({
        type: "POST",
        url: "./" + actionUrl,
        data: form.serialize(), // serializes the form's elements.

    }).done(function(data) {
        addPendingToast({
            titleConfigurer: (el) => {el.text("Saved"); el.addClass("text-primary")},
            body: `Kategori pemasukan berhasil disimpan`
        });
        window.location.href = "list";

    }).fail(alsoHandleGeneralError(function(error) {
        const {message} = error.responseJSON;

        showToast({
            titleConfigurer: (el) => {el.text("Error"); el.addClass("text-danger")},
            body: message
        });
    }, (msg) => {
        showToast({
            titleConfigurer: (el) => {el.text("Error"); el.addClass("text-danger")},
            body: msg
        });
    }));
}


let idPair = 0;
function addNewPair(selectedSpendingAllowanceId=null, besarAlokasi){
    const parent = $(".controls");

    const innerhtml = $("#alokasi-biasa").html();
    const element = $(innerhtml);
    parent.append(element);

    const selector = element.find(".spending-all-id");
    const inputAlokasi = element.find(".besar-alokasi");
    const deleteBtn = element.find(".btn-delete");

    selector.addClass("id-"+idPair);
    inputAlokasi.addClass("id-"+idPair);
    inputAlokasi.val(besarAlokasi);

    deleteBtn.click(function (){
        element.remove();
    });


    const spendingAllowances = getSpendingAllowance();
    for (const spendingAllowance of spendingAllowances) {

        const selected = (spendingAllowance.id == selectedSpendingAllowanceId);

        let option = $("<option></option>", {
            selected: selected,
            value: spendingAllowance.id,

        }).appendTo(selector);

        option.text(spendingAllowance.nama);
    }

    idPair = idPair+1;
}