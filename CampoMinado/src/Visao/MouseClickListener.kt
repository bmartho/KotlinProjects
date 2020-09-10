package Visao

import Modelo.Campo
import java.awt.event.MouseEvent
import java.awt.event.MouseEvent.*
import java.awt.event.MouseListener

class MouseClickListener (
    val campo : Campo,
    val onBotaoEsquerdo : (Campo) -> Unit,
    val onBotaoDireito : (Campo) -> Unit,
): MouseListener {
    override fun mouseClicked(e: MouseEvent?) {

    }

    override fun mousePressed(e: MouseEvent?) {
        when(e?.button){
            BUTTON1 -> onBotaoEsquerdo(campo)
            BUTTON2, BUTTON3 -> onBotaoDireito(campo)
        }
    }

    override fun mouseReleased(e: MouseEvent?) {

    }

    override fun mouseEntered(e: MouseEvent?) {

    }

    override fun mouseExited(e: MouseEvent?) {

    }
}
