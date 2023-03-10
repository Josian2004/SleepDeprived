package fhict.sm.sleepdeprived.ui.infoscreen

import fhict.sm.sleepdeprived.domain.SleepReason

data class TipModel (
    val tipTitle: String,
    val tipImage: Int,
    val sleepReason: SleepReason
        ) {
    private val tipParagraphs: ArrayList<String> = ArrayList()

    fun getTipParagraphs(): ArrayList<String> {
        return tipParagraphs
    }

    fun addTipParagraph(paragraphs: Array<String>): Unit {
        paragraphs.forEach { paragraph ->
            tipParagraphs.add(paragraph)
        }
    }
 }