package com.aluracursos.screenmatch.model;

public enum Categoria {
     /*
     serian nuestra constantes, entre parentesis esta
     como estan en json de APi.
     AL principio da error por eso se creo el atributo  categoriaOmdb
     y el construtor
     */

    /* Modulo 4.3.5  para el genero que desea buscar el usuario debemos hacer ese mapeo es
    * conversión porque en el Enum tenemos ejemplo ACCION pero ahi no tiene accento tilde como puede
    * que el usuario escriba diferente entonces debemos de mapaer esa posbilidades, por eso a ACCION en el
    * campo se le añadio "Acción" */


    ACCION("Action", "Acción"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comedia"),
    DRAMA("Drama", "Drama"),
    CRIMEN("Crime", "Crimen");


    private String categoriaOmdb;
    /* Modulo 4.3.5*/
    private String categoriaEspanol;

    /*Modulo 4.3.6  se añade el  getCategoriaEspañol  */
    Categoria(String categoriaOmdb, String categoriaEspanol) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaEspanol= categoriaEspanol;
    }


    /**
     * Método estático que convierte una cadena de texto a un objeto de tipo Categoria.
     * Recorre todas las categorías definidas en el enum Categoria y compara el valor
     * de categoriaOmdb de cada una con el texto proporcionado.
     * Si encuentra una coincidencia, devuelve esa categoría.
     * Si no encuentra ninguna coincidencia, lanza una excepción IllegalArgumentException
     * indicando que no se encontró ninguna categoría.
     *
     * @param text la cadena de texto a convertir
     * @return el objeto Categoria correspondiente al texto proporcionado
     * @throws IllegalArgumentException si no se encuentra ninguna categoría que coincida con el texto
     */
    /*
    La línea for (Categoria categoria : Categoria.values()) es una forma de recorrer todas
    las instancias del enum Categoria. En Java, los enums son tipos de datos especiales que
     representan un conjunto de valores constantes. Cada valor del enum es una instancia de
    la clase Categoria. El método Categoria.values() devuelve un arreglo con todas las
   instancias del enum Categoria. Entonces, el for-each loop está iterando sobre cada
     una de esas instancias, asignándolas a la variable categoria en cada iteración.
     */


    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            // Compara el valor de categoriaOmdb de cada categoría con el texto proporcionado,
            // sin importar si está en mayúsculas o minúsculas
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        // Si no se encontró ninguna categoría que coincida, lanza una excepción
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

    /* Modulo 4.3.7 ==> principal 4.3.8 buscarSeriePorCategoria()/
    * Entonces, cuando estamos comparando aquí categoriaEspanol, si el usuario no pasa acción,
    * por ejemplo, con acento, él va a igualar. Ok, acción es exactamente igual a lo que yo
    * tengo aquí dentro de categoriaEspanol, así que lo encontré. Ah, escribió otra cosa diferente,
    *  acción, no solo sin el acento, pero escribió dos veces con O. Ahí me va a decir, ok, no tengo
    * ninguna categoría encontrada
    *  */
    public static Categoria fromEspanol(String text) {
        for (Categoria categoria : Categoria.values()) {
            // Compara el valor de categoriaEspañol de cada categoría con el texto proporcionado,
            // sin importar si está en mayúsculas o minúsculas
            if (categoria.categoriaEspanol.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        // Si no se encontró ninguna categoría que coincida, lanza una excepción
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

}
