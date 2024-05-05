package com.aluracursos.screenmatch.model;

public enum Categoria {

    // serian nuestra constantes, entre parentesis esta
    // como estan en json de APi.
    //AL principio da error por eso se creo el atributo  categoriaOmdb
    // y el construtor
    ACCION("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIMEN("Crime");


    private String categoriaOmdb;

    Categoria(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
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

}
