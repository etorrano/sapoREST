package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="avs")
@NamedQuery(name="Av.findAll", query="SELECT a FROM Av a")
public class Av implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String descripcion;

	private String nombre;

	private Boolean privada=false;
	
	//bi-directional many-to-many association to Categoria
	@ManyToMany
	@JoinTable(
		name="avs_categorias"
		, joinColumns={
			@JoinColumn(name="id_av")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_categoria")
			}
		)
	private List<Categoria> categorias;

	
	//bi-directional many-to-one association to NotificacionesParametro
	@OneToMany(mappedBy="av")
	private List<NotificacionesParametro> notificacionesParametros;
	
	
	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario_duenio")
	private Usuario usuario;

	//bi-directional many-to-one association to CarritoProducto
	@OneToMany(mappedBy="av", cascade=CascadeType.REMOVE)
	private List<CarritoProducto> carritoProductos;

	//bi-directional many-to-one association to Stock
	@OneToMany(mappedBy="av", cascade=CascadeType.REMOVE)
	private List<Stock> stocks;

	//bi-directional many-to-many association to Usuario
	@ManyToMany(mappedBy="avs2", cascade=CascadeType.REMOVE)
	private List<Usuario> usuarios;

	public Av() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Categoria> getCategorias() {
		return this.categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<CarritoProducto> getCarritoProductos() {
		return this.carritoProductos;
	}

	public void setCarritoProductos(List<CarritoProducto> carritoProductos) {
		this.carritoProductos = carritoProductos;
	}

	public CarritoProducto addCarritoProducto(CarritoProducto carritoProducto) {
		getCarritoProductos().add(carritoProducto);
		carritoProducto.setAv(this);

		return carritoProducto;
	}

	public CarritoProducto removeCarritoProducto(CarritoProducto carritoProducto) {
		getCarritoProductos().remove(carritoProducto);
		carritoProducto.setAv(null);

		return carritoProducto;
	}

	public List<Stock> getStocks() {
		return this.stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public Stock addStock(Stock stock) {
		getStocks().add(stock);
		stock.setAv(this);

		return stock;
	}

	public Stock removeStock(Stock stock) {
		getStocks().remove(stock);
		stock.setAv(null);

		return stock;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Boolean getPrivada() {
		return privada;
	}

	public void setPrivada(Boolean privada) {
		this.privada = privada;
	}
	
	public void addColaborador(Usuario u){
		this.getUsuarios().add(u);
		u.getAvs2().add(this);
	}

	public List<NotificacionesParametro> getNotificacionesParametros() {
		return notificacionesParametros;
	}

	public void setNotificacionesParametros(List<NotificacionesParametro> notificacionesParametros) {
		this.notificacionesParametros = notificacionesParametros;
	}

	
}