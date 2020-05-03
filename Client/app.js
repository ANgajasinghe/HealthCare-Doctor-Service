$(document).ready(function() {
    $('select').selectpicker();
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
            
            if(validateInit(response)){
                InitUI(response.doc_list);
            }
        },
        error: function (jqXhr, textStatus, errorMessage) {}
    });
}

$(document).on('click' , '#btnUpdate',function(event) {
    //access Data attr from button 
    var doc_id = $(this).data().doc_id;
    SetModelUI(doc_id);

});

$("#btnAdd").on('click', function () {
    SetModelUI(null);
});




//UI CONTROLLER==================================================================================


function InitUIElement(){
    var DOMobj = {
        grid   : $("#doc_grid"),
        addBtn : $("#btnAdd"),
        modelHedding : $("#Add-heading"),
        modelBtn : $("#modelBtn")
    };

    return DOMobj;
}

 function InitUI(jsonList) {
    
    var tr = '';
    $.each(jsonList,function (key,value) { 
        tr = $('<tr/>');
        tr.append('<td>'+value.doc_reg_no+'</td>');
        tr.append('<td>'+value.specification_id+'</td>');
        tr.append('<td>'+value.doc_first_name+' '+value.doc_last_name+'</td>');
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
        tr.append("<td><input name='btnUpdate' type='button' value='Update'"+
        "class='btn btn-secondary' data-doc_id='" + value.doc_id + "'>" +
        "</td>");

        InitUIElement().grid.append(tr);
    });
    return;

};

function SetModelUI(doc_id){

    //refersh select item
    document.querySelector('form').reset();
    $('select').selectpicker('refresh');

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

// $(InitUIElement().addBtn).hover(function () {
//     $( this ).append( $( " <span> Add a new doctor </span>" ) );
        
//     }, function () {
//         $( this ).find( "span" ).last().remove();
//     }
// );




// data-doc_id='" + value.doc_id + 


// <td>
//             <p data-placement="top" data-toggle="tooltip" title="Edit"><button
//                     class="btn btn-primary btn-xs" data-title="Edit" data-toggle="modal"
//                     data-target="#edit"><span class="glyphicon glyphicon-pencil"></span></button>
//             </p>
//         </td>
//         <td>
//             <p data-placement="top" data-toggle="tooltip" title="Delete"><button
//                     class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal"
//                     data-target="#delete"><span class="glyphicon glyphicon-trash"></span></button>
//             </p>
//         </td>












