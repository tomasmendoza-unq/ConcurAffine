package app.transformaciones.impls;

import app.menu.Parametros;
import app.transformaciones.OpcionTransformacion;

public class Reflection implements OpcionTransformacion {
    @Override
    public Parametros ejecutar() {
        return new Parametros();
    }
}
