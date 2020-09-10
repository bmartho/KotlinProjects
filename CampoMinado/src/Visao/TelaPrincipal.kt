package Visao

import Modelo.Tabuleiro
import Modelo.TabuleiroEvento
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.SwingUtilities

fun main() {
    TelaPrincipal()
}

class TelaPrincipal : JFrame() {
    private val tabuleiro = Tabuleiro(qnteLinhas = 16, qnteColunas = 30, qnteMinas = 40)
    private val painelTabuleiro = PainelTabuleiro(tabuleiro)

    init {
        tabuleiro.onEvent(this::mostrarResultado)
        add(painelTabuleiro)
        setSize(630, 438)
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Campo minado"
        isVisible = true
    }

    fun mostrarResultado(evento: TabuleiroEvento) {
        SwingUtilities.invokeLater {
            val msg = when (evento) {
                TabuleiroEvento.VITORIA -> "uhul"
                TabuleiroEvento.DERROTA -> "Perdeu"
            }
            JOptionPane.showMessageDialog(this, msg)
            tabuleiro.reiniciar()
            painelTabuleiro.repaint()
            painelTabuleiro.validate()
        }
    }
}