$(document).ready(function() {
    Init();
});

//CLIENT-MODEL===================================================================================

function  validateInit(response) {
    var resultSet = response;
    if (resultSet.response_status === 0) {
        InitAterts(response.error.ERROR_NAME,"alert-warning");
        return "false";
    }
    return true;     
}

//validte
function validateForm(){
    //get all form elements
    var formArray = DOMobj().saveForm.serializeArray();
    var errorList = "";
    for (var i = 0; i < formArray.length; i++){
        if((formArray[i]['value'] === "" || formArray[i]['value'] === "-1")
        && formArray[i]['name'] !== "doc_tp3"
        && formArray[i]['name'] !== "doc_id"
        ){
            errorList = errorList + "<p>" + formArray[i]['name'] + " is Can not empty please fill it </p>";
        }
    }
    DOMobj().alerts.warning.show().html(errorList);
    if(errorList !== ""){
        return false;
    }
    return true
}


//CONTROLLER=====================================================================================
function Init() {  
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/doctors/webapi/doc",
        contentType: "application/json",
        dataType: "json",
        success: function(response){
            if(validateInit(response)){
               
                InitGrid(response.doc_list);
            }
        },
        error: function (jqXhr, textStatus, errorMessage) {}
    });

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/doctors/webapi/doc/spec",
        dataType: "json",
        success: function (response) {
            if(validateInit(response)){
                InitDropDown(response.doc_list);
            }
        },
        error: function (jqXhr, textStatus, errorMessage) {
            console.log(textStatus);
        }
    });

    //Set Alerts
    InitAterts();

}

//set moddel according to Update and add button 
$(document).on('click' , '#btnUpdate',function(event) {
    //access Data attr from button 
    var doc_id = $(this).data().doc_id;
    SetModelUI(doc_id);
    
    var form = DOMobj().form;

    form.doc_id.val(doc_id);
    form.doc_reg_no.val($(this).closest("tr").find('td:eq(0)').text());
    form.specification_id.val($(this).closest("tr").find('td:eq(1)').text());
    form.doc_first_name.val($(this).closest("tr").find('td:eq(3)').text());
    form.doc_last_name.val($(this).closest("tr").find('td:eq(4)').text());
    form.doc_email.val($(this).closest("tr").find('td:eq(5)').text());
    form.doc_tp1.val($(this).closest("tr").find('td:eq(6)').text());
    form.doc_tp2.val($(this).closest("tr").find('td:eq(7)').text());
    form.doc_tp3.val($(this).closest("tr").find('td:eq(8)').text());
    form.doc_address.val($(this).closest("tr").find('td:eq(9)').text());
    form.doc_city.val($(this).closest("tr").find('td:eq(10)').text());


});

$(document).on('click' , '#btnUpdate',function(event) {
    //access Data attr from button 
    var doc_id = $(this).data().doc_id;
    SetModelUI(doc_id);
    
    var form = DOMobj().form;

    form.doc_id.val(doc_id);
    form.doc_reg_no.val($(this).closest("tr").find('td:eq(0)').text());
    form.specification_id.val($(this).closest("tr").find('td:eq(1)').text());
    form.doc_first_name.val($(this).closest("tr").find('td:eq(3)').text());
    form.doc_last_name.val($(this).closest("tr").find('td:eq(4)').text());
    form.doc_email.val($(this).closest("tr").find('td:eq(5)').text());
    form.doc_tp1.val($(this).closest("tr").find('td:eq(6)').text());
    form.doc_tp2.val($(this).closest("tr").find('td:eq(7)').text());
    form.doc_tp3.val($(this).closest("tr").find('td:eq(8)').text());
    form.doc_address.val($(this).closest("tr").find('td:eq(9)').text());
    form.doc_city.val($(this).closest("tr").find('td:eq(10)').text());


});

$(document).on('click' , '#btnDelete',function(event) {
    var doc_id = $(this).data().doc_id;
    var form = DOMobj().formDel;
    form.doc_id.val(doc_id);
});


$("#btnAdd").on('click', function () {
    InitAterts();
    SetModelUI(null);
});

//------------Model button action-----------
$(DOMobj().buttons.save).on('click', function () {
    //-------Identify Save button---------------
    if(DOMobj().form.doc_id.val() === ""){ //implement save action 
        if(validateForm() === true){
            SaveDoctorInformation();
        }else{
            return;
        }    
    }
    else{ //Implement Update action 
        console.log(form.doc_id.val());
        //SaveDoctorInformation();
    }

});

//------------Remove Button action----------
$(DOMobj().buttons.delete).on('click', function () {
    console.log(DOMobj().formDel.doc_id.val())
});




//----------Remove Alerts--------------
$('table').on('click', function () {
        InitAterts();
});
$('.alert').on('click', function () {
        InitAterts();
});




function SaveDoctorInformation() {
   var obj  = DOMobj().saveForm.serializeArray();
   var formVal = FormToJSON(obj);

    $.ajax({
        type:"POST",
        url: "http://localhost:8080/doctors/webapi/doc/add",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(formVal),
        success: function (response) {
            if(validateInit(response) === true){
                InitGrid(response.doc_list);
                InitAterts("New Doctor Added Successfully" ,"alert-success")
            }
            
        },
        error: function (jqXhr, textStatus, errorMessage) {
            console.log(textStatus);
        }
    });
}

function FormToJSON(formArray) {//serialize data function

    var oJSON = {};
    for (var i = 0; i < formArray.length; i++){
        oJSON[formArray[i]['name']] = formArray[i]['value'];
    }

    $.each(oJSON, function(key, value){
        if (value === "" || value === null){
            delete oJSON[key];
        }
    });

    return oJSON;
}

//UI CONTROLLER==================================================================================


function DOMobj(){
    var DOMobj = {
        grid   : $("#doc_grid"),
        grid_body:$("#doc_grid_body"),
        drpdown:$("#specification_id"),
        addBtn : $("#btnAdd"),
        modelHedding : $("#Add-heading"),
        modelBtn : $("#modelBtn"),
        modelBtn1 : $("#modelBtn1"),
        saveForm : $("#form"),
        form:{
            doc_id :$("#doc_id"),
            doc_address: $("#doc_address"),
            doc_city: $("#doc_city"),
            doc_email: $("#doc_email"),
            doc_first_name:$("#doc_first_name"),
            doc_last_name:$("#doc_last_name"),
            doc_reg_no: $("#doc_reg_no"),
            doc_tp1: $("#doc_tp1"),
            doc_tp2: $("#doc_tp2"),
            doc_tp3: $("#doc_tp3"),
            specification_id:$("#specification_id")
        },
        formDel:{
            doc_id :$("#doc_idD"),
        },
        alerts:{
            warning : $("#alert_warning")
        },
        buttons:{
            delete: $('#modelBtnRemove'),
            save: $('#modelBtn')
            
        }
    };

    return DOMobj;
};

 function InitGrid(jsonList) {
    DOMobj().grid_body.empty();
    var tr = '';
    $.each(jsonList,function (key,value) { 
        tr = $('<tr/>');
        tr.append('<td>'+value.doc_reg_no+'</td>');
        tr.append('<td hidden>'+value.specification_id+'</td>');
        tr.append('<td>Surgeon</td>');
        tr.append('<td>'+value.doc_first_name+'</td>');
        tr.append('<td>'+value.doc_last_name+'</td>');
        tr.append('<td>'+value.doc_email+'</td>');
        tr.append('<td>'+value.doc_tp1+'</td>');
        tr.append('<td>'+value.doc_tp2+'</td>');
        value.doc_tp3 = value.doc_tp3 == null ? '<small><i>not available</i></small>' : value.doc_tp3;
        tr.append('<td>'+value.doc_tp3 +'</td>');
        tr.append('<td>'+value.doc_address+'</td>');
        tr.append('<td>'+value.doc_city+'</td>');
        tr.append('<td><p data-placement="top" data-toggle="tooltip" title="Edit">'+
        '<button id="btnUpdate" class="btn btn-primary btn-xs" data-title="Edit" data-toggle="modal" data-target="#add" data-doc_id='+value.doc_id+'>'+ 
        '<i class="fas fa-edit"></i></button></p>'+
       '</td>');
        tr.append('<td><p data-placement="top" data-toggle="tooltip" title="Delete">'+
        '<button id="btnDelete" class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal" data-target="#delete" data-doc_id='+value.doc_id+'>'+
        '<i class="fas fa-trash"></i></button></p>'+
        '</td>');

        DOMobj().grid.append(tr);
    });
    return;

};

function InitDropDown(jsonList){
    var option = '';
    $.each(jsonList, function (indexInArray, value) { 
         option = option + ('<option value='+value.specification_id+'>'+value.specification_name+'</option>');
    });
    DOMobj().drpdown.append(option);
};

function SetModelUI(doc_id){
    document.querySelector('form').reset();
    
    if(doc_id !== null){
        $(DOMobj().modelHedding).html("Edit Your Detail"); 
        $(DOMobj().modelBtn).removeClass("btn-success")
                                   .html("Update")
                                   .addClass("btn-warning");
    }
    else{
        $(DOMobj().modelHedding).html("Add a doctor"); 
        $(DOMobj().modelBtn).removeClass("btn-warning")
                                   .html("Save")
                                   .addClass("btn-success");
    }
    return;
};

function InitAterts(value , CssClass){
    if(value == null){
        DOMobj().alerts.warning.empty().hide();
    } 
    else if(CssClass === "alert-warning"){
        DOMobj().alerts.warning.removeClass('alert-danger')
                                      .addClass(CssClass)
                                      .show();

        DOMobj().alerts.warning.html( 'Can not save ' + value);                              
    }
    else if(CssClass === "alert-success"){
        DOMobj().alerts.warning.removeClass('alert-danger')
        .addClass(CssClass)
        .show();

        DOMobj().alerts.warning.html( value ); 
    }
    
}














