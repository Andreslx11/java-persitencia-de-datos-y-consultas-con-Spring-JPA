package com.aluracursos.screenmatch.principal;



import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;


import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private   String API_KEY;

    // para traer la api key de la variable de entorno
    public String API_KEY() {
        this.API_KEY= System.getenv("API_KEY_OMDB");
        return API_KEY;
    }

    // se crea para poder hacer uso instarciar por decirlo
    // al atributo private SerieRepository repository
    // que se creo para que spring boot haga la inyección dependencias
    // para interface SerieRepository
    private  SerieRepository repositorio;

    // constructor para lo de inyección de dependencias de SerieRepository
    public Principal(SerieRepository repository) {
        this.repositorio = repository; // repository va ser igual ese repository que estamos pasando en la clase ScreenmatchApplication
    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                                  
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
        DatosSerie datosSerie = getDatosSerie();
        List<DatosTemporadas> temporadas = new ArrayList<>();

        for (int i = 1; i <= datosSerie.totalTemporadas(); i++) {
            var json = consumoApi.obtenerDatos(URL_BASE + datosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        // se comento por que ya no queremos una lista queremos guardarlos en la base de datos
        //datosSeries.add(datos);

        // para guardar los datos en la base de datos se instacia el objeto
        Serie serie = new Serie(datos);  // se pasan los datos de la busqueda de la API
        repositorio.save(serie); // Guardar ese serie que acavamos de guardar  en el formato de nuestra de serie
                                // estos metodod como findAll() se encuentran en la docmunetación de spring Data jpa
        System.out.println(datos);
    }

    private void mostrarSeriesBuscadas() {
        // ya no queremos solo imprimir este datos series, sino realizar esa transformarción
        // al tipo de dato Serie que creamos
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
        List<Serie> series =repositorio.findAll();  // estos metodod como findAll() se encuentran en la docmunetación de spring Data jpa


        // ordenar las series por genero
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

}

