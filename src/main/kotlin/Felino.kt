import java.time.LocalDateTime

class Felino(
    codigoAtencion: String,
    nombre: String,
    especie: String,
    fechaIngreso: LocalDateTime,
    tipoDueno: TipoDueno
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

        if (minutos < 20) {
            return 0.0
        }

        return 9_000.0 * (minutos / 60.0)
    }
}