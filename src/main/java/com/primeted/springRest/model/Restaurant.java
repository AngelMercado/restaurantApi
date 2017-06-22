package com.primeted.springRest.model;
import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Entity
@Table(name="restaurantes")
public class Restaurant {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
    @NotEmpty
    @Column(name="nombre", nullable=false)
	private String nombre;
    @NotEmpty
    @Column(name="direccion", nullable=false)
    private String direccion;
    @NotEmpty
    @Column(name="descripcion", nullable=false)
	private String descripcion;
    @NotEmpty
    @Column(name="imagen", nullable=false)
    private String imagen;
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setname(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	@NotEmpty
    @Column(name="precio", nullable=false)
    private String precio;
}
