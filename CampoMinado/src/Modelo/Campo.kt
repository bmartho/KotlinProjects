package Modelo

enum class CampoEvento { ABERTURA, MARCACAO, DESMARCACAO, EXPLOSAO, REINICIALIZACAO }

data class Campo(val linha: Int, val coluna: Int) {
    private val vizinhos = ArrayList<Campo>()
    private val callbacks = ArrayList<(Campo, CampoEvento) -> Unit>()

    var marcado: Boolean = false
    var aberto: Boolean = false
    var minado: Boolean = false

    val desmarcado: Boolean get() = !marcado
    val fechado: Boolean get() = !aberto
    val seguro: Boolean get() = !minado
    val objetivoAlcancado: Boolean get() = seguro && aberto || marcado && minado
    val qntDeVizinhosMinados: Int get() = vizinhos.filter { it.minado }.size
    val vizinhancaSegura: Boolean get() = vizinhos.map { it.seguro }.reduce { resultado, seguro -> resultado && seguro }


    fun addVizinho(campo: Campo) {
        vizinhos.add(campo)
    }

    fun onEvent(callBack: (Campo, CampoEvento) -> Unit) {
        callbacks.add(callBack)
    }

    fun abrir() {
        if (fechado) {
            aberto = true;
            if (minado) {
                callbacks.forEach { it(this, CampoEvento.EXPLOSAO) }
            } else {
                callbacks.forEach { it(this, CampoEvento.ABERTURA) }
                vizinhos.filter { it.fechado && it.seguro && it.vizinhancaSegura }.forEach { it.abrir() }
            }
        }
    }

    fun alterarMarcacao() {
        if (fechado) {
            marcado = !marcado
            val evento = if (marcado) CampoEvento.MARCACAO else CampoEvento.DESMARCACAO
            callbacks.forEach { it(this, evento) }
        }
    }

    fun minar() {
        minado = true
    }

    fun reiniciar() {
        aberto = false
        minado = false
        marcado = false
        callbacks.forEach { it(this, CampoEvento.REINICIALIZACAO) }
    }
}
