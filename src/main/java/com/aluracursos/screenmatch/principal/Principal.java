package com.aluracursos.screenmatch.principal;


import com.aluracursos.screenmatch.model.*;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;


import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private String API_KEY;
    /*
     se crea para poder hacer uso instarciar por decirlo
     al atributo private SerieRepository repository
     que se creo para que spring boot haga la inyección dependencias
     para interface SerieRepository
     */
    private SerieRepository repositorio;
    // modulo 3.2 se crea como varible global para poder usar también  buscarEpisodioPorSerie(), originalmente era de buscarSerieWeb()
    List<Serie> series;
    Optional<Serie> serieBuscada;



    // para traer la api key de la variable de entorno
    public String API_KEY() {
        this.API_KEY = System.getenv("API_KEY_OMDB");
        return API_KEY;
    }

    // constructor para lo de inyección de dependencias de SerieRepository
    public Principal(SerieRepository repository) {
        this.repositorio = repository; // repository va ser igual ese repository que estamos pasando en la clase ScreenmatchApplication
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            /* Modulo 4.1.1 agragar la opción 4*/
            /* Modulo 4.2.2 agragar la opción 5*/
            /* Modulo 4.3.2 agragar la opción 6*/
            /* Modulo 5.1 agragar la opción 7*/
            /* Modulo 5.2.3  agragar la opción 8*/
            /* Modulo 5.3.2  agragar la opción 8*/
            var menu = """
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - buscar series por titulo   
                    5 - top 5 mejores series  
                    6 - buscar serie por categoria     
                    7 - filtrar series por temporadas y evaluación
                    8 - Buscar episodios por titulo
                    9 - Top 5 episodios por Serie      
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                case 4:
                    buscarSeriesPorTitulo();
                    break;
                case 5:
                     buscarTop5Series();
                     break;
                case 6:
                    buscarSeriePorCategoria();
                    break;
                case 7:
                    filtrarSeriesPorTemporadaYEvaluacion();
                case 8:
                    buscarEpisodiosPorTitulo();
                case 9:
                    buscarTop5Episodios();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }



    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY());
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }

    private void buscarEpisodioPorSerie() {
        /*modulo 3.2 se comenta esta lines por que ya no queremos traer lo episodios de la api
         ahora queremos traerlos de la base de datos*/
//        DatosSerie datosSerie = getDatosSerie();

        /* modulo 3.2 mostrar series buscadas las que estan almacendas en la base de datos */
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la serie de la cual quieres ver los episodios");
        var nombreSerie = teclado.nextLine();


        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();
        /* modulo 3.2  hacer el tratamiento de datos del optional*/
        if(serie.isPresent()){
            var serieEncontrada = serie.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();
            /*modulo 3.2 se corto y se ingreso al for, se cambio datosSerie.totalTemporadas()   y  datosSerie.titulo()*/
            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY());
                DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporada);
            }

            temporadas.forEach(System.out::println);
            /* modulo 3.2 tratamiento datos tenemos una lista de temporadas y lo que queremos es una lista de
               episodios, transformar ese lista  episodios a un tipo de dato episodios */
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                            .collect(Collectors.toList());
            /* mdulo 3.2 ya convertido esa lista de episodios a un tipo de dato episodio
              lo que vamos a querer  guardar esos datos, necesitamos identificar el dato
              y que lo guarde en me setEpisodios, episodios es la lista que creamos
              En el contexto del curso de Java y Spring Data JPA, cuando se tiene una relación bidireccional
              entre las clases "Serie" y "Episodio", la línea de código serieEncontrada.setEpisodios(episodios);
              cumple con las siguientes funciones:  serieEncontrada es una instancia de la clase Serie que
              representa una serie específica que se ha encontrado o recuperado de la base de datos.
              setEpisodios(episodios) es un método de la clase Serie que permite establecer la lista de
              episodios asociados a esa serie.  episodios es una lista de objetos de la clase Episodio que
              representan los episodios pertenecientes a la serie serieEncontrada.
              Al ejecutar esta lí*/
            serieEncontrada.setEpisodios(episodios);
            //modulo 3.2 guardar la serie que encontramos en nuestro repositorio (nuestra base de datos)
            repositorio.save(serieEncontrada);
        }


        }



    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        /* se comento por que ya no queremos una lista queremos guardarlos en la base de datos */
        //datosSeries.add(datos);

        /* para guardar los datos en la base de datos se instacia el objeto */
        Serie serie = new Serie(datos);  /* se pasan los datos de la busqueda de la API */
        repositorio.save(serie); /* Guardar ese serie que acavamos de guardar  en el formato de nuestra de serie */
        /* estos metodod como findAll() se encuentran en la docmunetación de spring Data jpa */
        System.out.println(datos);
    }

    private void mostrarSeriesBuscadas() {
        /* ya no queremos solo imprimir este datos series, sino realizar esa transformarción
         al tipo de dato Serie que creamos*/
        //datosSeries.forEach(System.out::println);

        /////// IMPORTANTE  esto se comento por que ya no queremos mostrar las cosas desde api
        // ahora queremos de nuestra base datos
        /*
        List<Serie> series = new ArrayList<>();
        series = datosSeries.stream()
                .map(d -> new Serie(d))   // CADA tipo de dato serie se convertir en nuevo Serie
                .collect(Collectors.toList());
         */

        // Para mostrar desde la base de datos
        // modulo 3.2 se comenta esta lista para volverla una varible global para usar esta lista en buscarEpisodioPorSerie()
        // mas arriba para no crear otra lista duplicando datos, para eso se declara al principio de las varibles globales
//        List<Serie> series = repositorio.findAll();  // estos metodos como findAll() se encuentran en la docmunetación de spring Data jpa
        series = repositorio.findAll();

        // ordenar las series por genero
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);

    }


        /*Modulo 4.1.3 crear metodo*/
        private void buscarSeriesPorTitulo(){
            System.out.println("Escribe el nombre de la serie de que desea buscar ");
            var nombreSerie = teclado.nextLine();

            /*Modulo 4.1.5 crear una lista para almacemar esas series series*/
            /* Modulo 5.3.4 se comenta es lista porque se paso a una varible global
            se coloco la intanciación de la variable en principio, para poder usarla en
            el metodo  buscarTop5Episodios()
             */
            serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);

           /* el tratamiento normal para los datos cuando se usa optional*/
           if(serieBuscada.isPresent()){
                System.out.println("La serie buscada es : "  + serieBuscada.get());
           }else {
                 System.out.println("Serie no encontrada ");
        }

        }


        private void buscarTop5Series(){
            List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
            topSeries.forEach(s ->
                    System.out.println("Series: " + s.getTitulo() +  "  Evaluación : " + s.getEvaluacion()));
        }

        /*Modulo 4.4.3  */
     private void buscarSeriePorCategoria(){
         System.out.println("Escriba el genero/categoría de la serie que desea buscar");
         var genero = teclado.nextLine();
         /*Modulo 4.3.8
         * va retonarla categoria correcta luego se pasa como argumento en el campo de
         * findByGenero(categoria)  para traer desde la base datos, recorda ese Genero
         *  finByGenero  es  como tenemos mapeado categoria en nuestra clase Serie
         * */
         var categoria = Categoria.fromEspanol(genero);
         List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
         System.out.println("Las series de la categoría " + genero);
         seriesPorCategoria.forEach(System.out::println);

     }

    public void filtrarSeriesPorTemporadaYEvaluacion(){
        System.out.println("¿Filtrar séries con cuántas temporadas? ");
        var totalTemporadas = teclado.nextInt();
        teclado.nextLine();
        System.out.println("¿Com evaluación apartir de cuál valor? ");
        var evaluacion = teclado.nextDouble();
        teclado.nextLine();
        /*Modulo 5.1 ==> interface SerieRepositorio 5.1.2/  se comento para hacerlo mejor con una query native desde
        el codigo con   @Query*/
        //List<Serie> filtroSeries = repositorio.findByTotalTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(totalTemporadas,evaluacion);
        //Modulo 5.1. se implementa el metodo para consulta nativa
        List<Serie> filtroSeries = repositorio.seriesPorTemparadaYEvaluacion(totalTemporadas,evaluacion);
        System.out.println("*** Series filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println(s.getTitulo() + "  - evaluacion: " + s.getEvaluacion()));
    }


//    private void  buscarEpisodiosPorTitulo(){
//        System.out.println("Escribe el nombre del episodio que deseas buscar");
//        var nombreEpisodio = teclado.nextLine();
//        /* Modulo 5.2.4  crear una lista para que nos retorne eso datos del repositorio*/
//        List<Episodio> episodiosEncontrados = repositorio.episodiosPorNombre(nombreEpisodio);
//        /* printf */
//        episodiosEncontrados.forEach(e ->
//                System.out.printf("Serie: %s Temporada %s Episodio %s Evaluación %s\n",
//                        e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion()));
//
//    }
//
//    /* Modulo 5.2.5  agragar la opción 8*/
//    private void buscarTop5Episodios(){
//        buscarSeriesPorTitulo();
//        /* como  buscarSeriesPorTitulo() retorna una lista de la clase optional se
//        * deben tratar los datos por eso if*/
//        if(serieBuscada.isPresent()){
//            Serie serie = serieBuscada.get();
//            List<Episodio> topEpisodios = repositorio.top5Episodios(serie);
//            topEpisodios.forEach(e ->
//                    System.out.printf("Serie: %s - Temporada %s - Episodio %s - Evaluación %s\n",
//                            e.getSerie().getTitulo(), e.getTemporada(), e.getTitulo(), e.getEvaluacion()));
//
//        }
//    }
private void  buscarEpisodiosPorTitulo(){
    System.out.println("Escribe el nombre del episodio que deseas buscar");
    var nombreEpisodio = teclado.nextLine();
    List<Episodio> episodiosEncontrados = repositorio.episodiosPorNombre(nombreEpisodio);
    episodiosEncontrados.forEach(e ->
            System.out.printf("Serie: %s Temporada %s Episodio %s Evaluación %s\n",
                    e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion()));

}

    private void buscarTop5Episodios(){
        buscarSeriesPorTitulo();
        if(serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = repositorio.top5Episodios(serie);
            topEpisodios.forEach(e ->
                    System.out.printf(" Serie:  %s - Temporada %s -  Episodio  %s -  Evaluación %s \n",
                            e.getSerie().getTitulo(), e.getTemporada(), e.getTitulo(), e.getEvaluacion()));

        }
    }

}

