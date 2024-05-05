package com.aluracursos.screenmatch.model;


import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.OptionalDouble;
/*
 IMPORTANTE  esta anotacion para indicarle JPA que esta class sera una entida de nuestra base datos una tabla
 y la va crear como nombre de la tabla sera serie
 Sin embargo como queremos almacenar varias series JPA nos da la opción para cambiar el nombre de nuesta tabla a
 nivel de la base de datos sin necesidad de cambiar el nombre a nuestra clase ( es algo de contexto no se va almacenar
 una sola serie sino varias por eso series no implica algo funcional en codigo)
 */
@Entity
@Table(name = "series")
public class Serie {

    // se colocaron los mismo datos de record Datos serie
    // solo que algunos se les cambio el tipo de dato

     /*
     Todas  nuestras series van ha necesitar un identificador unico Id
     la anotación @Id es para indicarle a JPA este id va ser el identificador de nuestra tabla
     tambien tenemos que indicarle a JPA cual va ser la forma de generar ese id, por que cuando se creo
     la tabla no se genero es campo Id  y como va ser llevado ese control, para eso  vamos a trabajar
     con una llave autoincremantal  JPA se va encar de cual va ser Id=1, Id=2, Id=3 asi sucesivamente
     para eso la anotación @GeneratedValue(strategy = GenerationType.IDENTITY) hay mas formas como Auto
     consultar docomentación

     IMPORTANTE generar los getter a setter para el nuevo atributo que creamos Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

     /*
     NO queremos que hayan series repetidas con el mismo nombre en nuestra app para eso usamos la anotación
    @Column(unique = true)
    */
    @Column(unique = true)
    private String titulo;
    private Integer totalTemporadas;
    private Double evaluacion;
    private String poster;

    /*
     se mapio como un emun de convertio las categorias a
     constantes dentro de nuestra aplicación.

     Con relación a la base datos debemos idicarle a JAP que esto es un ENUM y como debe tomar
     el orden para eso la anoatación @Enumerated() esta STRING por el texto y ORDINAL por números
     en este caso es mejor STRING ya que ORDINAL por números en un ambiente colaboarativo si alguien
     cambia algo puede genrar un Error
     */
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String actores;
    private String sinopsis;

    /* Toda serie contiene una lista de episodios hay que mapearla para la base de datos, tambien hay que
    hacer una logica por que las series no tienen los mismo episodios, mientras tanto vamos
    con la anotación     @Transient  indicarle a JAP que ignore esta lista
    */
    @Transient
     private List<Episodio> episodios;




    // IMPORTANTE JPK NO OBLIGA TENER UN CONSTRUCTOR PREDETERMINADO CUANDO CREAMOS UNA CLASE GAMOS AUTOMATICAMENT UNO
    // PERO CUANDO PERSONALIZAMOS UN CONSTRUCTOR PERDEMOS EL QUE GAMOS CUANDO CREMAOS LA CLASE Y NOS TOCA AÑADIRLO
    // DE FORMA MANUAL EN ESTE CASO POR REQUIMIENTO DE JPA FUNCIONE
    public Serie(){}


    public Serie( DatosSerie datosSerie) {
        this.titulo = datosSerie.titulo();
        this.totalTemporadas = datosSerie.totalTemporadas();
        this.evaluacion = OptionalDouble.of(Double.valueOf(datosSerie.evaluacion())).orElse(0);
        this.poster = datosSerie.poster();
        // se presenta le problema que el dato que traemos de la API es tipo String y nuestra aplicación
        // el dato es tipo Categoria para eso se creo un metodo static que realice la converción en la
        // clase Categoria => fromString(), trim para que no traiga ningun valor vacio
        this.genero = Categoria.fromString(datosSerie.genero().split(",")[0].trim());
        this.actores = datosSerie.actores();
        this.sinopsis = datosSerie.sinopsis();
        //this.sinopsis = ConsultaChatGPT.obtenerTraduccion( datosSerie.sinopsis());// esto consume de la cuenta open AI

    }


    @Override
    public String toString() {
        return "genero= " + genero +
                " titulo= '" + titulo + '\'' +
                ", totalTemporadas= " + totalTemporadas +
                ", evaluacion= " + evaluacion +
                ", poster='" + poster + '\'' +
                ", actores='" + actores + '\'' +
                ", sinopsis='" + sinopsis + '\'';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
}
