import java.time.LocalDateTime

class Canino(
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

        var costo =
            12_000.0 * (minutos / 60.0)

        if (tipoDueno == TipoDueno.CONVENIO) {
            costo *= 0.80
        }

        return costo
    }
}