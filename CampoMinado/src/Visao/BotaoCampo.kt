package Visao

import Modelo.Campo
import Modelo.CampoEvento
import javafx.scene.paint.Color.GREY
import java.awt.Color
import java.awt.Font.BOLD
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.SwingUtilities

private val COLOR_NORMAL = Color(184, 184, 184)
private val COLOR_MARCACAO = Color(8, 179, 247)
private val COLOR_EXPLOSAO = Color(189, 66, 68)
private val COLOR_VERDE = Color(0, 100, 0)

class BotaoCampo(val campo: Campo) : JButton() {
    init {
        font = font.deriveFont(BOLD)
        background = COLOR_NORMAL
        isOpaque = true
        border = BorderFactory.createBevelBorder(
            0
        )
        addMouseListener(MouseClickListener(campo, { it.abrir() }, { it.alterarMarcacao() }))
        campo.onEvent(this::aplicarEstilo)
    }

    fun aplicarEstilo(campo: Campo, campoEvento: CampoEvento) {
        when (campoEvento) {
            CampoEvento.EXPLOSAO -> explodiu()
            CampoEvento.ABERTURA -> aberto()
            CampoEvento.MARCACAO -> marcado()
            else -> padrao()
        }
        SwingUtilities.invokeLater {
            repaint()
            revalidate()
        }
    }

    private fun padrao() {
        background = COLOR_NORMAL
        text = ""
        border = BorderFactory.createBevelBorder(0)
    }

    private fun marcado() {
        background = COLOR_MARCACAO
        foreground = Color.BLACK
        text = "M"
    }

    private fun aberto() {
        background = COLOR_NORMAL
        border = BorderFactory.createLineBorder(Color.GRAY)

        foreground = when (campo.qntDeVizinhosMinados) {
            1 -> COLOR_VERDE
            2 -> Color.blue
            3 -> Color.yellow
            4, 5, 6 -> Color.red
            else -> Color.pink
        }

        text = if(campo.qntDeVizinhosMinados > 0 ) campo.qntDeVizinhosMinados.toString() else ""
    }

    private fun explodiu() {
        background = COLOR_EXPLOSAO
        text = "X"
    }
}