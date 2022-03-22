package com.greglturnquist.hackingspringboot.reactive;

import org.springframework.data.annotation.Id;

import com.mongodb.client.model.geojson.Point;

import java.util.Date;

import java.util.Objects;
public class Item {

	private @Id String id;
	private String name;
	private double price;
	private String description;
	private Date releaseDate;
	private int availableUnits;
	private Point location;
	private boolean active;
	
	
	private Item() {}
	
	Item(String name, double price){
		this.name  = name;
		this.price = price;
	}
	
	Item(String name, String description, double price){
		this.name        = name;
		this.description = description;
		this.price       = price;
		
	}
	Item(String id, String name, String description, double price){
		this.id          = id;
		this.name        = name;
		this.description = description;
		this.price       = price;
		
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Item item = (Item) o;
		return Double.compare(item.price, price) == 0 && Objects.equals(id, item.id) && Objects.equals(name, item.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, price);
	}

	@Override
	public String toString() {
		return "Item{" + "id=" + id + ", name='" + name + '\'' + ", price=" + price + '}';
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String desciption) {
		this.description = description;
	}
}
