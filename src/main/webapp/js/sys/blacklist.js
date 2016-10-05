$(function() {
    ko.applyBindings(new ViewModel());
});


var ViewModel = function() {
    var self = this;
    
    self.phone = ko.observable("");
    self.idNumber = ko.observable("");
    self.list = ko.observableArray([]);
    self.shouldShowPage = ko.observable(false);
    
    self.reset = function() {
        self.phone("");
        self.idNumber("");
    };
    
    var params = {};
    
    self.query = function() {
        params.phone = self.phone();
        params.idNumber = self.idNumber();
        
        if (params.phone == '' && params.idNumber == '') {
            alert("请输入“手机号”或者 “身份证号”");
            return;
        }
        
        self.search(params);
    };
    
    self.search = function(params) {
        $.ajax({
            url: '../../patient/blackList',
            type: 'post',
            data: params,
            success: function(response) {
                if (response.retCode == '00000') {
                    self.list.removeAll();
                    
                    $.each(response.data, function(){
                        self.list.push(new Patient(this));
                    });
                    
                    if (response.data.length == 0) {
                        self.shouldShowPage(true);
                    } else {
                        self.shouldShowPage(false);
                    }
                }
            }
        });
    };
    
    self.selected = null;
    self.select = function() {
        self.selected = this;
    }
    
    self.unlock = function() {
        $.ajax({
            url: '../../patient/unlock',
            type: 'post',
            data: {idNumber: self.selected.idNumber()},
            success: function(response) {
                if (response.retCode == '00000') {
                    $("#myModal").modal("hide");
                    alert("解锁成功");
                    self.search(params);
                } else {
                    alert(response.retInfo);
                }
            }
        });
    }
};

var Patient = function(data) {
    var self = this;

    self.name = ko.observable(data.name);
    self.idNumber = ko.observable(data.idNumber);
    self.phone = ko.observable(data.phone);
    self.defaultCount = ko.observable(data.defaultCount);
};

