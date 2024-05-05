package com.aluracursos.screenmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/*
 modulo 3.1 se convierte la clase episodios para poder mapearla con relacion al clase entidad Serie para el mapeo
 de relaciones de tables en la base de datos
*/
@Entity
@Table(name = "episodios")
public class Episodio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double  evaluacion;
    private LocalDate fechaDeLanzamiento;

    /*
    * modulo 3.1 para poder identificar a que serie pertecene un episodio, se uso en  clase Serie para
    * llenar el campo de la anotación, es usado como intersecion para trasabilidad de que espisodios
    * pertnecen a una serie, de esta forma reconoce que estamos mapeando esa relacion a traves de aquel
    * campo ( se debe convertir la clase episodio en un entidad)  en  asi
    *
    * esto aclaro esta la clase Serie
    *   @OneToMany(mappedBy = "serie")
     private List<Episodio> episodios;
    *
    *
    *  y se le crean lo getter y setter
    * */
    // modulo 3.1 aqui la anotacion es diferente por que es de muchos a uno
    @ManyToOne
    private Serie serie;

    // modulo 3.1 crear el constructor predeterminado que nos pide JPA
    public Episodio(){}

    public Episodio(Integer numero, DatosEpisodio d) {
        this.temporada = numero;
        this.titulo = d.titulo();
        this.numeroEpisodio = d.numeroEpisodio();
        try{
            this.evaluacion = Double.valueOf(d.evaluacion());
        }catch (NumberFormatException e){
            this.evaluacion = 0.0;
        }
        try{
            this.fechaDeLanzamiento = LocalDate.parse(d.fechaDeLanzamiento());
        } catch (DateTimeParseException e){
            this.fechaDeLanzamiento = null;
        }

    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }


    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public LocalDate getFechaDeLanzamiento() {
        return fechaDeLanzamiento;
    }

    public void setFechaDeLanzamiento(LocalDate fechaDeLanzamiento) {
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }

    @Override
    public String toString() {
        return
                "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", evaluacion=" + evaluacion +
                ", fechaDeLanzamiento=" + fechaDeLanzamiento;
    }
}
