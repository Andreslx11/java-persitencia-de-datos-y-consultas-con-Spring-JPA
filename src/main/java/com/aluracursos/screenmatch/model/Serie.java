package com.aluracursos.screenmatch.model;


import java.util.OptionalDouble;

public class Serie {

    // se colocaron los mismo datos de record Datos serie
    // solo que algunos se les cambio el tipo de dato
    private String titulo;
    private Integer totalTemporadas;
    private Double evaluacion;
    private String poster;
    // se mapio como un emun de convertio las categorias a
    //constantes dentro de nuestra aplicación
    private Categoria genero;
    private String actores;
    private String sinopsis;


    public Serie(DatosSerie datosSerie) {
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
        return   "genero= " + genero +
                " titulo= '" + titulo + '\'' +
                ", totalTemporadas= " + totalTemporadas +
                ", evaluacion= " + evaluacion +
                ", poster='" + poster + '\'' +
                ", actores='" + actores + '\'' +
                ", sinopsis='" + sinopsis + '\'' ;
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
