package Modelo

import java.util.*
import kotlin.collections.ArrayList

enum class TabuleiroEvento { VITORIA, DERROTA }

class Tabuleiro(val qnteLinhas: Int, val qnteColunas: Int, val qnteMinas: Int) {
    private val campos = ArrayList<ArrayList<Campo>>()
    private val callbacks = ArrayList<(TabuleiroEvento) -> Unit>()

    init {
        gerarCampos()
        associarVizinhos()
        sortearMinas()
    }

    private fun sortearMinas() {
        val gerador = Random()

        var linhaSorteada = -1
        var colunaSorteada = -1
        var quantidade = 0

        while (quantidade < qnteMinas) {
            linhaSorteada = gerador.nextInt(qnteLinhas-1)
            colunaSorteada = gerador.nextInt(qnteColunas-1)

            val campoSorteado = campos[linhaSorteada][colunaSorteada]
            if (campoSorteado.seguro) {
                campoSorteado.minar()
                quantidade++
            }
        }
    }

    private fun gerarCampos() {
        for (linha in 0 until qnteLinhas) {
            campos.add(ArrayList())
            for (coluna in 0 until qnteColunas) {
                val novoCampo = Campo(linha, coluna)
                novoCampo.onEvent(this::verificaVitoria)
                campos[linha].add(novoCampo)
            }
        }
    }

    private fun associarVizinhos() {
        forEachCampo { associarVizinhos(it) }
    }

    private fun associarVizinhos(campo: Campo) {
        val (linha, coluna) = campo
        val linhas = arrayOf(linha - 1, linha, linha + 1)
        val colunas = arrayOf(coluna - 1, coluna, coluna + 1)

        linhas.forEach { l ->
            colunas.forEach { c ->
                val actual = campos.getOrNull(l)?.getOrNull(c)
                actual?.takeIf { campo != it }?.let { campo.addVizinho(it) }
            }
        }
    }

    fun forEachCampo(callback: (Campo) -> Unit) {
        campos.forEach { lol -> lol.forEach(callback) }
    }

    private fun objetivoAlcancado(): Boolean {
        var jogadorGanhou = true
        forEachCampo { if (!it.objetivoAlcancado) jogadorGanhou = false }
        return jogadorGanhou
    }

    private fun verificaVitoria(campo: Campo, evento: CampoEvento) {
        if (evento == CampoEvento.EXPLOSAO) {
            callbacks.forEach { it(TabuleiroEvento.DERROTA) }
        } else if (objetivoAlcancado()) {
            callbacks.forEach { it(TabuleiroEvento.VITORIA) }
        }
    }

    fun onEvent(callBack: (TabuleiroEvento) -> Unit) {
        callbacks.add(callBack)
    }

    fun reiniciar(){
        forEachCampo { it.reiniciar() }
        sortearMinas()
    }
}