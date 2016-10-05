/**
 * 
 */
$(function(){
	ko.applyBindings(new ViewModel());
});

var ViewModel = function(){
	var self = this;
	
	self.list = ko.observableArray([]);
	
	self.departmentSelector = new DepartmentSelector();
	
	self.selected = null;
	
	var all = function() {
		$.ajax({
			url : 'department',
			type: 'GET',
			success : function(response) {
				if (response.retCode == '00000') {
					self.list.removeAll();
					$.each(response.data, function() {
						self.list.push(new Department(this));
					});
				}
			}	
		});
	};
	all();
	
	self.edit = function(d) {
		$('#edit-dialog').modal('show');
		self.departmentSelector.initial(false, d, d.children());
	};
	
	self.select = function() {
		self.selected = this;
	};
	
	self.remove = function() {
		$.ajax({
			url : 'department/' + self.selected.id(),
			type: 'DELETE',
			success : function(response) {
				if (response.retCode == '00000') {
					all();
				}
			}	
		});
	};
	
	self.save = function() {
		self.departmentSelector.save(function(){
			all();
		});
	};
};

var DepartmentSelector = function() {
	var self = this;
	
	self.data = ko.observableArray([]);
	self.primary = ko.observable(null);
	self.secondary = ko.observableArray([]);
	self.selected = ko.observableArray([]);
	
	self.message = ko.observable('');
	
	self.editable = ko.observable(false);;
	
	self.initial = function(editable, primary, secondary) {
		self.editable(editable);
		$.ajax({
			url : 'department/data',
			type: 'GET',
			success : function(response) {
				if (response.retCode == '00000') {
					self.data.removeAll();
					$.each(response.data, function() {
						self.data.push(new Department(this));
					});
					self.primary(null);
					self.secondary.removeAll();
					self.selected.removeAll();
					self.message('');
					
					if (primary) {
						var json = primary.toJson();
						delete json.children;
						var d = new Department(json);
						self.data.push(d);
						self.primary(d);
					}
					if (secondary) {
						$.each(secondary, function(){
							var d = new Department(this.toJson());
							self.data.push(d);
							self.secondary.push(d);
						});
					}
				}
			}	
		});
	};
	
	self.select = function() {
		if (self.isSelected(this)) {
			self.selected.remove(this);
		} else {
			self.selected.push(this);
		}
	};
	
	self.isSelected = function(d) {
		return contains(d, self.selected()) > -1;
	};
	
	self.add = function() {
		$.each(self.selected(), function(){
			if (contains(this, self.secondary()) == -1) {
				self.secondary.push(this);
			}
		});
		self.selected.removeAll();
	};
	self.remove = function() {
		$.each(self.selected(), function(){
			if (contains(this, self.secondary()) > -1) {
				self.secondary.remove(this);
			}
		});
		self.selected.removeAll();
	};
	
	self.validPrimary = function() {
		if (self.primary() == null) {
			self.message('请选择一级科室');
			return false;
		}
		self.message('');
		return true;
	};
	
	self.validSecondary = function() {
		if (self.secondary().length == 0) {
			self.message('请选择二级科室');
			return false;
		}
		self.message('');
		return true;
	};
	
	self.save = function(callback) {
		var primary = self.primary();
		if (!self.validPrimary() || !self.validSecondary()) {
			return;
		}
		$.each(self.secondary(), function(){
			primary.addChild(this);
		});
		
		$.ajax({
			url : 'department',
			type: 'POST',
			contentType:'application/json',
			data: JSON.stringify(primary.toJson()),
			success : function(response) {
				if (response.retCode == '00000') {
					$('#edit-dialog').modal('hide');
					callback.call();
				}
			}	
		});
	};
	
	self.unselectPrimary = ko.computed(function() {
		var array = new Array();
		$.each(self.data(), function(){
			if (contains(this, self.secondary()) == -1) {
				array.push(this);
			}
		});
		return array;
	});
	
	self.unselectSecondary = ko.computed(function() {
		self.validSecondary();
		var array = new Array();
		$.each(self.data(), function(){
			if (contains(this, self.secondary()) == -1) {
				array.push(this);
			}
		});
		var index = contains(self.primary(), array);
		if (index > -1) {
			array.splice(index, 1);
		}
		return array;
	});
};										

var Department = function(data) {
	var self = this;
	
	self.id = ko.observable(data.id);
	self.name = ko.observable(data.name);
	self.pid = ko.observable(data.pid);
	self.children = ko.observableArray([]);
	
	if (data.children) {
		$.each(data.children, function(){
			self.children.push(new Department(this));
		});
	}
	
	self.addChild = function(d) {
		if (contains(d, self.children()) == -1) {
			d.pid(self.id()); 
			self.children.push(d);
		}
	};
	
	self.toJson = function() {
		var json = {
				id 		: self.id(),
				name	: self.name(),
				pid		: self.pid(),
				children: []
		};
		$.each(self.children(), function(){
			json.children.push(this.toJson());
		});
		return json;
	};
};

var contains = function(entity, array) {
	var index = -1;
	if (entity != null) {
		$.each(array, function(i){
			if (array[i].id() == entity.id()) {
				index = i;
				return;
			}
		});
	}
	return index;
};