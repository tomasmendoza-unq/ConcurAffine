package app.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TimerLogger {

    private final String csv;
    private long start;
    private String ultimaImagen = "";
    private PrintWriter writer;
    private List<Long> tiempos;

    public TimerLogger(String csv) {
        this.csv = csv;
        this.tiempos = new ArrayList<>();

        try {
            this.writer = new PrintWriter(new FileWriter(csv, true)); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        this.start = System.currentTimeMillis();
    }


    private void logEncabezadoSiCambioImagen(String nuevaImagen) {
        if (!nuevaImagen.equals(ultimaImagen)) {
            writer.println();
            writer.println("# Imagen: " + nuevaImagen);
            writer.println("workers,buffer,img,opcion,tiempo");
            ultimaImagen = nuevaImagen;
        }
    }

    private String extraerNombreArchivo(String path) {
        int lastSlash = path.lastIndexOf('/');
        String fileName = lastSlash >= 0 ? path.substring(lastSlash + 1) : path;
        return fileName;
    }

    public void close() {
        if (writer != null) {
            writer.close();
        }
    }

    public void registrarTiempo() {
        long end = System.currentTimeMillis();
        long duration = end - start;
        tiempos.add(duration);
    }

    private long promedio() {
        List<Long> tiempos = this.tiempos.subList(3, 13);

        long promedio = tiempos.stream().mapToLong(Long::longValue).sum()/10;

        return promedio;
    }

    public void registrarPromedio(int iThreads, int bufferSize, String imagen, int opcion) {

        long promedio = this.promedio();

        String nombreImagen = extraerNombreArchivo(imagen);
        logEncabezadoSiCambioImagen(nombreImagen);

        writer.println(opcion + "," + iThreads + "," + bufferSize + "," + nombreImagen + "," + promedio);
        System.out.println("Imagen: "  + nombreImagen + ", Opción: " + opcion + ", Threads: " + iThreads + ", Buffer: " + bufferSize + " => Promedio: " + promedio + " ms");

        this.tiempos.clear();
    }
}
