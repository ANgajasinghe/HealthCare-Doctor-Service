InitAterts();
$(document).ready(function() {
    //Set Alerts
    Init();
});

//CLIENT-MODEL===================================================================================

function  validateResponse(response) {
    var resultSet = response;
    if (resultSet.response_status === 0) {
        InitAterts(response.error.ERROR_NAME,"alert-warning");
        return "false";
    };
    return true;     
};

function validateForm(){
    //get all form elements
    var formArray = DOMobj().saveForm.serializeArray();
    var errorList = "";
    for (var i = 0; i < formArray.length; i++){
        //form Empty checker ------
        if((formArray[i]['value'].trim() === "" || formArray[i]['value'].trim() === "-1")
        && formArray[i]['name'] !== "doc_tp3"
        && formArray[i]['name'] !== "doc_id"
        ){
            errorList = errorList + "<p>" + formArray[i]['name'] + " is Can not empty please fill it </p>";
        };

        if(formArray[i]['name'] === "doc_tp3" || 
        formArray[i]['name'] === "doc_tp2" || 
        formArray[i]['name'] === "doc_tp1"){

            if(!$.isNumeric(formArray[i]['value'].trim()) ){
                if(formArray[i]['name'] === "doc_tp3"){
                    if(formArray[i]['value'] !== ""){
                        //if(formArray[i]['value'].trim().length < 10){
                            console.log("Calling");
                            errorList = errorList + "<p>" + formArray[i]['name'] + " <b>Phone Number must need 10 digit or More</b></p>";
                       // }
                    }
                } else{
                   // if(formArray[i]['value'].trim().length < 10){
                        errorList = errorList + "<p>" + formArray[i]['name'] + " <b>Phone Number must need 10 digit or More</b></p>";
                   // }
                }

              
            }
        };

        
    };
    var closeBtn = '<p class="text-sm-right"><i class="fas fa-window-close"></i></p>';
    DOMobj().alerts.warning.show().html(closeBtn + errorList);
    if(errorList !== ""){
        return false;
    };
    return true
};

function FormToJSON(formArray) {

    var oJSON = {};
    for (var i = 0; i < formArray.length; i++){
        oJSON[formArray[i]['name']] = formArray[i]['value'].trim();
    }

    $.each(oJSON, function(key, value){
        if (value === "" || value === null){
            delete oJSON[key];
        }
    });

    return oJSON;
};



//CONTROLLER=====================================================================================
function Init() { 
    //get all doctors from table  
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/doctors/webapi/doc",
        contentType: "application/json",
        dataType: "json",
        success: function(response){
            if(validateResponse(response)){
               
                InitGrid(response.doc_list);
            }
        },
        error: function (jqXhr, textStatus, errorMessage) {}
    });

    //get all Specification
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/doctors/webapi/doc/spec",
        dataType: "json",
        success: function (response) {
            if(validateResponse(response)){
                InitDropDown(response.doc_list);
            }
        },
        error: function (jqXhr, textStatus, errorMessage) {
            console.log(textStatus);
        }
    });

    

};

//set moddel according to Update and add button 
$(document).on('click' , '#btnUpdate',function(event) {
    //access Data attr from button 
    var doc_id = $(this).data().doc_id;
    SetModelUI(doc_id);
    
    var form = DOMobj().form;

    form.doc_id.val(doc_id);
    form.doc_reg_no.val($(this).closest("tr").find('td:eq(0)').text());
    form.specification_id.val($(this).closest("tr").find('td:eq(1)').text());

    if($(this).closest("tr").find('td:eq(2)').text() === "0"){
        form.deactive.prop("checked",true);
    } else{
        form.active.prop("checked",true);
    }
    form.doc_first_name.val($(this).closest("tr").find('td:eq(4)').text());
    form.doc_last_name.val($(this).closest("tr").find('td:eq(5)').text());
    form.doc_email.val($(this).closest("tr").find('td:eq(6)').text());
    form.doc_tp1.val($(this).closest("tr").find('td:eq(7)').text());
    form.doc_tp2.val($(this).closest("tr").find('td:eq(8)').text());
    form.doc_tp3.val($(this).closest("tr").find('td:eq(9)').text());
    form.doc_address.val($(this).closest("tr").find('td:eq(10)').text());
    form.doc_city.val($(this).closest("tr").find('td:eq(11)').text());

    

});

$(document).on('click' , '#btnDelete',function(event) {
    var doc_id = $(this).data().doc_id;
    DOMobj().formDel.doc_id.val(doc_id);
});

$("#btnAdd").on('click', function () {
    InitAterts();
    SetModelUI(null);
});


$(DOMobj().buttons.delete).on('click', function () {
    var id = DOMobj().formDel.doc_id.val();
    $.ajax({
        type: "DELETE",
        url: "http://localhost:8080/doctors/webapi/doc/delete/"+id,
        data: "data",
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
            if(validateResponse(response) === true){
                InitGrid(response.doc_list);
                InitAterts("Delete Operation Successfully Implemented"  ,"alert-success")
            }
        }
    });
   
});


$(DOMobj().buttons.save).on('click', function () {
    
    if(DOMobj().form.doc_id.val() === ""){  
        if(validateForm() === true){
            SaveDoctorInformation("POST");
        }else{
            return;
        }    
    }
    else{ 
        SaveDoctorInformation("PUT");
    }

});

function SaveDoctorInformation(type) {
   var obj  = DOMobj().saveForm.serializeArray();
   var formVal = FormToJSON(obj);

   console.log(formVal);

    var url ="";
    var message = "";

    if (type === "POST") {
        url = "http://localhost:8080/doctors/webapi/doc/add";
        message = "New Doctor Added Successfully";
    } 
    else if(type === "PUT"){
        url = "http://localhost:8080/doctors/webapi/doc/update";
        message = formVal.doc_reg_no + ":- Doctor updated sucessfully";
    } 


    $.ajax({
        type: type,
        url: url,
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(formVal),
        success: function (response) {
            if(validateResponse(response) === true){
                InitGrid(response.doc_list);
                InitAterts(message ,"alert-success")
            }
            
        },
        error: function (jqXhr, textStatus, errorMessage) {
            console.log(textStatus);
        }
    });
};





//UI CONTROLLER==================================================================================
/*
UI Controller start in here
    DOMobj() :- Have all DOM id(s) and return those;
    InitGrid(jsonList):-Service returns Just JSON obj this function will bind that to grid 
    SetModelUI(doc_id):- The same model form uses in add and update events: this function will change model form according to action
    InitAterts(value , CssClass):- Alert Init and change same alert classes in this function, according to action.
    //----------Remove Alerts-------------- :
        In this section functions will remove responces Alerts when click the table , add button or same alert.
    */

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
            specification_id:$("#specification_id"),
            active:$("#radiobtn1"),
            deactive:$("#radiobtn2")
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
        value.doc_status_id === 0 ? 
        tr.append('<td class="text-danger">'+value.doc_reg_no+'</td>') : tr.append('<td class="text-success">'+value.doc_reg_no+'</td>')
        //tr.append('<td>'+value.doc_reg_no+'</td>');
        tr.append('<td hidden>'+value.specification_id+'</td>');
        tr.append('<td hidden>'+value.doc_status_id+'</td>');
        tr.append('<td>'+value.specification_name+'</td>');
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
                                   .prop("value","Update")
                                   .addClass("btn-warning");
    }
    else{
        $(DOMobj().modelHedding).html("Add a doctor"); 
        $(DOMobj().modelBtn).removeClass("btn-warning")
                                   .prop("value","Save")
                                   .addClass("btn-success");
    }
    return;
};

function InitAterts(value , CssClass){

    var closeBtn = '<div class="text-sm-right"><i class="fas fa-window-close"></i></div>';
    if(value == null){
        DOMobj().alerts.warning.empty().hide();
    } 
    else if(CssClass === "alert-warning"){
        DOMobj().alerts.warning.removeClass('alert-danger')
                                      .addClass(CssClass)
                                      .show();

        DOMobj().alerts.warning.html( 'Can not save ' + closeBtn + value);                              
    }
    else if(CssClass === "alert-success"){
        DOMobj().alerts.warning.removeClass('alert-danger')
        .addClass(CssClass)
        .show();

        DOMobj().alerts.warning.html( closeBtn + value ); 
    }
    
};

//----------Remove Alerts--------------
$('table').on('click', function () {
    InitAterts();
});
$('.alert').on('click', function () {
    InitAterts();
});













