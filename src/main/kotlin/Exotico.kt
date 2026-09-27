import java.time.LocalDateTime

class Exotico(
    codigoAtencion: String,
    nombre: String,
    especie: String,
    fechaIngreso: LocalDateTime,
    tipoDueno: TipoDueno,
    val silvestre: Boolean
) : Paciente(
    codigoAtencion,
    nombre,
    especie,
    fechaIngreso,
    tipoDueno
) {

    override fun costoPorTipo(
        minutos: Long
    ): Double {

        var costo =
            20_000.0 * (minutos / 60.0)

        if (silvestre) {
            costo *= 1.30
        }

        return costo
    }
}