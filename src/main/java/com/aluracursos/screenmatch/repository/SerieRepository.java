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

import com.aluracursos.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

// extiende de JpaRepository<LaEntidad, aqui_es_tipo_de_dato_del_Id>
public interface SerieRepository  extends JpaRepository<Serie, Long> {
}
