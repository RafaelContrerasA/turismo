import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String nombre;
    
    @Column(name = "desc", nullable = true)
    private String descrip;

    @Column(name = "value")
    private float precio;

    @Column(name = "inStock")
    private boolean disponibilidad;

    // Constructor vacío
    public Producto() {}

    // Constructor con parámetros
    public Producto(String nombre, String descrip, float precio, boolean disponibilidad) {
        this.nombre = nombre;
        this.descrip = descrip;
        this.precio = precio;
        this.disponibilidad = disponibilidad;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}
