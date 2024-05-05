package com.aluracursos.screenmatch.model;


import jakarta.persistence.*;


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
    // @Transient  modulo 3.1 se elimina esta anotación porque en el modulo 3 se mapean los espisodios

    /*
     modulo 3.1  crear la relacion entre espisodio para eso se usa la anotacion @OneToMany, toca identificar cual
     es el campo que va relacionar espisodios con serie, en el campo serie es el atributo serie que colocamos en
     en
    */
    /*
    * modulo 3.3  se le agrega un campo  @OneToMany para indicarle que si hubo un cambio en serie debe reflejar en
    * episodios y vicebersa, esta el problema que que los episodios no se guardan en la base de datos, por que solo
    * se tenia que si habia un cambio en Serie se reflejara en la base datos, exiten varios tipos de CascadeType */

     /*
      * Modulo 3.4   cuando se cargar archivo se debe especificar como de forma anciosa que trae todo o  peresozo que
      * que viene cuando lo llamamos especificamente
      *  esto es para SOLUCCIONAR EL ERRO failed to lazily initialize a collection of role:
      * com.aluracursos.screenmatch.model.Serie.episodios: could not initialize proxy - no Session
      *
      *
         La explicación sería la siguiente:

        En el contexto de la persistencia de datos con JPA, "carga diferida" y "carga anticipada" se refieren a la forma en
         que se cargan los datos de las relaciones entre entidades desde la base de datos.

        Carga diferida: Cuando se accede a una relación (como la lista de episodios de una serie), los datos de esa relación
        * se cargan desde la base de datos solo cuando se necesitan, es decir, cuando se accede a ellos por primera vez.

        Carga anticipada: Cuando se carga una entidad (como una serie), también se cargan automáticamente los datos de las
        * relaciones asociadas (como la lista de episodios), sin necesidad de acceder a ellos explícitamente.

        En el caso de la clase Serie, el objetivo es mostrar los episodios asociados a cada serie. Por lo tanto,
        *  la carga anticipada (eager loading) sería la opción más adecuada, ya que permite traer los datos de los
        * episodios junto con los datos de la serie, evitando así problemas de inicialización perezosa (lazy initialization)
        * y mejorando la experiencia del usuario al visualizar los episodios.
        *
        * para eso se añadio el campo  fetch = FetchType.EAGER
        *  fetch = FetchType.EAGER  es ancioso
        * etch = FetchType.LAZY   es peresozo
      *  */
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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


    // Modulo 3.4 se agrega    ", episodios='" + episodios + '\'' para cuando se consulte mostrarSeries() muestre
    // la información de los episodios
    @Override
    public String toString() {
        return "genero= " + genero +
                " titulo= '" + titulo + '\'' +
                ", totalTemporadas= " + totalTemporadas +
                ", evaluacion= " + evaluacion +
                ", poster='" + poster + '\'' +
                ", actores='" + actores + '\'' +
                ", sinopsis='" + sinopsis + '\'' +
                ", episodios='" + episodios + '\'';
    }

    /* Este es un método de acceso (getter) que se utiliza para obtener la lista de episodios asociada a una serie.
       Aquí está lo que significa cada parte:
       public: Indica que este método es de acceso público, es decir, puede ser llamado desde cualquier parte del código.
       List<Episodio>: Esto define el tipo de retorno del método, en este caso, una lista de objetos de tipo Episodio.
       getEpisodios(): Este es el nombre del método, que sigue la convención de nombrar los getters con el prefijo "get"
       seguido del nombre del atributo que se quiere obtener.
       return episodios;: Esta es la implementación del método, que simplemente devuelve la lista de episodios almacenada
       en el atributo episodios de la clase.
       En resumen, este método permite a otras partes del código acceder a la lista de episodios asociada a una serie.
      Es una forma de encapsular y exponer de manera controlada los datos internos de la clase Serie.*/
    public List<Episodio> getEpisodios() {
        return episodios;
    }

    /**/
    public void setEpisodios(List<Episodio> episodios) {
        /* Modulo 3.4  para clave foranea es setSerie por que la relacion debe ser en ambas partes, dentro de mi clase episodio
         tengo un atributo (atributo Serie serie)que va indicar a que serie se refiere
         Cuando estamos utilizando este código aquí, le estamos diciendo que para cada uno de nuestros episodios,
         él va a aceptar en ese valor de serie, el valor de ella misma, o sea, de This, de esta serie o de la serie que
         estemos buscando en ese momento
         Supongamos, estoy buscando Game of Thrones (Juego de Tronos), allí, cuando vaya a ser ese setSerie, para cada
         uno de los episodios, esa relación, él va a decir, ah, estoy trabajando con el ID de Game of Thrones, y así
         sucesivamente para cada una de las búsquedas que vayamos a realizar..*/
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
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
