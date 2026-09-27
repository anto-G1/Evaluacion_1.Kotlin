import kotlinx.coroutines.delay

class PetCareService {

    private val boxes =
        mutableListOf<Box>()

    private val historial =
        mutableListOf<Ticket>()

    private val pacientesAtendidos =
        mutableListOf<Paciente>()


    private var secuenciaTicket = 1

    private var recaudacionTotal = 0.0

    private var recaudacionCanino = 0.0

    private var recaudacionFelino = 0.0

    private var recaudacionExotico = 0.0


    init {

        for (numero in 1..10) {

            boxes.add(
                Box(numero)
            )
        }
    }


    suspend fun registrarEntrada(
        paciente: Paciente
    ): Boolean {

        // Busca el primer box que se encuentre libre.
        val box = boxes.find {
            it.estado is EstadoBox.Libre
        }


        if (box == null) {

            println(
                "Sistema sin capacidad: no hay boxes libres."
            )

            return false
        }


        if (
            !box.iniciarProceso(
                "Registrando entrada"
            )
        ) {

            println(
                "No fue posible iniciar el registro de entrada."
            )

            return false
        }


        box.mostrarEstado()

// Simula los 3 segundos de comunicación con el sensor.
        delay(3_000)


        if (!box.asignar(paciente)) {

            println(
                "No fue posible asignar el paciente al box."
            )

            return false
        }


        println(
                    "${paciente.codigoAtencion} - " +
                    "${paciente.nombre} (${paciente.especie}) " +
                    "asignado al box ${box.numero}."
        )


        if (paciente is Exotico) {

            if (paciente.silvestre) {

                println(
                    "Animal silvestre: Si"
                )

            } else {

                println(
                    "Animal silvestre: No"
                )
            }
        }


        return true
    }


    suspend fun registrarSalida(
        codigo: String,
        minutos: Long
    ): Ticket? {


        val box = boxes.find {

            when (
                val estadoActual = it.estado
            ) {

                is EstadoBox.EnAtencion -> {
                    estadoActual
                        .paciente
                        .codigoAtencion == codigo
                }

                else -> {
                    false
                }
            }
        }


        if (box == null) {

            println(
                "Paciente no encontrado: $codigo"
            )

            return null
        }


        val estadoActual = box.estado


        val paciente =
            if (
                estadoActual is EstadoBox.EnAtencion
            ) {

                estadoActual.paciente

            } else {

                return null
            }


        if (minutos < 0) {

            println(
                "Error: el tiempo no puede ser negativo."
            )

            return null
        }


        if (
            !box.iniciarProceso(
                "Calculando tarifa"
            )
        ) {

            println(
                "No fue posible iniciar la salida."
            )

            return null
        }


        box.mostrarEstado()


        delay(6_500)


        val monto =
            paciente.calcularMonto(minutos)

// Un felino con menos de 20 minutos puede tener monto $0 sin que se considere un error de tarifa.
        val felinoGratis =
            paciente is Felino &&
                    minutos < 20


        if (
            monto <= 0.0 &&
            !felinoGratis
        ) {

            println(
                "Error de datos: tarifa inválida."
            )

            box.asignar(paciente)

            return null
        }


        val tipo =
            when (paciente) {

                is Canino -> {
                    "Canino"
                }

                is Felino -> {
                    "Felino"
                }

                is Exotico -> {
                    "Exotico"
                }

                else -> {
                    "Paciente"
                }
            }


        val ticket = Ticket(
            secuenciaTicket,
            tipo,
            paciente.codigoAtencion,
            minutos,
            monto
        )


        secuenciaTicket++


        historial.add(ticket)

        pacientesAtendidos.add(paciente)


        recaudacionTotal += monto


        when (paciente) {

            is Canino -> {
                recaudacionCanino += monto
            }

            is Felino -> {
                recaudacionFelino += monto
            }

            is Exotico -> {
                recaudacionExotico += monto
            }
        }


        box.liberar()


        println(
            "Ticket ${ticket.numero} emitido. " +
                    "Monto: $${ticket.montoPagado}"
        )


        return ticket
    }


    fun boxesDisponibles(): Int {

        val libres =
            boxes.filter {
                it.estado is EstadoBox.Libre
            }

        return libres.size
    }


    fun clientesConvenio():
            List<Paciente> {

        return pacientesAtendidos.filter {

            it.tipoDueno ==
                    TipoDueno.CONVENIO
        }
    }


    fun ingresoPromedio(): Double {

        if (historial.isEmpty()) {

            return 0.0
        }

        return recaudacionTotal /
                historial.size
    }


    fun codigosFinalizados():
            List<String> {

        return historial.map {

            it.codigoAtencion
        }
    }


    fun pacienteMayorTiempo(): String {

        if (historial.isEmpty()) {

            return "Sin pacientes atendidos"
        }


        val ticketMayor = historial.reduce { mayor, ticket ->

            if (ticket.minutos > mayor.minutos) {
                ticket
            } else {
                mayor
            }
        }


        val paciente =
            pacientesAtendidos.find {

                it.codigoAtencion ==
                        ticketMayor.codigoAtencion
            }


        return if (paciente != null) {

            "${paciente.nombre} " +
                    "(${paciente.codigoAtencion})"

        } else {

            ticketMayor.codigoAtencion
        }
    }


    fun tipoMayorIngreso(): String {

        if (historial.isEmpty()) {

            return "Sin datos"
        }


        var tipo = "Canino"

        var mayor =
            recaudacionCanino


        if (
            recaudacionFelino > mayor
        ) {

            tipo = "Felino"

            mayor =
                recaudacionFelino
        }


        if (
            recaudacionExotico > mayor
        ) {

            tipo = "Exotico"
        }


        return tipo
    }


    fun reporteCierre() {

        println(
            "\n=== CIERRE DE TURNO PETCARE ==="
        )


        historial.forEach { ticket ->

            println(
                "Ticket ${ticket.numero} | " +
                        "${ticket.tipo} | " +
                        "${ticket.codigoAtencion} | " +
                        "${ticket.minutos} min | " +
                        "$${ticket.montoPagado}"
            )
        }


        println(
            "Total recaudado: " +
                    "$$recaudacionTotal"
        )


        println(
            "Pacientes atendidos: " +
                    historial.size
        )


        println(
            "Ingreso promedio: " +
                    "$${ingresoPromedio()}"
        )


        println(
            "Tipo con mayor ingreso: " +
                    tipoMayorIngreso()
        )


        println(
            "Recaudacion Canino: " +
                    "$$recaudacionCanino"
        )


        println(
            "Recaudacion Felino: " +
                    "$$recaudacionFelino"
        )


        println(
            "Recaudacion Exotico: " +
                    "$$recaudacionExotico"
        )


        println(
            "Boxes disponibles: " +
                    boxesDisponibles()
        )
    }
}