function onRemoveFinancialEvent(el){
    el = $(el);
    const financialEventId = el.attr("data-financial-event-id");
    const financialEventNama = el.attr("data-financial-event-name");

    var hideModalFunc;
    hideModalFunc = showModal({
        title: "Delete Financial Event",
        body: `Apakah Anda yakin ingin menghapus budget "${financialEventNama}"?`,
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
            deleteFinancialEvent(financialEventId);  // TODO @Pradipta.davi
            hideModalFunc();
        },
        onCancel: ()=>{
            console.log("cancel");
            hideModalFunc();
        },
    });
}