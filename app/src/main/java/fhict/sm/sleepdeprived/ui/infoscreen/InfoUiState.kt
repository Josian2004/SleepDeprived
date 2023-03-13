package fhict.sm.sleepdeprived.ui.infoscreen

data class InfoUiState(
    val characterText: String = "",
    val recommendedTips: List<TipModel> = emptyList(),
    val otherTips: List<TipModel> = emptyList(),
    val tooMuchCaffeine: Boolean = false,

    val showPopUp: Boolean = false,
    val selectedTip: TipModel? = null
) {
}