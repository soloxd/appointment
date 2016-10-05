/**
 * 
 */
$(function(){
	ko.applyBindings(new ViewModel());
});

var ViewModel = function(){
	var self = this;
	
	self.top = new Department({children:[]});
	
	(function(){
		// departments
		$.ajax({
			url : 'department',
			type: 'GET',
			success : function(response) {
				if (response.retCode == '00000') {
					self.top.children.removeAll();
					$.each(response.data, function() {
						self.top.children.push(new Department(this, self.top));
					});
					
					if (response.data.length > 0) {
						self.top.children()[0].active(true);
						if (self.top.children()[0].children().length > 0) {
							self.top.children()[0].children()[0].active(true);
						}
					}
				}
			}	
		});
		
		//doctors
	})();
	
	self.secondary = ko.computed(function(){
		var array = null;
		$.each(self.top.children(), function(){
			if (this.actived() == true) {
				array = this;
			}
		});
		return array == null ? [] : array.children();
	});
};

var Department = function(data, parent) {
	var self = this;
	
	self.id = ko.observable(data.id);
	self.name = ko.observable(data.name);
	self.pid = ko.observable(data.pid);
	self.children = ko.observableArray([]);
	self.parent = parent;
	self.actived = ko.observable(false);
	
	if (data.children) {
		$.each(data.children, function(){
			self.children.push(new Department(this, self));
		});
	}
	
	self.active = function() {
		if (self.parent != null) {
			$.each(self.parent.children(), function(){
				this.actived(false);
			});
		}
		self.actived(true);
		if (self.children().length > 0) {
			self.children()[0].active();
		}
	};
};
