package com.pmarlen.proto.model;

public class Car {
	private String id;
	private String brand;
	private Integer year;	
	private String color;
	private Integer price;	
	private boolean soldState;

	public Car() {
	}

	public Car(String id, String brand, Integer year, String color, Integer price, boolean soldState) {
		this.id = id;
		this.brand = brand;
		this.year = year;
		this.color = color;
		this.price = price;
		this.soldState = soldState;
	}
	
	
	//new Car(getRandomId(), getRandomBrand(), getRandomYear(), getRandomColor(), getRandomPrice(), getRandomSoldState())
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the price
	 */
	public Integer getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Integer price) {
		this.price = price;
	}

	/**
	 * @return the soldState
	 */
	public boolean isSoldState() {
		return soldState;
	}

	/**
	 * @param soldState the soldState to set
	 */
	public void setSoldState(boolean soldState) {
		this.soldState = soldState;
	}
}
