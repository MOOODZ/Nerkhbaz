package ir.moodz.sarafkoochooloo.presentation.main

sealed interface MainAction {
    data object OnPullDownRefresh: MainAction
}