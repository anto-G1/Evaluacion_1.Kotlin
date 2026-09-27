class Box(
    val numero: Int
) {

    var estado: EstadoBox = EstadoBox.Libre
        private set

    fun iniciarProceso(
        motivo: String
    ): Boolean {

        return when (estado) {

            EstadoBox.Libre -> {
                estado = EstadoBox.EnProceso(motivo)
                true
            }

            is EstadoBox.EnAtencion -> {
                estado = EstadoBox.EnProceso(motivo)
                true
            }

            is EstadoBox.EnProceso -> {
                false
            }

            is EstadoBox.FueraDeServicio -> {
                false
            }
        }
    }

    fun asignar(
        paciente: Paciente
    ): Boolean {

        return if (estado is EstadoBox.EnProceso) {

            estado =
                EstadoBox.EnAtencion(paciente)

            true

        } else {

            false
        }
    }

    fun liberar(): Boolean {

        return if (estado is EstadoBox.EnProceso) {

            estado = EstadoBox.Libre

            true

        } else {

            false
        }
    }

    fun fueraDeServicio(
        motivo: String
    ): Boolean {

        return if (estado is EstadoBox.Libre) {

            estado =
                EstadoBox.FueraDeServicio(motivo)

            true

        } else {

            false
        }
    }

    fun mostrarEstado() {

        when (val estadoActual = estado) {

            EstadoBox.Libre -> {
                println(
                    "Box $numero: ${EstadoBox.Libre.mensaje}"
                )
            }

            is EstadoBox.EnAtencion -> {
                println(
                    "Box $numero: atendiendo a " +
                            estadoActual.paciente.nombre
                )
            }

            is EstadoBox.EnProceso -> {
                println(
                    "Box $numero: ${estadoActual.motivo}"
                )
            }

            is EstadoBox.FueraDeServicio -> {
                println(
                    "Box $numero fuera de servicio: " +
                            estadoActual.motivo
                )
            }
        }
    }
}