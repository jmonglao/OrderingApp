package com.example.finalproject;

public class Order {
	
	private String itemOrder = null;
	private String quantity = null;
	private String totalCost = null;
	private String comment = null;
	//------------------------------------
	
	public String getItemOrder() {
		return itemOrder;
	}
	public void setItemOrder(String itemOrder) {
		this.itemOrder = itemOrder;
	}
	
	//-------------------------------------
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	//-------------------------------------
	
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
		
	//-------------------------------------

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	//-------------------------------------

	
}
