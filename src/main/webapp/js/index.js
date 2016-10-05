/**
 * 
 */
$(function(){
	ko.applyBindings(new ViewModel());
});

var ViewModel = function(){
	var self = this;
	
	self.departments = ko.observableArray([]);
	self.doctors = ko.observableArray([]);
	self.primary = ko.observable();
	self.secondary = ko.observable();
	
	(function(){
		// departments
		$.ajax({
			url : 'department',
			type: 'GET',
			success : function(response) {
				if (response.retCode == '00000') {
					$.each(response.data, function() {
						self.departments.push(new Department(this));
					});
				}
				self.setPrimary(self.departments()[0]);
			}	
		});
	})();
	
	self.setPrimary = function(d) {
		if (self.primary() != null) {
			self.primary().actived(false);
		}
		self.primary(d);
		self.primary().actived(true);
		
		self.setSecondary(self.primary().children()[0]);
	};
	
	self.setSecondary = function(d) {
		if (self.secondary() != null) {
			self.secondary().actived(false);
		}
		self.secondary(d);
		self.secondary().actived(true);
		
		getDoctors(1);
	};
	
	var getDoctors = function(page) {
		$.ajax({
			url : 'doctor/list',
			type: 'POST',
			data: {deptId: self.secondary().id(), page: page, size:10},
			success : function(response) {
				if (response.retCode == '00000') {
					$.each(response.data.list, function(){
						self.doctors.push(this);
					});
				}
				console.log(self.doctors());
			}	
		});
	};
};

var Department = function(data) {
	var self = this;
	
	self.id = ko.observable(data.id);
	self.name = ko.observable(data.name);
	self.pid = ko.observable(data.pid);
	self.children = ko.observableArray([]);
	self.actived = ko.observable(false);
	if (data.children) {
		$.each(data.children, function(){
			self.children.push(new Department(this));
		});
	}
};
