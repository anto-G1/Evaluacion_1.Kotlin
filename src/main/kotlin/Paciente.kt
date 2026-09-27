import java.time.LocalDateTime

open class Paciente(
    val codigoAtencion: String,
    val nombre: String,
    val especie: String,
    val fechaIngreso: LocalDateTime,
    val tipoDueno: TipoDueno
) {

    // Valida que el código tenga dos letras, dos números y dos letras.
    init {
        require(
            codigoAtencion.matches(
                Regex("[A-Z]{2}\\d{2}[A-Z]{2}")
            )
        ) {
            "Codigo de atencion invalido"
        }
    }

    // Metodo que será sobrescrito por cada tipo de paciente.
    protected open fun costoPorTipo(
        minutos: Long
    ): Double {
        return 0.0
    }

    // Calcula el costo, agrega IVA y aplica beneficio municipal si corresponde.
    fun calcularMonto(minutos: Long): Double {

        val costo = costoPorTipo(minutos)

        val montoConIva = costo * 1.19

        return if (tipoDueno == TipoDueno.MUNICIPAL) {
            montoConIva * 0.50
        } else {
            montoConIva
        }
    }
}