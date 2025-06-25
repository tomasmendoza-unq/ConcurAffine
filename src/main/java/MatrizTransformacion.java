public class MatrizTransformacion {

    public double[][] matrizDeTraslacion(int tx, int ty){
        double[][] matriz = {
                {1, 0, tx},
                {0, 1, ty},
                {0, 0, 1}
        };

        return matriz;
    }

    public double[][] matrizRescalado(int sx, int sy){
        double[][] matriz = {
                {sx, 0, 0},
                {0, sy, 0},
                {0, 0, 1}
        };

        return matriz;
    }
    public double[][] matrizReflection(){
        double[][] matriz = {
                {1, 0, 0},
                {0, -1, 0},
                {0, 0, 1}
        };

        return matriz;
    }
    public double[][] matrizShear(int d){
        double[][] matriz = {
                {1, d, 0},
                {0, 1, 0},
                {0, 0, 1}
        };

        return matriz;
    }

    public double[][] calculoDeInversa(double[][] matriz){
        double a = matriz[0][0], b = matriz[0][1], c = matriz[0][2];
        double d = matriz[1][0], e = matriz[1][1], f = matriz[1][2];

        double det = a * e - b * d;

        double[][] inversa = {
                { e / det, -b / det, (b*f - c*e) / det },
                {-d / det,  a / det, (c*d - a*f) / det },
                { 0,        0,        1 }
        };

        return inversa;
    }

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
            default:
                return null;
        }
    }
}
