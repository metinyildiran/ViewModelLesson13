package com.example.viewmodellesson13.data.state

import androidx.annotation.DrawableRes
import com.example.viewmodellesson13.R

sealed class ReactionState(@DrawableRes val reactionResourceId: Int) {
    object Happy : ReactionState(R.drawable._430612_emoticon_laugh_lol_reactions_sad_icon)
    object Sad : ReactionState(R.drawable._430614_emoticon_expression_face_reactions_sad_icon)
    object Pleased : ReactionState(R.drawable._430615_emoticon_face_happy_reactions_smile_icon)
    object Angry : ReactionState(R.drawable._430616_angry_emoji_emoticon_face_reactions_icon)
    object Shocked : ReactionState(R.drawable._430619_emoji_emoticon_face_reactions_wow_icon)
}