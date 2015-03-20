package com.example.finalproject;

public class Clients {
	
	private String name = null;
	private String address = null;
	private String phone = null;
	
	//-------------------------------
	
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		//-------------------------------
		
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		
		//-------------------------------
		
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}

		@Override
		public String toString() {
			return "Client [name = " + name + ", address = " + address + ", phone = " + phone + "]";
		}
}
