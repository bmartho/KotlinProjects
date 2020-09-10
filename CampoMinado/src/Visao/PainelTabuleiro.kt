package Visao

import Modelo.Tabuleiro
import java.awt.GridLayout
import javax.swing.JPanel

class PainelTabuleiro(tabuleiro: Tabuleiro) : JPanel() {
    init {
        layout = GridLayout(tabuleiro.qnteLinhas, tabuleiro.qnteColunas)
        tabuleiro.forEachCampo {
            val botao = BotaoCampo(it)
            add(botao)
        }
    }
}