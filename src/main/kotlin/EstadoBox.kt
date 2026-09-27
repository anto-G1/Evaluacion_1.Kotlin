sealed class EstadoBox {

    object Libre : EstadoBox() {
        val mensaje = "Box libre y disponible"
    }

    data class EnAtencion(
        val paciente: Paciente
    ) : EstadoBox()

    data class EnProceso(
        val motivo: String
    ) : EstadoBox()


    data class FueraDeServicio(
        val motivo: String
    ) : EstadoBox()
}