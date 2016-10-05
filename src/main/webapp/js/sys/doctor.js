$(function() {
    ko.applyBindings(new ViewModel());
});


var ViewModel = function() {
    var self = this;
    
    self.deptList1 = ko.observableArray([]);
    self.deptList2 = ko.observableArray([]);
    
    self.dept1 = ko.observable("");
    self.dept2 = ko.observable("");
    
    self.title = ko.observableArray([]);
    self.name = ko.observable("");
    self.open = ko.observable("");
    
    self.loadDept = function(list, pid) {
        if (!pid && pid != 0) {
            return;
        }
        
        $.ajax({
            url: '../../select/deptListByPid',
            type: 'post',
            data: {pid: pid},
            success: function(response) {
                if (response.retCode == '00000') {
                    list.removeAll();
                    list(response.data);
                }
            }
        });
    };
    self.loadDept(self.deptList1, 0);
    
    self.loadDeptList2 = function() {
        self.loadDept(self.deptList2, self.dept1());
    }
    
    self.list = ko.observableArray([]);
    self.total = ko.observable("");
    self.shouldShowPage = ko.observable(true);
    
    self.reset = function() {
        self.dept1("");
        self.dept2("");
        self.title("");
        self.name("");
        self.open("");
        self.deptList2.removeAll();
    };
    
    var params = {};
    
    self.query = function() {
        params.deptId = self.dept2() ? self.dept2() : self.dept1();
        params.title = self.title();
        params.name = self.name();
        params.open = self.open();
        params.page = 1;
        params.size = 10;
        
        self.search(params);
    };
    
    
    self.search = function(params) {
        $.ajax({
            url: '../../doctor/list',
            type: 'post',
            data: params,
            success: function(response) {
                if (response.retCode == '00000') {
                    self.list.removeAll();
                    
                    $.each(response.data.list, function(){
                        self.list.push(new Doctor(this));
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

var Doctor = function(data) {
    var self = this;

    self.id = ko.observable(data.id);
    self.name = ko.observable(data.name);
    self.title = ko.observable(data.title);
    self.deptName = ko.observable(data.deptName);
    self.symbol = ko.observable(data.symbol);
    self.jobNumber = ko.observable(data.jobNumber);
    self.open = ko.observable(data.open);
    
    self.doctorOpen = function() {
        $.ajax({
            url: '../../doctor/save',
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

