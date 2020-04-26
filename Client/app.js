

var Model = (function name() {
    var OnGetCompleate = function (response) {
        var resultSet = response;

        if (resultSet.response_status === 1) {
            //console.log(response.responseJSON);
            return true;

        }
        else if (resultSet.response_status === 0) {
            console.log("it works with eror");
        }
    }

    return{
        efg : function(response,status){
            return OnGetCompleate(response,status)
        }
    }



})();


var UIController = (function () {

    var DOMobj = {
        grid : $("#doc_grid")
    };

    var setGrid = function (jsonList) {
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
            DOMobj.grid.append(tr);
        });
    }

    return{
        Grid : function(jsonList) {
            return setGrid(jsonList);
        }
    }


})();


var Controller = (function (Mod,UICrtl) {
    var url = {
        "getAllDocters": "http://localhost:8080/doctors/webapi/doc"
    };
    var getAllDoc = function () {
        $.ajax({
            type: "GET",
            url: url.getAllDocters,
            dataType: "json",
            success: function(response){
               if(Mod.efg(response)){
                   UICrtl.Grid(response.doc_list);
               }
            },
            error: function (jqXhr, textStatus, errorMessage) {}
        });
    }



    


    return {
        init: function () {
            console.log('Application is started');
            getAllDoc();
        }
    }

})(Model,UIController);


$(document).ready(function(CM) {
    Controller.init();
});











