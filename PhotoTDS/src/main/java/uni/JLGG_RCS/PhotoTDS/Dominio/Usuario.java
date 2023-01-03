package uni.JLGG_RCS.PhotoTDS.Dominio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Usuario implements Persistente {
	
	private static final int MAX_CARACTERES_PRESENTACION = 200;
	
	private Integer id;
	
	private final String nombreCompleto;
	private final String nombreUsuario;
	private final Date fechaNacimiento;
	private final String email;
	
	private String password;
	private String presentacion;
	private Foto fotoPerfil;
	private boolean premium;
	
	private List<Usuario> seguidores;
	private List<Foto> fotos;
	private List<Album> albumes;
	private List<Notificacion> notificaciones;
	
	private Descuento descuento;
	
	
	/**
	 * Constructor principal de usuarios. Todos los atributos que recibe
	 * deben ser distintos de null, y los atributos de tipo String deben
	 * ser distintos de ""
	 * 
	 * @param nombreCompleto el nombre completo
	 * @param nombreUsuario el nombre del usuario
	 * @param fechaNacimiento la fecha de nacimiento.
	 * @param email el email
	 * @param password el password a usar
	 */
	public Usuario(String nombreCompleto, String nombreUsuario, Date fechaNacimiento, String email, String password) {
		
		if ((nombreCompleto == null) || (nombreCompleto.equals("")))
			throw new IllegalArgumentException();		
		this.nombreCompleto = nombreCompleto;
		
		if ((nombreUsuario == null) || (nombreUsuario.equals("")))
			throw new IllegalArgumentException();		
		this.nombreUsuario = nombreUsuario;

		if (fechaNacimiento == null)
			throw new IllegalArgumentException();
		this.fechaNacimiento = fechaNacimiento;
		
		if ((email == null) || (email.equals("")))
			throw new IllegalArgumentException();		
		this.email = email;
		
		if ((password == null) || (password.equals("")))
			throw new IllegalArgumentException();		
		this.password = password;
		
		// La presentacion es opcional
		this.presentacion = "";
		
		// Podemos no tener foto de perfil
		this.fotoPerfil = null;
		
		// Por defecto los usuarios no seran premium
		this.premium = false;
				
		// Por defecto no tendran seguidores, fotos, albumes ni notificaciones
		this.seguidores = new ArrayList<Usuario>();
		this.fotos = new ArrayList<Foto>();
		this.albumes = new ArrayList<Album>();
		this.notificaciones = new ArrayList<Notificacion>();
		
		// Se define directamente el descuento
		descuento = new DescuentoEdad(this);
	}
	
	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Getter del nombre completo
	 * 
	 * @return el nombre completo del usuario
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * Getter del nombre de usuario
	 * 
	 * @return el nombre de usuario del usuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * Getter de la fecha de nacimiento
	 * 
	 * @return la fecha de nacimiento del usuario
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * Getter del email
	 * 
	 * @return el email del usuario
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Getter del password
	 * 
	 * @return el password del usuario
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter del password. Requiere que sea no nulo y
	 * distinto de ""
	 * 
	 * @param password el nuevo password del usuario
	 */
	public void setPassword(String password) {
		if ((password == null) || (password.equals("")))
			throw new IllegalArgumentException();		
		this.password = password;
	}
	
	/**
	 * Getter de la presentacion
	 * 
	 * @return la presentacion del usuario
	 */
	public String getPresentacion() {
		return presentacion;
	}

	/**
	 * Setter de la presentacion. Requiere que no sea nula.
	 * Si la presentacion tiene demasiados caracteres, se trunca
	 * su longitud al maximo de caracteres posibles.
	 * 
	 * @param presentacion la nueva presentacion
	 */
	public void setPresentacion(String presentacion) {
		if (presentacion == null)
			throw new IllegalArgumentException();
		
		if (presentacion.length() > MAX_CARACTERES_PRESENTACION)
			this.presentacion = presentacion.substring(0, MAX_CARACTERES_PRESENTACION);
		else
			this.presentacion = presentacion;
	}

	/**
	 * Getter de la foto de perfil
	 * 
	 * @return la foto de perfil del usuario
	 */
	public Foto getFotoPerfil() {
		return fotoPerfil;
	}

	/**
	 * Setter de la foto de perfil
	 * 
	 * @param foto la nueva foto de perfil del usuario
	 */
	public void setFotoPerfil(Foto foto) {
		this.fotoPerfil = foto;
	}
	
	/**
	 * Getter del estado premium o no del usuario
	 * 
	 * @return true si el usuario es premium, false si no
	 */
	public boolean isPremium() {
		return premium;
	}
	
	/**
	 * Setter del estado premium del usuario. 
	 * 
	 * @param premium true si el usuario pasa a ser premium, false si deja de serlo
	 */
	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	/**
	 * Getter de la lista de seguidores. Devuelve una versión no modificable.
	 * 
	 * @return una lista de seguidores
	 */
	public List<Usuario> getSeguidores() {
		return Collections.unmodifiableList(seguidores);
	}
	
	/**
	 * Permite incluir un nuevo seguidor a la lista de seguidores
	 * El nuevo seguidor no puede ser el propio usuario
	 * 
	 * @param seguidor el nuevo seguidor
	 */
	public void addSeguidor(Usuario seguidor) {
		if (seguidor == this)
			throw new IllegalArgumentException();
		
		seguidores.add(seguidor);
	}

	/**
	 * Permite incluir una lista de nuevos seguidores. En esta lista
	 * no puede estar el propio usuario
	 * 
	 * @param seguidores una lista de nuevos seguidores
	 */
	public void addSeguidores(Collection<Usuario> seguidores) {
		boolean ciclo = seguidores.stream()
				.anyMatch(u -> u == this);
		
		if (ciclo)
			throw new IllegalArgumentException();
		
		this.seguidores.addAll(seguidores);
	}

	/**
	 * Getter de la lista de fotos. Devuelve una versión no modificable.
	 * 
	 * @return una lista de fotos
	 */
	public List<Foto> getFotos() {
		return Collections.unmodifiableList(fotos);
	}
	
	/**
	 * Permite incluir una nueva foto a la lista de seguidores
	 * 
	 * @param foto la nueva foto
	 */
	public void addFoto(Foto foto) {
		fotos.add(foto);
	}

	/**
	 * Permite incluir una lista de nuevas fotos
	 * 
	 * @param fotos una lista de nuevas fotos
	 */
	public void addFotos(Collection<Foto> fotos) {
		this.fotos.addAll(fotos);
	}
	
	/**
	 * Getter de la lista de albumes. Devuelve una versión no modificable.
	 * 
	 * @return una lista de albumes
	 */
	public List<Album> getAlbumes() {
		return Collections.unmodifiableList(albumes);
	}
	
	/**
	 * Permite incluir un nuevo album a la lista de albumes
	 * 
	 * @param album el nuevo album
	 */
	public void addAlbum(Album album) {
		albumes.add(album);
	}

	/**
	 * Permite incluir una lista de nuevos albumes
	 * 
	 * @param albumes la lista de nuevos albumes
	 */
	public void addAlbumes(Collection<Album> albumes) {
		this.albumes.addAll(albumes);
	}
	
	/**
	 * Getter de la lista de notificaciones. Devuelve una versión no modificable.
	 * 
	 * @return una lista de notificaciones
	 */
	public List<Notificacion> getNotificaciones() {
		return Collections.unmodifiableList(notificaciones);
	}
	
	/**
	 * Permite incluir una nueva notificacion
	 * 
	 * @param notificacion la nueva notificacion
	 */
	public void addNotificacion(Notificacion notificacion) {
		notificaciones.add(notificacion);
	}

	/**
	 * Permite incluir una lista de nuevas notificaciones
	 * 
	 * @param notifiaciones la lista de nuevas notificaciones
	 */
	public void addNotificaciones(Collection<Notificacion> notifiaciones) {
		this.notificaciones.addAll(notifiaciones);
	}
	
	/**
	 * Devuelve el número de meGusta que ha recibido un usuario en total,
	 * computando la suma de los meGusta de todas sus fotos y albumes.
	 * 
	 * @return el numero total de meGusta
	 */
	public int getMeGustaTotales() {
		int total = 0;
		fotos.stream().map(f -> f.getMeGusta()).reduce(total, (i1,i2) -> i1+i2);
		albumes.stream().map(f -> f.getMeGusta()).reduce(total, (i1,i2) -> i1+i2);
		return total;
	};
	
	/**
	 * Calcula el precio que debe pagar un usuario aplicando un descuento
	 * a una cantidad bruta total
	 * 
	 * @param total la cantidad total que debe pagar sin descuentos
	 * @return el precio final, calculado segun los descuentos que aplican al usuario
	 */
	public double calcularPrecioPremium(double total) {
		return total*(1-descuento.getDescuento());
	}
	
	/**
	 * Publica una foto, avisando a todos sus seguidres mediante notificaciones
	 * 
	 * @param foto la foto a publicar
	 */
	public void publicarFoto(Foto foto) {
		addFoto(foto);
		Notificacion nueva = new Notificacion(foto);
		for (Usuario s : seguidores)
			s.addNotificacion(nueva);
	}
	
	/**
	 * Publica una foto, avisando a todos sus seguidores mediante notificaciones
	 * 
	 * @param foto la foto a publicar
	 */
	public void publicarAlbum(Album album) {
		addAlbum(album);
		Notificacion nueva = new Notificacion(album);
		for (Usuario s : seguidores)
			s.addNotificacion(nueva);
	}

	public List<Publicacion> getPublicacionesRelevantes() {
		List<Publicacion> lista = new LinkedList<>();
		lista.addAll(fotos);
		lista.addAll(albumes);
		
		seguidores.stream()
			.forEach(u -> {
				lista.addAll(u.getFotos());
				lista.addAll(u.getAlbumes());
			});
		
		return lista;
	}
	
	/**
	 * Usa el nombre de usuario para establecer igualdad
	 */
	@Override
	public int hashCode() {
		return Objects.hash(nombreUsuario);
	}

	/**
	 * Devuelve true si los dos usuarios tienen el mismo nombre de usuario
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(nombreUsuario, other.nombreUsuario);
	}
	
	
}
