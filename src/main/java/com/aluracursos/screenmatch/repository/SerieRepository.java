package com.aluracursos.screenmatch.repository;
// MAPEO PARA LA BASE DE DATOS
/*
* IMPORTANTE  el nombre que se le da a la interface que se va mapear es nombre
* que se le debe dar a la interface seguido de nombre del package repository  en este caso SerieRepository
* para JAP entienda que se refiere la entidad Serie
* */


/* el mapeo de la entidad Serie
 a la base de datos, utilizando Spring Data JPA.La interfaz SerieRepository que se muestra es un ejemplo de
 este tipo de mapeo. Aquí está lo que se puede decir sobre esta interfaz:

Herencia de JpaRepository:
La interfaz SerieRepository extiende de JpaRepository<Serie, Long>, lo que significa que está heredando los
 métodos básicos de CRUD (Create, Read, Update, Delete) para la entidad Serie.

JpaRepository es una interfaz proporcionada por Spring Data JPA que abstrae la
interacción con la base de datos, permitiendo realizar operaciones CRUD de manera sencilla.

Mapeo de la entidad Serie:
Al extender de JpaRepository<Serie, Long>, se está indicando que la entidad Serie será mapeada a la base de datos.
El segundo parámetro genérico Long representa el tipo de dato del identificador (primary key) de la entidad Serie.


Implementación del mapeo:
Spring Data JPA se encarga de implementar automáticamente esta interfaz SerieRepository y proporcionar las
implementaciones de los métodos CRUD. Esto se logra gracias a la convención de nombres y la anotación @Repository
que se agrega a la interfaz.

En resumen, la interfaz SerieRepository representa el mapeo de la entidad Serie a la base de datos,
utilizando las funcionalidades proporcionadas por Spring Data JPA. Esto permite realizar operaciones CRUD
en la base de datos de manera sencilla y abstracta, sin necesidad de escribir código SQL manualmente.*/


/*IMPORTANTE  cuando se vaya instaciar se puede instaciar normal por que extiende de otra interface en
* este caso JpaRepository */

import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

// extiende de JpaRepository<LaEntidad, aqui_es_tipo_de_dato_del_Id>
public interface SerieRepository  extends JpaRepository<Serie, Long> {

    /*Modulo 4.1.4 es para el motodo buscarSeriePorTitulo() de class principa 4.1.3
    IMPORTANTE ESTO ES UNA CONSULTA DEREIVADA spring data JPA
    IMPORTANTE   este Titulo  de  findByTitluloContainsIgnorCase   es nombre
    del atributo que tiene en nuestra clase por el cual vamos hacer la consulta  y esta iniciando en
    mayuscula por esta siendo concatenado por la convenciones,
    y eso con concatenación  findByTitluloContainsIgnorCase  es metodos de spring Data JPA
    de consulta a bases de datos -- buscar documentación , ignoreCase es para que ignore entre mayusculas y
    minisculas, el campo es String que va ser lña serie buscada por nuestro usuario
     */

    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    /*Modulo 4.2.1  metodo para traer top 5 series de la base de datos
    IMPORTANTE ESTO ES UNA CONSULTA DEREIVADA spring data JPA
    este OrderByEvaluacion de  findTop5ByOrderByEvaluacionDesc  traeria las evaluacion top 5 series
    pero de menor a mayor para eso se usa Desc que es como reverse, y el Evaluacion recordar es nombre del atributo
    como lo tenemos en nuestra clase
    * */
    List<Serie> findTop5ByOrderByEvaluacionDesc();

    /*Modulo 4.3.1  buscando series por categoria
    *IMPORTANTE ESTO ES UNA CONSULTA DEREIVADA spring data JPA
    * categoria lo mapeamos en nuestra clase como genero,
    * recordar también Categoria es el Enum ,
    *
    * importante Como es Enum  hay que hacer la conversion  de datos, lo que el usario paso
    * a que se refiere en nuestro enum, para eso en principal se creo el metodo  buscarSeriePorCategoria(); */
    List<Serie> findByGenero(Categoria categoria);

    /* Modulo 5.1.1 metodo para encontrar las temporadas por la serie y cantidad de

    JPQL (Java Persistence Query Language) es un lenguaje de consultas orientado a objetos que se utiliza en el
    contexto de la especificación JPA (Java Persistence API).

    * esta es una consulta JPQL que permite buscar series que cumplan con dos criterios:
    Que el número total de temporadas sea menor o igual a un valor determinado.
    Que la evaluación (calificación) sea mayor o igual a un valor determinado.
    Veamos cómo se podría explicar esta consulta:

    findBy: Este es un prefijo utilizado en los métodos de los repositorios de Spring Data JPA para indicar que
    se trata de una consulta personalizada.
    *
    TotalTemporadas: Este es el nombre del atributo de la entidad que representa el número total de temporadas de una serie.
    *
    LessThanEqual: Este operador indica que se deben seleccionar las series cuyo número total de temporadas sea menor o igual
     a un valor específico.
     *
    And: Este operador permite combinar dos criterios de búsqueda, en este caso, el número de temporadas y la evaluación.
    Evaluacion: Este es el nombre del atributo de la entidad que representa la evaluación o calificación de una serie.
    GreaterThanEqual: Este operador indica que se deben seleccionar las series cuya evaluación sea mayor o igual a un valor específico.
    *
    En resumen, esta consulta JPQL permite buscar series que cumplan con ambos criterios: tener un número total de temporadas menor
    * o igual a un valor determinado y una evaluación mayor o igual a otro valor determinado.

    */
    /*Modulo 5.1 se comento para hacerlo mejor con una query native desde el codigo con   @Query*/

   // List<Serie> findByTotalTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(int totalTemporadas, Double evaluacion);

    /* Modulo 5.1
    * asi se puede hacer en postgresql una consulta de forma nativa query native
    * SELECT * FROM series WHERE series.total_temporadas <= 6 AND series.evaluacion >= 7.5
    *
    * */


    /* Modulo 5.1.2   se puede dar cualquier nombre al metodo por que no estamos trabajando  con palbras llaves especificas
     * de JPA
     *
     * IMPORTANTE y la anotación @Query es para poder ejucutar un query SQL native seria @Query(value= "el codigo como se haria la consulta
     * en la misma terminal postgresql lenguaje SQl" y en el otro campo nativeQuery=true)
     * */
    /*modulo 5.1  IMPORTANTE pero esta forma de consulta native puede causar problemas si
     cambiamos otra base de datos como SQL server ya que puede diferir el lenguaje SQL,
     para eso se USA  JPQL que es lenguaje de QUERY NATIVE DE JAVA  que trabaja a nivel de nuestras
    * entidades no  a la base directament*/

//    @Query(value = "SELECT * FROM series WHERE series.total_temporadas <= 6 AND series.evaluacion >= 7.5", nativeQuery = true )

    /* Modulo 5.1.3  /QUERY  JPQL trabaja con las entidades de la aplicación, que están
     mapeadas a las tablas de la base de datos a través de JPA.

     LA consulta despues de SELET se coloca una letra que va represendar la entidad con la que estamos trabajando
     despues de FROM ya no es el nombre de la tabla ahora se Coloca el Nombre de la Clase
     despues de WHERE  se coloca las condiciones el alias de de la entidad el nombre corto que asigmos en este caso s reemplaza donde el nombre
     de la tabla, y los nombres de los atributos como totalTemporadas debe esta igual como en la clase

     los dos puntos : se utilizan para hacer referencia a un parámetro de la consulta, no a un atributo de la entidad.
     en este caso esto  :totalTemporadas indica  del campo int totalTemporadas el valor que se le va pasar igual para
     evaluacion

     */
    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.evaluacion >= :evaluacion")
    List<Serie> seriesPorTemparadaYEvaluacion(int totalTemporadas, Double evaluacion);

    /* Modulo 5.2.1 ==> principal 5.2.3/
     para buscar episodiod por parte de titulo consulta JPQL
    * como vamos hacer una relación entre una tabla y otra debemos usar JOIN para indicar que va ser una interceción de series
    * y episodios y esa intercion es s.episodios
    * ILIKE es ignore las diferencias entre mayúsculas y minúsculas y no haya problemas
     *
     *El % en la consulta JPQL que has mostrado es un comodín utilizado en el contexto de una consulta
     *  de texto. En este caso, %:nombreEpisodio% se utiliza para buscar todos los episodios cuyo título
     *  contiene la subcadena especificada por nombreEpisodio.

    % al principio indica que la subcadena puede aparecer al principio del título.

    % al final indica que la subcadena puede aparecer al final del título.
    *
    Por lo tanto, la consulta busca episodios cuyos títulos contienen la subcadena
    * dada en cualquier parte de su texto, sin importar lo que haya antes o después
    * de esa subcadena en el título. Esto proporciona una flexibilidad en la búsqueda
    *  de texto en JPQL.

     * */
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%")
    List<Episodio> episodiosPorNombre(String nombreEpisodio);

    /*Modulo 5.3.1 ==> class princiapal 5.3.1
      * para busca top 5 episosdios de una seie*/
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5 ")
    List<Episodio> top5Episodios(Serie serie);


}
