$(document).ready(function() {
    Init();
});

//CLIENT-MODEL===================================================================================
function  validateInit(response) {
    var resultSet = response;
    if (resultSet.response_status === 0) {
        console.log(resultSet);
        return "Healthcare Doctor Service is Down at the morment";
    }
    return true;     
}

//CONTROLLER=====================================================================================
function Init() {  
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/doctors/webapi/doc",
        dataType: "json",
        success: function(response){
            //
            if(validateInit(response)){
                InitUI(response.doc_list);
            }
        },
        error: function (jqXhr, textStatus, errorMessage) {}
    });
}

//UI CONTROLLER==================================================================================
function InitUIElement(){
    var DOMobj = {
        grid : $("#doc_grid")
    };

    return DOMobj;
}

 function InitUI(jsonList) {
    var tr = '';
    $.each(jsonList,function (key,value) { 
        tr = $('<tr/>');
        tr.append('<td>'+value.doc_id+'</td>');
        tr.append('<td>'+value.doc_reg_no+'</td>');
        tr.append('<td>'+value.specification_id+'</td>');
        tr.append('<td>'+value.doc_first_name+'</td>');
        tr.append('<td>'+value.doc_last_name+'</td>');
        tr.append('<td>'+value.doc_email+'</td>');
        tr.append('<td>'+value.doc_tp1+'</td>');
        tr.append('<td>'+value.doc_tp2+'</td>');
        value.doc_tp3 = value.doc_tp3 == null ? '<small><i>not available</i></small>' : value.doc_tp3;
        tr.append('<td>'+value.doc_tp3 +'</td>');
        tr.append('<td>'+value.doc_address+'</td>');
        tr.append('<td>'+value.doc_city+'</td>');
        tr.append('<td>'+value.doc_status_id+'</td>');
        InitUIElement().grid.append(tr);
    });
    return;

};
















