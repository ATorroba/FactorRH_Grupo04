
package es.upm.dit.isst.tfgapi.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Table(name = "libros")

@Entity
public class libro {

    @Id
    @Column(name = "idLibro", length = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Integer idLibro;
    @Column(name = "nombre", length = 4)
    private String nombre;

    public libro() {

    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}