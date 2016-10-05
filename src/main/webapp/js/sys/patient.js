$(function() {
    ko.applyBindings(new ViewModel());
});


var ViewModel = function() {
    var self = this;
    
    self.name = ko.observable("");
    self.phone = ko.observable("");
    self.idNumber = ko.observable("");
    self.sex = ko.observable("");
    
    self.list = ko.observableArray([]);
    self.total = ko.observable("");
    self.shouldShowPage = ko.observable(true);
    
    self.reset = function() {
        self.name("");
        self.phone("");
        self.idNumber("");
        self.sex("");
    };
    
    var params = {};
    
    self.query = function() {
        params.name = self.name();
        params.phone = self.phone();
        params.idNumber = self.idNumber();
        params.sex = self.sex();
        params.page = 1;
        params.size = 10;
        
        self.search(params);
    };
    
    
    self.search = function(params) {
        $.ajax({
            url: '../../patient/list',
            type: 'post',
            data: params,
            success: function(response) {
                if (response.retCode == '00000') {
                    self.list.removeAll();
                    
                    $.each(response.data.list, function(){
                        self.list.push(new Patient(this));
                    });
                    
                    if (response.data.count > 0) {
                        self.shouldShowPage(true);
                        var s = (params.page - 1) * 10;
                        self.total("显示 " + (s + 1) + " 至 " + (s + response.data.list.length) + " 条， 共 " + response.data.count + " 条");
                    } else {
                        self.shouldShowPage(false);
                        self.total("无符合条件数据");
                    }
                    
                    laypage({
                        cont: 'page',
                        skin: '#00AA91',
                        pages: response.data.total,
                        curr: params.page || 1,
                        jump: function(obj, first) {
                            if(!first && response.data.count > 0) {
                                params.page = obj.curr;
                                self.search(params);
                            }
                        }
                    });
                }
            }
        });
    };
    self.query();
};

var Patient = function(data) {
    var self = this;

    self.name = ko.observable(data.name);
    self.idNumber = ko.observable(data.idNumber);
    self.phone = ko.observable(data.phone);
    self.sex = ko.observable(data.sex == '0' ? '女' : '男');
    self.apptCount = ko.observable(data.apptCount);
    self.regTime = ko.observable(data.regTime);
    
    self.doctorOpen = function() {
        $.ajax({
            url: '../../patient/save',
            type: 'post',
            data: {
                id: self.id,
                open: self.open() ? 1 : 0
            },
            success: function(response) {
                if (response.retCode == '00000') {
                    console.log('操作成功！');
                }
            }
        });
    }
};

