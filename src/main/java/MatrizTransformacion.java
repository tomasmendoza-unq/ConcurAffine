public class MatrizTransformacion {

    /**
     * Matriz que define la traslación de la imagen
     * @param tx desplazamiento en X
     * @param ty desplazamiento en Y
     * @return Una matriz desplazada en función de X e Y
     */
    public double[][] matrizDeTraslacion(int tx, int ty){
        double[][] matriz = {
                {1, 0, tx},
                {0, 1, ty},
                {0, 0, 1}
        };

        return matriz;
    }

    /**
     * Matriz que define el rescalado de una imagen
     * @param sx Cuanto crece/decrece en horizontalmente
     * @param sy Cuanto crece/decrece en verticalmente
     * @return Una matriz rescalada en función de X e Y
     */
    public double[][] matrizRescalado(int sx, int sy){
        double[][] matriz = {
                {sx, 0, 0},
                {0, sy, 0},
                {0, 0, 1}
        };

        return matriz;
    }

    /**
     * Matriz que define la transformación de reflexión
     * @return Una matriz que es el reflejo de la original
     */
    public double[][] matrizReflection(){
        double[][] matriz = {
                {1, 0, 0},
                {0, -1, 0},
                {0, 0, 1}
        };

        return matriz;
    }

    /**
     * Matriz que define la operación de deformación angular (en busca de la traducción)
     * @param d desplazamiento de la imagen
     * @return Una matriz con su punto más alto desplazado pero con la misma proporción
     */
    public double[][] matrizShear(int d){
        double[][] matriz = {
                {1, d, 0},
                {0, 1, 0},
                {0, 0, 1}
        };

        return matriz;
    }

    /**
     * Matriz que define la operación de rotación.
     * @param rotacion Un número en grados para indicar cuanto rota la imagen
     * @return La imagen rotada tanto grados indicados hacia la... izquierda
     */
    public double[][] matrizRotacion(double rotacion) {
        double coseno = Math.cos(Math.toRadians(rotacion));
        double seno = Math.sin(Math.toRadians(rotacion));

        double[][] matriz = {
            {coseno, (-1*seno), 0},
            {seno,   coseno,    0},
            {0,      0,         1}
        };

        return matriz;
    }

    /**
     * Calculo para la matriz inversa de las matrices de transformación
     * @param matriz Una matriz para conseguir su inversa
     * @return La matriz inversa de la dada
     */
    public double[][] calculoDeInversa(double[][] matriz){
        double a = matriz[0][0], b = matriz[0][1], c = matriz[0][2];
        double d = matriz[1][0], e = matriz[1][1], f = matriz[1][2];

        double det1 = (a * e - b * d);
        double det2 = (b * d - a * e);

        double ai = e / det1;
        double bi = b / det2;
        double ci = (c * e - b * f) / det2;
        double di = d / det2;
        double ei = a / det1;
        double fi = (c * d - a * f) / det1;
        
        double[][] inversa = {
                {ai, bi, ci},
                {di, ei, fi},
                {0,  0,  1}
        };

        return inversa;
    }

    /**
     * Método para conseguir un tipo específico de matriz de transformación
     * 
     * @param opcion Un número entre 1 y 5, siendo:<br/>
     * 1. La Matriz de traslación.<br/>
     * 2. La Matriz de rescalado.<br/>
     * 3. La Matriz de reflexión.<br/>
     * 4. La Matriz de deformación angular.<br/>
     * 5. La Matriz de rotación.
     * @return Una matriz en base a las opciones
     */
    public double[][] getMatriz(int opcion) {
        switch(opcion) {
            case 1:
                return matrizDeTraslacion(50, 50); // MODIFICAR PARA QUE SE PARAMETRICE
            case 2:
                return matrizRescalado(3,3);
            case 3:
                return matrizReflection();
            case 4:
                return matrizShear(2);
            case 5:
                return matrizRotacion(45);
            default:
                return null;
        }
    }

    public double[] transformar(double x, double y, double[][] m) {
        double xT = m[0][0] * x + m[0][1] * y + m[0][2];
        double yT = m[1][0] * x + m[1][1] * y + m[1][2];
        return new double[] {xT, yT};
    }
}
