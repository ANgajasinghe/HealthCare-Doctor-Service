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

function validateForm(){

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
}

$(document).on('click' , '#btnUpdate',function(event) {
    //access Data attr from button 
    var doc_id = $(this).data().doc_id;
    SetModelUI(doc_id);
    
    var form = InitUIElement().form;

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



$("#btnAdd").on('click', function () {
    SetModelUI(null);
});

$('#modelBtn').on('click', function () {
    var form = InitUIElement().form;
    if(form.doc_id.val() === ""){
        //save

        SaveDoctorInformation();
    }
    else{
        console.log(form.doc_id.val());
        //SaveDoctorInformation();
    }

});

function SaveDoctorInformation() {
   var obj  = InitUIElement().saveForm.serializeArray();
   var formVal = FormToJSON(obj);
//    var formVal = {
//     doc_address: "214/33,Kandy,Madapatha",
//     doc_city: "Piliyanadala",
//     doc_email: "Kumara@fff.com",
//     doc_first_name: "Supun",
//     doc_last_name: "dilshan",
//     doc_reg_no: "D9000",
//     doc_tp1: "5558858",
//     doc_tp2: "5558588",
//     doc_tp3: "5858585858",
//     specification_id: "2"
//    }

   console.log(formVal);

    $.ajax({
        type:"POST",
        url: "http://localhost:8080/doctors/webapi/doc/add",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(formVal),
        success: function (response) {
            console.log(response)
            Init();
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


function InitUIElement(){
    var DOMobj = {
        grid   : $("#doc_grid"),
        grid_body:$("#doc_grid_body"),
        drpdown:$("#specification_id"),
        addBtn : $("#btnAdd"),
        modelHedding : $("#Add-heading"),
        modelBtn : $("#modelBtn"),
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
        }
    };

    return DOMobj;
}

 function InitGrid(jsonList) {
    InitUIElement().grid_body.empty();
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
        '<button class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal" data-target="#delete" data-doc_id='+value.doc_id+'>'+
        '<i class="fas fa-trash"></i></button></p>'+
        '</td>');

        InitUIElement().grid.append(tr);
    });
    return;

};


function InitDropDown(jsonList){
    var option = '';
    $.each(jsonList, function (indexInArray, value) { 
         option = option + ('<option value='+value.specification_id+'>'+value.specification_name+'</option>');
    });
    InitUIElement().drpdown.append(option);
}

function SetModelUI(doc_id){
    document.querySelector('form').reset();
    
    if(doc_id !== null){
        $(InitUIElement().modelHedding).html("Edit Your Detail"); 
        $(InitUIElement().modelBtn).removeClass("btn-success")
                                   .html("Update")
                                   .addClass("btn-warning");
    }
    else{
        $(InitUIElement().modelHedding).html("Add a doctor"); 
        $(InitUIElement().modelBtn).removeClass("btn-warning")
                                   .html("Save")
                                   .addClass("btn-success");
    }
    return;
}














