$(function() {
    ko.applyBindings(new ViewModel());
});


var ViewModel = function() {
    var self = this;
    
    self.deptList1 = ko.observableArray([]);
    self.deptList2 = ko.observableArray([]);
    
    self.dept1 = ko.observable("");
    self.dept2 = ko.observable("");
    self.doctorName = ko.observable("");
    self.regWay = ko.observable("");
    self.status = ko.observable("");
    self.regPerson = ko.observable("");
    self.patientName = ko.observable("");
    
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
        self.doctorName("");
        self.regWay("");
        self.status("");
        self.regPerson("");
        self.patientName("");
        self.deptList2.removeAll();
        $("#apptDateStart").val("");
        $("#apptDateEnd").val("");
    };
    
    var params = {};
    
    self.query = function() {
        params.deptId = self.dept2() ? self.dept2() : self.dept1() ? self.dept1 : null;
        params.doctorName = self.doctorName();
        params.regWay = self.regWay();
        params.status = self.status();
        params.apptDateStart = $("#apptDateStart").val();
        params.apptDateEnd = $("#apptDateEnd").val();
        params.regPerson = self.regPerson();
        params.patientName = self.patientName();
        
        params.page = 1;
        params.size = 10;
        
        self.search(params);
    };
    
    
    self.search = function(params) {
        $.ajax({
            url: '../../appt/list',
            type: 'post',
            data: params,
            success: function(response) {
                if (response.retCode == '00000') {
                    self.list.removeAll();
                    
                    $.each(response.data.list, function(){
                        self.list.push(new Appt(this));
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

var Appt = function(data) {
    var self = this;

    self.id = ko.observable(data.id);
    self.patientId = ko.observable(data.patientId);
    self.patientName = ko.observable(data.patientName + '　' + (data.patientSex　? '男' : '女'));
    self.patientPhone = ko.observable(data.patientPhone);
    
    self.doctorName = ko.observable(data.doctorName + ' （' + data.doctorSymbol + '）');
    self.doctorTitle = ko.observable(data.doctorTitle);
    self.deptName = ko.observable(data.deptName);
    
    self.apptCode = ko.observable('预约码：' + data.apptCode);
    self.apptDate = ko.observable(data.apptDate);
    self.apptType = ko.observable(data.apptType);
    
    self.regPerson = ko.observable(data.regPerson);
    self.regTime = ko.observable(data.regTime);
    self.regWay = ko.observable(data.regWay == 0 ? '电话预约' : data.regWay == 1 ? '网上预约' : data.regWay == 2 ? '现场预约' : data.regWay);
    
    self.status = ko.observable(data.status == 0 ? '未登记' : data.status == 1 ? '已登记' : data.status == 2 ? '已取消' : data.status);
    self.sms = ko.observable(data.sms == 0 ? '未发送' : '已发送');
};

